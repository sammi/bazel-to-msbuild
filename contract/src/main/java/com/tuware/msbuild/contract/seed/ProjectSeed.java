package com.tuware.msbuild.contract.seed;

import com.tuware.msbuild.contract.msbuild.property.ConfigurationType;
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
    private Path folder;
    private String name;
    private List<String> sourceFileList;
    private List<String> headerFileList;
    private ConfigurationType configurationType;
    private List<ProjectSeed> dependentProjectSeedList;
}
