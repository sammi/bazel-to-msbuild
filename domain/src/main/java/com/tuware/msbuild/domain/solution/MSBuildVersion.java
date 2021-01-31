package com.tuware.msbuild.domain.solution;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class MSBuildVersion {
    private String major;
    private String minor;
    private String patch;
    private String revision;
}
