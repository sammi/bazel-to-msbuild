package com.tuware.msbuild.adapter.query

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.adapter.provider.BazelQueryAllProtoCommandsProvider
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class PackageQuerySpec extends Specification {

    private static final String WORKSPACE = "workspace"

    PackageQuery packageQueryAdapter = new PackageQuery()

    def "parse the result from bazel query command as Build.QueryResult object"() {

        given:
        Path bazelWorkspaceAbsolutePath = Paths.get(new ClassPathResource(WORKSPACE).getFile().getAbsolutePath())

        when:
        def expectRule = Build.Rule.newBuilder()
                .setName("//main:hello-world")
                .setRuleClass("cc_binary")
                .addRuleInput("//main:hello-world.cc")
                .build()
        def expectTarget = new Build.Target.Builder()
                .setType(Build.Target.Discriminator.RULE)
                .setRule(expectRule)

        BazelQueryAllProtoCommandsProvider commandsProvider = new BazelQueryAllProtoCommandsProvider()
        List<String> commands = commandsProvider.provide()
        Build.QueryResult actualResult = packageQueryAdapter.query(bazelWorkspaceAbsolutePath, commands)

        then:
        Build.Target actualTarget = actualResult.getTarget(0)
        actualTarget.getType() == expectTarget.getType()
        def actualRule = actualTarget.getRule()
        actualRule.getName() == expectRule.getName()
        actualRule.getRuleClass() == expectRule.getRuleClass()
        actualRule.getRuleInput(0) == expectRule.getRuleInput(0)

    }

}
