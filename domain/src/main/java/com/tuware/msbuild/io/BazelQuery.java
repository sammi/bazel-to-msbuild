package com.tuware.msbuild.io;

public interface BazelQuery<T> {
    T query(String bazelProjectRootPath, String query) throws InterruptedException;
}
