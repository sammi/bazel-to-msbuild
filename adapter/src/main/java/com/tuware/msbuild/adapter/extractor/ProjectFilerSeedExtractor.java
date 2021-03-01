package com.tuware.msbuild.adapter.extractor;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.Extractor;
import com.tuware.msbuild.contract.seed.ProjectFilerSeed;

public class ProjectFilerSeedExtractor implements Extractor<Build.QueryResult, ProjectFilerSeed> {

    @Override
    public ProjectFilerSeed extract(Build.QueryResult bazelQResult) {
        return ProjectFilerSeed.builder().build();
    }
}
