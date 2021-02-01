package com.tuware.msbuild.domain.midl;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class GenerateTypeLibrary {
    private String condition;
    private boolean value;
}
