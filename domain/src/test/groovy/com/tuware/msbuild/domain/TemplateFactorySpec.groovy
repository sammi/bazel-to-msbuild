package com.tuware.msbuild.domain

import com.tuware.msbuild.contract.msbuild.project.Project
import com.tuware.msbuild.contract.msbuild.solution.MSBuildVersion
import com.tuware.msbuild.contract.msbuild.solution.MsBuildEnvironment
import com.tuware.msbuild.contract.msbuild.solution.Solution
import com.tuware.msbuild.contract.template.CppProjectTemplate
import spock.lang.Specification

import java.nio.file.Paths

class TemplateFactorySpec extends Specification {

    def "build default c++ project for single cpp source file"() {

        given:
        String sourceFile = "a.cpp"
        String projectUuid = UUID.randomUUID().toString()
        TemplateFactory cppProjectGenerator = new TemplateFactory()

        when:
        CppProjectTemplate cppProjectTemplate = cppProjectGenerator.createCppProject(sourceFile, projectUuid)

        then:
        cppProjectTemplate.getGlobals().getProjectGuid() == projectUuid
        cppProjectTemplate.getDefaultTargets() == "Build"
        cppProjectTemplate.getClCompileItemGroup().getClCompileList().get(0).getInclude() == sourceFile
    }

    def "build default c++ project user template"() {
        given:
        TemplateFactory cppProjectGenerator = new TemplateFactory()

        when:
        Project projectUser = cppProjectGenerator.createProjectUser()

        then:
        projectUser.getToolsVersion() == "Current"
    }

    def "build default c++ project filter template"() {
        given:
        TemplateFactory cppProjectGenerator = new TemplateFactory()

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

    def "build default solution template data"() {
        given:
        MsBuildEnvironment msBuildEnvironment = MsBuildEnvironment.builder()
                .formatVersion(MSBuildVersion.builder().major("12").minor("00").build())
                .visualStudioVersion(MSBuildVersion.builder().major("16").minor("0").patch("30804").revision("86").build())
                .minimumVisualStudioVersion(MSBuildVersion.builder().major("10").minor("0").patch("40219").revision("1").build())
                .build()
        TemplateFactory templateFactory = new TemplateFactory(msBuildEnvironment)

        when:
        UUID projectGuid = UUID.randomUUID()
        UUID solutionGuid = UUID.randomUUID()
        Solution solution = templateFactory.buildConsoleApp(
                "projectName",
                "projectLocation",
                Paths.get("TestCppWinRTConsoleAppSolution.sln"),
                projectGuid,
                solutionGuid
        )

        then:
        solution != null

    }
}
