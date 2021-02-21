package com.tuware.msbuild.application.query;

import com.google.devtools.build.lib.analysis.AnalysisProtosV2;
import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.io.BazelQuery;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CcBinaryQuery {

    private BazelQuery<Build.QueryResult> packageQuery;
    private BazelQuery<AnalysisProtosV2.CqueryResult> actionQuery;

    public CcBinaryQuery(BazelQuery<Build.QueryResult> packageQuery, BazelQuery<AnalysisProtosV2.CqueryResult> actionQuery) {
        this.packageQuery = packageQuery;
        this.actionQuery = actionQuery;
    }

    List<String> query(String bazelProjectRootPath, String query) throws InterruptedException {

        Build.QueryResult queryResult = packageQuery.query(bazelProjectRootPath, query);

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

    List<String> cquery(String bazelProjectRootPath, String location) throws InterruptedException {

        AnalysisProtosV2.CqueryResult queryResult = actionQuery.query(bazelProjectRootPath, location);

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
