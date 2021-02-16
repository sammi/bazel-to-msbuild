package com.tuware.msbuild.cli;

import com.tuware.msbuild.service.BuildXmlException;
import com.tuware.msbuild.service.ProjectService;
import com.tuware.msbuild.service.SolutionService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class CppConsoleApp {
    private SolutionService solutionService;
    private ProjectService projectService;

    public CppConsoleApp(SolutionService solutionService, ProjectService projectService) {
        this.solutionService = solutionService;
        this.projectService = projectService;
    }

    public void createDefaultProject(
            String projectName,
            Path location,
            String solutionName
    ) {

        UUID projectGuid = UUID.randomUUID();
        UUID solutionGuid = UUID.randomUUID();
        String cppFileName = String.format("%s.cpp", projectName);
        String sourceFilesFilterGuid = UUID.randomUUID().toString();
        String headerFilesFilterGuid = UUID.randomUUID().toString();
        String resourceFilesFilterGuid = UUID.randomUUID().toString();

        String solution = solutionService.buildCppConsoleAppSolution(projectName, location, solutionName, projectGuid, solutionGuid);
        String vcxproj = projectService.createProject(cppFileName, projectGuid.toString());
        String vcxprojFilters = projectService.createProjectFilters(sourceFilesFilterGuid, headerFilesFilterGuid, resourceFilesFilterGuid);
        String vcxprojUser = projectService.createProjectUser();
        String cppFile = projectService.createMainCpp();

        try {
            Files.write(Paths.get(String.format("%s.sln", projectName)), solution.getBytes());
            Files.write(Paths.get(String.format("%s.vcxproj", projectName)), vcxproj.getBytes());
            Files.write(Paths.get(String.format("%s.vcxproj.user", projectName)), vcxprojUser.getBytes());
            Files.write(Paths.get(String.format("%s.vcxproj.filters", projectName)), vcxprojFilters.getBytes());
            Files.write(Paths.get(String.format("%s.cpp", projectName)), cppFile.getBytes());
        } catch (IOException e) {
            throw new BuildXmlException("Failed to write context to file system.", e);
        }
    }
}
