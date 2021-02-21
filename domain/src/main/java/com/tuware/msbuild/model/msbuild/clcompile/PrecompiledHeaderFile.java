package com.tuware.msbuild.model.msbuild.clcompile;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class PrecompiledHeaderFile {
    private String value;
}
