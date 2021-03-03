package com.tuware.msbuild.contract.seed;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class ProjectFilerSeed {
    private UUID sourceFilesFilterGuid;
    private UUID headerFilesFilterGuid;
    private UUID resourceFilesFilterGuid;
    private String sourceFile;
}
