package com.tuware.msbuild.domain.clcompile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ClCompile {

    @XmlElement(name = "Filter")
    private String filter;

    @XmlElement(name = "PrecompiledHeader")
    private PrecompiledHeader precompiledHeader;

    @XmlElement(name = "PrecompiledHeaderFile")
    private PrecompiledHeaderFile precompiledHeaderFile;

    @XmlElement(name = "PrecompiledHeaderOutputFile")
    private PrecompiledHeaderOutputFile precompiledHeaderOutputFile;

    @XmlElement(name = "WarningLevel")
    private WarningLevel warningLevel;

    @XmlElement(name = "AdditionalOptions")
    private AdditionalOptions additionalOptions;

    @XmlElement(name = "DisableSpecificWarnings")
    private DisableSpecificWarnings disableSpecificWarnings;

    @XmlElement(name = "PreprocessorDefinitions")
    private PreprocessorDefinitions preprocessorDefinitions;

    @XmlElement(name = "AdditionalIncludeDirectories")
    private AdditionalIncludeDirectories additionalIncludeDirectories;

}
