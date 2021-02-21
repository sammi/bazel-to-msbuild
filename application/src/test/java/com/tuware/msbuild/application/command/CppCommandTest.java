package com.tuware.msbuild.application.command;

import com.tuware.msbuild.application.generator.ProjectGenerator;
import com.tuware.msbuild.application.generator.SolutionGenerator;
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
class CppCommandTest {
    @Mock
    private ProjectGenerator projectGenerator;

    @Mock
    private SolutionGenerator solutionGenerator;

    @InjectMocks
    private CppCommand cppCommand;

    @Test
    void createCppConsoleApp() {

        String name = "test";
        String location = "source\\repos\\test";
        Path solutionPath = Paths.get("source\\repos\\test");
        String cppFileName = "test.cpp";

        when(solutionGenerator.buildCppConsoleAppSolution(
                any(), any(), any(), any(), any())
        ).thenReturn("solution");

        when(projectGenerator.createProject(
                anyString(), anyString())
        ).thenReturn("vcxproj");

        when(projectGenerator.createProjectUser()).thenReturn("vcxproj.user");

        when(projectGenerator.createProjectFilters(
                anyString(), anyString(), anyString())
        ).thenReturn("vcxproj.filters");

        when(projectGenerator.createMainCpp()).thenReturn("cpp");

        cppCommand.defaultProject(name, solutionPath, location);

        verify(solutionGenerator, times(1)).buildCppConsoleAppSolution(
            eq(name),
                eq(solutionPath), eq(location),
                any(),
            any()
        );

        verify(projectGenerator, times(1)).createProject(
                eq(cppFileName),
                any()
        );

        verify(projectGenerator, times(1)).createProjectFilters(
                any(),
                any(),
                any()
        );

        verify(projectGenerator, times(1)).createProjectUser();

        verify(projectGenerator, times(1)).createMainCpp();

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
