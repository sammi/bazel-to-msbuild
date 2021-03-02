package com.tuware.msbuild.adapter.composer


import com.tuware.msbuild.contract.seed.ProjectSeed
import com.tuware.msbuild.contract.template.ProjectTemplateData
import spock.lang.Specification

class ProjectComposerSpec extends Specification {

    def "build default c++ project for single cpp source file"() {

        given:
        String sourceFile = "a.cpp"
        String projectUuid = UUID.randomUUID().toString()
        ProjectSeed projectSeed = ProjectSeed.builder().cppFileName(sourceFile).projectGuild(projectUuid).build()
        ProjectComposer templateFactory = new ProjectComposer()

        when:
        ProjectTemplateData cppProjectTemplate = templateFactory.compose(projectSeed)

        then:
        cppProjectTemplate.getGlobals().getProjectGuid() == projectUuid
        cppProjectTemplate.getDefaultTargets() == "Build"
        cppProjectTemplate.getClCompileItemGroup().getClCompileList().get(0).getInclude() == sourceFile
    }
}
