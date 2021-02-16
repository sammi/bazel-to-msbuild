package com.tuware.msbuild.adapters.generator;

import com.tuware.msbuild.application.domain.msbuild.clcompile.ClCompile;
import com.tuware.msbuild.application.domain.msbuild.project.*;
import com.tuware.msbuild.application.domain.msbuild.property.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ProjectGenerator {

    public String createProjectFilters(
        String sourceFilesFilterGuid,
        String headerFilesFilterGuid,
        String resourceFilesFilterGuid
    ) {
        String sourceFilesFilterName = "Source Files";
        Project project = Project.builder()
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

        return TemplateUtils.buildXml("/templates/vcxproj.filters.hbs", project);
    }

    public String createProjectUser() {
        return TemplateUtils.buildXml("/templates/vcxproj.user.hbs",
                Project.builder().toolsVersion("Current").build()
        );
    }

    public String createProject(String cppFileName, String projectGuild) {
        ItemGroup projectConfigurationsItemGroup = ItemGroup.builder()
                .label("ProjectConfigurations")
                .projectConfigurationList(Arrays.asList(
                        ProjectConfiguration.builder().include("Debug|Win32")
                                .configuration("Debug")
                                .platform("Win32")
                                .build(),
                        ProjectConfiguration.builder().include("Release|Win32")
                                .configuration("Release")
                                .platform("Win32")
                                .build(),
                        ProjectConfiguration.builder().include("Debug|x64")
                                .configuration("Debug")
                                .platform("x64")
                                .build(),
                        ProjectConfiguration.builder().include("Release|x64")
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

        Map<String, Object> data = new HashMap<>();
        data.put("defaultTargets", project.getDefaultTargets());
        data.put("projectConfigurations", projectConfigurationsItemGroup);
        data.put("globals", globals);
        data.put("cppDefaultPropsImport", cppDefaultPropsImport);
        data.put("configurationPropertyGroupList", configurationPropertyGroupList);
        data.put("cppPropsImport", cppPropsImport);
        data.put("clCompileItemGroup", clCompileItemGroup);

        return TemplateUtils.buildXml("/templates/vcxproj.hbs", data);
    }

    public String createMainCpp() {
        return String.join("\n", TemplateUtils.buildXml("/templates/App.hbs", new Object()));
    }
}
