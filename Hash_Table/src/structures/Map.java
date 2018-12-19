package structures;

import helpers.KeyValuePair;
import interfaces.ICollection;
import interfaces.IMap;
import interfaces.ISet;

import java.util.*;


/**
 * @author Alex Krytskyi
 * @version 9/29/18
 * The type Map.
 *
 * @param <K> the type parameter
 * @param <V> the type parameter
 */
public class Map<K, V> implements IMap<K, V>
{
    private HashTable<KeyValuePair<K, V>> table;

    /**
     * Instantiates a new Map.
     */
    public Map()
    {
        this.table = new HashTable<>();
    }

    @Override
    public void add(K key, V value)
    {
        table.add(new KeyValuePair<>(key,value));
    }

    @Override
    public void remove(K key)
    {
        table.remove(new KeyValuePair<>(key, null));
    }

    @Override
    public V get(K key)
    {
        KeyValuePair<K,V> element = (KeyValuePair<K, V>) table.get(new KeyValuePair<>(key, null));
        return (element != null)? element.getValue() : null;
    }

    @Override
    public boolean keyExists(K key)
    {
        return table.contains(new KeyValuePair<>(key, null));
    }

    @Override
    public boolean valueExists(V value)
    {
        for (KeyValuePair<K,V> element : table)
        {
            if(element.getValue().equals(value))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size()
    {
        return table.size();
    }

    @Override
    public boolean isEmpty()
    {
        return table.isEmpty();
    }

    @Override
    public void clear()
    {
        table.clear();
    }

    @Override
    public Iterator<KeyValuePair<K, V>> iterator()
    {
        return table.iterator();
    }

    @Override
    public ISet<K> keyset()
    {
        Set<K> keySet = new Set<>();
        for (KeyValuePair<K,V> element : table)
        {
            keySet.add(element.getKey());
        }

        return keySet;
    }

    @Override
    public ICollection<V> values()
    {
        HashTable<V> valuesCollection = new HashTable<>();
        for (KeyValuePair<K,V> element : table)
        {
            valuesCollection.add(element.getValue());
        }

        return valuesCollection;
    }

    @Override
    public String toString()
    {
        return "Map{" +
                "table=" + table +
                '}';
    }
}
