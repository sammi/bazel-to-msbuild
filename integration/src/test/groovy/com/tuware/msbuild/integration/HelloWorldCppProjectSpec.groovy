package com.tuware.msbuild.integration

import com.github.jknack.handlebars.Handlebars
import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.adapter.composer.ProjectComposer
import com.tuware.msbuild.adapter.extractor.CCBinaryExtractor
import com.tuware.msbuild.adapter.generator.CppGenerator
import com.tuware.msbuild.adapter.generator.TemplateBuilder
import com.tuware.msbuild.adapter.provider.BazelQueryAllProtoProvider
import com.tuware.msbuild.adapter.query.BazelProcessBuilder
import com.tuware.msbuild.adapter.query.PackageQuery
import com.tuware.msbuild.adapter.repository.FileRepository
import com.tuware.msbuild.contract.adapter.*
import com.tuware.msbuild.contract.seed.ProjectSeed
import com.tuware.msbuild.contract.template.CppProjectTemplateData
import com.tuware.msbuild.feature.CppProjectFeature
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class HelloWorldCppProjectSpec extends Specification {

    def "generate msbuild solution from bazel hello world cpp project"() {
        given:
        Provider provider = new BazelQueryAllProtoProvider()
        BazelProcessBuilder bazelProcessBuilder = new BazelProcessBuilder()
        Query<Build.QueryResult> query = new PackageQuery(bazelProcessBuilder)
        Extractor<Build.QueryResult, ProjectSeed> extractor = new CCBinaryExtractor()
        Composer<CppProjectTemplateData, ProjectSeed> composer = new ProjectComposer()
        Handlebars handlebars = new Handlebars()
        TemplateBuilder templateBuilder = new TemplateBuilder(handlebars)
        Generator<CppProjectTemplateData> generator = new CppGenerator(templateBuilder)

        Path bazelWorkspaceFolder = Paths.get(new ClassPathResource("stage1").getFile().getAbsolutePath())
        Path msbuildProjectFilePath = Paths.get(new ClassPathResource("stage1").getFile().getAbsolutePath() + "/HelloWorld.vcxproj")
        FileRepository repository = Mock()

        CppProjectFeature cppProjectFeature = new CppProjectFeature(
                provider,
                query,
                extractor,
                composer,
                generator,
                repository
        )

        String xmlHeader = '<?xml version="1.0" encoding="utf-8"?>'

        when:
        cppProjectFeature.buildMsbuildSolutionFromBazelWorkspace(bazelWorkspaceFolder, msbuildProjectFilePath)

        then:
        1 * repository.save(msbuildProjectFilePath, { it.contains(xmlHeader) })

    }
}
