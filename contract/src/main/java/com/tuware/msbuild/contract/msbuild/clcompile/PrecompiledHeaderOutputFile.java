package com.tuware.msbuild.contract.msbuild.clcompile;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class PrecompiledHeaderOutputFile {
    private String value;
}
