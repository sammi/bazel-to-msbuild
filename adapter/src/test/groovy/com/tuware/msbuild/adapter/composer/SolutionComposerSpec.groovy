package com.tuware.msbuild.adapter.composer


import com.tuware.msbuild.contract.msbuild.solution.Solution
import com.tuware.msbuild.contract.seed.SolutionSeed
import spock.lang.Specification

import java.nio.file.Path

class SolutionComposerSpec extends Specification {

    def "build default solution template data"() {
        given:
        SolutionComposer solutionComposer = new SolutionComposer()

        when:
        Solution solution = solutionComposer.compose(
                SolutionSeed.builder()
                        .projectFilePath(Mock(Path.class))
                    .projectList(Arrays.asList(
                            SolutionSeed.Project.builder().uuid(UUID.randomUUID()).path(Mock(Path.class)).build()
                    ))
                .build()
        )

        then:
        solution != null

    }

}
