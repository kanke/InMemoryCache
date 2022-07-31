package org.kanke;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.LRUMap;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class InMemoryCache<K, T> implements Cache {
    private final long timeToLive;
    private LRUMap cacheMap;

    public InMemoryCache(long timeToLive, final long timerInterval, int maxItems) {
        this.timeToLive = timeToLive * 1000;
        cacheMap = new LRUMap(maxItems);

        if (timeToLive > 0 && timerInterval > 0) {
            Timer cleanUpTimer = new Timer(true);
            TimerTask cleanupTask = new TimerTask() {
                @Override
                public void run() {
                    cleanUp();
                }
            };
            cleanUpTimer.scheduleAtFixedRate(cleanupTask, 0, timerInterval * 1000);
        }
    }

    @Override
    public void add(String key, Object value) {
        synchronized (cacheMap) {
            cacheMap.put(key, new CacheObject(value));
        }
    }

    /*
    Removes the specified mapping from this map.
     */
    @Override
    public void remove(String key) {
        synchronized (cacheMap) {
            cacheMap.remove(key);
        }
    }

    @Override
    public T get(String key) {
        synchronized (cacheMap) {
            CacheObject cacheObject;
            cacheObject = (CacheObject) cacheMap.get(key);

            if (cacheObject == null)
                return null;
            else {
                cacheObject.lastAccessed = System.currentTimeMillis();
                return (T) cacheObject.value;
            }
        }
    }

    /*
    Clears the map, resetting the size to zero and nullifying references to avoid garbage collection issues.
     */
    @Override
    public void clear() {
        synchronized (cacheMap) {
            cacheMap.clear();
        }
    }

    @Override
    public long size() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    public void cleanUp() {
        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey;

        synchronized (cacheMap) {
            MapIterator itr = cacheMap.mapIterator();

            deleteKey = new ArrayList<>((cacheMap.size() / 2) + 1);
            K key;
            CacheObject cacheObject;

            while (itr.hasNext()) {
                key = (K) itr.next();
                cacheObject = (CacheObject) itr.getValue();

                if (cacheObject != null && (now > (timeToLive + cacheObject.lastAccessed))) {
                    deleteKey.add(key);
                }
            }
        }

        for (K key : deleteKey) {
            synchronized (cacheMap) {
                cacheMap.remove(key);
            }
            Thread.yield();
        }
    }
}
