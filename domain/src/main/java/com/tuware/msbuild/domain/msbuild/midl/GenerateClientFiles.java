package com.tuware.msbuild.domain.msbuild.midl;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class GenerateClientFiles {
    private String condition;
    private String value;
}
