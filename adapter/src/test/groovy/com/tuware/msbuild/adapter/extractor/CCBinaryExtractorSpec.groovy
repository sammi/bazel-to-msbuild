package com.tuware.msbuild.adapter.extractor

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.contract.input.ProjectInput
import spock.lang.Specification

class CCBinaryExtractorSpec extends Specification {

    def "Extract c++ source code from bazel cc_binary package"() {
        given:
        CCBinaryExtractor ccBinaryMapper = new CCBinaryExtractor()
        Build.QueryResult queryResult = Build.QueryResult.newBuilder().build()

        when:
        ProjectInput projectInput = ccBinaryMapper.extract(queryResult)

        then:
        projectInput.getProjectGuild() != null
    }
}
