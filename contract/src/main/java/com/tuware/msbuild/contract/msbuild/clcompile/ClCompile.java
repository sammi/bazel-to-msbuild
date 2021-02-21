package com.tuware.msbuild.contract.msbuild.clcompile;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ClCompile {
    private String include;
    private String filter;
    private PrecompiledHeader precompiledHeader;
    private PrecompiledHeaderFile precompiledHeaderFile;
    private PrecompiledHeaderOutputFile precompiledHeaderOutputFile;
    private WarningLevel warningLevel;
    private AdditionalOptions additionalOptions;
    private DisableSpecificWarnings disableSpecificWarnings;
    private PreprocessorDefinitions preprocessorDefinitions;
    private AdditionalIncludeDirectories additionalIncludeDirectories;
}
