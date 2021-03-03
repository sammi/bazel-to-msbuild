package com.tuware.msbuild.adapter.extractor;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.Extractor;
import com.tuware.msbuild.contract.seed.ProjectFilerSeed;
import org.springframework.stereotype.Component;

@Component
public class ProjectFilerSeedExtractor implements Extractor<Build.QueryResult, ProjectFilerSeed> {

    @Override
    public ProjectFilerSeed extract(Build.QueryResult bazelQResult, String ruleClass) {
        return ProjectFilerSeed.builder().build();
    }
}
