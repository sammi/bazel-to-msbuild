package com.tuware.msbuild.contract.msbuild;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class Config {
    private String key;
    private String value;
}
