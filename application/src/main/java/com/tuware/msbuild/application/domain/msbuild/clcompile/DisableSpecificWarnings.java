package com.tuware.msbuild.application.domain.msbuild.clcompile;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class DisableSpecificWarnings {
    private String value;
}
