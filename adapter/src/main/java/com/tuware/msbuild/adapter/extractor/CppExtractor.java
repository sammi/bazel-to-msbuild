package com.tuware.msbuild.adapter.extractor;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.Extractor;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class CppExtractor implements Extractor<Build.QueryResult, Map<String, ProjectSeed>> {

    @Override
    public Map<String, ProjectSeed> extract(Build.QueryResult bazelQueryResult) {
        Map<String, ProjectSeed> projectSeedMap = new HashMap<>();
        bazelQueryResult.getTargetList().stream()
                .filter(target -> target.getType().equals(Build.Target.Discriminator.RULE))
                .map(Build.Target::getRule)
                .filter(this::isCppRule)
                .forEach(rule -> projectSeedMap.put(
                    rule.getName(),
                    extractProjectSeedFromCppRule(rule)
                ));
        return projectSeedMap;
    }

    private ProjectSeed extractProjectSeedFromCppRule(Build.Rule rule) {
        List<String> sourceFileList = rule.getRuleInputList().stream()
            .filter(isCppSourceCode())
            .map(this::getLabelRelativePath)
        .collect(Collectors.toList());
        return ProjectSeed.builder().sourceFileList(sourceFileList).build();
    }

    private boolean isCppRule(Build.Rule rule) {
        return rule.getRuleClass().equals("cc_binary") || rule.getRuleClass().equals("cc_library");
    }

    private Predicate<String> isCppSourceCode() {
        return ruleInput -> !ruleInput.contains("@") && (
                ruleInput.endsWith(".cc") || ruleInput.endsWith(".h") || ruleInput.endsWith(".cpp")
        );
    }

    private String getLabelRelativePath(String ruleInput) {
        return ruleInput.replace("//", "").replace(":", File.separator);
    }

}
