package com.tuware.msbuild.feature;

@FunctionalInterface
public interface Converter {
    void convert(String projectName, String sourcePath, String targetPath) throws ConverterException;
}
