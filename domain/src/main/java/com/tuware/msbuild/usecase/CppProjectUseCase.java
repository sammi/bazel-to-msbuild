package com.tuware.msbuild.usecase;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.ApplicationAdapter;
import com.tuware.msbuild.contract.io.BazelQuery;
import com.tuware.msbuild.contract.io.BazelQueryException;
import com.tuware.msbuild.contract.template.CppProjectTemplate;
import org.springframework.stereotype.Component;

@Component
public class CppProjectUseCase {

    private ApplicationAdapter applicationAdapter;
    private BazelQuery<Build.QueryResult> packageQuery;
    private CppProjectGenerator cppProjectGenerator;

    public CppProjectUseCase(ApplicationAdapter applicationAdapter, BazelQuery<Build.QueryResult> packageQuery, CppProjectGenerator cppProjectGenerator) {
        this.applicationAdapter = applicationAdapter;
        this.packageQuery = packageQuery;
        this.cppProjectGenerator = cppProjectGenerator;
    }

    public void buildVisualStudioFiles(String bazelProjectRootPath, String projectUuid, String projectName) throws BazelQueryException, AdapterException {
        Build.QueryResult queryResult = packageQuery.query(bazelProjectRootPath, "...");
        String sourceFile = packageQuery.getSourceFile(queryResult);
        CppProjectTemplate cppProjectTemplate = cppProjectGenerator.createProject(sourceFile, projectUuid);
        applicationAdapter.saveMsbuildProjectXmlFile(cppProjectTemplate, projectName);
    }

}
