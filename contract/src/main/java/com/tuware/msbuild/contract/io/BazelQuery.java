package com.tuware.msbuild.contract.io;

public interface BazelQuery<T> {
    T query(String bazelProjectRootPath, String query) throws BazelQueryException;
}
