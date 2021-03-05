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
        Path msbuildSolutionFolder = Stub(Path.class)
        File file = Stub(File.class)
        file.getPath() >> "FakePath"
        msbuildSolutionFolder.toFile() >> file

        Build.QueryResult queryResult = GroovyMock()

        UUID projectUuid = UUID.randomUUID()
        String projectName = "testProject"
        ProjectSeed projectSeed = ProjectSeed.builder()
            .name(projectName)
            .uuid(projectUuid)
            .cppFileName(GroovyMock(String.class))
        .build()

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
        UUID solutionUuid = UUID.randomUUID()

        when:
        cppProjectConverter.buildSingleProjectSolution(bazelWorkspaceFolder, msbuildSolutionFolder, projectName, solutionUuid, projectUuid)

        then:

        1 * queryService.query(bazelWorkspaceFolder) >> queryResult

        1 * extractorService.extractProject(queryResult, projectUuid) >> projectSeed
        1 * extractorService.extractProjectFilter() >> projectFilerSeed

        1 * extractorService.buildSolutionSeed(solutionUuid, projectName, _, _) >> solutionSeed

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
