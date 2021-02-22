package com.tuware.msbuild.contract.adapter;

public interface QueryAdapter<T> {

    T query(String bazelProjectRootPath, String query) throws AdapterException;

}
