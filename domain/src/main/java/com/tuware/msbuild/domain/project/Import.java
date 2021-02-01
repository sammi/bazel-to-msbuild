package com.tuware.msbuild.domain.project;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Import {
    private String project;
    private String condition;
}
