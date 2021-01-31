package com.tuware.msbuild.service;

import com.tuware.msbuild.domain.clcompile.ClCompile;
import com.tuware.msbuild.domain.project.*;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.util.Arrays;
import java.util.Collections;

@Component
public class ProjectService {

    public String createProjectFilters(
        String sourceFilesFilterGuid,
        String headerFilesFilterGuid,
        String resourceFilesFilterGuid
    ) throws JAXBException {

        String sourceFilesFilterName = "Source Files";

        Project project = Project.builder()
                .xmlns("http://schemas.microsoft.com/developer/msbuild/2003")
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

        return XmlUtils.toXml(project);
    }

    public String createProjectUser() throws JAXBException {

        Project project = Project.builder()
                .xmlns("http://schemas.microsoft.com/developer/msbuild/2003")
                .toolsVersion("Current")
                .propertyGroupList(Collections.singletonList(new PropertyGroup()))
                .build();

        return XmlUtils.toXml(project);
    }
}
