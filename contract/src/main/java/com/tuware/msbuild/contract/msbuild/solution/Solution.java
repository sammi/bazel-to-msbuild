package com.tuware.msbuild.contract.msbuild.solution;

import lombok.*;

import java.nio.file.Path;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Solution {
    List<Project> projectList;
    private Path fileName;
    private MSBuildVersion formatVersion;
    private MSBuildVersion visualStudioVersion;
    private MSBuildVersion minimumVisualStudioVersion;
    private Global global;
}
