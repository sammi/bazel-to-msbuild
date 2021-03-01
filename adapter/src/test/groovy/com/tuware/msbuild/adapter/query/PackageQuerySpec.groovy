package com.tuware.msbuild.adapter.query

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class PackageQuerySpec extends Specification {

    PackageQuery packageQueryAdapter = new PackageQuery(new BazelProcessBuilder())

    def "parse the result from bazel query command as Build.QueryResult object"() {

        given:
        Path bazelWorkspaceAbsolutePath = Paths.get(new ClassPathResource("stage1").getFile().getAbsolutePath())

        when:
        def expectRule = Build.Rule.newBuilder()
                .setName("//main:hello-world")
                .setRuleClass("cc_binary")
                .addRuleInput("//main:hello-world.cc")
                .build()
        def expectTarget = new Build.Target.Builder()
                .setType(Build.Target.Discriminator.RULE)
                .setRule(expectRule)
        //start bazel by cmd /c, so no need find absolute file path for bazel command, the windows will look up by PATH
        //--batch is required, otherwise the target/test-classes/stage1 and target/test-classes/stage3
        //will be holding after tests are done
        //When run mvn clean  again, it will be broken because the target folder cannot be deleted.
        List<String> commands = Arrays.asList(
                "cmd",
                "/c",
                "bazel",
                "--batch",
                "query",
                "...",
                "--output=proto")
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
