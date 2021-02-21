package com.tuware.msbuild.application.io;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.application.adapter.DefaultApplicationAdapter;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.io.BazelQuery;
import com.tuware.msbuild.contract.io.BazelQueryException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PackageQuery implements BazelQuery<Build.QueryResult> {

    private DefaultApplicationAdapter defaultApplicationAdapter;

    public PackageQuery(DefaultApplicationAdapter defaultApplicationAdapter) {
        this.defaultApplicationAdapter = defaultApplicationAdapter;
    }

    @Override
    public Build.QueryResult query(String bazelProjectRootPath, String query) throws BazelQueryException {
        try {
            Process process = defaultApplicationAdapter.startBazelQueryProcess(bazelProjectRootPath, "query", query);
            return Build.QueryResult.parseFrom(process.getInputStream());
        } catch (AdapterException | IOException e) {
            throw new BazelQueryException(e);
        }
    }
}
