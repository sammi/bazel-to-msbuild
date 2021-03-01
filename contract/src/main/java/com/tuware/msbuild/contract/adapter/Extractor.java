package com.tuware.msbuild.contract.adapter;

/**
 *
 * @param <Q> Query result class
 * @param <S> Seed data data, the data is being grabbed from query result object.
 */
public interface Extractor<Q, S> {

    S extract(Q bazelQResult);

}
