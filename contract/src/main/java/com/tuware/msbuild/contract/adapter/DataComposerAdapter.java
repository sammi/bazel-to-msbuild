package com.tuware.msbuild.contract.adapter;

public interface DataComposerAdapter<T> {

    T compose(String cppFileName, String projectGuild);

}
