package com.tuware.msbuild.feature

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.contract.adapter.*
import com.tuware.msbuild.contract.input.ProjectInput
import com.tuware.msbuild.contract.template.CppProjectTemplateData
import spock.lang.Specification

class CppProjectFeatureSpec extends Specification{

    def "convert bazel cpp project to visual studio msbuild project and solution"() {

        given:
        Query<Build.QueryResult> packageQuery = Mock()
        Extractor<Build.QueryResult, ProjectInput> cppBinaryExtractor = Mock()
        Composer<CppProjectTemplateData, ProjectInput> cppProjectComposer = Mock()
        Generator<CppProjectTemplateData> cppProjectGenerator = Mock()
        Provider bazelQueryProvider = Mock()

        CppProjectFeature cppProjectConverter = new CppProjectFeature(
                bazelQueryProvider,
                packageQuery,
                cppBinaryExtractor,
                cppProjectComposer,
                cppProjectGenerator
        )

        String bazelWorkspaceAbsolutePath = GroovyMock()
        String msbuildSolutionAbsolutePath = GroovyMock()
        List<String> bazelCommands = Mock()

        Build.QueryResult queryResult = GroovyMock()
        ProjectInput projectInput = Mock()
        CppProjectTemplateData cppProjectTemplate = Mock()

        when:
        cppProjectConverter.buildMsbuildSolutionFromBazelWorkspace(bazelWorkspaceAbsolutePath, msbuildSolutionAbsolutePath)

        then:
        1 * bazelQueryProvider.provide() >> bazelCommands
        1 * packageQuery.query(bazelWorkspaceAbsolutePath, bazelCommands) >> queryResult
        1 * cppBinaryExtractor.extract(queryResult) >> projectInput
        1 * cppProjectComposer.compose(projectInput) >> cppProjectTemplate
        1 * cppProjectGenerator.generate(cppProjectTemplate, msbuildSolutionAbsolutePath)
    }

}
