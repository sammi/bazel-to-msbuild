package com.tuware.msbuild.contract.adapter;

/**
 * @param <Q> Query result class
 * @param <S> Seed data type, Seed data is being extracted from query result object
 */
public interface Extractor<Q, S> {

    S extract(Q bazelQResult);

}
