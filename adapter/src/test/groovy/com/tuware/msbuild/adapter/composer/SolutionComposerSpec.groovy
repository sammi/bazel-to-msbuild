package com.tuware.msbuild.adapter.composer

import com.tuware.msbuild.contract.msbuild.solution.MSBuildVersion
import com.tuware.msbuild.contract.msbuild.solution.MsBuildEnvironment
import com.tuware.msbuild.contract.msbuild.solution.Solution
import com.tuware.msbuild.contract.seed.SolutionSeed
import spock.lang.Specification

class SolutionComposerSpec extends Specification{

    def "build default solution template data"() {
        given:
        MsBuildEnvironment msBuildEnvironment = MsBuildEnvironment.builder()
                .formatVersion(MSBuildVersion.builder().major("12").minor("00").build())
                .visualStudioVersion(MSBuildVersion.builder().major("16").minor("0").patch("30804").revision("86").build())
                .minimumVisualStudioVersion(MSBuildVersion.builder().major("10").minor("0").patch("40219").revision("1").build())
                .build()
        SolutionComposer solutionComposer = new SolutionComposer(msBuildEnvironment)

        when:
        Solution solution = solutionComposer.compose(SolutionSeed.builder().location("fakeLocation").build())

        then:
        solution != null

    }

}
