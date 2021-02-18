package com.tuware.msbuild.application.domain;

import java.util.Objects;

public class Pair<K,V> {

    private K key;

    public K getKey() { return key; }

    private V value;

    public V getValue() { return value; }

    public Pair(@NamedArg("key") K key, @NamedArg("value") V value) {
        this.key = key;
        this.value = value;
    }

    public String toString() {
        return key + "=" + value;
    }

    public int hashCode() {
        return key.hashCode() * 13 + (value == null ? 0 : value.hashCode());
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Pair) {
            Pair<K, V> pair = (Pair<K, V>) o;
            if (Objects.equals(key, pair.key)) {
                return Objects.equals(value, pair.value);
            }
            return false;
        }
        return false;
    }
}