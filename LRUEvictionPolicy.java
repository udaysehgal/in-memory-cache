import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUEvictionPolicy<K> implements EvictionPolicy<K> {
    private final Map<K, Boolean> map; 
    private final int capacity;

    public LRUEvictionPolicy(int capacity) {
        this.capacity = capacity;
        this.map = Collections.synchronizedMap(new LinkedHashMap<K, Boolean>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<K, Boolean> eldest) {
                return size() > capacity;
            }
        });
    }

    @Override
    public void keyAccessed(K key) {
        synchronized (map) { 
            map.put(key, true);
        }
    }

     
    @Override
    public K evictKey() {
        synchronized (map) {
            if (map.isEmpty()) {
                return null;
            }
            return map.keySet().iterator().next();
        }
    }

}
