package com.tuware.msbuild.service;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.domain.midl.Midl;
import com.tuware.msbuild.domain.project.*;
import com.tuware.msbuild.domain.property.PlatformToolset;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
public class ProjectService {

    public Project createProject(Build.QueryResult queryResult) {
        return Project.builder()
                .xmlns("http://schemas.microsoft.com/developer/msbuild/2003")
                .defaultTargets("Build")
                .initialTargets("Test")
                .treatAsLocalProperty("MyProperty" + queryResult.getTargetCount())
                .sdk("DotNet.SDK.4.0")
                .toolsVersion("16.0.0")
                .itemGroupList(
                        Arrays.asList(
                                ItemGroup.builder().midlList(
                                        Collections.singletonList(
                                                Midl.builder()
                                                        .include("Config.idl")
                                                        .dependentUpon("Config.xml")
                                                        .build()
                                        )
                                ).build(),
                                ItemGroup.builder().projectConfigurationList(
                                        Collections.singletonList(
                                                ProjectConfiguration.builder()
                                                        .include("Debug|x64")
                                                        .configuration("Debug")
                                                        .platform("x64")
                                                        .build()
                                        )
                                ).build(),
                                ItemGroup.builder().clIncludeList(
                                        Collections.singletonList(
                                                ClInclude.builder()
                                                        .include("Cluster.h")
                                                        .dependentUpon("Cluster.idl")
                                                        .build()
                                        )
                                ).build()
                        )
                )
                .propertyGroupList(Collections.singletonList(
                        PropertyGroup.builder().platformToolsetList(
                                Arrays.asList(
                                        PlatformToolset.builder().condition("'$(VisualStudioVersion)' == '15.0'").value("v141").build(),
                                        PlatformToolset.builder().condition("'$(VisualStudioVersion)' == '16.0'").value("v142").build()
                                )
                        ).build()
                ))
                .build();
    }
}
