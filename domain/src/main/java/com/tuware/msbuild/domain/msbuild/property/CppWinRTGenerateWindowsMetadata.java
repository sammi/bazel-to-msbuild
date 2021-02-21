package com.tuware.msbuild.domain.msbuild.property;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class CppWinRTGenerateWindowsMetadata {
    private boolean value;
}
