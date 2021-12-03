package com.tuware.msbuild.adapter.provider

import org.apache.commons.lang3.SystemUtils
import spock.lang.Specification

class BazelQueryAllProtoCommandsProviderSpec extends Specification {

    BazelQueryAllProtoCommandsProvider bazelQueryAllProtoCommandsProvider = new BazelQueryAllProtoCommandsProvider()

    def "bazel command is using cmd -c in windows and output is proto"() {
        when:
        def commands = bazelQueryAllProtoCommandsProvider.provide()

        then:
        if (SystemUtils.IS_OS_WINDOWS)
            commands == ["cmd", "/c", "bazel", "--batch", "query", "...", "--output=proto"]
        else
            commands == ["bazel", "--batch", "query", "...", "--output=proto"]

    }

}
