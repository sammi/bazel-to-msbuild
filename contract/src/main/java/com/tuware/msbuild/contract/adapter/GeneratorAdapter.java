package com.tuware.msbuild.contract.adapter;

public interface GeneratorAdapter<T> {

    void generate(T projectTemplateData, String generateProjectFileAbsolutePath)  throws AdapterException;

}
