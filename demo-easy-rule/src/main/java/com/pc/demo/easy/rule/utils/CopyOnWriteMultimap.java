package com.pc.demo.easy.rule.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CopyOnWriteMultimap<K, V> {

  private volatile Map<K, Set<V>> map;

  public CopyOnWriteMultimap() {
    this.map = Collections.emptyMap();
  }

  public boolean containsKey(Object k) {
    return map.containsKey(k);
  }

  public boolean containsValue(Object k, Object v) {
    Set<V> set = map.get(k);
    if (set == null) {
      return false;
    }
    return set.contains(v);
  }

  public Set<V> get(Object k) {
    return map.get(k);
  }

  public boolean isEmpty() {
    return map.isEmpty();
  }

  public synchronized void clear() {
    this.map = Collections.emptyMap();
  }

  public synchronized boolean put(K k, V v) {
    Map<K, Set<V>> copy = new HashMap<K, Set<V>>(this.map);
    Set<V> set = copy.get(k);
    if (set == null) {
      set = new HashSet<>();
    } else {
      set = new HashSet<>(set);
    }
    if (!set.add(v)) {
      return false;
    }
    copy.put(k, Collections.unmodifiableSet(set));
    this.map = Collections.unmodifiableMap(copy);
    return true;
  }

  public synchronized Set<V> removeKey(K k) {
    Map<K, Set<V>> copy = new HashMap<K, Set<V>>(this.map);
    Set<V> prev = copy.remove(k);
    this.map = Collections.unmodifiableMap(copy);
    return prev;
  }

  public synchronized boolean removeValue(K k, V v) {
    Map<K, Set<V>> copy = new HashMap<K, Set<V>>(this.map);
    Set<V> prev = copy.get(k);
    if (prev == null) {
      return false;
    }
    if (!prev.contains(v)) {
      return false;
    }
    Set<V> set = new HashSet<V>(prev);
    set.remove(v);
    if (set.isEmpty()) {
      copy.remove(k);
    } else {
      copy.put(k, Collections.unmodifiableSet(set));
    }
    this.map = Collections.unmodifiableMap(copy);
    return true;
  }

}
