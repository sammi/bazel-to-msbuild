package com.tuware.msbuild.adapter.composer


import com.tuware.msbuild.contract.input.ProjectInput
import com.tuware.msbuild.contract.template.CppProjectTemplate
import spock.lang.Specification

class ProjectComposerSpec extends Specification {

    def "build default c++ project for single cpp source file"() {

        given:
        String sourceFile = "a.cpp"
        String projectUuid = UUID.randomUUID().toString()
        ProjectInput projectInput = ProjectInput.builder().cppFileName(sourceFile).projectGuild(projectUuid).build()
        ProjectComposer templateFactory = new ProjectComposer()

        when:
        CppProjectTemplate cppProjectTemplate = templateFactory.compose(projectInput)

        then:
        cppProjectTemplate.getGlobals().getProjectGuid() == projectUuid
        cppProjectTemplate.getDefaultTargets() == "Build"
        cppProjectTemplate.getClCompileItemGroup().getClCompileList().get(0).getInclude() == sourceFile
    }
}
