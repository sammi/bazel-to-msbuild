package com.tuware.msbuild.usecase.generator

import com.tuware.msbuild.contract.msbuild.project.Project
import com.tuware.msbuild.contract.template.CppProjectTemplate
import com.tuware.msbuild.usecase.generator.CppProjectGenerator
import spock.lang.Specification

class CppProjectTemplateGeneratorSpec extends Specification {

    def "create default c++ project for single cpp source file"() {

        given:
        String sourceFile = "a.cpp"
        String projectUuid = UUID.randomUUID().toString()
        CppProjectGenerator cppProjectGenerator = new CppProjectGenerator()

        when:
        CppProjectTemplate cppProjectTemplate = cppProjectGenerator.createProject(sourceFile, projectUuid)

        then:
        cppProjectTemplate.getGlobals().getProjectGuid() == projectUuid
        cppProjectTemplate.getDefaultTargets() == "Build"
        cppProjectTemplate.getClCompileItemGroup().getClCompileList().get(0).getInclude() == sourceFile
    }

    def "create default c++ project user template"() {
        given:
        CppProjectGenerator cppProjectGenerator = new CppProjectGenerator()

        when:
        Project projectUser = cppProjectGenerator.createProjectUser()

        then:
        projectUser.getToolsVersion() == "Current"
    }

    def "create default c++ project filter template"() {
        given:
        CppProjectGenerator cppProjectGenerator = new CppProjectGenerator()

        when:
        UUID id1 = UUID.randomUUID()
        UUID id2 = UUID.randomUUID()
        UUID id3 = UUID.randomUUID()
        String sourceFilesFilterGuid = String.format("{%s}", id1)
        String headerFilesFilterGuid = String.format("{%s}", id2)
        String resourceFilesFilterGuid = String.format("{%s}", id3)
        Project projectUserFilter = cppProjectGenerator.createProjectFilter(
                sourceFilesFilterGuid, headerFilesFilterGuid, resourceFilesFilterGuid)

        then:
        projectUserFilter != null
    }
}
