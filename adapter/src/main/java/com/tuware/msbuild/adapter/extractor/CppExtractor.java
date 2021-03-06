package com.tuware.msbuild.adapter.extractor;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.Extractor;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import org.springframework.stereotype.Component;

import java.io.File;
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
                .filter(isCppSourceCode())
                .map(this::getFilePath)
                .collect(Collectors.toList());
        return ProjectSeed.builder()
                .path(Paths.get(getFolderPath(rule.getName())))
                .name(getProjectName(rule.getName()))
                .sourceFileList(sourceFileList)
                .build();
    }

    private boolean isCppRule(Build.Rule rule) {
        return rule.getRuleClass().equals("cc_binary") || rule.getRuleClass().equals("cc_library");
    }

    private Predicate<String> isCppSourceCode() {
        return ruleInput -> !ruleInput.contains("@") && (
                ruleInput.endsWith(".cc") || ruleInput.endsWith(".h") || ruleInput.endsWith(".cpp")
        );
    }

    private String getProjectName(String label) {
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

    private String getFilePath(String label) {
        return label.replace("//", "").replace(":", File.separator);
    }

}
