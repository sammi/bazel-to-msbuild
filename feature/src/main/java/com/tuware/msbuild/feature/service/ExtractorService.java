package com.tuware.msbuild.feature.service;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.Extractor;
import com.tuware.msbuild.contract.seed.ProjectFilerSeed;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import com.tuware.msbuild.contract.seed.SolutionSeed;
import org.springframework.stereotype.Component;

@Component
public class ExtractorService {

    private Extractor<Build.QueryResult, ProjectSeed> projectSeedExtractor;
    private Extractor<Build.QueryResult, ProjectFilerSeed> projectFilerExtractor;
    private Extractor<Build.QueryResult, SolutionSeed> solutionExtractor;

    public ExtractorService(
            Extractor<Build.QueryResult, ProjectSeed> projectSeedExtractor,
            Extractor<Build.QueryResult, ProjectFilerSeed> projectFilerExtractor,
            Extractor<Build.QueryResult, SolutionSeed> solutionExtractor
    ) {
        this.projectSeedExtractor = projectSeedExtractor;
        this.projectFilerExtractor = projectFilerExtractor;
        this.solutionExtractor = solutionExtractor;
    }

    public ProjectSeed extractProject(Build.QueryResult queryResult) {
        return projectSeedExtractor.extract(queryResult);
    }

    public ProjectFilerSeed extractProjectFilter(Build.QueryResult queryResult) {
        return projectFilerExtractor.extract(queryResult);
    }

    public SolutionSeed extractSolution(Build.QueryResult queryResult) {
        return solutionExtractor.extract(queryResult);
    }

}
