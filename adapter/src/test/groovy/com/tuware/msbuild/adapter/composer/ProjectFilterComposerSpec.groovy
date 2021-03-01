package com.tuware.msbuild.adapter.composer

import com.tuware.msbuild.contract.msbuild.project.Project
import com.tuware.msbuild.contract.seed.ProjectFilerSeed
import spock.lang.Specification

class ProjectFilterComposerSpec extends Specification {

    def "build default c++ project filter template"() {
        given:
        ProjectFilterComposer projectFilterComposer = new ProjectFilterComposer()

        when:
        Project projectUserFilter = projectFilterComposer.compose(ProjectFilerSeed.builder().build())

        then:
        projectUserFilter != null
    }

}
