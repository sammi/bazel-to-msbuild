package com.tuware.msbuild.contract.seed;

import lombok.*;

import java.nio.file.Path;
import java.util.UUID;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class ProjectSeed {
    private String cppFileName;
    private UUID projectGuid;
    private Path projectFilePath;
}
