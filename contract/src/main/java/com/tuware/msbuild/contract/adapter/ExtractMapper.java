package com.tuware.msbuild.contract.adapter;


public interface ExtractMapper<R, O> {

    O extract(R queryResult);

}
