package com.tuware.msbuild.adapter;

import com.google.devtools.build.lib.analysis.AnalysisProtosV2;
import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.BazelQueryAdapter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CcBinaryQueryAdapter {

    private BazelQueryAdapter<Build.QueryResult> packageQuery;
    private BazelQueryAdapter<AnalysisProtosV2.CqueryResult> actionQuery;

    public CcBinaryQueryAdapter(BazelQueryAdapter<Build.QueryResult> packageQuery, BazelQueryAdapter<AnalysisProtosV2.CqueryResult> actionQuery) {
        this.packageQuery = packageQuery;
        this.actionQuery = actionQuery;
    }

    List<String> query(String bazelProjectRootPath, String query) throws AdapterException {

        Build.QueryResult queryResult;
        try {
            queryResult = packageQuery.query(bazelProjectRootPath, query);
        } catch (com.tuware.msbuild.contract.adapter.AdapterException e) {
            throw new AdapterException(e);
        }

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

    List<String> cquery(String bazelProjectRootPath, String location) throws AdapterException {

        AnalysisProtosV2.CqueryResult queryResult;
        try {
            queryResult = actionQuery.query(bazelProjectRootPath, location);
        } catch (com.tuware.msbuild.contract.adapter.AdapterException e) {
            throw new AdapterException(e);
        }

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
