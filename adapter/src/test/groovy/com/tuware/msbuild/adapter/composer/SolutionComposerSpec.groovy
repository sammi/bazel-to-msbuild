package com.tuware.msbuild.adapter.composer


import com.tuware.msbuild.contract.msbuild.solution.Solution
import com.tuware.msbuild.contract.seed.SolutionSeed
import spock.lang.Specification

class SolutionComposerSpec extends Specification {

    def "build default solution template data"() {
        given:
        SolutionComposer solutionComposer = new SolutionComposer()

        when:
        Solution solution = solutionComposer.compose(SolutionSeed.builder().location("fakeLocation").build())

        then:
        solution != null

    }

}
