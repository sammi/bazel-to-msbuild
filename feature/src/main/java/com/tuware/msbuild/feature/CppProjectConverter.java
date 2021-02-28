package com.tuware.msbuild.feature;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.*;
import com.tuware.msbuild.contract.input.ProjectInput;
import com.tuware.msbuild.contract.template.CppProjectTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CppProjectConverter implements Converter {

    private Query<Build.QueryResult> packageQuery;
    private Extractor<Build.QueryResult, ProjectInput> extractor;
    private Composer<CppProjectTemplate, ProjectInput> cppProjectComposer;
    private Generator<CppProjectTemplate> generator;

    public CppProjectConverter(Query<Build.QueryResult> packageQuery, Extractor<Build.QueryResult, ProjectInput> extractor, Composer<CppProjectTemplate, ProjectInput> cppProjectComposer, Generator<CppProjectTemplate> generator) {
        this.packageQuery = packageQuery;
        this.extractor = extractor;
        this.cppProjectComposer = cppProjectComposer;
        this.generator = generator;
    }

    @Override
    public void convert(String bazelWorkspaceAbsolutePath, String msbuildProjectAbsolutePath, List<String> commands) throws ConverterException {

        Build.QueryResult queryResult;
        try {
            queryResult = packageQuery.query(bazelWorkspaceAbsolutePath, commands);
        } catch (AdapterException e) {
            throw new ConverterException(e);
        }

        ProjectInput projectInput = extractor.extract(queryResult);

        CppProjectTemplate cppProjectTemplate = cppProjectComposer.compose(projectInput);
        try {
            generator.generate(cppProjectTemplate, msbuildProjectAbsolutePath);
        } catch (AdapterException e) {
            throw new ConverterException(e);
        }
    }

}
