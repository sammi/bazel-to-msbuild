package com.tuware.msbuild.usecase

import com.tuware.msbuild.contract.msbuild.solution.MSBuildVersion
import com.tuware.msbuild.contract.msbuild.solution.MsBuildEnvironment
import com.tuware.msbuild.contract.msbuild.solution.Solution
import spock.lang.Specification

import java.nio.file.Paths

class SolutionGeneratorSpec extends Specification{

    def "build default solution project"() {
        given:
        MsBuildEnvironment msBuildEnvironment = MsBuildEnvironment.builder()
                .formatVersion(MSBuildVersion.builder().major("12").minor("00").build())
                .visualStudioVersion(MSBuildVersion.builder().major("16").minor("0").patch("30804").revision("86").build())
                .minimumVisualStudioVersion(MSBuildVersion.builder().major("10").minor("0").patch("40219").revision("1").build())
                .build()
        SolutionGenerator solutionGenerator = new SolutionGenerator(msBuildEnvironment)

        when:
        UUID projectGuid = UUID.randomUUID()
        UUID solutionGuid = UUID.randomUUID()
        Solution solution = solutionGenerator.buildConsoleApp(
            "projectName",
            "projectLocation",
            Paths.get("TestCppWinRTConsoleAppSolution.sln"),
            projectGuid,
            solutionGuid
        )

        then:
        solution != null

    }
}
