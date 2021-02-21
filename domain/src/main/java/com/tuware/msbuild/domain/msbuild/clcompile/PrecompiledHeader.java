package com.tuware.msbuild.domain.msbuild.clcompile;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class PrecompiledHeader {
    private String value;
}
