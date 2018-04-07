package org.jthoughtlabs.enahanced.api.map;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class TimeOutMap. This class can be specified by default timeout for the
 * values that are stored.
 *
 * Uses {@link ConcurrentHashMap} internally to lookup the timeouts. This class
 * internally runs a thread to keep polling the timeouts, and this thread will
 * be garbage collected along with the main thread
 *
 *
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 */
public class TimeOutMap<K, V> implements Map<K, V> {

	/** The map. */
	private Map<K, V> map;

	/** The timeout. */
	private long timeout;

	/** The time out map. */
	private Map<Integer, List<K>> timeOutMap = new ConcurrentHashMap<>();

	/**
	 * Instantiates a new time out map.
	 *
	 * @param map
	 *          the map
	 * @param timeout
	 *          the timeout
	 */
	public TimeOutMap(Map<K, V> map, long timeout) {
		this.map = map;
		this.timeout = timeout;

		Runnable run = getTimeOutVerifier(map);
		Thread t = new Thread(run);
		t.start();

	}

	/**
	 * Gets the time out verifier.
	 *
	 * @param map
	 *          the map
	 * @return the time out verifier
	 */
	private Runnable getTimeOutVerifier(Map<K, V> map) {
		return () -> {
			while (true) {
				LocalTime time = LocalTime.now();
				int hourMin = time.getHour() * 10 + time.getMinute();

				List<K> list = this.timeOutMap.get(hourMin);
				if (list != null && !list.isEmpty()) {
					list.stream().forEach(l -> map.remove(l));
				}
			}
		};
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}

	@Override
	public V put(K key, V value) {

		LocalTime time = LocalTime.now().plusMinutes(timeout);
		int hourMin = time.getHour() * 10 + time.getMinute();
		put(hourMin, key);
		return map.put(key, value);
	}

	@Override
	public V remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		LocalTime time = LocalTime.now().plusMinutes(timeout);
		int hourMin = time.getHour() * 10 + time.getMinute();
		Set<? extends K> keySet = m.keySet();
		List<K> list = timeOutMap.get(hourMin);
		if (list == null) {
			list = new ArrayList<>();
			timeOutMap.put(hourMin, list);
		}
		list.addAll(keySet);
		map.putAll(m);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	/**
	 * Put.
	 *
	 * @param key
	 *          the key
	 * @param value
	 *          the value
	 * @return the list
	 */
	public List<K> put(Integer key, K value) {
		List<K> list = timeOutMap.get(key);
		if (list == null) {
			list = new ArrayList<>();
			timeOutMap.put(key, list);
		}
		list.add(value);
		return list;
	};

}
