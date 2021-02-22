package com.tuware.msbuild.contract.adapter;

import java.util.List;

public interface ExtractMapper<T> {

    List<String> getSourceFileList(T queryResult);

}
