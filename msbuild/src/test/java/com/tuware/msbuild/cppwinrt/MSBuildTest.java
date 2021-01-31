package com.tuware.msbuild.cppwinrt;

import com.tuware.msbuild.domain.solution.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

class MSBuildTest {

    @Test
    void buildConsoleApp() throws IOException, URISyntaxException {

        MSBuild msbuild = new MSBuild();

        UUID projectGuid = UUID.randomUUID();
        UUID solutionGuid = UUID.randomUUID();

        String message = msbuild.buildCppConsoleAppSolution(
                "TestCppWinRTConsoleApp",
                "projectFolderPath",
                Paths.get("TestCppWinRTConsoleAppSolution.sln"),
                projectGuid,
                solutionGuid,
                MsBuildEnvironment.builder()
                        .formatVersion(MSBuildVersion.builder().major("12").minor("00").build())
                        .visualStudioVersion(MSBuildVersion.builder().major("16").minor("0").patch("30804").revision("86").build())
                        .minimumVisualStudioVersion(MSBuildVersion.builder().major("10").minor("0").patch("40219").revision("1").build())
                        .build()
        );

        Path result = Paths.get(getClass().getResource("/solution.sln").toURI());
        String expect = String.join("\n", Files.readAllLines(result))
                .replaceAll("16809c39-239c-40a0-a257-1b846d94ca18", projectGuid.toString())
                .replaceAll("a73d7e39-016f-4e07-834c-29cdf3baa343", solutionGuid.toString());

        assertThat(message.trim(), equalTo(expect.trim()));
    }

}
