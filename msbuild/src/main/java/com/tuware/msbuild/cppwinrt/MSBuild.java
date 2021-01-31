package com.tuware.msbuild.cppwinrt;

import com.tuware.msbuild.service.ProjectService;
import com.tuware.msbuild.service.SolutionService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class MSBuild {
    private SolutionService solutionService;
    private ProjectService projectService;

    public MSBuild(SolutionService solutionService, ProjectService projectService) {
        this.solutionService = solutionService;
        this.projectService = projectService;
    }

    public String buildCppConsoleAppSolution(
        String name,
        String location,
        Path solutionPath,
        UUID projectGuid,
        UUID solutionGuid
    ) throws URISyntaxException, IOException {
        return solutionService.buildCppConsoleAppSolution(name, location, solutionPath, projectGuid, solutionGuid);
    }
}
