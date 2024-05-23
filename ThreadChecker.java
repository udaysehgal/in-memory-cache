public class ThreadChecker {
    public static void main(String[] args) throws InterruptedException {
        Cache<String, String> cache = new CacheImpl<>(11, new LRUEvictionPolicy<String>(11));
        final int numberOfThreads = 10;
        Thread[] threads = new Thread[numberOfThreads];

        // Simulating concurrent access
        for (int i = 0; i < numberOfThreads; i++) {
            int threadId = i;
            threads[i] = new Thread(() -> {
                cache.put("key" + threadId, "value" + threadId);
                String value = cache.get("key" + threadId);
                System.out.println("Thread " + threadId + ": " + value);
                assert ("value" + threadId).equals(value) : "Thread " + threadId + " failed to retrieve the correct value";

                if (threadId % 2 == 0) {
                    cache.remove("key" + threadId);
                    assert cache.get("key" + threadId) == null : "Remove operation failed in Thread " + threadId;
                }
            });
            threads[i].start();
        }

        // Test for Concurrent Removals
        for (int i = 0; i < numberOfThreads; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                String key = "removeKey" + threadId;
                String value = "removeValue" + threadId;

                cache.put(key, value);
                cache.remove(key);

                String retrievedValue = cache.get(key);
                System.out.println("Thread " + threadId + " retrieved after removal: [" + key + ", " + retrievedValue + "]");

                assert retrievedValue == null : "Concurrent removal failed for Thread " + threadId + " (expected null, got: " + retrievedValue + ")";
            });
            threads[i].start();
        }



        // Test for Simultaneous Put and Get
        for (int i = 0; i < numberOfThreads; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                String key = "simulKey" + threadId;
                String expectedValue = "simulValue" + threadId;
                cache.put(key, expectedValue);
                System.out.println("Thread " + threadId + " put: [" + key + ", " + expectedValue + "]");

                String value = cache.get(key);
                System.out.println("Thread " + threadId + " got: [" + key + ", " + value + "]");

                assert expectedValue.equals(value) : "Simultaneous put-get failed for Thread " + threadId + " (expected: " + expectedValue + ", got: " + value + ")";
            });
            threads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].join();
        }

    }
}
