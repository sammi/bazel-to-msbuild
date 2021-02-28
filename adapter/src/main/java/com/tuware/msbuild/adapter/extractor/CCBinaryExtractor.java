package com.tuware.msbuild.adapter.extractor;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.Extractor;
import com.tuware.msbuild.contract.input.ProjectInput;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CCBinaryExtractor implements Extractor<Build.QueryResult, ProjectInput> {

    @Override
    public ProjectInput extract(Build.QueryResult bazelQueryResult) {
        List<String> sourceFileList = new ArrayList<>();

        bazelQueryResult.getTargetList().stream()
                .filter(target -> target.getType().equals(Build.Target.Discriminator.RULE))
                .map(Build.Target::getRule)
                .filter(rule -> rule.getRuleClass().equals("cc_binary"))
                .forEach(
                        rule -> rule.getRuleInputList().stream()
                                .filter(ruleInput -> !ruleInput.contains("@"))
                                .forEach(sourceFileList::add)
                );

        String projectUuid = UUID.randomUUID().toString();

        return ProjectInput.builder()
                .cppFileName(sourceFileList.stream().findFirst().orElse(null))
                .projectGuild(projectUuid).build();
    }

}
