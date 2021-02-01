package com.tuware.msbuild.service;

import com.github.jknack.handlebars.Handlebars;
import com.tuware.msbuild.domain.solution.MSBuildVersion;
import com.tuware.msbuild.domain.solution.MsBuildEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

class SolutionServiceTest {

    private SolutionService solutionService;

    @BeforeEach
    void setup() {
        solutionService = new SolutionService(
                new Handlebars(),
                MsBuildEnvironment.builder()
                    .formatVersion(MSBuildVersion.builder().major("12").minor("00").build())
                    .visualStudioVersion(MSBuildVersion.builder().major("16").minor("0").patch("30804").revision("86").build())
                    .minimumVisualStudioVersion(MSBuildVersion.builder().major("10").minor("0").patch("40219").revision("1").build())
                .build()
        );
    }

    @Test
    void buildCppConsoleAppSolution() throws IOException, URISyntaxException {
        UUID projectGuid = UUID.randomUUID();
        UUID solutionGuid = UUID.randomUUID();

        String message = solutionService.buildCppConsoleAppSolution(
                "TestCppWinRTConsoleApp",
                "projectFolderPath",
                Paths.get("TestCppWinRTConsoleAppSolution.sln"),
                projectGuid,
                solutionGuid);

        Path expectFile = Paths.get(getClass().getResource("/App.sln").toURI());
        String expect = String.join("\n", Files.readAllLines(expectFile))
                .replaceAll("16809c39-239c-40a0-a257-1b846d94ca18", projectGuid.toString())
                .replaceAll("a73d7e39-016f-4e07-834c-29cdf3baa343", solutionGuid.toString());

        assertThat(message.trim(), equalTo(expect.trim()));
    }

}
