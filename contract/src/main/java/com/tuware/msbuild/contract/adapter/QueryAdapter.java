package com.tuware.msbuild.contract.adapter;

public interface QueryAdapter<T> {

    T query(String bazelProjectRootAbsolutePath, String queryExpression) throws AdapterException;

}
