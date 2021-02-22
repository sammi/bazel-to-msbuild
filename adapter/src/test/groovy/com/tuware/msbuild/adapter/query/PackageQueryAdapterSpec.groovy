package com.tuware.msbuild.adapter.query

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

class PackageQueryAdapterSpec extends Specification {

    PackageQueryAdapter packageQueryAdapter = new PackageQueryAdapter(new BazelWindowsProcessBuilder())

    def "run bazel query ... for hello world c++ project, and output as protobuf, parse the output"() {

        given:
        String bazelProjectRootPath = new ClassPathResource("stage1").getFile().getAbsoluteFile()

        when:
        def expectRule = Build.Rule.newBuilder()
                .setName("//main:hello-world")
                .setRuleClass("cc_binary")
                .addRuleInput("//main:hello-world.cc")
                .build()
        def expectTarget = new Build.Target.Builder()
                .setType(Build.Target.Discriminator.RULE)
                .setRule(expectRule)
        Build.QueryResult actualResult = packageQueryAdapter.query(bazelProjectRootPath, "...")

        then:
        Build.Target actualTarget = actualResult.getTarget(0)
        actualTarget.getType() == expectTarget.getType()
        def actualRule = actualTarget.getRule()
        actualRule.getName() == expectRule.getName()
        actualRule.getRuleClass() == expectRule.getRuleClass()
        actualRule.getRuleInput(0) == expectRule.getRuleInput(0)

    }

}
