package com.tuware.msbuild.domain.project;

import lombok.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ApplicationDefinition {
    @XmlAttribute(name = "Include")
    private String include;

    @XmlElement(name = "SubType")
    private String subType;
}
