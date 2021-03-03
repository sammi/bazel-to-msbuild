package com.tuware.msbuild.feature

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.contract.msbuild.project.Project
import com.tuware.msbuild.contract.msbuild.solution.Solution
import com.tuware.msbuild.contract.seed.ProjectFilerSeed
import com.tuware.msbuild.contract.seed.ProjectSeed
import com.tuware.msbuild.contract.seed.SolutionSeed
import com.tuware.msbuild.contract.template.ProjectTemplateData
import com.tuware.msbuild.feature.service.*
import spock.lang.Specification

import java.nio.file.Path

class CppProjectFeatureSpec extends Specification {

    def "convert bazel cpp project to visual studio msbuild project and solution"() {

        given:
        QueryService queryService = Mock()
        ExtractorService extractorService = Mock()
        ComposerService composerService = Mock()
        GeneratorService generatorService = Mock()
        RepositoryService repositoryService = Mock()

        CppProjectFeature cppProjectConverter = new CppProjectFeature(
                queryService,
                composerService,
                extractorService,
                generatorService,
                repositoryService
        )

        Path bazelWorkspaceFolder = Mock()
        Path msbuildSolutionFolder = Mock()

        Build.QueryResult queryResult = GroovyMock()

        UUID projectUUID = UUID.randomUUID()
        ProjectSeed projectSeed = ProjectSeed.builder().cppFileName(GroovyMock(String.class)).projectGuid(projectUUID).build()

        ProjectFilerSeed projectFilerSeed = Mock()
        SolutionSeed solutionSeed = Mock()

        ProjectTemplateData cppProjectTemplate = Mock()
        Project projectFilterTemplate = Mock()
        Project projectUserTemplate = Mock()
        Solution solutionTemplate = Mock()

        String projectXml = GroovyMock()
        String projectFilterXml = GroovyMock()
        String projectUserXml = GroovyMock()
        String solutionXml = GroovyMock()
        String projectName = GroovyMock()

        when:
        cppProjectConverter.buildMsbuildSolutionFromBazelWorkspace(bazelWorkspaceFolder, msbuildSolutionFolder, projectName)

        then:

        1 * queryService.query(bazelWorkspaceFolder) >> queryResult

        1 * extractorService.extractProject(queryResult) >> projectSeed
        1 * extractorService.extractProjectFilter(queryResult) >> projectFilerSeed

        1 * extractorService.buildSolutionSeed(msbuildSolutionFolder, projectName, _, projectUUID) >> solutionSeed

        1 * composerService.composeProjectTemplateData(projectSeed) >> cppProjectTemplate
        1 * composerService.composeCppProjectFilterTemplateData(projectFilerSeed) >> projectFilterTemplate
        1 * composerService.composeCppProjectUserTemplateData(_) >> projectUserTemplate
        1 * composerService.composeSolutionTemplateData(solutionSeed) >> solutionTemplate

        1 * generatorService.generateProjectXml(cppProjectTemplate) >> projectXml
        1 * generatorService.generateCppProjectFilterXml(projectFilterTemplate) >> projectFilterXml
        1 * generatorService.generateProjectUserXml(projectUserTemplate) >> projectUserXml
        1 * generatorService.generateSolution(solutionTemplate) >> solutionXml

        1 * repositoryService.saveProject(msbuildSolutionFolder, projectName, projectXml)
        1 * repositoryService.saveProjectFilter(msbuildSolutionFolder, projectName, projectFilterXml)
        1 * repositoryService.saveProjectUser(msbuildSolutionFolder, projectName, projectUserXml)
        1 * repositoryService.saveSolution(msbuildSolutionFolder, projectName, solutionXml)
    }

}
