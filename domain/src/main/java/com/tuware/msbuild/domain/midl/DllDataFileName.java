package com.tuware.msbuild.domain.midl;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class DllDataFileName {
    private String condition;
    private String value;
}
