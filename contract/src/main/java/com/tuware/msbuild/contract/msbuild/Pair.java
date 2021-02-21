package com.tuware.msbuild.contract.msbuild;

public class Pair<K,V> {

    private K key;
    private V value;

    public K getKey() { return key; }
    public V getValue() { return value; }

    public Pair(@NamedArg("key") K key, @NamedArg("value") V value) {
        this.key = key;
        this.value = value;
    }
}