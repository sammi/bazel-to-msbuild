package com.tuware.msbuild.feature;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.*;
import com.tuware.msbuild.contract.input.ProjectInput;
import com.tuware.msbuild.contract.template.CppProjectTemplateData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CppProjectConverter implements Converter {

    private Query<Build.QueryResult> query;
    private Extractor<Build.QueryResult, ProjectInput> extractor;
    private Composer<CppProjectTemplateData, ProjectInput> composer;
    private Generator<CppProjectTemplateData> generator;

    public CppProjectConverter(
            Query<Build.QueryResult> query,
            Extractor<Build.QueryResult, ProjectInput> extractor,
            Composer<CppProjectTemplateData, ProjectInput> composer,
            Generator<CppProjectTemplateData> generator
    ) {
        this.query = query;
        this.extractor = extractor;
        this.composer = composer;
        this.generator = generator;
    }

    @Override
    public void convert(String bazelWorkspaceAbsolutePath, String msbuildSolutionAbsolutePath, List<String> bazelCommands) throws ConverterException {

        Build.QueryResult queryResult;
        try {
            queryResult = query.query(bazelWorkspaceAbsolutePath, bazelCommands);
        } catch (AdapterException e) {
            throw new ConverterException(e);
        }

        ProjectInput projectInput = extractor.extract(queryResult);

        CppProjectTemplateData cppProjectTemplateData = composer.compose(projectInput);
        try {
            generator.generate(cppProjectTemplateData, msbuildSolutionAbsolutePath);
        } catch (AdapterException e) {
            throw new ConverterException(e);
        }
    }

}
