package com.tuware.msbuild.feature


import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.contract.adapter.*
import com.tuware.msbuild.contract.seed.ProjectSeed
import com.tuware.msbuild.contract.template.CppProjectTemplateData
import spock.lang.Specification

import java.nio.file.Path

class CppProjectFeatureSpec extends Specification{

    def "convert bazel cpp project to visual studio msbuild project and solution"() {

        given:
        Query<Build.QueryResult> query = Mock()
        Extractor<Build.QueryResult, ProjectSeed> extractor = Mock()
        Composer<CppProjectTemplateData, ProjectSeed> composer = Mock()
        Generator<CppProjectTemplateData> generator = Mock()
        Provider provider = Mock()
        Repository<Path, String> repository = Mock()

        CppProjectFeature cppProjectConverter = new CppProjectFeature(
                provider,
                query,
                extractor,
                composer,
                generator,
                repository
        )

        Path bazelWorkspaceAbsolutePath = Mock()
        Path msbuildSolutionAbsolutePath = Mock()
        List<String> bazelCommands = Mock()

        Build.QueryResult queryResult = GroovyMock()
        ProjectSeed projectSeed = Mock()
        CppProjectTemplateData cppProjectTemplate = Mock()
        String xml = GroovyMock()

        when:
        cppProjectConverter.buildMsbuildSolutionFromBazelWorkspace(bazelWorkspaceAbsolutePath, msbuildSolutionAbsolutePath)

        then:
        1 * provider.provide() >> bazelCommands
        1 * query.query(bazelWorkspaceAbsolutePath, bazelCommands) >> queryResult
        1 * extractor.extract(queryResult) >> projectSeed
        1 * composer.compose(projectSeed) >> cppProjectTemplate
        1 * generator.generate(cppProjectTemplate) >> xml
        1 * repository.save(msbuildSolutionAbsolutePath, xml)
    }

}
