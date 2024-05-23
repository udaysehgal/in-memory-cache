import java.util.Stack;

public class LIFOEvictionPolicy<K> implements EvictionPolicy<K> {
    private final Stack<K> stack;

    public LIFOEvictionPolicy() {
        this.stack = new Stack<>();
    }

    @Override
    public synchronized void keyAccessed(K key) {
        if (stack.contains(key)) {
            stack.remove(key);
        }
        stack.push(key);
    }

    @Override
    public synchronized K evictKey() {
        if (!stack.isEmpty()) {
            return stack.pop();
        }
        return null;
    }
}
