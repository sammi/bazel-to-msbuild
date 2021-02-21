package com.tuware.msbuild.contract.adapter;

public interface BazelQueryAdapter<T> {
    T query(String bazelProjectRootPath, String query) throws AdapterException;
}
