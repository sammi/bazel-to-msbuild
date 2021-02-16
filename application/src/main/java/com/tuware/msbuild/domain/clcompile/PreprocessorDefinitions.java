package com.tuware.msbuild.domain.clcompile;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class PreprocessorDefinitions {
    private String value;
}
