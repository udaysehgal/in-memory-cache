public class LRUEvictionPolicyTest {
    public static void main(String[] args) {
        Cache<String, String> cache = new CacheImpl<>(3, new LRUEvictionPolicy<>(3));

        // Test 1: put and get
        cache.put("key1", "value1");
        assert "value1".equals(cache.get("key1")) : "Get operation failed to retrieve the correct value";
        System.out.println("Test 1 - key1: " + cache.get("key1"));

        // Test 2: Remove operation
        cache.put("key2", "value2");
        cache.remove("key2");
        assert cache.get("key2") == null : "Remove operation failed";
        System.out.println("Test 2 - key2 should be null: " + cache.get("key2"));

        // Test 3: Clear operation
        cache.clear();
        assert cache.size() == 0 : "Clear operation failed";
        System.out.println("Test 3 - Cache size should be 0: " + cache.size());

        // Test 4: Size operation
        cache.put("key3", "value3");
        cache.put("key4", "value4");
        assert cache.size() == 2 : "Size operation failed";
        System.out.println("Test 4 - Cache size should be 2: " + cache.size());

        // Test 5: LRU Eviction policy
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        cache.get("key1"); 

        //this should evict key2 since that is the least recently used key and hence the subsequent print statement should be null
        cache.put("key4", "value4");
        assert cache.get("key2") == null : "LRU Eviction policy failed";
        System.out.println("Test 5 - key2 should be evicted: " + cache.get("key2"));
    }
}
