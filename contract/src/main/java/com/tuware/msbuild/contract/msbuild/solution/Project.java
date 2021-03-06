package com.tuware.msbuild.contract.msbuild.solution;

import lombok.*;

import java.nio.file.Path;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Project {
    private ProjectTypeGuid projectTypeGuid;
    private String name;
    private Path projectFilePath;
    private UniqueProjectGuid uuid;
}
