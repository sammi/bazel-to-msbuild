package com.tuware.msbuild.contract.adapter;


public interface Extractor<R, O> {

    O extract(R bazelQueryResult);

}
