package com.tuware.msbuild.feature.service;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.Extractor;
import com.tuware.msbuild.contract.adapter.Provider;
import com.tuware.msbuild.contract.seed.ProjectFilerSeed;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import com.tuware.msbuild.contract.seed.SolutionSeed;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class ExtractorService {

    private Extractor<Build.QueryResult, ProjectSeed> projectSeedExtractor;
    private Provider<ProjectFilerSeed> projectFilerProvider;

    public ExtractorService(
            Extractor<Build.QueryResult, ProjectSeed> projectSeedExtractor,
            Provider<ProjectFilerSeed> projectFilerProvider
    ) {
        this.projectSeedExtractor = projectSeedExtractor;
        this.projectFilerProvider = projectFilerProvider;
    }

    public ProjectSeed extractProject(Build.QueryResult queryResult, UUID projectUuid) {
        ProjectSeed projectSeed = projectSeedExtractor.extract(queryResult);
        projectSeed.setProjectGuid(projectUuid);
        return projectSeed;
    }

    public ProjectFilerSeed extractProjectFilter() {
        return projectFilerProvider.provide();
    }

    public SolutionSeed buildSolutionSeed(String projectName, UUID solutionUUID, UUID projectUUID) {

        Path projectFilePath = Paths.get(projectName + ".vcxproj");

        return SolutionSeed.builder()
                .name(projectName)
                .projectFilePath(projectFilePath)
                .solutionUuid(solutionUUID)
                .projectUuid(projectUUID)
                .build();
    }

}
