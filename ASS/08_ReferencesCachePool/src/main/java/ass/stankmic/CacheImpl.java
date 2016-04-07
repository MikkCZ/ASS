package ass.stankmic;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 * @param <K>
 * @param <V>
 */
public class CacheImpl<K extends String, V extends String> implements Cache<K, V> {

    private final Map<K, SoftReference<V>> cached;

    public CacheImpl<K, V> getInstance() {
        return CacheImplHolder.INSTANCE;
    }

    private CacheImpl() {
        this.cached = new HashMap<K, SoftReference<V>>();
    }

    public V get(K key) {
        Reference<V> ref = cached.get(key);
        if (ref == null) {
            return null;
        }
        V value = ref.get();
        if (value == null) {
            remove(key);
            return null;
        }
        return value;
    }

    public void put(K key, V value) {
        cached.put(key, new SoftReference<V>(value));
    }

    private void remove(K key) {
        cached.remove(key);
    }

    private static class CacheImplHolder {

        private static final CacheImpl INSTANCE = new CacheImpl();
    }

}
