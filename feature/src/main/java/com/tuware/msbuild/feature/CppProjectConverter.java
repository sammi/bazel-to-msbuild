package com.tuware.msbuild.feature;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.*;
import com.tuware.msbuild.contract.input.ProjectInput;
import com.tuware.msbuild.contract.template.CppProjectTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CppProjectConverter implements Converter {

    private QueryAdapter<Build.QueryResult> packageQuery;
    private ComposerAdapter<CppProjectTemplate, ProjectInput> cppProjectComposer;
    private GeneratorAdapter generatorAdapter;
    private ExtractMapper<Build.QueryResult, List<String>> extractMapper;

    public CppProjectConverter(
            QueryAdapter<Build.QueryResult> packageQuery,
            ComposerAdapter<CppProjectTemplate, ProjectInput> cppProjectComposer,
            GeneratorAdapter generatorAdapter,
            ExtractMapper<Build.QueryResult, List<String>> extractMapper
    ) {
        this.packageQuery = packageQuery;
        this.cppProjectComposer = cppProjectComposer;
        this.generatorAdapter = generatorAdapter;
        this.extractMapper = extractMapper;
    }

    @Override
    public void convert(String msbuildProjectName, String bazelProjectPath, String msbuildProjectPath) throws ConverterException {

        Build.QueryResult queryResult;
        try {
            queryResult = packageQuery.query(bazelProjectPath, "...");
        } catch (AdapterException e) {
            throw new ConverterException(e);
        }

        List<String> sourceFileList = extractMapper.extract(queryResult);

        String projectUuid = UUID.randomUUID().toString();
        ProjectInput projectInput = ProjectInput.builder()
                .cppFileName(sourceFileList.get(0))
                .projectGuild(projectUuid).build();
        CppProjectTemplate cppProjectTemplate = cppProjectComposer.compose(projectInput);
        try {
            generatorAdapter.generateProject(cppProjectTemplate, msbuildProjectName, msbuildProjectPath);
        } catch (AdapterException e) {
            throw new ConverterException(e);
        }
    }

}
