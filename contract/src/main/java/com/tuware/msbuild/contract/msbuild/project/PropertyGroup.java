package com.tuware.msbuild.contract.msbuild.project;

import com.tuware.msbuild.contract.msbuild.property.*;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class PropertyGroup {
    private String condition;
    private String label;
    private ConfigurationType configurationType;
    private PlatformToolset platformToolset;
    private CharacterSet characterSet;
    private CppWinRTOptimized cppWinRTOptimized;
    private UseDebugLibraries useDebugLibraries;
    private LinkIncremental linkIncremental;
    private WholeProgramOptimization wholeProgramOptimization;
    private CppWinRTRootNamespaceAutoMerge cppWinRTRootNamespaceAutoMerge;
    private CppWinRTGenerateWindowsMetadata cppWinRTGenerateWindowsMetadata;
    private MinimalCoreWin minimalCoreWin;
    private ProjectName projectName;
    private DefaultLanguage defaultLanguage;
    private MinimumVisualStudioVersion minimumVisualStudioVersion;
    private AppContainerApplication appContainerApplication;
    private ApplicationType applicationType;
    private ApplicationTypeRevision applicationTypeRevision;
    private WindowsTargetPlatformVersion windowsTargetPlatformVersion;
    private WindowsTargetPlatformMinVersion windowsTargetPlatformMinVersion;
    private String rootNamespace;
    private MSBuildAllProjects msBuildAllProjects;
    private PreferredToolArchitecture preferredToolArchitecture;
    private CanReferenceWinRT canReferenceWinRT;
    private CppWinRTPackage cppWinRTPackage;
    private LanguageStandard languageStandard;
    private ErrorText errorText;
    private String vcProjectVersion;
    private String keyword;
    private UUID projectGuid;
    private String tootNamespace;
}
