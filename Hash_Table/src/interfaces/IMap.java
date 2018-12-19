package interfaces;

import helpers.KeyValuePair;

import java.util.Iterator;

/**
 * Provides the methods needed for a look-up table (symbol table).
 * This allows the structure to store key/value pairs, where
 * each key is unique and identifies values in the table.
 *
 * DO NOT ALTER THIS FILE!
 *
 * @author Josh Archer
 * @version 1.0
 */
public interface IMap<K, V> extends Iterable<KeyValuePair<K, V>>
{
    /**
     * Adds a key/value pair to the map. If the key already exists
     * in the map then this will update the value associated with
     * the key.
     *
     * @param key the key
     * @param value the value
     */
    public void add(K key, V value);

    /**
     * Removes a key (and the associated value) from the map. If
     * no key is found matching the input parameter, then no change
     * is made to the map.
     * @param key the key
     */
    public void remove(K key);

    /**
     * Returns the value associated with a key.
     *
     * @param key the key
     * @return the value associated with the key
     */
    public V get(K key);

    /**
     * Reports whether the input key is in the map.
     *
     * @param key the key
     * @return true if the key is in the map, otherwise false
     */
    public boolean keyExists(K key);

    /**
     * Reports whether the input value is in the map.
     *
     * @param value the value
     * @return true if the value is in the map, otherwise false
     */
    public boolean valueExists(V value);

    /**
     * Returns the number of key/value pairs in the map.
     *
     * @return the number of elements
     */
    public int size();

    /**
     * Reports whether the map is empty or not.
     *
     * @return true if no key/value pairs are in the map, otherwise
     * false
     */
    public boolean isEmpty();

    /**
     * Removes all key/value pairs in the map.
     */
    public void clear();

    /**
     * Returns an iterator over the key/value pairs in the map.
     *
     * @return an iterator using the Iterator<T> interface.
     */
    public Iterator<KeyValuePair<K, V>> iterator();

    /**
     * Returns an ISet<K> object with the all keys in the map.
     *
     * @return a set of keys
     */
    public ISet<K> keyset();

    /**
     * Returns an ICollection<V> object with all values in the map.
     *
     * @return a collection of values
     */
    public ICollection<V> values();
}
