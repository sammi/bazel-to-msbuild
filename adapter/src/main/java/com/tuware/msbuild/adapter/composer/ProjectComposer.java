package com.tuware.msbuild.adapter.composer;

import com.tuware.msbuild.contract.adapter.ComposerAdapter;
import com.tuware.msbuild.contract.input.ProjectInput;
import com.tuware.msbuild.contract.msbuild.clcompile.ClCompile;
import com.tuware.msbuild.contract.msbuild.project.*;
import com.tuware.msbuild.contract.msbuild.property.*;
import com.tuware.msbuild.contract.template.CppProjectTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ProjectComposer implements ComposerAdapter<CppProjectTemplate, ProjectInput> {

    static final String DEBUG_X_64 = "Debug|x64";
    static final String RELEASE_X_64 = "Release|x64";
    static final String DEBUG_WIN_32 = "Debug|Win32";
    static final String RELEASE_WIN_32 = "Release|Win32";

    @Override
    public CppProjectTemplate compose(ProjectInput templateData) {
        String cppFileName = templateData.getCppFileName();
        String projectGuild = templateData.getProjectGuild();
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
}
