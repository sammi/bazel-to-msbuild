package com.tuware.msbuild.adapter.mapper

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.contract.input.ProjectInput
import spock.lang.Specification

class CCBinaryMapperSpec extends Specification {

    def "Extract c++ source code from bazel cc_binary package"() {
        given:
        CCBinaryMapper ccBinaryMapper = new CCBinaryMapper()
        Build.QueryResult queryResult = Build.QueryResult.newBuilder().build()

        when:
        ProjectInput projectInput = ccBinaryMapper.extract(queryResult)

        then:
        projectInput.getProjectGuild() != null
    }
}
