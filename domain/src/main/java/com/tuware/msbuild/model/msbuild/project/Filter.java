package com.tuware.msbuild.model.msbuild.project;

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
