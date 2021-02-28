package com.tuware.msbuild.feature;

import java.util.List;

public interface Converter {
    void convert(String bazelProjectPath, String msbuildProjectPath, List<String> commands) throws ConverterException;
}
