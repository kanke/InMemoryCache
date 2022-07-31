package org.kanke;

public class CacheObject<T> {
    public long lastAccessed = System.currentTimeMillis();
    public T value;
    public CacheObject(T value) {
        this.value = value;
    }
}
