package com.tuware.msbuild.domain.midl;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class HeaderFileName {
    private String condition;
    private String value;
}
