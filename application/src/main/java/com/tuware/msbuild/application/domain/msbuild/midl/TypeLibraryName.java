package com.tuware.msbuild.application.domain.msbuild.midl;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class TypeLibraryName {
    private String condition;
    private boolean value;
}