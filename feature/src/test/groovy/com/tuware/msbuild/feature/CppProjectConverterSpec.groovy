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
        ExtractMapper bazelQueryMapper = Mock()
        GeneratorAdapter projectGeneratorAdapter = Mock()
        CppProjectConverter cppProjectConverter = new CppProjectConverter(packageQuery, cppProjectComposer, projectGeneratorAdapter, bazelQueryMapper)
        Build.QueryResult queryResult = Build.QueryResult.newBuilder().build()

        String sourcePath = "project_absolute_file_path"
        String targetPath = "outputFolder"

        CppProjectTemplate cppProjectTemplate = CppProjectTemplate.builder().build()

        List<String> commands = Arrays.asList(
                "cmd",
                "/c",
                "bazel",
                "--batch",
                "query",
                "...",
                "--output=proto")

        when:
        cppProjectConverter.convert(sourcePath, targetPath, commands)

        then:
        1 * packageQuery.query(sourcePath, commands) >> queryResult
        1 * bazelQueryMapper.extract(queryResult) >> ["someFile.cpp"]
        1 * cppProjectComposer.compose(_) >> cppProjectTemplate
        1 * projectGeneratorAdapter.generate(cppProjectTemplate, _)
    }

}
