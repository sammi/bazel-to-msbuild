package com.tuware.msbuild.feature;

@FunctionalInterface
public interface Converter {
    void convert(String msbuildProjectName, String bazelProjectPath, String msbuildProjectPath) throws ConverterException;
}
