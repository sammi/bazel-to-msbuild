package com.tuware.msbuild.feature

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.contract.adapter.QueryAdapter
import com.tuware.msbuild.contract.adapter.ExtractMapper
import com.tuware.msbuild.contract.adapter.ComposerAdapter
import com.tuware.msbuild.contract.adapter.GeneratorAdapter
import com.tuware.msbuild.contract.template.CppProjectTemplate
import spock.lang.Specification

class CppProjectConverterSpec extends Specification{

    def "convert bazel cpp project to visual studio msbuild project and solution"() {

        given:
        QueryAdapter<Build.QueryResult> packageQuery = Mock()
        ComposerAdapter<CppProjectTemplate> cppProjectComposer = Mock()
        ExtractMapper bazelQueryMapper = Mock()
        GeneratorAdapter projectGeneratorAdapter = Mock()
        CppProjectConverter cppProjectConverter = new CppProjectConverter(packageQuery, cppProjectComposer, projectGeneratorAdapter, bazelQueryMapper)
        String bazelProjectRootPath = "project_absolute_file_path"
        Build.QueryResult queryResult = Build.QueryResult.newBuilder().build()
        def sourceFileList = ["someFile.cpp"]
        CppProjectTemplate cppProjectTemplate = CppProjectTemplate.builder().build()

        when:
        cppProjectConverter.convert(bazelProjectRootPath)

        then:
        1 * packageQuery.query(bazelProjectRootPath, "...") >> queryResult
        1 * bazelQueryMapper.getSourceFileList(queryResult) >> sourceFileList
        1 * cppProjectComposer.compose(sourceFileList.get(0), _) >> cppProjectTemplate
        1 * projectGeneratorAdapter.generateProject(cppProjectTemplate, _, _)
    }

}
