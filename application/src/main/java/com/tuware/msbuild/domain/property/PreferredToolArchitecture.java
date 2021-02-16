package com.tuware.msbuild.domain.property;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class PreferredToolArchitecture {
    private String value;
}
