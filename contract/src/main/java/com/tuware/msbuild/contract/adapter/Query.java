package com.tuware.msbuild.contract.adapter;

import java.util.List;

public interface Query<T> {

    T query(String bazelProjectRootAbsolutePath, List<String> commands) throws AdapterException;

}
