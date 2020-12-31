package com.tuware.msbuild;

import com.tuware.msbuild.midl.Midl;
import com.tuware.msbuild.project.*;
import com.tuware.msbuild.property.PlatformToolset;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        Project project = Project.builder()
                .xmlns("http://schemas.microsoft.com/developer/msbuild/2003")
                .defaultTargets("Build")
                .initialTargets("Test")
                .treatAsLocalProperty("MyProperty")
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
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Project.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            System.out.print("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            marshaller.marshal(project, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
