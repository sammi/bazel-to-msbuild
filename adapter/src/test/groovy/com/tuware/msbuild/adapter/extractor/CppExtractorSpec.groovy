package com.tuware.msbuild.adapter.extractor

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.contract.seed.ProjectSeed
import spock.lang.Specification

class CppExtractorSpec extends Specification {

    def "Extract map of cpp ProjectSeed from bazel query result object"() {
        given:

        CppExtractor cppExtractor = new CppExtractor()
        Build.QueryResult queryResult = Build.QueryResult.newBuilder()
            .addAllTarget(Arrays.asList(
                    buildCppRuleTarget("test1"),
                    buildCppRuleTarget("test2"),
            ))
        .build()

        when:
        List<ProjectSeed> projectSeeds = cppExtractor.extract(queryResult)

        then:
        projectSeeds.size() == 2
        projectSeeds.forEach(projectSeed -> {
            projectSeed.getName().contains("test")
            projectSeed.uuid == null
            projectSeed.getSourceFileList() == [
                    "main" + File.separator + "test.cpp",
                    "main" + File.separator + "test.h",
            ]
        })

    }

    private static Build.Target buildCppRuleTarget(String name) {
        Build.Target.newBuilder()
                .setType(Build.Target.Discriminator.RULE)
                .setRule(Build.Rule.newBuilder().setRuleClass("cc_binary")
                        .setName(name)
                        .addAllRuleInput(
                                Arrays.asList(
                                        "//main:test.cpp",
                                        "//main:test.h"
                                )
                        ).build()
                ).build()
    }
}
