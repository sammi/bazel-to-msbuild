package com.tuware.msbuild.adapters.generator;

import com.tuware.msbuild.application.domain.Pair;
import com.tuware.msbuild.application.domain.msbuild.solution.*;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Component
public class SolutionGenerator {

    public static final String DEBUG_X_64 = "Debug|x64";
    public static final String DEBUG_X_86 = "Debug|x86";
    public static final String RELEASE_X_64 = "Release|x64";
    public static final String RELEASE_X_86 = "Release|x86";
    public static final String DEBUG_WIN_32 = "Debug|Win32";
    public static final String RELEASE_WIN_32 = "Release|Win32";

    private MsBuildEnvironment msBuildEnvironment;

    public SolutionGenerator(MsBuildEnvironment msBuildEnvironment) {
        this.msBuildEnvironment = msBuildEnvironment;
    }

    public String buildCppConsoleAppSolution(
            String projectName,
            Path location,
            String solutionName,
            UUID projectGuid,
            UUID solutionGuid
    ) {
        return TemplateUtils.buildXml("/templates/solution.hbs", buildConsoleApp(
                projectName,
                solutionName,
                location,
                projectGuid,
                solutionGuid
        ));
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
                                                new Pair<>(DEBUG_X_64, DEBUG_X_64),
                                                new Pair<>(DEBUG_X_86, DEBUG_X_86),
                                                new Pair<>(RELEASE_X_64, RELEASE_X_64),
                                                new Pair<>(RELEASE_X_86, RELEASE_X_86)
                                        ))
                                        .build(),
                                GlobalSection.builder()
                                        .phase(Phase.POST_SOLUTION)
                                        .vsPackage(VsPackage.ProjectConfigurationPlatforms)
                                        .configList(Arrays.asList(
                                                new Pair<>(String.format("{%s}.Debug|x64.ActiveCfg", projectGuid), DEBUG_X_64),
                                                new Pair<>(String.format("{%s}.Debug|x64.Build.0", projectGuid), DEBUG_X_64),
                                                new Pair<>(String.format("{%s}.Debug|x86.ActiveCfg", projectGuid), DEBUG_WIN_32),
                                                new Pair<>(String.format("{%s}.Debug|x86.Build.0", projectGuid), DEBUG_WIN_32),
                                                new Pair<>(String.format("{%s}.Release|x64.ActiveCfg", projectGuid), RELEASE_X_64),
                                                new Pair<>(String.format("{%s}.Release|x64.Build.0", projectGuid), RELEASE_X_64),
                                                new Pair<>(String.format("{%s}.Release|x86.ActiveCfg", projectGuid), RELEASE_WIN_32),
                                                new Pair<>(String.format("{%s}.Release|x86.Build.0", projectGuid), RELEASE_WIN_32)
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
