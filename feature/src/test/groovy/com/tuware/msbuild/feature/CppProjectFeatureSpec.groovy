package com.tuware.msbuild.feature

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.contract.adapter.Repository
import com.tuware.msbuild.contract.msbuild.project.Project
import com.tuware.msbuild.contract.msbuild.solution.Solution
import com.tuware.msbuild.contract.seed.ProjectFilerSeed
import com.tuware.msbuild.contract.seed.ProjectSeed
import com.tuware.msbuild.contract.seed.SolutionSeed
import com.tuware.msbuild.contract.template.ProjectTemplateData
import com.tuware.msbuild.feature.service.ComposerService
import com.tuware.msbuild.feature.service.ExtractorService
import com.tuware.msbuild.feature.service.GeneratorService
import com.tuware.msbuild.feature.service.QueryService
import spock.lang.Specification

import java.nio.file.Path

class CppProjectFeatureSpec extends Specification {

    def "convert bazel cpp project to visual studio msbuild project and solution"() {

        given:
        QueryService queryService = Mock()
        ExtractorService extractorService = Mock()
        ComposerService composerService = Mock()
        GeneratorService generatorService = Mock()
        Repository<Path, String> repository = Mock()

        CppProjectFeature cppProjectConverter = new CppProjectFeature(
                queryService,
                composerService,
                extractorService,
                generatorService,
                repository
        )

        Path bazelWorkspaceFolder = Mock()
        Path msbuildSolutionFolder = Mock()

        Build.QueryResult queryResult = GroovyMock()
        ProjectSeed projectSeed = Mock()

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
        String projectName = "App"

        when:
        cppProjectConverter.buildMsbuildSolutionFromBazelWorkspace(bazelWorkspaceFolder, msbuildSolutionFolder, projectName)

        then:

        1 * queryService.query(bazelWorkspaceFolder) >> queryResult

        1 * extractorService.extractProject(queryResult) >> projectSeed
        1 * extractorService.extractProjectFilter(queryResult) >> projectFilerSeed
        1 * extractorService.extractSolution(queryResult) >> solutionSeed

        1 * composerService.composeProjectTemplateData(projectSeed) >> cppProjectTemplate
        1 * composerService.composeCppProjectFilterTemplateData(projectFilerSeed) >> projectFilterTemplate
        1 * composerService.composeCppProjectUserTemplateData(_) >> projectUserTemplate
        1 * composerService.composeSolutionTemplateData(solutionSeed) >> solutionTemplate

        1 * generatorService.generateProjectXml(cppProjectTemplate) >> projectXml
        1 * generatorService.generateCppProjectFilterXml(projectFilterTemplate) >> projectFilterXml
        1 * generatorService.generateProjectUserXml(projectUserTemplate) >> projectUserXml
        1 * generatorService.generateSolution(solutionTemplate) >> solutionXml

        1 * repository.save(_, projectXml)
        1 * repository.save(_, projectFilterXml)
        1 * repository.save(_, projectUserXml)
        1 * repository.save(_, solutionXml)
    }

}
