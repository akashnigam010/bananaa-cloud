package in.socyal.sc.dbhelper.cache;

public interface AppCache {
	public <K, V> Cache<K, V> getCache(String name);
	public <K, V> Cache<K, V> getCache(Class<?> clazz);
}
