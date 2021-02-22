package com.tuware.msbuild.adapter.composer

import com.tuware.msbuild.contract.input.ProjectFilerInput
import com.tuware.msbuild.contract.msbuild.project.Project
import spock.lang.Specification

class ProjectFilterComposerSpec extends Specification{

    def "build default c++ project filter template"() {
        given:
        ProjectFilterComposer projectFilterComposer = new ProjectFilterComposer()

        when:
        Project projectUserFilter = projectFilterComposer.compose(ProjectFilerInput.builder().build())

        then:
        projectUserFilter != null
    }

}
