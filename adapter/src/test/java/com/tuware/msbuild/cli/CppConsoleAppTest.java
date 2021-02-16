package com.tuware.msbuild.cli;

import com.tuware.msbuild.service.ProjectService;
import com.tuware.msbuild.service.SolutionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CppConsoleAppTest {
    @Mock
    private ProjectService projectService;

    @Mock
    private SolutionService solutionService;

    @InjectMocks
    private CppConsoleApp cppConsoleApp;

    @Test
    void createCppConsoleApp() {

        String name = "test";
        String location = "source\\repos\\test";
        Path solutionPath = Paths.get("source\\repos\\test");
        String cppFileName = "test.cpp";

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

        when(projectService.createMainCpp()).thenReturn("cpp");

        cppConsoleApp.createDefaultProject(name, solutionPath, location);

        verify(solutionService, times(1)).buildCppConsoleAppSolution(
            eq(name),
                eq(solutionPath), eq(location),
                any(),
            any()
        );

        verify(projectService, times(1)).createProject(
                eq(cppFileName),
                any()
        );

        verify(projectService, times(1)).createProjectFilters(
                any(),
                any(),
                any()
        );

        verify(projectService, times(1)).createProjectUser();

        verify(projectService, times(1)).createMainCpp();

        try {
            Files.delete(Paths.get(String.format("%s.sln", name)));
            Files.delete(Paths.get(String.format("%s.vcxproj", name)));
            Files.delete(Paths.get(String.format("%s.vcxproj.filters", name)));
            Files.delete(Paths.get(String.format("%s.vcxproj.user", name)));
            Files.delete(Paths.get(String.format("%s.cpp", name)));
        } catch (IOException e) {
            fail(String.format("Failed to delete test generated files, error: %s", e.getMessage()), e);
        }
    }
}
