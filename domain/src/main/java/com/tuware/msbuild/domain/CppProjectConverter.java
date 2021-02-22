package com.tuware.msbuild.domain;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.BazelQueryAdapter;
import com.tuware.msbuild.contract.adapter.QueryResultMapper;
import com.tuware.msbuild.contract.adapter.XmlFileGeneratorAdapter;
import com.tuware.msbuild.contract.template.CppProjectTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CppProjectConverter implements Converter{

    private BazelQueryAdapter<Build.QueryResult> packageQuery;
    private TemplateFactory templateFactory;
    private XmlFileGeneratorAdapter xmlFileGeneratorAdapter;
    private QueryResultMapper<Build.QueryResult> queryResultMapper;

    public CppProjectConverter(BazelQueryAdapter<Build.QueryResult> packageQuery, TemplateFactory templateFactory, XmlFileGeneratorAdapter xmlFileGeneratorAdapter,
                               QueryResultMapper<Build.QueryResult> queryResultMapper) {
        this.packageQuery = packageQuery;
        this.templateFactory = templateFactory;
        this.xmlFileGeneratorAdapter = xmlFileGeneratorAdapter;
        this.queryResultMapper = queryResultMapper;
    }

    @Override
    public void convert(String bazelProjectRootPath) throws ConverterException {

        Build.QueryResult queryResult;
        try {
            queryResult = packageQuery.query(bazelProjectRootPath, "...");
        } catch (AdapterException e) {
            throw new ConverterException(e);
        }

        List<String> sourceFileList =  queryResultMapper.getCppSourceFiles(queryResult);

        String projectUuid = UUID.randomUUID().toString();
        CppProjectTemplate cppProjectTemplate = templateFactory.createCppProject(sourceFileList.get(0), projectUuid);
        String projectName = "SomeName";
        try {
            xmlFileGeneratorAdapter.generateXmlFiles(cppProjectTemplate, projectName);
        } catch (AdapterException e) {
            throw new ConverterException(e);
        }
    }

}
