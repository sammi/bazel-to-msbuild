package com.tuware.msbuild.contract.seed;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ProjectFilerSeed {
    private String sourceFilesFilterGuid;
    private String headerFilesFilterGuid;
    private String resourceFilesFilterGuid;
}
