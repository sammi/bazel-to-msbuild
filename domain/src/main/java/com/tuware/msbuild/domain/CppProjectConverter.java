package com.tuware.msbuild.domain;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.*;
import com.tuware.msbuild.contract.template.CppProjectTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CppProjectConverter implements Converter{

    private QueryAdapter<Build.QueryResult> packageQuery;
    private ComposerAdapter<CppProjectTemplate> cppProjectComposer;
    private GeneratorAdapter generatorAdapter;
    private ExtractMapper<Build.QueryResult> extractMapper;

    public CppProjectConverter(QueryAdapter<Build.QueryResult> packageQuery, ComposerAdapter<CppProjectTemplate> cppProjectComposer, GeneratorAdapter generatorAdapter, ExtractMapper<Build.QueryResult> extractMapper) {
        this.packageQuery = packageQuery;
        this.cppProjectComposer = cppProjectComposer;
        this.generatorAdapter = generatorAdapter;
        this.extractMapper = extractMapper;
    }

    @Override
    public void convert(String bazelProjectRootPath) throws ConverterException {

        Build.QueryResult queryResult;
        try {
            queryResult = packageQuery.query(bazelProjectRootPath, "...");
        } catch (AdapterException e) {
            throw new ConverterException(e);
        }

        List<String> sourceFileList =  extractMapper.getSourceFileList(queryResult);

        String projectUuid = UUID.randomUUID().toString();
        CppProjectTemplate cppProjectTemplate = cppProjectComposer.compose(sourceFileList.get(0), projectUuid);
        String projectName = "SomeName";
        String outputPath = ".";
        try {
            generatorAdapter.generateProject(cppProjectTemplate, projectName, outputPath);
        } catch (AdapterException e) {
            throw new ConverterException(e);
        }
    }

}
