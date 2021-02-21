package com.tuware.msbuild.contract.adapter;

public interface BazelQueryAdapter<T> {
    T query(String bazelProjectRootPath, String query) throws AdapterException;
    String getSourceFile(T queryResult) throws AdapterException;
}
