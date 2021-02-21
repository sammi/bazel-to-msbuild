package com.tuware.msbuild.contract.msbuild.solution;

import lombok.*;

import java.nio.file.Path;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Project {
    private ProjectTypeGuid projectTypeGuid;
    private String name;
    private Path location;
    private UniqueProjectGuid uniqueProjectGuid;
}
