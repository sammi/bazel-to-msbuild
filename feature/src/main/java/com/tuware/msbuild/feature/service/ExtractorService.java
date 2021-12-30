package com.tuware.msbuild.feature.service;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.Extractor;
import com.tuware.msbuild.contract.adapter.Provider;
import com.tuware.msbuild.contract.msbuild.solution.ProjectTypeGuid;
import com.tuware.msbuild.contract.seed.ProjectFilerSeed;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import com.tuware.msbuild.contract.seed.SolutionSeed;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ExtractorService {

    private final Extractor<Build.QueryResult, List<ProjectSeed>> projectSeedExtractor;
    private final Provider<ProjectFilerSeed> projectFilerProvider;

    public ExtractorService(
            Extractor<Build.QueryResult, List<ProjectSeed>> projectSeedExtractor,
            Provider<ProjectFilerSeed> projectFilerProvider
    ) {
        this.projectSeedExtractor = projectSeedExtractor;
        this.projectFilerProvider = projectFilerProvider;
    }

    public List<ProjectSeed> extractProjectSeedList(Build.QueryResult queryResult) {
        List<ProjectSeed> projectSeedList = projectSeedExtractor.extract(queryResult);
        projectSeedList.forEach(projectSeed -> {
            projectSeed.setUuid(UUID.randomUUID());
            projectSeed.getDependentProjectSeedList().forEach( ps -> ps.setUuid(UUID.randomUUID()));
        });
        return projectSeedList;
    }

    public ProjectFilerSeed extractProjectFilter(ProjectSeed projectSeed) {
        ProjectFilerSeed projectFilerSeed = projectFilerProvider.provide();
        projectFilerSeed.setSourceFileList(projectSeed.getSourceFileList());
        return projectFilerSeed;
    }

    public SolutionSeed buildSolutionSeed(String solutionName, Path solutionPath, List<ProjectSeed> projectSeedList) {
        return SolutionSeed.builder()
                .uuid(UUID.randomUUID())
                .name(solutionName)
                .path(solutionPath)
                .projectList(projectSeedList.stream().map(projectSeed -> SolutionSeed.Project.builder()
                        .name(projectSeed.getName())
                        .uuid(projectSeed.getUuid())
                        .path(projectSeed.getFolder())
                        .typeUuid(ProjectTypeGuid.CPP)
                .build()
                ).collect(Collectors.toList()))
                .build();
    }

}
