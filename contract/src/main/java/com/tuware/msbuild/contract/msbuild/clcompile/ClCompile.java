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
    private String warningLevel;
    private Boolean sDLCheck;
    private Boolean conformanceMode;
    private AdditionalOptions additionalOptions;
    private DisableSpecificWarnings disableSpecificWarnings;
    private String preprocessorDefinitions;
    private String additionalIncludeDirectories;
    private Boolean functionLevelLinking;
    private Boolean intrinsicFunctions;
}
