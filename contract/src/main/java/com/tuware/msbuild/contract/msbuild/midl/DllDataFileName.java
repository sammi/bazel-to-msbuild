package com.tuware.msbuild.contract.msbuild.midl;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class DllDataFileName {
    private String condition;
    private String value;
}
