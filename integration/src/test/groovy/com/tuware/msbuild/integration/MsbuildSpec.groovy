package com.tuware.msbuild.integration

import com.tuware.msbuild.adapter.composer.ProjectComposer
import com.tuware.msbuild.adapter.composer.ProjectFilterComposer
import com.tuware.msbuild.adapter.composer.ProjectUserComposer
import com.tuware.msbuild.adapter.composer.SolutionComposer
import com.tuware.msbuild.adapter.extractor.CppExtractor
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
import com.tuware.msbuild.feature.service.*
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class MsbuildSpec extends Specification {

    def "generate msbuild solution from bazel hello-world cpp workspace"() {
        given:
        BazelQueryAllProtoProvider bazelQueryAllProtoProvider = new BazelQueryAllProtoProvider()
        PackageQuery packageQuery = new PackageQuery()
        QueryService queryService = new QueryService(bazelQueryAllProtoProvider, packageQuery)

        CppExtractor cppExtractor = new CppExtractor()

        Provider<ProjectFilerSeed> projectFilerProvider = new ProjectFilterSeedProvider()

        ExtractorService extractorService = new ExtractorService(cppExtractor, projectFilerProvider)

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
        cppProjectFeature.buildSolution(bazelWorkspaceFolder, msbuildSolutionFolder, "App")

        then:
        4 * repository.save({ it ->
            {
                String path = it.toString()
                path.contains("main") || path.contains("App")
            }
        }, { it != null })

    }
}
