package com.tuware.msbuild.domain

import com.google.devtools.build.lib.query2.proto.proto2api.Build
import com.tuware.msbuild.contract.adapter.BazelQueryAdapter
import com.tuware.msbuild.contract.adapter.BazelQueryMapper
import com.tuware.msbuild.contract.adapter.XmlFileGeneratorAdapter
import com.tuware.msbuild.contract.template.CppProjectTemplate
import spock.lang.Specification

class CppProjectConverterSpec extends Specification{

    def "convert bazel cpp project to visual studio msbuild project and solution"() {

        given:
        BazelQueryAdapter<Build.QueryResult> packageQuery = Mock()
        TemplateFactory templateFactory = Mock()
        BazelQueryMapper bazelQueryMapper = Mock()
        XmlFileGeneratorAdapter applicationAdapter = Mock()
        CppProjectConverter cppProjectConverter = new CppProjectConverter(packageQuery, templateFactory, applicationAdapter, bazelQueryMapper)
        String bazelProjectRootPath = "project_absolute_file_path"
        Build.QueryResult queryResult = Build.QueryResult.newBuilder().build()
        def sourceFileList = ["someFile.cpp"]
        CppProjectTemplate cppProjectTemplate = CppProjectTemplate.builder().build()

        when: "call convert method"
        cppProjectConverter.convert(bazelProjectRootPath)

        then: "run bazel query, get source file from the query result, build msbuild project xml, and save msvc project and solution xml files"
        1 * packageQuery.query(bazelProjectRootPath, "...") >> queryResult
        1 * bazelQueryMapper.getCppSourceFiles(queryResult) >> sourceFileList
        1 * templateFactory.createCppProject(sourceFileList.get(0), _) >> cppProjectTemplate
        1 * applicationAdapter.generateXmlFiles(cppProjectTemplate, _)
    }

}
