package com.tuware.msbuild.adapter.composer;

import com.tuware.msbuild.contract.adapter.Composer;
import com.tuware.msbuild.contract.msbuild.Config;
import com.tuware.msbuild.contract.msbuild.solution.*;
import com.tuware.msbuild.contract.seed.SolutionSeed;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class SolutionComposer implements Composer<Solution, SolutionSeed> {

    private static final String DEBUG = "Debug";
    private static final String X_64 = "x64";
    private static final String DEBUG_X_64 = DEBUG + "|" + X_64;
    private static final String X_86 = "x86";
    private static final String DEBUG_X_86 = DEBUG + "|" + X_86;
    private static final String RELEASE = "Release";
    private static final String RELEASE_X_64 = RELEASE + "|" + X_64;
    private static final String RELEASE_X_86 = RELEASE + "|" + X_86;
    private static final String DEBUG_WIN_32 = DEBUG + "|Win32";
    private static final String RELEASE_WIN_32 = RELEASE + "|Win32";
    private static final String ACTIVE_CFG = "ActiveCfg";
    private static final String BUILD_0 = "Build.0";
    private static final String S = "{%s}.";

    private final MsBuildEnvironment msBuildEnvironment;

    public SolutionComposer() {
        this.msBuildEnvironment = MsBuildEnvironment.builder()
                .formatVersion(MSBuildVersion.builder().major("12").minor("00").build())
                .visualStudioVersion(MSBuildVersion.builder().major("16").minor("0").patch("30804").revision("86").build())
                .minimumVisualStudioVersion(MSBuildVersion.builder().major("10").minor("0").patch("40219").revision("1").build())
                .build();
    }

    @Override
    public Solution compose(SolutionSeed solutionSeed) {

        Path solutionPath = solutionSeed.getPath();
        UUID solutionGuid = solutionSeed.getUuid();

        List<Project> projectList = solutionSeed.getProjectList().stream().map(project ->
                Project.builder()
                        .projectTypeGuid(ProjectTypeGuid.CPP)
                        .name(project.getName())
                        .projectFilePath(Paths.get(project.getPath().toFile().getPath(), project.getName() + ".vcxproj"))
                        .uuid(UniqueProjectGuid.builder().value(project.getUuid()).build())
                        .build()
        ).collect(Collectors.toList());

        List<Config> configList = new ArrayList<>();
        for (SolutionSeed.Project project : solutionSeed.getProjectList()) {
            configList.add(Config.builder().key(String.format(S + DEBUG + "|" + X_64 + "." + ACTIVE_CFG, project.getUuid())).value(DEBUG_X_64).build());
            configList.add(Config.builder().key(String.format(S + DEBUG + "|" + X_64 + "." + BUILD_0, project.getUuid())).value(DEBUG_X_64).build());
            configList.add(Config.builder().key(String.format(S + DEBUG + "|" + X_86 + "." + ACTIVE_CFG, project.getUuid())).value(DEBUG_WIN_32).build());
            configList.add(Config.builder().key(String.format(S + DEBUG + "|" + X_86 + "." + BUILD_0, project.getUuid())).value(DEBUG_WIN_32).build());
            configList.add(Config.builder().key(String.format(S + RELEASE + "|" + X_64 + "." + ACTIVE_CFG, project.getUuid())).value(RELEASE_X_64).build());
            configList.add(Config.builder().key(String.format(S + RELEASE + "|" + X_64 + "." + BUILD_0, project.getUuid())).value(RELEASE_X_64).build());
            configList.add(Config.builder().key(String.format(S + RELEASE + "|" + X_86 + "." + ACTIVE_CFG, project.getUuid())).value(RELEASE_WIN_32).build());
            configList.add(Config.builder().key(String.format(S + RELEASE + "|" + X_86 + "." + BUILD_0, project.getUuid())).value(RELEASE_WIN_32).build());
        }

        return Solution.builder()
                .fileName(solutionPath)
                .formatVersion(msBuildEnvironment.getFormatVersion())
                .visualStudioVersion(msBuildEnvironment.getVisualStudioVersion())
                .minimumVisualStudioVersion(msBuildEnvironment.getMinimumVisualStudioVersion())
                .projectList(projectList)
                .global(
                        Global.builder().globalSectionList(Arrays.asList(
                                GlobalSection.builder()
                                        .phase(Phase.PRE_SOLUTION)
                                        .vsPackage(VsPackage.SolutionConfigurationPlatforms)
                                        .configList(Arrays.asList(
                                                Config.builder().key(DEBUG_X_64).value(DEBUG_X_64).build(),
                                                Config.builder().key(DEBUG_X_86).value(DEBUG_X_86).build(),
                                                Config.builder().key(RELEASE_X_64).value(RELEASE_X_64).build(),
                                                Config.builder().key(RELEASE_X_86).value(RELEASE_X_86).build()
                                        ))
                                        .build(),
                                GlobalSection.builder()
                                        .phase(Phase.POST_SOLUTION)
                                        .vsPackage(VsPackage.ProjectConfigurationPlatforms)
                                        .configList(configList)
                                        .build(),
                                GlobalSection.builder()
                                        .phase(Phase.PRE_SOLUTION)
                                        .vsPackage(VsPackage.SolutionProperties)
                                        .configList(Collections.singletonList(
                                                Config.builder().key("HideSolutionNode").value("FALSE").build()
                                        ))
                                        .build(),
                                GlobalSection.builder()
                                        .phase(Phase.POST_SOLUTION)
                                        .vsPackage(VsPackage.ExtensibilityGlobals)
                                        .configList(Collections.singletonList(
                                                Config.builder().key("SolutionGuid").value(String.format("{%s}", solutionGuid)).build()
                                        ))
                                        .build()
                        )).build()
                )
                .build();
    }
}
