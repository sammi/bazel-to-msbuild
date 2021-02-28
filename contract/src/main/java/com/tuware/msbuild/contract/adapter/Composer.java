package com.tuware.msbuild.contract.adapter;

public interface Composer<T, I> {

    T compose(I data);

}
