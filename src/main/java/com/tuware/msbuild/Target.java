package com.tuware.msbuild;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Target {

    @XmlAttribute(name = "Name")
    private String name;

    @XmlAttribute(name = "BeforeTargets")
    private String beforeTargets;

    @XmlElement(name = "PropertyGroup")
    private List<PropertyGroup> propertyGroupList;

    @XmlElement(name = "Error")
    private List<Error> errorList;
}
