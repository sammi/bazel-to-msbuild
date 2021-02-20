package com.tuware.msbuild.application.domain.msbuild.project;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Filter {
    private String include;
    private String uniqueIdentifier;
    private String extensions;
}