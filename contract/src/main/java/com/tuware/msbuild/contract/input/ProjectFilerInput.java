package com.tuware.msbuild.contract.input;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ProjectFilerInput {
    private String sourceFilesFilterGuid;
    private String headerFilesFilterGuid;
    private String resourceFilesFilterGuid;
}
