package com.tuware.msbuild.application.domain.msbuild.project;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ClInclude {
    private String include;
    private String dependentUpon;
    private String filter;
}
