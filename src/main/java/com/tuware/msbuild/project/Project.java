package com.tuware.msbuild.project;

import lombok.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Project")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Project {

    @XmlAttribute(name = "ToolsVersion")
    private String toolsVersion;

    @XmlAttribute(name = "DefaultTargets")
    private String defaultTargets;

    @XmlAttribute(name = "xmlns")
    private String xmlns;

    @XmlAttribute(name = "InitialTargets")
    private String initialTargets;

    @XmlAttribute(name = "SDK")
    private String sdk;

    @XmlAttribute(name = "TreatAsLocalProperty")
    private String treatAsLocalProperty;

    @XmlElement(name = "PropertyGroup")
    private List<PropertyGroup> propertyGroupList;

    @XmlElement(name = "Import")
    private List<Import> importList;

    @XmlElement(name = "ImportGroup")
    private List<ImportGroup> importGroupList;

    @XmlElement(name = "ItemDefinitionGroup")
    private List<ItemDefinitionGroup> itemDefinitionGroupList;

    @XmlElement(name = "ItemGroup")
    private List<ItemGroup> itemGroupList;

    @XmlElement(name = "Target")
    private List<Target> targetList;

}
