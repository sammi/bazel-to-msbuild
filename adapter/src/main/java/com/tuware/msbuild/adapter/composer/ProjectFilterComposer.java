package com.tuware.msbuild.adapter.composer;

import com.tuware.msbuild.contract.adapter.ComposerAdapter;
import com.tuware.msbuild.contract.input.ProjectFilerInput;
import com.tuware.msbuild.contract.msbuild.clcompile.ClCompile;
import com.tuware.msbuild.contract.msbuild.project.Filter;
import com.tuware.msbuild.contract.msbuild.project.ItemGroup;
import com.tuware.msbuild.contract.msbuild.project.Project;

import java.util.Arrays;
import java.util.Collections;

public class ProjectFilterComposer implements ComposerAdapter<Project, ProjectFilerInput> {

    @Override
    public Project compose(ProjectFilerInput templateData) {

        String sourceFilesFilterGuid = templateData.getSourceFilesFilterGuid();
        String headerFilesFilterGuid = templateData.getHeaderFilesFilterGuid();
        String resourceFilesFilterGuid = templateData.getResourceFilesFilterGuid();

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
}
