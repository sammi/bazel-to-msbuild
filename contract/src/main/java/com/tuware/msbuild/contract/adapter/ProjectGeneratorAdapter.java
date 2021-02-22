package com.tuware.msbuild.contract.adapter;

import com.tuware.msbuild.contract.template.CppProjectTemplate;

public interface ProjectGeneratorAdapter {

    void generateProject(CppProjectTemplate cppProjectTemplate, String projectName, String outputFolder)  throws AdapterException;

}
