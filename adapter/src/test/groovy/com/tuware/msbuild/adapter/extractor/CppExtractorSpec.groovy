package com.tuware.msbuild.adapter.extractor

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.contract.seed.ProjectSeed
import spock.lang.Specification

class CppExtractorSpec extends Specification {

    def "Extract ProjectSeed list from bazel query result object"() {
        given:

        CppExtractor cppExtractor = new CppExtractor()
        Build.QueryResult queryResult = Build.QueryResult.newBuilder()
            .addAllTarget(Arrays.asList(
                    buildCppRuleTarget("test1", "test1.cpp", "test1.hpp"),
                    buildCppRuleTarget("test2", "test2.cc", "test2.h"),
            ))
        .build()

        when:
        List<ProjectSeed> projectSeeds = cppExtractor.extract(queryResult)

        then:
        projectSeeds.size() == 2

        projectSeeds[0].getName() == "test1"
        projectSeeds[0].uuid == null
        projectSeeds[0].getSourceFileList() == ["test1.cpp"]
        projectSeeds[0].getHeaderFileList() == ["test1.hpp"]

        projectSeeds[1].getName() == "test2"
        projectSeeds[1].uuid == null
        projectSeeds[1].getSourceFileList() == ["test2.cc"]
        projectSeeds[1].getHeaderFileList() == ["test2.h"]
    }

    private static Build.Target buildCppRuleTarget(String name, String sourceFile, String headerFile) {
        Build.Target.newBuilder()
                .setType(Build.Target.Discriminator.RULE)
                .setRule(Build.Rule.newBuilder().setRuleClass("cc_binary")
                        .setName(name)
                        .addAllRuleInput(
                                Arrays.asList(
                                        "//main:" + sourceFile,
                                        "//main:" + headerFile
                                )
                        ).build()
                ).build()
    }
}
