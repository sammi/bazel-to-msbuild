package com.tuware.msbuild;

import com.tuware.msbuild.cppwinrt.MSBuild;
import com.tuware.msbuild.service.ProjectService;
import com.tuware.msbuild.service.SolutionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MSBuildTest {
    @Mock
    private ProjectService projectService;

    @Mock
    private SolutionService solutionService;

    @InjectMocks
    private MSBuild msBuild;

    @Test
    void createCppConsoleApp() throws IOException, URISyntaxException {

        String name = "test";
        String location = "source\\repos\\test";
        Path solutionPath = Paths.get("source\\repos\\test");
        UUID projectGuid = UUID.randomUUID();
        UUID solutionGuid = UUID.randomUUID();
        String cppFileName = "test.cpp";
        String sourceFilesFilterGuid = UUID.randomUUID().toString();
        String headerFilesFilterGuid = UUID.randomUUID().toString();
        String resourceFilesFilterGuid = UUID.randomUUID().toString();

        when(solutionService.buildCppConsoleAppSolution(
                any(), any(), any(), any(), any())
        ).thenReturn("solution");

        when(projectService.createProject(
                anyString(), anyString())
        ).thenReturn("vcxproj");

        when(projectService.createProjectUser()).thenReturn("vcxproj.user");

        when(projectService.createProjectFilters(
                anyString(), anyString(), anyString())
        ).thenReturn("vcxproj.filters");

        msBuild.createCppConsoleApp(
                name,
                location,
                solutionPath,
                projectGuid,
                solutionGuid,
                cppFileName,
                sourceFilesFilterGuid,
                headerFilesFilterGuid,
                resourceFilesFilterGuid
        );

        verify(solutionService, times(1)).buildCppConsoleAppSolution(
            eq(name),
            eq(location),
            eq(solutionPath),
            eq(projectGuid),
            eq(solutionGuid)
        );

        verify(projectService, times(1)).createProject(
                eq(cppFileName),
                eq(projectGuid.toString())
        );

        verify(projectService, times(1)).createProjectFilters(
                eq(sourceFilesFilterGuid),
                eq(headerFilesFilterGuid),
                eq(resourceFilesFilterGuid)
        );

        verify(projectService, times(1)).createProjectUser();

        Files.delete(Paths.get(String.format("%s.sln", name)));
        Files.delete(Paths.get(String.format("%s.vcxproj", name)));
        Files.delete(Paths.get(String.format("%s.vcxproj.filters", name)));
        Files.delete(Paths.get(String.format("%s.vcxproj.user", name)));
    }
}
