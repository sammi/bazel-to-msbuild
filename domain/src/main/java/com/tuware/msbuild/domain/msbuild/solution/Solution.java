package com.tuware.msbuild.domain.msbuild.solution;

import lombok.*;

import java.nio.file.Path;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Solution {
    private Path fileName;

    private MSBuildVersion formatVersion;
    private MSBuildVersion visualStudioVersion;
    private MSBuildVersion minimumVisualStudioVersion;

    List<Project> projectList;

    private Global global;
}
