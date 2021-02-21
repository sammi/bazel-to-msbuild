package com.tuware.msbuild.adapter;

import com.google.devtools.build.lib.analysis.AnalysisProtosV2;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.BazelQueryAdapter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ActionQueryAdapter implements BazelQueryAdapter<AnalysisProtosV2.CqueryResult> {

    private DefaultApplicationAdapter defaultApplicationAdapter;

    public ActionQueryAdapter(DefaultApplicationAdapter defaultApplicationAdapter) {
        this.defaultApplicationAdapter = defaultApplicationAdapter;
    }

    @Override
    public AnalysisProtosV2.CqueryResult query(String bazelProjectRootPath, String query) throws AdapterException {
        try {
            Process process = defaultApplicationAdapter.startBazelQueryProcess(bazelProjectRootPath, "cquery", query);
            return AnalysisProtosV2.CqueryResult.parseFrom(process.getInputStream());
        } catch (IOException | AdapterException e) {
            throw new AdapterException(e);
        }
    }

    @Override
    public String getSourceFile(AnalysisProtosV2.CqueryResult queryResult) throws AdapterException {
        throw new AdapterException("Not Implemented yet");
    }

}
