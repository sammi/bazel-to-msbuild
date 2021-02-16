package com.tuware.msbuild.application.domain.msbuild.property;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class CppWinRTRootNamespaceAutoMerge {
    private boolean value;
}
