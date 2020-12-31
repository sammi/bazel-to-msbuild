package com.tuware.msbuild.domain.project;

import com.tuware.msbuild.domain.clcompile.ClCompile;
import com.tuware.msbuild.domain.link.Link;
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
public class ItemDefinitionGroup {

    @XmlAttribute(name = "Condition")
    private String condition;

    @XmlElement(name = "ClCompile")
    private List<ClCompile> clCompileList;

    @XmlElement(name = "Link")
    private List<Link> linkList;
}
