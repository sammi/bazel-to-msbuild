package com.tuware.msbuild.cppwinrt;

import com.github.jknack.handlebars.Handlebars;
import com.tuware.msbuild.domain.solution.*;
import javafx.util.Pair;
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
        String name = "TestCppWinRTConsoleApp";
        String location = "projectFolderPath";
        Path solutionPath = Paths.get("TestCppWinRTConsoleAppSolution.sln");

        MsBuildEnvironment msBuildEnvironment = MsBuildEnvironment.builder()
                .formatVersion(MSBuildVersion.builder().major("12").minor("00").build())
                .visualStudioVersion(MSBuildVersion.builder().major("16").minor("0").patch("30804").revision("86").build())
                .minimumVisualStudioVersion(MSBuildVersion.builder().major("10").minor("0").patch("40219").revision("1").build())
        .build();

        UUID projectGuid = UUID.randomUUID();
        UUID solutionGuid = UUID.randomUUID();

        Solution solution = Solution.builder()
                .fileName(solutionPath)
                .formatVersion(msBuildEnvironment.getFormatVersion())
                .visualStudioVersion(msBuildEnvironment.getVisualStudioVersion())
                .minimumVisualStudioVersion(msBuildEnvironment.getMinimumVisualStudioVersion())
                .projectList(Arrays.asList(Project.builder()
                    .projectTypeGuid(ProjectTypeGuid.CPP)
                    .name(name)
                    .location(Paths.get(location))
                    .uniqueProjectGuid(UniqueProjectGuid.builder().value(projectGuid).build())
                    .build()
                ))
                .global(
                        Global.builder().globalSectionList(Arrays.asList(
                                GlobalSection.builder()
                                    .phase(Phase.PRE_SOLUTION)
                                    .vsPackage(VsPackage.SolutionConfigurationPlatforms)
                                        .configList(Arrays.asList(
                                              new Pair<>("Debug|x64", "Debug|x64"),
                                              new Pair<>("Debug|x86", "Debug|x86"),
                                              new Pair<>("Release|x64", "Release|x64"),
                                              new Pair<>("Release|x86", "Release|x86")
                                        ))
                                .build(),
                                GlobalSection.builder()
                                        .phase(Phase.POST_SOLUTION)
                                        .vsPackage(VsPackage.ProjectConfigurationPlatforms)
                                        .configList(Arrays.asList(
                                                new Pair<>(String.format("{%s}.Debug|x64.ActiveCfg", projectGuid), "Debug|x64"),
                                                new Pair<>(String.format("{%s}.Debug|x64.Build.0", projectGuid), "Debug|x64"),
                                                new Pair<>(String.format("{%s}.Debug|x86.ActiveCfg", projectGuid), "Debug|Win32"),
                                                new Pair<>(String.format("{%s}.Debug|x86.Build.0", projectGuid), "Debug|Win32"),
                                                new Pair<>(String.format("{%s}.Release|x64.ActiveCfg", projectGuid), "Release|x64"),
                                                new Pair<>(String.format("{%s}.Release|x64.Build.0", projectGuid), "Release|x64"),
                                                new Pair<>(String.format("{%s}.Release|x86.ActiveCfg", projectGuid), "Release|Win32"),
                                                new Pair<>(String.format("{%s}.Release|x86.Build.0", projectGuid), "Release|Win32")
                                        ))
                                .build(),
                                GlobalSection.builder()
                                        .phase(Phase.PRE_SOLUTION)
                                        .vsPackage(VsPackage.SolutionProperties)
                                        .configList(Collections.singletonList(new Pair<>("HideSolutionNode", "FALSE")))
                                .build(),
                                GlobalSection.builder()
                                        .phase(Phase.POST_SOLUTION)
                                        .vsPackage(VsPackage.ExtensibilityGlobals)
                                        .configList(Collections.singletonList(new Pair<>("SolutionGuid", String.format("{%s}", solutionGuid))))
                                .build()
                        )).build()
                )
                .build();

        assertThat(msbuild.buildConsoleApp(
                name,
                location,
                solutionPath,
                projectGuid,
                solutionGuid,
                msBuildEnvironment
        ), equalTo(solution));

        Map<String, Solution> data = new HashMap<>();
        data.put("solution", solution);

        Path path = Paths.get(getClass().getResource("/templates/solution.hbs").toURI());
        String template = String.join("\n", Files.readAllLines(path));

        Handlebars handlebars = new Handlebars();
        String message = handlebars.prettyPrint(true).compileInline(template).apply(solution);

        Path result = Paths.get(getClass().getResource("/solution.sln").toURI());
        String expect = String.join("\n", Files.readAllLines(result))
                .replaceAll("16809c39-239c-40a0-a257-1b846d94ca18", projectGuid.toString())
                .replaceAll("a73d7e39-016f-4e07-834c-29cdf3baa343", solutionGuid.toString());

        assertThat(message.trim(), equalTo(expect.trim()));
    }

}
