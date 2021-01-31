package com.tuware.msbuild.domain.project;

import com.tuware.msbuild.domain.clcompile.ClCompile;
import com.tuware.msbuild.domain.midl.Midl;
import lombok.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ItemGroup {

    @XmlAttribute(name = "Label")
    private String label;

    @XmlElement(name = "Midl")
    private List<Midl> midlList;

    @XmlElement(name = "ProjectConfiguration")
    private List<ProjectConfiguration> projectConfigurationList;

    @XmlElement(name = "ClInclude")
    private List<ClInclude> clIncludeList;

    @XmlElement(name = "ApplicationDefinition")
    private List<ApplicationDefinition> applicationDefinitionList;

    @XmlElement(name = "None")
    private List<None> noneList;

    @XmlElement(name = "Filter")
    private List<Filter> filterList;

    @XmlElement(name = "ClCompile")
    private List<ClCompile> clCompileList;
}
