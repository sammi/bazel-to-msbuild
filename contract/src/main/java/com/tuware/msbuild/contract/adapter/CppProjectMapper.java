package com.tuware.msbuild.contract.adapter;

import java.util.List;

public interface CppProjectMapper<T> {
    List<String> getSourceFileList(T queryResult);
}
