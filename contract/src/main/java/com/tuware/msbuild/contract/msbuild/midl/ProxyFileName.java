package com.tuware.msbuild.contract.msbuild.midl;


import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ProxyFileName {
    private String condition;
    private boolean value;
}
