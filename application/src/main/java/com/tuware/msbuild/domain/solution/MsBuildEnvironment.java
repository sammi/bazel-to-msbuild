package com.tuware.msbuild.domain.solution;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class MsBuildEnvironment {
    private MSBuildVersion formatVersion;
    private MSBuildVersion visualStudioVersion;
    private MSBuildVersion minimumVisualStudioVersion;
}
