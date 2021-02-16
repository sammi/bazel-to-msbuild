package com.tuware.msbuild.command;

import com.tuware.msbuild.generator.BuildXmlException;
import com.tuware.msbuild.generator.ProjectGenerator;
import com.tuware.msbuild.generator.SolutionGenerator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class CppCommand {
    private SolutionGenerator solutionGenerator;
    private ProjectGenerator projectGenerator;

    public CppCommand(SolutionGenerator solutionGenerator, ProjectGenerator projectGenerator) {
        this.solutionGenerator = solutionGenerator;
        this.projectGenerator = projectGenerator;
    }

    public void defaultProject(
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

        String solution = solutionGenerator.buildCppConsoleAppSolution(projectName, location, solutionName, projectGuid, solutionGuid);
        String vcxproj = projectGenerator.createProject(cppFileName, projectGuid.toString());
        String vcxprojFilters = projectGenerator.createProjectFilters(sourceFilesFilterGuid, headerFilesFilterGuid, resourceFilesFilterGuid);
        String vcxprojUser = projectGenerator.createProjectUser();
        String cppFile = projectGenerator.createMainCpp();

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
