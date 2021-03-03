package com.tuware.msbuild.contract.adapter;

/**
 *
 * @param <C> - provided content class type.
 */
public interface Provider <C> {

    C provide();

}
