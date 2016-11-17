package in.socyal.sc.persistence.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class InfinispanCache<K, V> implements Cache<K, V> {
	protected final org.infinispan.Cache<K, V> cache;

	protected InfinispanCache(org.infinispan.Cache<K, V> cache) {
		this.cache = cache;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(cache);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof InfinispanCache)) {
			return false;
		}

		InfinispanCache<?, ?> other = (InfinispanCache<?, ?>) obj;
		return Objects.equals(cache, other.cache);
	}

	@Override
	public V get(K key) {
		return this.cache.get(key);
	}

	@Override
	public Map<K, V> getAll(Set<? extends K> keys) {
		Map<K, V> values = new HashMap<>();
		for (K key : keys) {
			V value = get(key);
			values.put(key, value);
		}
		return values;
	}

	@Override
	public boolean containsKey(K key) {
		return this.cache.containsKey(key);
	}

	@Override
	public V getAndPut(K key, V value) {
		return this.cache.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		this.cache.putAll(map);
	}

	@Override
	public boolean putIfAbsent(K key, V value) {
		return (this.cache.putIfAbsent(key, value) != null);
	}

	@Override
	public boolean remove(K key) {
		return (this.cache.remove(key) != null);
	}

	@Override
	public boolean remove(K key, V oldValue) {
		return this.cache.remove(key, oldValue);
	}

	@Override
	public V getAndRemove(K key) {
		return this.cache.remove(key);
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		return this.cache.replace(key, oldValue, newValue);
	}

	@Override
	public boolean replace(K key, V value) {
		return (this.cache.replace(key, value) != null);
	}

	@Override
	public V getAndReplace(K key, V value) {
		return this.cache.replace(key, value);
	}

	@Override
	public void removeAll(Set<? extends K> keys) {
		for (K key : keys) {
			remove(key);
		}
	}

	@Override
	public void removeAll() {
		this.cache.clear();
	}

	@Override
	public void clear() {
		this.cache.clear();
	}

	@Override
	public void getName() {
		this.cache.getName();
	}
}
