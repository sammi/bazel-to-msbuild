package com.tuware.msbuild.domain;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.ApplicationAdapter;
import com.tuware.msbuild.contract.adapter.BazelQueryAdapter;
import com.tuware.msbuild.contract.template.CppProjectTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CppProjectConverter implements Converter{

    private BazelQueryAdapter<Build.QueryResult> packageQuery;
    private TemplateFactory templateFactory;
    private ApplicationAdapter applicationAdapter;

    public CppProjectConverter(BazelQueryAdapter<Build.QueryResult> packageQuery, TemplateFactory templateFactory, ApplicationAdapter applicationAdapter) {
        this.applicationAdapter = applicationAdapter;
        this.packageQuery = packageQuery;
        this.templateFactory = templateFactory;
    }

    @Override
    public void convert(String bazelProjectRootPath) throws ConverterException {

        Build.QueryResult queryResult;
        try {
            queryResult = packageQuery.query(bazelProjectRootPath, "...");
        } catch (AdapterException e) {
            throw new ConverterException(e);
        }

        String sourceFile;
        try {
            sourceFile = packageQuery.getSourceFile(queryResult);
        } catch (AdapterException e) {
            throw new ConverterException(e);
        }

        String projectUuid = UUID.randomUUID().toString();
        CppProjectTemplate cppProjectTemplate = templateFactory.createCppProject(sourceFile, projectUuid);
        String projectName = "SomeName";
        try {
            applicationAdapter.saveMsbuildProjectXmlFile(cppProjectTemplate, projectName);
        } catch (AdapterException e) {
            throw new ConverterException(e);
        }
    }

}
