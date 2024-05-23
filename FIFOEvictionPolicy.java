import java.util.LinkedList;
import java.util.Queue;

public class FIFOEvictionPolicy<K> implements EvictionPolicy<K> {
    private final Queue<K> queue;

    public FIFOEvictionPolicy() {
        this.queue = new LinkedList<>();
    }

    @Override
    public synchronized void keyAccessed(K key) {
        if (!queue.contains(key)) {
            queue.add(key);
        }
    }

    @Override
    public synchronized K evictKey() {
        return queue.poll();
    }
}
