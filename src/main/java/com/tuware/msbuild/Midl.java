package com.tuware.msbuild;


import com.tuware.msbuild.midl.*;
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
public class Midl {

    @XmlAttribute(name = "Include")
    private String include;

    @XmlAttribute(name = "Condition")
    private String condition;

    @XmlElement(name = "DependentUpon")
    private String dependentUpon;

    @XmlElement(name = "Filter")
    private String filter;

    @XmlElement(name = "EnableWindowsRuntime")
    private boolean enableWindowsRuntime;

    @XmlElement(name = "MetadataFileName")
    private String metadataFileName;

    @XmlElement(name = "GenerateClientFiles")
    private GenerateClientFiles generateClientFiles;

    @XmlElement(name = "GenerateStublessProxies")
    private GenerateStublessProxies generateStublessProxies;

    @XmlElement(name = "GenerateTypeLibrary")
    private GenerateTypeLibrary generateTypeLibrary;

    @XmlElement(name = "HeaderFileName")
    private HeaderFileName headerFileName;

    @XmlElement(name = "DllDataFileName")
    private DllDataFileName dllDataFileName;

    @XmlElement(name = "InterfaceIdentifierFileName")
    private InterfaceIdentifierFileName interfaceIdentifierFileName;

    @XmlElement(name = "ProxyFileName")
    private ProxyFileName proxyFileName;

    @XmlElement(name = "TypeLibraryName")
    private TypeLibraryName typeLibraryName;
}
