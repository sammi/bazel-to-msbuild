package com.tuware.msbuild;

import com.tuware.msbuild.property.*;
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
public class PropertyGroup {

    @XmlAttribute(name = "Condition")
    private String condition;

    @XmlAttribute(name = "Label")
    private String label;

    @XmlElement(name = "ConfigurationType")
    private ConfigurationType configurationType;

    @XmlElement(name = "PlatformToolset")
    private List<PlatformToolset> platformToolsetList;

    @XmlElement(name = "CharacterSet")
    private CharacterSet characterSet;

    @XmlElement(name = "CppWinRTOptimized")
    private CppWinRTOptimized cppWinRTOptimized;

    @XmlElement(name = "UseDebugLibraries")
    private UseDebugLibraries useDebugLibraries;

    @XmlElement(name = "LinkIncremental")
    private LinkIncremental linkIncremental;

    @XmlElement(name = "WholeProgramOptimization")
    private WholeProgramOptimization wholeProgramOptimization;

    @XmlElement(name = "CppWinRTRootNamespaceAutoMerge")
    private CppWinRTRootNamespaceAutoMerge cppWinRTRootNamespaceAutoMerge;

    @XmlElement(name = "CppWinRTGenerateWindowsMetadata")
    private CppWinRTGenerateWindowsMetadata cppWinRTGenerateWindowsMetadata;

    @XmlElement(name = "MinimalCoreWin")
    private MinimalCoreWin minimalCoreWin;

    @XmlElement(name = "ProjectGuid")
    private ProjectGuid projectGuid;

    @XmlElement(name = "ProjectName")
    private ProjectName projectName;

    @XmlElement(name = "DefaultLanguage")
    private DefaultLanguage defaultLanguage;

    @XmlElement(name = "MinimumVisualStudioVersion")
    private MinimumVisualStudioVersion minimumVisualStudioVersion;

    @XmlElement(name = "AppContainerApplication")
    private AppContainerApplication appContainerApplication;

    @XmlElement(name = "ApplicationType")
    private ApplicationType applicationType;

    @XmlElement(name = "ApplicationTypeRevision")
    private ApplicationTypeRevision applicationTypeRevision;

    @XmlElement(name = "WindowsTargetPlatformVersion")
    private WindowsTargetPlatformVersion windowsTargetPlatformVersion;

    @XmlElement(name = "WindowsTargetPlatformMinVersion")
    private WindowsTargetPlatformMinVersion windowsTargetPlatformMinVersion;

    @XmlElement(name = "RootNamespace")
    private RootNamespace rootNamespace;

    @XmlElement(name = "MSBuildAllProjects")
    private MSBuildAllProjects msBuildAllProjects;

    @XmlElement(name = "PreferredToolArchitecture")
    private PreferredToolArchitecture PreferredToolArchitecture;

    @XmlElement(name = "CanReferenceWinRT")
    private CanReferenceWinRT canReferenceWinRT;

    @XmlElement(name = "CppWinRTPackage")
    private CppWinRTPackage cppWinRTPackage;

    @XmlElement(name = "LanguageStandard")
    private LanguageStandard languageStandard;

    @XmlElement(name = "ErrorText")
    private ErrorText errorText;
}
