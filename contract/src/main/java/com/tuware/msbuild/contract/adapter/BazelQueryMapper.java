package com.tuware.msbuild.contract.adapter;

import java.util.List;

public interface BazelQueryMapper<T> {
    List<String> getCppSourceFiles(T queryResult);
}
