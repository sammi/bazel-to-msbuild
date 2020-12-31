package com.tuware.msbuild.domain.project;

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
public class Page {
    @XmlAttribute(name = "Include")
    private String include;

    @XmlElement(name = "SubType")
    private String subType;

    @XmlElement(name = "Filter")
    private String filter;

    @XmlElement(name = "ShowAllFiles")
    private boolean showAllFiles;
}
