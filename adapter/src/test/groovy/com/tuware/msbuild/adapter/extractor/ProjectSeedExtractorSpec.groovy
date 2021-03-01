package com.tuware.msbuild.adapter.extractor

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.contract.seed.ProjectSeed
import spock.lang.Specification

class ProjectSeedExtractorSpec extends Specification {

    def "Extract c++ source code from bazel cc_binary package"() {
        given:

        ProjectSeedExtractor ccBinaryMapper = new ProjectSeedExtractor()
        Build.QueryResult queryResult = Build.QueryResult.newBuilder().build()

        when:
        ProjectSeed projectSeed = ccBinaryMapper.extract(queryResult)

        then:
        projectSeed.getProjectGuild() != null
    }
}
