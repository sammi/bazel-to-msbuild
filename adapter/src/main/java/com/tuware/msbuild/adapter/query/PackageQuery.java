package com.tuware.msbuild.adapter.query;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Query;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Component
public class PackageQuery implements Query<Build.QueryResult> {

    private BazelProcessBuilder bazelProcessBuilder;

    public PackageQuery() {
        this.bazelProcessBuilder = new BazelProcessBuilder();
    }

    @Override
    public Build.QueryResult query(Path bazelWorkspaceAbsolutePath, List<String> commands) throws AdapterException {
        try {
            Process process = bazelProcessBuilder.startBazelQueryProcess(bazelWorkspaceAbsolutePath.toFile(), commands);
            return Build.QueryResult.parseFrom(process.getInputStream());
        } catch (AdapterException | IOException e) {
            throw new AdapterException(e);
        }
    }
}
