package com.tuware.msbuild.feature.service;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.Extractor;
import com.tuware.msbuild.contract.adapter.Provider;
import com.tuware.msbuild.contract.seed.ProjectFilerSeed;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import com.tuware.msbuild.contract.seed.SolutionSeed;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.UUID;

@Component
public class ExtractorService {

    private static final String CC_BINARY = "cc_binary";
    private Extractor<Build.QueryResult, ProjectSeed> projectSeedExtractor;
    private Provider<ProjectFilerSeed> projectFilerProvider;

    public ExtractorService(
            Extractor<Build.QueryResult, ProjectSeed> projectSeedExtractor,
            Provider<ProjectFilerSeed> projectFilerProvider
    ) {
        this.projectSeedExtractor = projectSeedExtractor;
        this.projectFilerProvider = projectFilerProvider;
    }

    public ProjectSeed extractProject(Build.QueryResult queryResult) {
        return projectSeedExtractor.extract(queryResult, CC_BINARY);
    }

    public ProjectFilerSeed extractProjectFilter() {
        return projectFilerProvider.provide();
    }

    public SolutionSeed buildSolutionSeed(Path solutionPath, String solutionName, UUID solutionUUID, UUID projectUUID) {

        return SolutionSeed.builder()
                .name(solutionName)
                .location(solutionPath.toFile().getPath())
                .solutionGuid(solutionUUID)
                .projectGuid(projectUUID)
                .build();
    }

}
