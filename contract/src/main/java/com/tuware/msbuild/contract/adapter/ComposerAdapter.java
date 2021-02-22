package com.tuware.msbuild.contract.adapter;

public interface ComposerAdapter<T> {

    T compose(String cppFileName, String projectGuild);

}
