package com.tuware.msbuild.adapter.composer

import com.tuware.msbuild.contract.input.ProjectFilerInput
import com.tuware.msbuild.contract.input.ProjectInput
import com.tuware.msbuild.contract.input.SolutionInput
import com.tuware.msbuild.contract.msbuild.project.Project
import com.tuware.msbuild.contract.msbuild.solution.MSBuildVersion
import com.tuware.msbuild.contract.msbuild.solution.MsBuildEnvironment
import com.tuware.msbuild.contract.msbuild.solution.Solution
import com.tuware.msbuild.contract.template.CppProjectTemplate
import spock.lang.Specification

class ComposerSpec extends Specification {

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

    def "build default c++ project user template"() {
        given:
        ProjectUserComposer cppProjectUserComposer = new ProjectUserComposer()

        when:
        Project projectUser = cppProjectUserComposer.compose(new Object())

        then:
        projectUser.getToolsVersion() == "Current"
    }

    def "build default c++ project filter template"() {
        given:
        ProjectFilterComposer projectFilterComposer = new ProjectFilterComposer()

        when:
        Project projectUserFilter = projectFilterComposer.compose(ProjectFilerInput.builder().build())

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
        SolutionComposer solutionComposer = new SolutionComposer(msBuildEnvironment)

        when:
        Solution solution = solutionComposer.compose(SolutionInput.builder().location("fakeLocation").build())

        then:
        solution != null

    }
}
