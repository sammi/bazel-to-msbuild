package com.tuware.msbuild.adapter.provider;

import com.tuware.msbuild.contract.adapter.Provider;
import com.tuware.msbuild.contract.seed.ProjectFilerSeed;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProjectFilterSeedProvider implements Provider<ProjectFilerSeed> {

    @Override
    public ProjectFilerSeed provide() {
        return ProjectFilerSeed.builder()
            .headerFilesFilterGuid(UUID.randomUUID())
            .sourceFilesFilterGuid(UUID.randomUUID())
            .resourceFilesFilterGuid(UUID.randomUUID())
        .build();
    }
}
