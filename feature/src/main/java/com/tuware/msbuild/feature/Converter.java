package com.tuware.msbuild.feature;

import java.util.List;

public interface Converter {
    void convert(String bazelWorkspaceAbsolutePath, String msbuildSolutionAbsolutePath, List<String> bazelCommands) throws ConverterException;
}
