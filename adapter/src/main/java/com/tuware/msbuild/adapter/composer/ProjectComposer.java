package com.tuware.msbuild.adapter.composer;

import com.tuware.msbuild.contract.adapter.Composer;
import com.tuware.msbuild.contract.msbuild.clcompile.ClCompile;
import com.tuware.msbuild.contract.msbuild.link.Link;
import com.tuware.msbuild.contract.msbuild.project.*;
import com.tuware.msbuild.contract.msbuild.property.*;
import com.tuware.msbuild.contract.msbuild.solution.UniqueProjectGuid;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import com.tuware.msbuild.contract.template.ProjectTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ProjectComposer implements Composer<ProjectTemplate, ProjectSeed> {

    static final String DEBUG_X_64 = "Debug|x64";
    static final String RELEASE_X_64 = "Release|x64";
    static final String DEBUG_WIN_32 = "Debug|Win32";
    static final String RELEASE_WIN_32 = "Release|Win32";
    public static final String WIN_32 = "Win32";
    public static final String DEBUG = "Debug";
    public static final String RELEASE = "Release";
    public static final String X_64 = "x64";
    private static final String LEVEL_3 = "Level3";
    private static final String $_SOLUTION_DIR_ADDITIONAL_INCLUDE_DIRECTORIES = "$(SolutionDir);%(AdditionalIncludeDirectories)";
    private static final String CONSOLE = "Console";

    @Override
    public ProjectTemplate compose(ProjectSeed projectSeed) {
        UUID projectGuild = projectSeed.getUuid();
        ConfigurationType configurationType = projectSeed.getConfigurationType();

        ItemGroup projectConfigurationsItemGroup = ItemGroup.builder()
                .label("ProjectConfigurations")
                .projectConfigurationList(Arrays.asList(
                        ProjectConfiguration.builder().include(DEBUG_WIN_32)
                                .configuration(DEBUG)
                                .platform(WIN_32)
                                .build(),
                        ProjectConfiguration.builder().include(RELEASE_WIN_32)
                                .configuration(RELEASE)
                                .platform(WIN_32)
                                .build(),
                        ProjectConfiguration.builder().include(DEBUG_X_64)
                                .configuration(DEBUG)
                                .platform(X_64)
                                .build(),
                        ProjectConfiguration.builder().include(RELEASE_X_64)
                                .configuration(RELEASE)
                                .platform(X_64)
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
                        .configurationType(configurationType)
                        .useDebugLibraries(UseDebugLibraries.builder().value(true).build())
                        .platformToolset(PlatformToolset.builder().value("v142").build())
                        .characterSet(CharacterSet.Unicode)
                        .build(),
                PropertyGroup.builder()
                        .condition("'$(Configuration)|$(Platform)'=='Release|Win32'")
                        .configurationType(configurationType)
                        .useDebugLibraries(UseDebugLibraries.builder().value(false).build())
                        .platformToolset(PlatformToolset.builder().value("v142").build())
                        .wholeProgramOptimization(WholeProgramOptimization.builder().value(true).build())
                        .characterSet(CharacterSet.Unicode)
                        .build(),
                PropertyGroup.builder()
                        .condition("'$(Configuration)|$(Platform)'=='Debug|x64'")
                        .configurationType(configurationType)
                        .useDebugLibraries(UseDebugLibraries.builder().value(true).build())
                        .platformToolset(PlatformToolset.builder().value("v142").build())
                        .characterSet(CharacterSet.Unicode)
                        .build(),
                PropertyGroup.builder()
                        .condition("'$(Configuration)|$(Platform)'=='Release|x64'")
                        .configurationType(configurationType)
                        .useDebugLibraries(UseDebugLibraries.builder().value(false).build())
                        .platformToolset(PlatformToolset.builder().value("v142").build())
                        .wholeProgramOptimization(WholeProgramOptimization.builder().value(true).build())
                        .characterSet(CharacterSet.Unicode)
                        .build()
        );

        ItemGroup clCompileItemGroup = ItemGroup.builder()
                .clCompileList(Stream.of(
                        projectSeed.getSourceFileList().stream().map(sourceFile -> ClCompile.builder().include(sourceFile).build()).collect(Collectors.toList()),
                        projectSeed.getHeaderFileList().stream().map(headerFile -> ClCompile.builder().include(headerFile).build()).collect(Collectors.toList())
                ).flatMap(Collection::stream).collect(Collectors.toList())).build();

        List<ProjectReference> projectReferenceList;
        if (projectSeed.getDependentProjectSeedList() != null) {
            projectReferenceList =
                    projectSeed.getDependentProjectSeedList().stream().map(
                            ps -> ProjectReference.builder()
                                    .include(ps.getFolder().toFile().getPath() + File.separator + ps.getName())
                                    .project(UniqueProjectGuid.builder().value(ps.getUuid()).build())
                                    .build()).collect(Collectors.toList());
        } else {
            projectReferenceList = new ArrayList<>();
        }


        ItemGroup projectReferenceItemGroup = ItemGroup.builder().projectReferenceList(projectReferenceList).build();

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

        String preprocessorDefinitions = projectSeed.getPreprocessorDefinitions() == null ? "" : ";" + projectSeed.getPreprocessorDefinitions();

        List<ItemDefinitionGroup> itemDefinitionGroupList = Arrays.asList(
                ItemDefinitionGroup.builder().condition("'$(Configuration)|$(Platform)'=='Debug|Win32'")
                    .clCompile(ClCompile.builder()
                            .warningLevel(LEVEL_3)
                            .sDLCheck(true)
                            .preprocessorDefinitions("WIN32;_DEBUG;_CONSOLE;%(PreprocessorDefinitions)" + preprocessorDefinitions)
                            .conformanceMode(true)
                            .additionalIncludeDirectories($_SOLUTION_DIR_ADDITIONAL_INCLUDE_DIRECTORIES)
                    .build())
                    .link(Link.builder().subSystem(CONSOLE).generateDebugInformation(true).build())
                .build(),
                ItemDefinitionGroup.builder().condition("'$(Configuration)|$(Platform)'=='Release|Win32'")
                        .clCompile(ClCompile.builder()
                                .warningLevel(LEVEL_3)
                                .functionLevelLinking(true)
                                .intrinsicFunctions(true)
                                .sDLCheck(true)
                                .preprocessorDefinitions("WIN32;NDEBUG;_CONSOLE;%(PreprocessorDefinitions)" + preprocessorDefinitions)
                                .conformanceMode(true)
                                .additionalIncludeDirectories($_SOLUTION_DIR_ADDITIONAL_INCLUDE_DIRECTORIES)
                                .build())
                        .link(Link.builder()
                                .subSystem(CONSOLE)
                                .enableCOMDATFolding(true)
                                .optimizeReferences(true)
                                .generateDebugInformation(true)
                        .build())
                .build(),
                ItemDefinitionGroup.builder().condition("'$(Configuration)|$(Platform)'=='Debug|x64'")
                        .clCompile(ClCompile.builder()
                                .warningLevel(LEVEL_3)
                                .sDLCheck(true)
                                .preprocessorDefinitions("_DEBUG;_CONSOLE;%(PreprocessorDefinitions)" + preprocessorDefinitions)
                                .conformanceMode(true)
                                .additionalIncludeDirectories($_SOLUTION_DIR_ADDITIONAL_INCLUDE_DIRECTORIES)
                                .build())
                        .link(Link.builder()
                                .subSystem(CONSOLE)
                                .generateDebugInformation(true)
                                .build())
                .build(),
                ItemDefinitionGroup.builder().condition("'$(Configuration)|$(Platform)'=='Release|x64'")
                        .clCompile(ClCompile.builder()
                                .warningLevel(LEVEL_3)
                                .sDLCheck(true)
                                .preprocessorDefinitions("NDEBUG;_CONSOLE;%(PreprocessorDefinitions)" + preprocessorDefinitions)
                                .conformanceMode(true)
                                .additionalIncludeDirectories($_SOLUTION_DIR_ADDITIONAL_INCLUDE_DIRECTORIES)
                                .build())
                        .link(Link.builder()
                                .subSystem(CONSOLE)
                                .enableCOMDATFolding(true)
                                .optimizeReferences(true)
                                .generateDebugInformation(true)
                                .build())
                .build()
        );

        return ProjectTemplate.builder()
                .defaultTargets(project.getDefaultTargets())
                .projectConfigurations(projectConfigurationsItemGroup)
                .globals(globals)
                .cppDefaultPropsImport(cppDefaultPropsImport)
                .configurationPropertyGroupList(configurationPropertyGroupList)
                .cppPropsImport(cppPropsImport)
                .clCompileItemGroup(clCompileItemGroup)
                .projectReferenceItemGroup(projectReferenceItemGroup)
                .itemDefinitionGroupList(itemDefinitionGroupList)
                .build();
    }
}
