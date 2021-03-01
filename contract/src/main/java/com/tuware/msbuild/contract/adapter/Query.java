package com.tuware.msbuild.contract.adapter;

import java.nio.file.Path;
import java.util.List;

/**
 * @param <Q> - Query result class
 */
public interface Query<Q> {

    Q query(Path bazelProjectRootAbsolutePath, List<String> commands) throws AdapterException;

}
