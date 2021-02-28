package com.tuware.msbuild.adapter.query;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.QueryAdapter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class PackageQueryAdapter implements QueryAdapter<Build.QueryResult> {

    private BazelProcessBuilder bazelProcessBuilder;

    public PackageQueryAdapter(BazelProcessBuilder bazelProcessBuilder) {
        this.bazelProcessBuilder = bazelProcessBuilder;
    }

    @Override
    public Build.QueryResult query(String bazelWorkspaceAbsolutePath, List<String> commands) throws AdapterException {
        try {
            Process process = bazelProcessBuilder.startBazelQueryProcess(bazelWorkspaceAbsolutePath, commands);
            return Build.QueryResult.parseFrom(process.getInputStream());
        } catch (AdapterException | IOException e) {
            throw new AdapterException(e);
        }
    }
}
