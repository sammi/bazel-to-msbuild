package com.tuware.msbuild.adapter.extractor;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.Extractor;
import com.tuware.msbuild.contract.msbuild.property.ConfigurationType;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tuware.msbuild.contract.msbuild.property.ConfigurationType.*;

@Component
public class CppExtractor implements Extractor<Build.QueryResult, List<ProjectSeed>> {

    private static final String CC_IMPORT = "cc_import";
    private static final String CC_LIBRARY = "cc_library";
    private static final String CC_BINARY = "cc_binary";
    private static final String CC_TEST = "cc_test";
    private static final String FILEGROUP = "filegroup";

    @Override
    public List<ProjectSeed> extract(Build.QueryResult bazelQueryResult) {
        return bazelQueryResult.getTargetList().stream()
                .filter(target -> target.getType().equals(Build.Target.Discriminator.RULE))
                .map(Build.Target::getRule)
                .filter(this::isValidRule)
                .map(this::extractProjectSeedFromCppRule)
                .collect(Collectors.toList());
    }

    private boolean isValidRule(Build.Rule rule) {
        return (isGeneratedByWindowsDllLibrary(rule) && isDynamicLibrary(rule)) ||
                !isGeneratedByWindowsDllLibrary(rule) && (isApplication(rule) || isStaticLibrary(rule));
    }

    private boolean isGeneratedByWindowsDllLibrary(Build.Rule rule) {
        Optional<Build.Attribute> generatorFunction = rule.getAttributeList().stream()
                .filter(attribute -> attribute.getName().equals("generator_function"))
                .findFirst();
        return generatorFunction.map(attribute -> attribute.getStringValue().equals("windows_dll_library")).orElse(false);
    }

    private ProjectSeed extractProjectSeedFromCppRule(Build.Rule rule) {

        List<String> sourceFileList = rule.getRuleInputList().stream()
                .filter(this::isCorCppSourceFile)
                .map(this::getFileName)
                .collect(Collectors.toList());

        List<String> headerFileList = rule.getRuleInputList().stream()
                .filter(this::isCorCppHeaderFile)
                .map(this::getFileName)
                .collect(Collectors.toList());

        List<ProjectSeed> dependentProjectSeedList = rule.getRuleInputList().stream().filter(
                this::isDependentPackage
        ).map(
                ruleInput -> ProjectSeed.builder()
                        .folder(Paths.get("$(SolutionDir)", getFolderPath(ruleInput)))
                        .name(getFileName(ruleInput) + ".vcxproj")
                        .build()
        ).collect(Collectors.toList());

        return ProjectSeed.builder()
                .folder(Paths.get(getFolderPath(rule.getName())))
                .name(getFileName(StringUtils.stripEnd(rule.getName(), ".dll")))
                .sourceFileList(sourceFileList)
                .headerFileList(headerFileList)
                .configurationType(getToConfigurationType(rule))
                .dependentProjectSeedList(dependentProjectSeedList)
                .preprocessorDefinitions(extractPreprocessorDefinitions(rule))
                .build();
    }

    private String extractPreprocessorDefinitions(Build.Rule rule) {
        Optional<Build.Attribute> copts = rule.getAttributeList().stream()
                .filter(attribute -> attribute.getName().equals("copts"))
                .findFirst();
        if (copts.isPresent()) {
            Build.Attribute attribute = copts.get();
            List<String> preprocessorDefinitions = attribute.getStringListValueList().stream().map(
                    stringListValue -> StringUtils.stripStart(stringListValue, "/D")
            ).collect(Collectors.toList());
            return String.join(";", preprocessorDefinitions);
        }
        return null;
    }

    private ConfigurationType getToConfigurationType(Build.Rule rule) {
        switch (rule.getRuleClass()) {
            case CC_LIBRARY:
            case CC_IMPORT:
            case FILEGROUP:
                return StaticLibrary;
            case CC_BINARY:
                return isDynamicLibrary(rule) ? DynamicLibrary : Application;

            default:
                return Application;
        }
    }

    private boolean isApplication(Build.Rule rule) {
        return (rule.getRuleClass().equals(CC_BINARY) && !isDynamicLibrary(rule)) || rule.getRuleClass().equals(CC_TEST);
    }

    private boolean isDynamicLibrary(Build.Rule rule) {
        return rule.getAttributeList().stream().filter(attribute -> attribute.getName().equals("linkshared")).anyMatch(Build.Attribute::getExplicitlySpecified);
    }

    private boolean isStaticLibrary(Build.Rule rule) {
        return rule.getRuleClass().equals(CC_LIBRARY);
    }

    private boolean isDependentPackage(String ruleInput) {
        return !ruleInput.startsWith("@") && !isCorCppSourceFile(ruleInput) && !isCorCppHeaderFile(ruleInput);
    }

    private boolean isCorCppSourceFile(String ruleInput) {
        return ruleInput.endsWith(".c") ||
                ruleInput.endsWith(".cc") ||
                ruleInput.endsWith(".cpp") ||
                ruleInput.endsWith(".cxx") ||
                ruleInput.endsWith(".c++") ||
                ruleInput.endsWith(".C");
    }

    private boolean isCorCppHeaderFile(String ruleInput) {
        return ruleInput.endsWith(".h") ||
                ruleInput.endsWith(".hh") ||
                ruleInput.endsWith(".hpp") ||
                ruleInput.endsWith(".hxx") ||
                ruleInput.endsWith(".inc") ||
                ruleInput.endsWith(".inl") ||
                ruleInput.endsWith(".H");
    }

    private String getFileName(String label) {
        String[] pair = label != null ? label.split(":") : new String[]{};
        return pair.length > 1 ? pair[1] : extractProjectName(label);
    }

    private String extractProjectName(String label) {
        return label != null ? label.replace("//", ":") : null;
    }

    private String getFolderPath(String label) {
        String[] pair = label.split(":");
        return pair[0].replace("//", "");
    }

}
