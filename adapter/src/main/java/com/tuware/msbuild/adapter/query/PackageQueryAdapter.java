package com.tuware.msbuild.adapter.query;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.QueryAdapter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PackageQueryAdapter implements QueryAdapter<Build.QueryResult> {

    private BazelWindowsProcessBuilder bazelWindowsProcessBuilder;

    public PackageQueryAdapter(BazelWindowsProcessBuilder bazelWindowsProcessBuilder) {
        this.bazelWindowsProcessBuilder = bazelWindowsProcessBuilder;
    }

    @Override
    public Build.QueryResult query(String bazelProjectRootAbsolutePath, String queryExpression) throws AdapterException {
        try {
            Process process = bazelWindowsProcessBuilder.startBazelQueryProcess(bazelProjectRootAbsolutePath, "query", queryExpression);
            return Build.QueryResult.parseFrom(process.getInputStream());
        } catch (AdapterException | IOException e) {
            throw new AdapterException(e);
        }
    }
}
