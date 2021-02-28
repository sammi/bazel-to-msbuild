package com.tuware.msbuild.contract.adapter;

public interface Generator<T> {

    void generate(T projectTemplateData, String generateProjectFileAbsolutePath)  throws AdapterException;

}
