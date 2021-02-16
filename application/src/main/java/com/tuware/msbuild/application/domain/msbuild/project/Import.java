package com.tuware.msbuild.application.domain.msbuild.project;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Import {
    private String project;
    private String condition;
}
