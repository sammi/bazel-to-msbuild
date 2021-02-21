package com.tuware.msbuild.application.io;

import com.google.devtools.build.lib.analysis.AnalysisProtosV2;
import com.tuware.msbuild.application.adapter.DefaultApplicationAdapter;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.io.BazelQuery;
import com.tuware.msbuild.contract.io.BazelQueryException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ActionQuery implements BazelQuery<AnalysisProtosV2.CqueryResult> {

    private DefaultApplicationAdapter defaultApplicationAdapter;

    public ActionQuery(DefaultApplicationAdapter defaultApplicationAdapter) {
        this.defaultApplicationAdapter = defaultApplicationAdapter;
    }

    @Override
    public AnalysisProtosV2.CqueryResult query(String bazelProjectRootPath, String query) throws BazelQueryException {
        try {
            Process process = defaultApplicationAdapter.startBazelQueryProcess(bazelProjectRootPath, "cquery", query);
            return AnalysisProtosV2.CqueryResult.parseFrom(process.getInputStream());
        } catch (IOException | AdapterException e) {
            throw new BazelQueryException(e);
        }
    }

}
