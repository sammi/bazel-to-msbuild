package com.tuware.msbuild.contract.adapter;

/**
 * @param <I> - The identifier class, when saved to file, it will be a Path
 * @param <C> - The content class, when save text, it will be String
 */
public interface Repository<I, C> {

    void save(final I identifier, final C content) throws AdapterException;

}
