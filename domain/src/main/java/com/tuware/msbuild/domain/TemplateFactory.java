package com.tuware.msbuild.domain;

import com.tuware.msbuild.contract.msbuild.Pair;
import com.tuware.msbuild.contract.msbuild.clcompile.ClCompile;
import com.tuware.msbuild.contract.msbuild.project.Project;
import com.tuware.msbuild.contract.msbuild.project.*;
import com.tuware.msbuild.contract.msbuild.property.*;
import com.tuware.msbuild.contract.msbuild.solution.*;
import com.tuware.msbuild.contract.template.CppProjectTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
class TemplateFactory {

    static final String DEBUG_X_64 = "Debug|x64";
    static final String DEBUG_X_86 = "Debug|x86";
    static final String RELEASE_X_64 = "Release|x64";
    static final String RELEASE_X_86 = "Release|x86";
    static final String DEBUG_WIN_32 = "Debug|Win32";
    static final String RELEASE_WIN_32 = "Release|Win32";

    private MsBuildEnvironment msBuildEnvironment;

    TemplateFactory(MsBuildEnvironment msBuildEnvironment) {
        this.msBuildEnvironment = msBuildEnvironment;
    }

    CppProjectTemplate createCppProject(String cppFileName, String projectGuild) {
        ItemGroup projectConfigurationsItemGroup = ItemGroup.builder()
                .label("ProjectConfigurations")
                .projectConfigurationList(Arrays.asList(
                        ProjectConfiguration.builder().include(DEBUG_WIN_32)
                                .configuration("Debug")
                                .platform("Win32")
                                .build(),
                        ProjectConfiguration.builder().include(RELEASE_WIN_32)
                                .configuration("Release")
                                .platform("Win32")
                                .build(),
                        ProjectConfiguration.builder().include(DEBUG_X_64)
                                .configuration("Debug")
                                .platform("x64")
                                .build(),
                        ProjectConfiguration.builder().include(RELEASE_X_64)
                                .configuration("Release")
                                .platform("x64")
                                .build()
                        )
                ).build();

        PropertyGroup globals = PropertyGroup.builder()
                .label("Globals")
                .vcProjectVersion("16.0")
                .keyword("Win32Proj")
                .projectGuid(projectGuild)
                .rootNamespace("ConsoleApplication3")
                .windowsTargetPlatformVersion(WindowsTargetPlatformVersion.builder().value("10.0").build())
                .build();

        String cppDefaultPropsImport = "$(VCTargetsPath)\\Microsoft.Cpp.Default.props";
        String cppPropsImport = "$(VCTargetsPath)\\Microsoft.Cpp.props";

        List<PropertyGroup> configurationPropertyGroupList = Arrays.asList(
                PropertyGroup.builder()
                    .condition("'$(Configuration)|$(Platform)'=='Debug|Win32'")
                    .configurationType(ConfigurationType.Application)
                    .useDebugLibraries(UseDebugLibraries.builder().value(true).build())
                    .platformToolset(PlatformToolset.builder().value("v142").build())
                    .characterSet(CharacterSet.Unicode)
                .build(),
                PropertyGroup.builder()
                        .condition("'$(Configuration)|$(Platform)'=='Release|Win32'")
                        .configurationType(ConfigurationType.Application)
                        .useDebugLibraries(UseDebugLibraries.builder().value(false).build())
                        .platformToolset(PlatformToolset.builder().value("v142").build())
                        .wholeProgramOptimization(WholeProgramOptimization.builder().value(true).build())
                        .characterSet(CharacterSet.Unicode)
                        .build(),
                PropertyGroup.builder()
                        .condition("'$(Configuration)|$(Platform)'=='Debug|x64'")
                        .configurationType(ConfigurationType.Application)
                        .useDebugLibraries(UseDebugLibraries.builder().value(true).build())
                        .platformToolset(PlatformToolset.builder().value("v142").build())
                        .characterSet(CharacterSet.Unicode)
                        .build(),
                PropertyGroup.builder()
                        .condition("'$(Configuration)|$(Platform)'=='Release|x64'")
                        .configurationType(ConfigurationType.Application)
                        .useDebugLibraries(UseDebugLibraries.builder().value(false).build())
                        .platformToolset(PlatformToolset.builder().value("v142").build())
                        .wholeProgramOptimization(WholeProgramOptimization.builder().value(true).build())
                        .characterSet(CharacterSet.Unicode)
                        .build()
        );

        ItemGroup clCompileItemGroup = ItemGroup.builder()
                .clCompileList(Collections.singletonList(ClCompile.builder().include(cppFileName).build()))
                .build();

        Project project = Project.builder()
                .defaultTargets("Build")
                .itemGroupList(Arrays.asList(
                        projectConfigurationsItemGroup,
                        clCompileItemGroup
                ))
                .propertyGroupList(Stream.concat(
                        Stream.of(globals),
                        configurationPropertyGroupList.stream()
                ).collect(Collectors.toList()))
                .importList(Arrays.asList(
                    Import.builder().project(cppDefaultPropsImport).build(),
                    Import.builder().project(cppPropsImport).build(),
                    Import.builder().project("$(VCTargetsPath)\\Microsoft.Cpp.targets").build()
                )
            ).build();

        return CppProjectTemplate.builder()
                .defaultTargets(project.getDefaultTargets())
                .projectConfigurations(projectConfigurationsItemGroup)
                .globals(globals)
                .cppDefaultPropsImport(cppDefaultPropsImport)
                .configurationPropertyGroupList(configurationPropertyGroupList)
                .cppPropsImport(cppPropsImport)
                .clCompileItemGroup(clCompileItemGroup)
                .build();
    }

    Project createProjectUser() {
        return  Project.builder().toolsVersion("Current").build();
    }

    Project createProjectFilter(String sourceFilesFilterGuid, String headerFilesFilterGuid, String resourceFilesFilterGuid) {

        String sourceFilesFilterName = "Source Files";

        return Project.builder()
                .toolsVersion("4.0")
                .itemGroupList(
                        Arrays.asList(
                                ItemGroup.builder().filterList(Arrays.asList(
                                        Filter.builder()
                                                .include(sourceFilesFilterName)
                                                .uniqueIdentifier(sourceFilesFilterGuid)
                                                .extensions("cpp;c;cc;cxx;c++;cppm;ixx;def;odl;idl;hpj;bat;asm;asmx")
                                                .build(),
                                        Filter.builder()
                                                .include("Header Files")
                                                .uniqueIdentifier(headerFilesFilterGuid)
                                                .extensions("h;hh;hpp;hxx;h++;hm;inl;inc;ipp;xsd")
                                                .build(),
                                        Filter.builder()
                                                .include("Resource Files")
                                                .uniqueIdentifier(resourceFilesFilterGuid)
                                                .extensions("rc;ico;cur;bmp;dlg;rc2;rct;bin;rgs;gif;jpg;jpeg;jpe;resx;tiff;tif;png;wav;mfcribbon-ms")
                                                .build()
                                )).build(),
                                ItemGroup.builder().clCompileList(
                                        Collections.singletonList(
                                                ClCompile.builder()
                                                        .include("ConsoleApplication1.cpp")
                                                        .filter(sourceFilesFilterName)
                                                        .build()
                                        )
                                ).build()
                        )
                )
                .build();
    }

    Solution buildConsoleApp(
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
                .projectList(Collections.singletonList(com.tuware.msbuild.contract.msbuild.solution.Project.builder()
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
