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

        String solutionName= "testSolution"

        UUID project1Uuid = UUID.randomUUID()
        UUID project2Uuid = UUID.randomUUID()
        String project1 = "project1"
        String project2 = "project2"

        ProjectSeed projectSeed1 = buildProjectSeed(project1, project1Uuid)
        ProjectSeed projectSeed2 = buildProjectSeed(project2, project2Uuid)

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
        cppProjectConverter.buildSolution(bazelWorkspaceFolder, msbuildSolutionFolder, solutionName, solutionUuid)

        then:

        1 * queryService.query(bazelWorkspaceFolder) >> queryResult

        1 * extractorService.extractProjectSeedList(queryResult) >> [projectSeed1, projectSeed2]
        2 * extractorService.extractProjectFilter() >> projectFilerSeed

        1 * extractorService.buildSolutionSeed(solutionUuid, solutionName, _, _) >> solutionSeed

        1 * composerService.composeProjectTemplateData(projectSeed1) >> cppProjectTemplate
        2 * composerService.composeCppProjectFilterTemplateData(projectFilerSeed) >> projectFilterTemplate
        2 * composerService.composeCppProjectUserTemplateData(_) >> projectUserTemplate
        1 * composerService.composeSolutionTemplateData(solutionSeed) >> solutionTemplate

        1 * generatorService.generateProjectXml(cppProjectTemplate) >> projectXml
        2 * generatorService.generateCppProjectFilterXml(projectFilterTemplate) >> projectFilterXml
        2 * generatorService.generateProjectUserXml(projectUserTemplate) >> projectUserXml
        1 * generatorService.generateSolution(solutionTemplate) >> solutionXml

        1 * repositoryService.saveProject(msbuildSolutionFolder, _, project1, projectXml)
        2 * repositoryService.saveProjectFilter(msbuildSolutionFolder, _, projectFilterXml)
        2 * repositoryService.saveProjectUser(msbuildSolutionFolder, _, projectUserXml)
        1 * repositoryService.saveSolution(msbuildSolutionFolder, solutionName, solutionXml)
    }

    private static ProjectSeed buildProjectSeed(String project1, UUID projectUuid) {
        ProjectSeed projectSeed = ProjectSeed.builder()
                .name(project1)
                .uuid(projectUuid)
                .sourceFileList(Collections.singletonList("test.cpp"))
                .build()
        projectSeed
    }

}
