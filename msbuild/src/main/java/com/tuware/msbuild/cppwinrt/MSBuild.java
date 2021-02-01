package com.tuware.msbuild.cppwinrt;

import com.tuware.msbuild.service.ProjectService;
import com.tuware.msbuild.service.SolutionService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class MSBuild {
    private SolutionService solutionService;
    private ProjectService projectService;

    public MSBuild(SolutionService solutionService, ProjectService projectService) {
        this.solutionService = solutionService;
        this.projectService = projectService;
    }

    public void createCppConsoleApp(
            String name,
            String location,
            Path solutionPath,
            UUID projectGuid,
            UUID solutionGuid,
            String cppFileName,
            String sourceFilesFilterGuid,
            String headerFilesFilterGuid,
            String resourceFilesFilterGuid
    ) throws IOException, URISyntaxException {
        String solution = solutionService.buildCppConsoleAppSolution(name, location, solutionPath, projectGuid, solutionGuid);
        String vcxproj = projectService.createProject(cppFileName, projectGuid.toString());
        String vcxprojFilters = projectService.createProjectFilters(sourceFilesFilterGuid, headerFilesFilterGuid, resourceFilesFilterGuid);
        String vcxprojUser = projectService.createProjectUser();

        Files.write(Paths.get(String.format("%s.sln", name)), solution.getBytes());
        Files.write(Paths.get(String.format("%s.vcxproj", name)), vcxproj.getBytes());
        Files.write(Paths.get(String.format("%s.vcxproj.user", name)), vcxprojUser.getBytes());
        Files.write(Paths.get(String.format("%s.vcxproj.filters", name)), vcxprojFilters.getBytes());
    }
}
