package com.tuware.msbuild.contract.adapter;

public interface ComposerAdapter<T, I> {

    T compose(I templateData);

}
