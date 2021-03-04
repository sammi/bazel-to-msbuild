package com.tuware.msbuild.integration

import com.tuware.msbuild.adapter.composer.ProjectComposer
import com.tuware.msbuild.adapter.composer.ProjectFilterComposer
import com.tuware.msbuild.adapter.composer.ProjectUserComposer
import com.tuware.msbuild.adapter.composer.SolutionComposer

import com.tuware.msbuild.adapter.extractor.CcBinaryExtractor

import com.tuware.msbuild.adapter.generator.ProjectFilterGenerator
import com.tuware.msbuild.adapter.generator.ProjectGenerator
import com.tuware.msbuild.adapter.generator.ProjectUserGenerator
import com.tuware.msbuild.adapter.generator.SolutionGenerator
import com.tuware.msbuild.adapter.provider.BazelQueryAllProtoProvider
import com.tuware.msbuild.adapter.provider.ProjectFilterSeedProvider
import com.tuware.msbuild.adapter.query.PackageQuery
import com.tuware.msbuild.adapter.repository.FileRepository
import com.tuware.msbuild.contract.adapter.Provider
import com.tuware.msbuild.contract.seed.ProjectFilerSeed
import com.tuware.msbuild.feature.CppProjectFeature
import com.tuware.msbuild.feature.service.ComposerService
import com.tuware.msbuild.feature.service.ExtractorService
import com.tuware.msbuild.feature.service.GeneratorService
import com.tuware.msbuild.feature.service.QueryService
import com.tuware.msbuild.feature.service.RepositoryService
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class HelloWorldCppProjectSpec extends Specification {

    def "generate msbuild solution from bazel hello world cpp project"() {
        given:
        BazelQueryAllProtoProvider bazelQueryAllProtoProvider = new BazelQueryAllProtoProvider()
        PackageQuery packageQuery = new PackageQuery()
        QueryService queryService = new QueryService(bazelQueryAllProtoProvider, packageQuery)

        CcBinaryExtractor projectSeedExtractor = new CcBinaryExtractor()

        Provider<ProjectFilerSeed> projectFilerProvider = new ProjectFilterSeedProvider()

        ExtractorService extractorService = new ExtractorService(projectSeedExtractor, projectFilerProvider)

        ProjectComposer projectComposer = new ProjectComposer()
        ProjectFilterComposer projectFilterComposer = new ProjectFilterComposer()
        ProjectUserComposer projectUserComposer = new ProjectUserComposer()

        SolutionComposer solutionComposer = new SolutionComposer()

        ComposerService composerService = new ComposerService(projectComposer, projectFilterComposer, projectUserComposer, solutionComposer)

        ProjectGenerator projectGenerator = new ProjectGenerator()
        ProjectFilterGenerator projectFilterGenerator = new ProjectFilterGenerator()
        ProjectUserGenerator projectUserGenerator = new ProjectUserGenerator()
        SolutionGenerator solutionGenerator = new SolutionGenerator()

        GeneratorService generatorService = new GeneratorService(projectGenerator, projectFilterGenerator, solutionGenerator, projectUserGenerator)

        Path bazelWorkspaceFolder = Paths.get(new ClassPathResource("stage1").getFile().getAbsolutePath())
        Path msbuildSolutionFolder = Paths.get(new ClassPathResource("stage1").getFile().getAbsolutePath())
        FileRepository repository = Mock()
        RepositoryService repositoryService = new RepositoryService(repository)

        CppProjectFeature cppProjectFeature = new CppProjectFeature(queryService, composerService, extractorService, generatorService, repositoryService)

        when:
        cppProjectFeature.buildSingleProjectSolution(bazelWorkspaceFolder, msbuildSolutionFolder, "App", UUID.randomUUID(), UUID.randomUUID())

        then:
        4 * repository.save({ it -> it.toString().contains("App") }, { it != null })

    }
}
