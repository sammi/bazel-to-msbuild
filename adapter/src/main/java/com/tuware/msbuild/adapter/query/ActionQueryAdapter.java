package com.tuware.msbuild.adapter.query;

import com.google.devtools.build.lib.analysis.AnalysisProtosV2;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.BazelQueryAdapter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ActionQueryAdapter implements BazelQueryAdapter<AnalysisProtosV2.CqueryResult> {

    private BazelWindowsProcessBuilder bazelWindowsProcessBuilder;

    public ActionQueryAdapter(BazelWindowsProcessBuilder bazelWindowsProcessBuilder) {
        this.bazelWindowsProcessBuilder = bazelWindowsProcessBuilder;
    }

    @Override
    public AnalysisProtosV2.CqueryResult query(String bazelProjectRootPath, String query) throws AdapterException {
        try {
            Process process = bazelWindowsProcessBuilder.startBazelQueryProcess(bazelProjectRootPath, "cquery", query);
            return AnalysisProtosV2.CqueryResult.parseFrom(process.getInputStream());
        } catch (IOException | AdapterException e) {
            throw new AdapterException(e);
        }
    }

}
