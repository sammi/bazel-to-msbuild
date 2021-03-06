package com.tuware.msbuild.adapter.extractor;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.Extractor;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class CppExtractor implements Extractor<Build.QueryResult, List<ProjectSeed>> {

    @Override
    public List<ProjectSeed> extract(Build.QueryResult bazelQueryResult) {
        return bazelQueryResult.getTargetList().stream()
                .filter(target -> target.getType().equals(Build.Target.Discriminator.RULE))
                .map(Build.Target::getRule)
                .filter(this::isCppRule)
                .map(this::extractProjectSeedFromCppRule)
                .collect(Collectors.toList());
    }

    private ProjectSeed extractProjectSeedFromCppRule(Build.Rule rule) {
        List<String> sourceFileList = rule.getRuleInputList().stream()
                .filter(isCppSourceFile())
                .map(this::getFileName)
                .collect(Collectors.toList());
        List<String> headerFileList = rule.getRuleInputList().stream()
                .filter(isCppHeaderFile())
                .map(this::getFileName)
                .collect(Collectors.toList());
        return ProjectSeed.builder()
                .folder(Paths.get(getFolderPath(rule.getName())))
                .name(getFileName(rule.getName()))
                .sourceFileList(sourceFileList)
                .headerFileList(headerFileList)
                .build();
    }

    private boolean isCppRule(Build.Rule rule) {
        return
                rule.getRuleClass().equals("cc_binary") ||
                rule.getRuleClass().equals("cc_library") ||
                rule.getRuleClass().equals("cc_test");
    }

    private Predicate<String> isCppSourceFile() {
        return ruleInput -> !ruleInput.contains("@") && (
                ruleInput.endsWith(".cc") || ruleInput.endsWith(".cpp")
        );
    }

    private Predicate<String> isCppHeaderFile() {
        return ruleInput -> !ruleInput.contains("@") && (
                ruleInput.endsWith(".h") || ruleInput.endsWith(".hpp")
        );
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
