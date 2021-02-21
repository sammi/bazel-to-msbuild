package com.tuware.msbuild.adapters.generator;

import com.tuware.msbuild.application.domain.msbuild.solution.MSBuildVersion;
import com.tuware.msbuild.application.domain.msbuild.solution.MsBuildEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SolutionGeneratorTest {

    private SolutionGenerator solutionGenerator;

    @BeforeEach
    void setup() {
        solutionGenerator = new SolutionGenerator(
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

        String message = solutionGenerator.buildCppConsoleAppSolution(
                "TestCppWinRTConsoleApp",
                Paths.get("TestCppWinRTConsoleAppSolution.sln"), "projectFolderPath",
                projectGuid,
                solutionGuid);

        Path expectFile = Paths.get(getClass().getResource("/App.sln").toURI());
        String expect = String.join("\n", Files.readAllLines(expectFile))
                .replaceAll("16809c39-239c-40a0-a257-1b846d94ca18", projectGuid.toString())
                .replaceAll("a73d7e39-016f-4e07-834c-29cdf3baa343", solutionGuid.toString());

        assertThat(message.trim()).isEqualTo(expect.trim());
    }

}
