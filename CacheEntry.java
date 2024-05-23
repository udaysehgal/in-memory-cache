public class CacheEntry<K, V> {
    private K key;
    private V value;
    private long lastAccessTime; 

    public CacheEntry(K key, V value) {
        this.key = key;
        this.value = value;
        this.lastAccessTime = System.currentTimeMillis();
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public void updateAccessTime() {
        this.lastAccessTime = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "CacheEntry{" +
               "key=" + key +
               ", value=" + value +
               ", lastAccessTime=" + lastAccessTime +
               '}';
    }
}
