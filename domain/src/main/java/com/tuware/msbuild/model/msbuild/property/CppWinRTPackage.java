package com.tuware.msbuild.model.msbuild.property;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class CppWinRTPackage {
    private String condition;
    private boolean value;
}
