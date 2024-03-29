package com.tuware.msbuild.contract.msbuild.midl;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class HeaderFileName {
    private String condition;
    private String value;
}
