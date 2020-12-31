package com.tuware.msbuild;

import com.tuware.msbuild.link.EnableCOMDATFolding;
import com.tuware.msbuild.link.GenerateWindowsMetadata;
import com.tuware.msbuild.link.OptimizeReferences;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Link {
    @XmlAttribute(name = "GenerateWindowsMetadata")
    private GenerateWindowsMetadata generateWindowsMetadata;

    @XmlAttribute(name = "EnableCOMDATFolding")
    private EnableCOMDATFolding enableCOMDATFolding;

    @XmlAttribute(name = "OptimizeReferences")
    private OptimizeReferences optimizeReferences;

}
