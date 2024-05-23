import java.util.HashMap;
import java.util.Map;

public class CacheImpl<K, V> implements Cache<K, V> {
    private final Map<K, CacheEntry<K, V>> cacheMap;
    private final EvictionPolicy<K> evictionPolicy;
    private final int maxCapacity;
    private final Object lock = new Object(); 

    public CacheImpl(int maxCapacity, EvictionPolicy<K> evictionPolicy) {
        this.maxCapacity = maxCapacity;
        this.cacheMap = new HashMap<>();
        this.evictionPolicy = evictionPolicy;
    }

    @Override
    public V get(K key) {
        synchronized (lock) {
            if (!cacheMap.containsKey(key)) {
                return null;
            }
            CacheEntry<K, V> entry = cacheMap.get(key);
            entry.updateAccessTime();
            evictionPolicy.keyAccessed(key);
            return entry.getValue();
        }
    }

    @Override
    public void put(K key, V value) {
        synchronized (lock) {
            if (cacheMap.containsKey(key) || cacheMap.size() < maxCapacity) {
                cacheMap.put(key, new CacheEntry<>(key, value));
            } else {
                K evictKey = evictionPolicy.evictKey();
                if (evictKey != null) {
                    cacheMap.remove(evictKey);
                }
                cacheMap.put(key, new CacheEntry<>(key, value));
            }
            evictionPolicy.keyAccessed(key);
        }
    }

    @Override
    public void remove(K key) {
        synchronized (lock) {
            cacheMap.remove(key);
        }
    }

    @Override
    public void clear() {
        synchronized (lock) {
            cacheMap.clear();
        }
    }

    @Override
    public int size() {
        synchronized (lock) {
            return cacheMap.size();
        }
    }
}
