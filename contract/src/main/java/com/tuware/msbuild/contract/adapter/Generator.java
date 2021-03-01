package com.tuware.msbuild.contract.adapter;

/**
 *
 * @param <T> Template Data Class
 */
public interface Generator<T> {

    String generate(T tData)  throws AdapterException;

}
