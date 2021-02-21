package com.tuware.msbuild.model.msbuild.midl;


import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ProxyFileName {
    private String condition;
    private boolean value;
}
