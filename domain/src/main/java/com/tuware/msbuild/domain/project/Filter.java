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
public class Filter {
    @XmlAttribute(name = "Include")
    private String include;

    @XmlElement(name = "UniqueIdentifier")
    private String uniqueIdentifier;

    @XmlElement(name = "Extensions")
    private String extensions;
}
