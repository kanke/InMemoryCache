package org.kanke;

public interface Cache<T> {
    void add(String key, Object value);

    void remove(String key);

    T get(String key);

    void clear();

    long size();
}
