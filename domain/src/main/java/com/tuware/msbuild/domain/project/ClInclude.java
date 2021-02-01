package com.tuware.msbuild.domain.project;

import lombok.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ClInclude {
    @XmlAttribute(name = "Include")
    private String include;

    @XmlElement(name = "DependentUpon")
    private String dependentUpon;

    @XmlElement(name = "Filter")
    private String filter;
}
