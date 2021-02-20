package com.tuware.msbuild.application.domain.msbuild.project;

import com.tuware.msbuild.application.domain.msbuild.property.*;
import lombok.*;

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
    private String projectGuid;
    private String tootNamespace;
}