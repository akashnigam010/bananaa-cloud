package in.socyal.sc.persistence.cache;

import java.util.Map;
import java.util.Set;

public interface Cache<K, V> {
	V get(K key);

	Map<K, V> getAll(Set<? extends K> keys);

	boolean containsKey(K key);

	V getAndPut(K key, V value);

	void putAll(Map<? extends K, ? extends V> map);

	boolean putIfAbsent(K key, V value);

	boolean remove(K key);

	boolean remove(K key, V oldValue);

	V getAndRemove(K key);

	boolean replace(K key, V oldValue, V newValue);

	boolean replace(K key, V value);

	V getAndReplace(K key, V value);

	void removeAll(Set<? extends K> keys);

	void removeAll();

	void clear();

	void getName();
}
