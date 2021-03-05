package com.tuware.msbuild.contract.seed;

import com.tuware.msbuild.contract.msbuild.solution.ProjectTypeGuid;
import lombok.*;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class SolutionSeed {
    private UUID uuid;
    private String name;
    private Path path;

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class Project {
        private ProjectTypeGuid typeUuid;
        private UUID uuid;
        private String name;
        private Path path;
    }

    private List<Project> projectList;

    private Path projectFilePath;
    private UUID projectUuid;
}
