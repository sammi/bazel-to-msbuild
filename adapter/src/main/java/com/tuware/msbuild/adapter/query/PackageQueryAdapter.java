package com.tuware.msbuild.adapter.query;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.BazelQueryAdapter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PackageQueryAdapter implements BazelQueryAdapter<Build.QueryResult> {

    private BazelWindowsProcessBuilder bazelWindowsProcessBuilder;

    public PackageQueryAdapter(BazelWindowsProcessBuilder bazelWindowsProcessBuilder) {
        this.bazelWindowsProcessBuilder = bazelWindowsProcessBuilder;
    }

    @Override
    public Build.QueryResult query(String bazelProjectRootPath, String query) throws AdapterException {
        try {
            Process process = bazelWindowsProcessBuilder.startBazelQueryProcess(bazelProjectRootPath, "query", query);
            return Build.QueryResult.parseFrom(process.getInputStream());
        } catch (AdapterException | IOException e) {
            throw new AdapterException(e);
        }
    }
}