package com.tuware.msbuild.contract.adapter;

public interface GeneratorAdapter<T> {

    void generate(T templateData, String projectNameWithoutExtension, String outputFolderAbsolutePath)  throws AdapterException;

}
