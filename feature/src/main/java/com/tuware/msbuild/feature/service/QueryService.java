package com.tuware.msbuild.feature.service;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Provider;
import com.tuware.msbuild.contract.adapter.Query;
import com.tuware.msbuild.feature.FeatureException;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Component
public class QueryService {

    private Provider<List<String>> provider;
    private Query<Build.QueryResult> query;

    public QueryService(Provider<List<String>> provider, Query<Build.QueryResult> query) {
        this.provider = provider;
        this.query = query;
    }

    public Build.QueryResult query(Path bazelWorkspaceFolder) throws FeatureException {
        List<String> bazelCommands = provider.provide();
        try {
            return query.query(bazelWorkspaceFolder, bazelCommands);
        } catch (AdapterException e) {
            throw new FeatureException(e);
        }
    }

}
