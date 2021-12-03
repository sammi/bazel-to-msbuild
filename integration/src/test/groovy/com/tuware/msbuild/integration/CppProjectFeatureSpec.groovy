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
import com.tuware.msbuild.adapter.provider.BazelQueryAllProtoCommandsProvider
import com.tuware.msbuild.adapter.provider.ProjectFilterSeedProvider
import com.tuware.msbuild.adapter.query.PackageQuery
import com.tuware.msbuild.adapter.repository.FileRepository
import com.tuware.msbuild.contract.adapter.Provider
import com.tuware.msbuild.contract.seed.ProjectFilerSeed
import com.tuware.msbuild.feature.CppProjectFeature
import com.tuware.msbuild.feature.service.*
import org.springframework.core.io.ClassPathResource
import spock.lang.Ignore
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

@Ignore
class CppProjectFeatureSpec extends Specification {

    def "generate bazel cpp project with only 1 source file solution"() {
        given:
        Path bazelWorkspaceFolder = Paths.get(new ClassPathResource("stage1").getFile().getAbsolutePath())
        FileRepository repository = Mock()
        CppProjectFeature cppProjectFeature = buildFeature(repository)

        when:
        cppProjectFeature.buildSolution(bazelWorkspaceFolder, "App1")

        then:
        4 * repository.save({ it ->
            {
                String path = it.toString()
                path.contains("main") || path.contains("App1")
            }
        }, { it != null })

    }

    def "generate bazel cpp project with only source and header file solution"() {
        given:
        Path bazelWorkspaceFolder = Paths.get(new ClassPathResource("stage2").getFile().getAbsolutePath())
        FileRepository repository = Mock()
        CppProjectFeature cppProjectFeature = buildFeature(repository)

        when:
        cppProjectFeature.buildSolution(bazelWorkspaceFolder, "App2")

        then:
        7 * repository.save({ it ->
            {
                String path = it.toString()
                path.contains("main") || path.contains("App2")
            }
        }, { it != null })

    }

    def "generate bazel cpp project with multiple packages solution"() {
        given:
        Path bazelWorkspaceFolder = Paths.get(new ClassPathResource("stage3").getFile().getAbsolutePath())
        FileRepository repository = Mock()
        CppProjectFeature cppProjectFeature = buildFeature(repository)

        when:
        cppProjectFeature.buildSolution(bazelWorkspaceFolder, "App3")

        then:
        10 * repository.save({ it ->
            {
                String path = it.toString()
                path.contains("stage3") || path.contains("hello-")
            }
        }, { it != null })

    }

    def "generate bazel cpp project for dll library sample project"() {
        given:
        Path bazelWorkspaceFolder = Paths.get(new ClassPathResource("dll").getFile().getAbsolutePath())
        FileRepository repository = Mock()
        CppProjectFeature cppProjectFeature = buildFeature(repository)

        when:
        cppProjectFeature.buildSolution(bazelWorkspaceFolder, "App4")

        then:
        7 * repository.save({ it ->
            {
                String path = it.toString()
                path.contains("dll")
            }
        }, { it != null })

    }

    private static CppProjectFeature buildFeature(FileRepository repository) {
        BazelQueryAllProtoCommandsProvider bazelQueryAllProtoProvider = new BazelQueryAllProtoCommandsProvider()
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
        RepositoryService repositoryService = new RepositoryService(repository)
        return new CppProjectFeature(queryService, composerService, extractorService, generatorService, repositoryService)
    }
}
