package com.tuware.msbuild;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ClInclude {
    @XmlAttribute(name = "Include")
    private String include;

    @XmlElement(name = "DependentUpon")
    private String dependentUpon;

    @XmlElement(name = "Filter")
    private String filter;
}
