package com.tuware.msbuild.feature

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.contract.msbuild.project.Project
import com.tuware.msbuild.contract.msbuild.solution.Solution
import com.tuware.msbuild.contract.seed.ProjectFilerSeed
import com.tuware.msbuild.contract.seed.ProjectSeed
import com.tuware.msbuild.contract.seed.SolutionSeed
import com.tuware.msbuild.contract.template.ProjectTemplate
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

        String solutionName= "testSolution"

        Path bazelWorkspaceFolder = Mock()
        Path msbuildSolutionFolder = Stub(Path.class)
        File file = Stub(File.class)
        file.getPath() >> "testSolutionPath"
        msbuildSolutionFolder.toFile() >> file

        Build.QueryResult queryResult = GroovyMock()

        UUID project1Uuid = UUID.randomUUID()
        String projectName1 = "projectName1"
        File projectFile1 = Stub(File.class)
        projectFile1.getPath() >> "projectName1Path"
        Path projectPath1 = Stub(Path.class)
        projectPath1.toFile() >> projectFile1

        UUID project2Uuid = UUID.randomUUID()
        String projectName2 = "projectName2"
        File projectFile2 = Stub(File.class)
        projectFile2.getPath() >> "projectName2Path"
        Path projectPath2 = Stub(Path.class)
        projectPath2.toFile() >> projectFile2

        ProjectSeed projectSeed1 = buildProjectSeed(projectPath1, projectName1, project1Uuid)
        ProjectSeed projectSeed2 = buildProjectSeed(projectPath2, projectName2, project2Uuid)

        List<ProjectSeed> projectSeedList = [projectSeed1, projectSeed2]

        ProjectFilerSeed projectFilerSeed1 = Stub()
        projectFilerSeed1.getSourceFileList() >> ["test1.cpp"]

        ProjectFilerSeed projectFilerSeed2 = Stub()
        projectFilerSeed2.getSourceFileList() >> ["test2.cpp"]

        SolutionSeed solutionSeed = Mock()

        ProjectTemplate cppProjectTemplate1 = Mock()
        ProjectTemplate cppProjectTemplate2 = Mock()

        Project projectFilterTemplate1 = Mock()

        Project projectFilterTemplate2 = Mock()

        Project projectUserTemplate = Mock()
        Solution solutionTemplate = Mock()

        String projectXml1 = GroovyMock()
        String projectXml2 = GroovyMock()

        String projectFilterXml1 = GroovyMock()
        String projectFilterXml2 = GroovyMock()

        String projectUserXml = GroovyMock()
        String solutionXml = GroovyMock()

        when:
        cppProjectConverter.buildSolution(bazelWorkspaceFolder, msbuildSolutionFolder, solutionName)

        then:

        1 * queryService.query(bazelWorkspaceFolder) >> queryResult

        1 * extractorService.extractProjectSeedList(queryResult) >> projectSeedList

        1 * extractorService.extractProjectFilter(projectSeed1) >> projectFilerSeed1
        1 * extractorService.extractProjectFilter(projectSeed2) >> projectFilerSeed2

        1 * extractorService.buildSolutionSeed(solutionName, _, _) >> solutionSeed

        1 * composerService.composeProjectTemplate(projectSeed1) >> cppProjectTemplate1
        1 * composerService.composeProjectTemplate(projectSeed2) >> cppProjectTemplate2

        1 * composerService.composeProjectFilterTemplate(projectFilerSeed1) >> projectFilterTemplate1
        1 * composerService.composeProjectFilterTemplate(projectFilerSeed2) >> projectFilterTemplate2

        2 * composerService.composeProjectUserTemplate() >> projectUserTemplate

        1 * composerService.composeSolutionTemplateData(solutionSeed) >> solutionTemplate

        1 * generatorService.generateProjectXml(cppProjectTemplate1) >> projectXml1
        1 * generatorService.generateProjectXml(cppProjectTemplate2) >> projectXml2

        1 * generatorService.generateProjectFilterXml(projectFilterTemplate1) >> projectFilterXml1
        1 * generatorService.generateProjectFilterXml(projectFilterTemplate2) >> projectFilterXml2

        2 * generatorService.generateProjectUserXml(projectUserTemplate) >> projectUserXml

        1 * generatorService.generateSolution(solutionTemplate) >> solutionXml

        1 * repositoryService.saveProject(msbuildSolutionFolder, projectPath1, projectName1, projectXml1)
        1 * repositoryService.saveProject(msbuildSolutionFolder, projectPath2, projectName2, projectXml2)

        1 * repositoryService.saveProjectFilter(msbuildSolutionFolder, projectPath1, projectName1, projectFilterXml1)
        1 * repositoryService.saveProjectFilter(msbuildSolutionFolder, projectPath2, projectName2, projectFilterXml2)

        1 * repositoryService.saveProjectUser(msbuildSolutionFolder, projectPath1, projectName1, projectUserXml)
        1 * repositoryService.saveProjectUser(msbuildSolutionFolder, projectPath2, projectName2, projectUserXml)

        1 * repositoryService.saveSolution(msbuildSolutionFolder, solutionName, solutionXml)
    }

    private static ProjectSeed buildProjectSeed(Path projectPath, String projectName, UUID projectUuid) {
        ProjectSeed projectSeed = ProjectSeed.builder()
                .name(projectName)
                .folder(projectPath)
                .uuid(projectUuid)
                .sourceFileList(Collections.singletonList("test.cpp"))
                .build()
        projectSeed
    }

}
