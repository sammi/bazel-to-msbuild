package com.tuware.msbuild.domain.msbuild.project;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class AppxManifest {
    private String include;
    private String dependentUpon;
}
