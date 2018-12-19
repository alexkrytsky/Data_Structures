package helpers;

/**
 * Stores a key/value pair together in an object.
 *
 * DO NOT ALTER THIS FILE!
 *
 * @author Josh Archer
 * @version 1.0
 */
public class KeyValuePair<K, V>
{
    private K key;
    private V value;

    public KeyValuePair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    public K getKey()
    {
        return key;
    }

    public void setKey(K key)
    {
        this.key = key;
    }

    public V getValue()
    {
        return value;
    }

    public void setValue(V value)
    {
        this.value = value;
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true;
        }
        else if (other == null || getClass() != other.getClass())
        {
            return false;
        }

        KeyValuePair<K, V> otherPair = (KeyValuePair<K, V>) other;
        return key.equals(otherPair.key);
    }

    @Override
    public int hashCode()
    {
        return key != null ? key.hashCode() : 0;
    }

    public String toString()
    {
        return key + " --> " + value;
    }
}
