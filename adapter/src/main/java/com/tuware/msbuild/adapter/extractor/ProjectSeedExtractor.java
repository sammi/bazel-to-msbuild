package com.tuware.msbuild.adapter.extractor;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.Extractor;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ProjectSeedExtractor implements Extractor<Build.QueryResult, ProjectSeed> {

    @Override
    public ProjectSeed extract(Build.QueryResult bazelQueryResult, String ruleClass) {
        List<String> sourceFileList = new ArrayList<>();

        bazelQueryResult.getTargetList().stream()
                .filter(target -> target.getType().equals(Build.Target.Discriminator.RULE))
                .map(Build.Target::getRule)
                .filter(rule -> rule.getRuleClass().equals(ruleClass))
                .forEach(
                        rule -> rule.getRuleInputList().stream()
                                .filter(ruleInput -> !ruleInput.contains("@"))
                                .forEach(ruleInput -> sourceFileList.add(
                                        ruleInput.replace("//", "")
                                                .replace(":", File.separator)
                                ))
                );

        String projectUuid = UUID.randomUUID().toString();

        return ProjectSeed.builder()
                .cppFileName(sourceFileList.stream().findFirst().orElse(null))
                .projectGuild(projectUuid).build();
    }

}
