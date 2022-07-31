package org.kanke;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class InMemoryCacheTest {

    @Test
    @DisplayName("Should add objects to the cache")
    public void shouldAddObjectsToTheCache() {
        InMemoryCache<String, String> cache = new InMemoryCache<>(200, 500, 3);
        cache.add("1", "Leia");
        cache.add("2", "Yoda");

        assertEquals(2, cache.size());
    }

    @Test
    @DisplayName("Should get an object from the cache")
    public void shouldGetObjectsFromTheCache() {
        InMemoryCache<String, String> cache = new InMemoryCache<>(200, 500, 3);
        cache.add("1", "Kylo");
        cache.add("2", "Vader");

        String cacheObject = cache.get("2");

        assertNotNull(cacheObject);
        assertEquals("Vader", cacheObject);
    }

    @Test
    @DisplayName("Should remove objects from the cache")
    public void shouldRemoveObjectsFromTheCache() {
        InMemoryCache<String, String> cache = new InMemoryCache<>(200, 500, 3);
        cache.add("1", "Palpatine");
        cache.add("2", "Ahsoka");

        cache.remove("1");

        assertEquals(1, cache.size());
    }

    @Test
    @DisplayName("Should clear objects from the cache")
    public void shouldClearObjectsFromTheCache() {
        InMemoryCache<String, String> cache = new InMemoryCache<>(200, 500, 3);
        cache.add("1", "Skywalker");
        cache.add("2", "Padme");

        cache.clear();

        assertEquals(0, cache.size());
    }

    @Test
    @DisplayName("Should not add more objects to the cache when maxItems reached")
    public void shouldNotAddMoreObjectsToTheCacheWhenMaxItemsReached() {
        InMemoryCache<String, String> cache = new InMemoryCache<>(200, 500, 3);
        cache.add("1", "Leia");
        cache.add("2", "Yoda");
        cache.add("3", "Palpatine");
        cache.add("4", "Ahsoka");
        cache.add("5", "C-3PO");

        assertEquals(3, cache.size());
    }

    @Test
    @DisplayName("Should expire objects in cache")
    public void shouldTestExpiredCacheObjects() throws InterruptedException {
        InMemoryCache<String, String> cache = new InMemoryCache<>(1, 1, 3);
        cache.add("1", "Chewbacca");
        cache.add("1", "Lando");

        Thread.sleep(3000);

        assertEquals(0, cache.size());
    }

    @Test
    @DisplayName("Should clean up cache objects")
    public void shouldTestCleanUpOfCacheObjects() throws InterruptedException {

        int maxItems = 600000;
        InMemoryCache<String, String> cache = new InMemoryCache<>(600, 600, maxItems);
        for (int i = 0; i < maxItems; i++) {
            String value = Integer.toString(i);
            cache.add(value, value);
        }

        Thread.sleep(200);
        long start = System.currentTimeMillis();
        cache.cleanUp();
        double finish = (double) (System.currentTimeMillis() - start) / 1000.0;

        assertNotNull(finish);
        assertTrue(finish > 0);
    }
}
