package com.tuware.msbuild.adapter.composer

import com.tuware.msbuild.contract.msbuild.project.Project
import spock.lang.Specification

class ProjectUserComposerSpec extends Specification {

    def "build default c++ project user template"() {
        given:
        ProjectUserComposer cppProjectUserComposer = new ProjectUserComposer()

        when:
        Project projectUser = cppProjectUserComposer.compose(new Object())

        then:
        projectUser.getToolsVersion() == "Current"
    }

}
