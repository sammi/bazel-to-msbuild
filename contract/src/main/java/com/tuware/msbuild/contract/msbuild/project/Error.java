package com.tuware.msbuild.contract.msbuild.project;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Error {
    private String condition;
    private String text;
}
