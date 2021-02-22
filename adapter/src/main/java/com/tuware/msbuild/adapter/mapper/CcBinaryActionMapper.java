package com.tuware.msbuild.adapter.mapper;

import com.google.devtools.build.lib.analysis.AnalysisProtosV2;
import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.QueryResultMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CcBinaryActionMapper implements QueryResultMapper<AnalysisProtosV2.CqueryResult> {

    @Override
    public List<String> getCppSourceFiles(AnalysisProtosV2.CqueryResult cqueryResult) {
        List<String> sourceFileList = new ArrayList<>();

        cqueryResult.getResultsList().stream()
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
