package com.tuware.msbuild.feature

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.contract.adapter.ComposerAdapter
import com.tuware.msbuild.contract.adapter.ExtractMapper
import com.tuware.msbuild.contract.adapter.GeneratorAdapter
import com.tuware.msbuild.contract.adapter.QueryAdapter
import com.tuware.msbuild.contract.input.ProjectInput
import com.tuware.msbuild.contract.template.CppProjectTemplate
import spock.lang.Specification

class CppProjectConverterSpec extends Specification{

    def "convert bazel cpp project to visual studio msbuild project and solution"() {

        given:
        QueryAdapter<Build.QueryResult> packageQuery = Mock()
        ComposerAdapter<CppProjectTemplate, ProjectInput> cppProjectComposer = Mock()
        ExtractMapper<Build.QueryResult, ProjectInput> bazelQueryMapper = Mock()
        GeneratorAdapter projectGeneratorAdapter = Mock()
        CppProjectTemplate cppProjectTemplate = Mock()
        ProjectInput projectInput = Mock()
        List<String> commands = Mock()
        //Using GroovyMock to mock final classes
        Build.QueryResult queryResult = GroovyMock()
        String bazelWorkspaceAbsolutePath = GroovyMock()
        String targetProjectFileAbsolutePath = GroovyMock()

        CppProjectConverter cppProjectConverter = new CppProjectConverter(packageQuery, cppProjectComposer, projectGeneratorAdapter, bazelQueryMapper)

        when:
        cppProjectConverter.convert(bazelWorkspaceAbsolutePath, targetProjectFileAbsolutePath, commands)

        then:
        1 * packageQuery.query(bazelWorkspaceAbsolutePath, commands) >> queryResult
        1 * bazelQueryMapper.extract(queryResult) >> projectInput
        1 * cppProjectComposer.compose(projectInput) >> cppProjectTemplate
        1 * projectGeneratorAdapter.generate(cppProjectTemplate, targetProjectFileAbsolutePath)
    }

}
