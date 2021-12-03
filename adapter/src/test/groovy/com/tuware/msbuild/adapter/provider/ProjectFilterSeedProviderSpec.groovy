package com.tuware.msbuild.adapter.provider

import com.tuware.msbuild.contract.seed.ProjectFilerSeed
import spock.lang.Specification


class ProjectFilterSeedProviderSpec extends Specification {

    ProjectFilterSeedProvider projectFilterSeedProvider = new ProjectFilterSeedProvider()

    def "generate ProjectFilerSeed fields with header, source, and resource UUID"() {

        when:
        ProjectFilerSeed projectFilerSeed = projectFilterSeedProvider.provide()

        then:
        UUID.fromString(projectFilerSeed.getHeaderFilesFilterGuid().toString()) != null
        UUID.fromString(projectFilerSeed.getSourceFilesFilterGuid().toString()) != null
        UUID.fromString(projectFilerSeed.getResourceFilesFilterGuid().toString()) != null
    }
}
