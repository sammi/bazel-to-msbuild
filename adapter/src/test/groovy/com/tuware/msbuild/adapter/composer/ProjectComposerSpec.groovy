package com.tuware.msbuild.adapter.composer


import com.tuware.msbuild.contract.seed.ProjectSeed
import com.tuware.msbuild.contract.template.ProjectTemplate
import spock.lang.Specification

class ProjectComposerSpec extends Specification {

    def "build default c++ project for single cpp source file"() {

        given:
        String sourceFile = "a.cpp"
        String headerFile = "a.h"
        UUID projectUuid = UUID.randomUUID()
        ProjectSeed projectSeed = ProjectSeed.builder()
            .sourceFileList(Collections.singletonList(sourceFile))
            .headerFileList(Collections.singletonList(headerFile))
            .uuid(projectUuid)
        .build()
        ProjectComposer templateFactory = new ProjectComposer()

        when:
        ProjectTemplate cppProjectTemplate = templateFactory.compose(projectSeed)

        then:
        cppProjectTemplate.getGlobals().getProjectGuid() == projectUuid
        cppProjectTemplate.getDefaultTargets() == "Build"
        cppProjectTemplate.getClCompileItemGroup().getClCompileList().get(0).getInclude() == sourceFile
    }
}
