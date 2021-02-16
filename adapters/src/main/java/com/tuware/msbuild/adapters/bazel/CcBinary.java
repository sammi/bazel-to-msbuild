package com.tuware.msbuild.adapters.bazel;

import com.google.devtools.build.lib.analysis.AnalysisProtosV2;
import com.google.devtools.build.lib.query2.proto.proto2api.Build;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CcBinary {

    List<String> query(String bazelProjectRootPath, String location) throws IOException {

        Build.QueryResult queryResult = QueryUtils.query(bazelProjectRootPath, location);

        List<String> sourceFileList = new ArrayList<>();

        queryResult.getTargetList().stream()
            .filter(target -> target.getType().equals(Build.Target.Discriminator.RULE))
            .map(Build.Target::getRule)
            .filter(rule -> rule.getRuleClass().equals("cc_binary"))
            .forEach(
                    rule -> rule.getRuleInputList().stream()
                            .filter(ruleInput -> !ruleInput.contains("@"))
                            .forEach(sourceFileList::add)
            );

        return sourceFileList;
    }

    List<String> cquery(String bazelProjectRootPath, String location) throws IOException {

        AnalysisProtosV2.CqueryResult queryResult = QueryUtils.cquery(bazelProjectRootPath, location);

        List<String> sourceFileList = new ArrayList<>();

        queryResult.getResultsList().stream()
                .filter(configuredTarget -> configuredTarget.getTarget().getType().equals(Build.Target.Discriminator.RULE))
                .filter(configuredTarget -> configuredTarget.getTarget().getRule().getRuleClass().equals("cc_binary"))
                .map(configuredTarget -> configuredTarget.getTarget().getRule())
                .forEach(
                        rule -> rule.getRuleInputList().stream()
                                .filter(ruleInput -> !ruleInput.contains("@"))
                                .forEach(sourceFileList::add)
                );

        return sourceFileList;
    }


}
