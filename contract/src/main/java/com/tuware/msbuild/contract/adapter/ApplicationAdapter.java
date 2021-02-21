package com.tuware.msbuild.contract.adapter;

import com.tuware.msbuild.contract.template.CppProjectTemplate;

import java.io.IOException;

public interface ApplicationAdapter {

    Process startBazelQueryProcess(String bazelProjectRootPath, String command, String query) throws IOException, AdapterException;

    void saveMsbuildProjectXmlFile(CppProjectTemplate cppProjectTemplate, String projectName)  throws AdapterException;

}
