package com.tuware.msbuild.contract.adapter;

import com.tuware.msbuild.contract.template.CppProjectTemplate;

public interface XmlFileGeneratorAdapter {

    void generateXmlFiles(CppProjectTemplate cppProjectTemplate, String projectName)  throws AdapterException;

}
