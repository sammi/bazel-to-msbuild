package com.tuware.msbuild.application.domain.msbuild.project;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Image {
    private String include;
    private Filter filter;
}
