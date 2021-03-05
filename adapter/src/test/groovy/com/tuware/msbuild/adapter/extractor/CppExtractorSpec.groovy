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
        Map<String,ProjectSeed> projectSeedMap = cppExtractor.extract(queryResult)

        then:
        projectSeedMap.size() == 2
        projectSeedMap.forEach((k, v) -> {
            k.contains("test")
            v.projectGuid == null
            v.getSourceFileList() == [
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
