package com.tuware.msbuild.feature

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.contract.adapter.Composer
import com.tuware.msbuild.contract.adapter.Extractor
import com.tuware.msbuild.contract.adapter.Generator
import com.tuware.msbuild.contract.adapter.Query
import com.tuware.msbuild.contract.input.ProjectInput
import com.tuware.msbuild.contract.template.CppProjectTemplate
import spock.lang.Specification

class CppProjectConverterSpec extends Specification{

    def "convert bazel cpp project to visual studio msbuild project and solution"() {

        given:
        Query<Build.QueryResult> packageQuery = Mock()
        Extractor<Build.QueryResult, ProjectInput> cppBinaryExtractor = Mock()
        Composer<CppProjectTemplate, ProjectInput> cppProjectComposer = Mock()
        Generator<CppProjectTemplate> cppProjectGenerator = Mock()

        CppProjectConverter cppProjectConverter = new CppProjectConverter(
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
        CppProjectTemplate cppProjectTemplate = Mock()

        when:
        cppProjectConverter.convert(bazelWorkspaceAbsolutePath, msbuildSolutionAbsolutePath, bazelCommands)

        then:
        1 * packageQuery.query(bazelWorkspaceAbsolutePath, bazelCommands) >> queryResult
        1 * cppBinaryExtractor.extract(queryResult) >> projectInput
        1 * cppProjectComposer.compose(projectInput) >> cppProjectTemplate
        1 * cppProjectGenerator.generate(cppProjectTemplate, msbuildSolutionAbsolutePath)
    }

}
