package com.tuware.msbuild.feature;

@FunctionalInterface
public interface Converter {
    void convert(String bazelProjectRootPath) throws ConverterException;
}
