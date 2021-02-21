package com.tuware.msbuild.usecase

import spock.lang.Specification

class CppProjectUseCaseSpec extends Specification{

    def "run bazel query for a c++ project with 1 source file and then generate visual studio project files"() {
        expect:
            true
    }
}
