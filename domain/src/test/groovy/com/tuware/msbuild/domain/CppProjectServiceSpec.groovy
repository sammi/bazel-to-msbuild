package com.tuware.msbuild.domain

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.domain.msbuild.project.Project
import com.tuware.msbuild.domain.service.CppProjectService
import spock.lang.Specification

class CppProjectServiceSpec extends Specification {

    def "build msbuild project object from bazel project query result"() {
        given:
            Build.QueryResult queryResult = new Build.QueryResult()
        CppProjectService cppProjectService = new CppProjectService()

        when:
        Project project = cppProjectService.consoleApp(queryResult)

        then:
            project != null
    }
}

