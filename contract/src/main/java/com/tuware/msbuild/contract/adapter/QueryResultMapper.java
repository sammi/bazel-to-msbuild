package com.tuware.msbuild.contract.adapter;

import java.util.List;

public interface QueryResultMapper<T> {
    List<String> getCppSourceFiles(T queryResult);
}
