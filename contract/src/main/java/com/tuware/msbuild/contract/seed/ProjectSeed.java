package com.tuware.msbuild.contract.seed;

import lombok.*;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class ProjectSeed {
    private UUID uuid;
    private Path path;
    private String name;
    private List<String> sourceFileList;
}
