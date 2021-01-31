package com.tuware.msbuild.service;

import com.github.jknack.handlebars.Handlebars;
import com.tuware.msbuild.domain.solution.*;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Component
public class SolutionService {

    private Handlebars handlebars;
    private MsBuildEnvironment msBuildEnvironment;

    public SolutionService(Handlebars handlebars, MsBuildEnvironment msBuildEnvironment) {
        this.handlebars = handlebars;
        this.msBuildEnvironment = msBuildEnvironment;
    }

    public String buildCppConsoleAppSolution(
            String name,
            String location,
            Path solutionPath,
            UUID projectGuid,
            UUID solutionGuid
    ) throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getResource("/templates/solution.hbs").toURI());
        String template = String.join("\n", Files.readAllLines(path));

        return handlebars.prettyPrint(true).compileInline(template).apply(buildConsoleApp(
                name,
                location,
                solutionPath,
                projectGuid,
                solutionGuid));
    }

    private Solution buildConsoleApp(
            String name,
            String location,
            Path solutionPath,
            UUID projectGuid,
            UUID solutionGuid
    ) {
        return Solution.builder()
                .fileName(solutionPath)
                .formatVersion(msBuildEnvironment.getFormatVersion())
                .visualStudioVersion(msBuildEnvironment.getVisualStudioVersion())
                .minimumVisualStudioVersion(msBuildEnvironment.getMinimumVisualStudioVersion())
                .projectList(Collections.singletonList(Project.builder()
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
    }
}
