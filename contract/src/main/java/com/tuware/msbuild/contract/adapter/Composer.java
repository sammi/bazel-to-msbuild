package com.tuware.msbuild.contract.adapter;

/**
 * @param <T> Template Data class
 * @param <S> Seed Data being used compose Template Data
 */
public interface Composer<T, S> {

    T compose(S seed);

}
