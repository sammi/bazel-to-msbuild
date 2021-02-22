package com.tuware.msbuild.feature;

public interface Converter {
    void convert(String msbuildProjectName, String bazelProjectPath, String msbuildProjectPath) throws ConverterException;
}
