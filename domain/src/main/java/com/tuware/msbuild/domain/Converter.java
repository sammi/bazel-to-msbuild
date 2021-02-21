package com.tuware.msbuild.domain;

@FunctionalInterface
public interface Converter {
    void convert(String bazelProjectRootPath) throws ConverterException;
}
