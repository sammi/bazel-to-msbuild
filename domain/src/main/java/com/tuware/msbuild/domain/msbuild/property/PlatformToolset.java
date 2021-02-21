package com.tuware.msbuild.domain.msbuild.property;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class PlatformToolset {
    private String condition;
    protected String value;
}
