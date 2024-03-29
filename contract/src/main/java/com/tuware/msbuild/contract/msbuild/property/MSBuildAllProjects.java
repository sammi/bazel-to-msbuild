package com.tuware.msbuild.contract.msbuild.property;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class MSBuildAllProjects {
    private String condition;
    private String value;
}
