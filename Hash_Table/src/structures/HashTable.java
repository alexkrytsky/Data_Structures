package structures;

import interfaces.ICollection;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**My custom hash table that uses chaining collision
 * @param <T>
 * @author alex
 * @version 1.0
 */
public class HashTable<T> implements ICollection<T>
{
    private static final int DEFAULT_SIZE = 10;
    private static final double DEFAULT_LOAD_FACTOR = 2.5;
    private static final double TABLE_INCREASE_COEF = 1.5;

    private HashTableNode<T>[] table;

    private double loadFactor;
    private int initialSize;
    private int size;
    private int usedSpace;
    private int modCount = 0;

    /**
     * Instantiates a new My hash table.
     */
    public HashTable()
    {
        this(DEFAULT_LOAD_FACTOR, DEFAULT_SIZE);
    }

    /**
     * Instantiates a new My hash table.
     *
     * @param loadFactor  the load factor
     * @param initialSize the initial size
     */
    public HashTable(double loadFactor, int initialSize)
    {
        this.loadFactor = loadFactor;
        this.initialSize = initialSize;
        this.table = new HashTableNode[this.initialSize];
    }
    @Override
    public void add(Object element)
    {
        if ((double) this.usedSpace / table.length >= loadFactor)
        {
            this.rehash();
        }

        //we know we have enough space for a new element
        int index = Math.abs(element.hashCode() % table.length);

        //search for spot
        HashTableNode<T> current = table[index];

        if (!this.contains(element))
        {
            HashTableNode<T> newElement = new HashTableNode<>((T)element);
            newElement.next = current;
            table[index] = newElement;

            this.modCount++;
            this.size++;
            this.usedSpace++;
        }
    }

    @Override
    public void remove(Object element)
    {
        //we know we have enough space for a new element
        int index = Math.abs(element.hashCode() % table.length);

        //search for spot
        HashTableNode<T> current = table[index];

        while (current != null)
        {
            if (current.element.equals(element) && !current.isEmpty)
            {
                current.isEmpty = true;

                this.size--;
                this.modCount++;
                return;
            }
            current = current.next;
        }
        throw new NoSuchElementException("No element found");
    }

    @Override
    public boolean contains(Object element)
    {
        //we know we have enough space for a new element
        int index = Math.abs(element.hashCode() % table.length);
        HashTableNode<T> current = this.table[index];

        //check whether we have an element in the structure or not
        while (current != null)
        {
            if (element.equals(current.element) && !current.isEmpty)
            {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    @Override
    public int size()
    {
        return this.size;
    }

    @Override
    public boolean isEmpty()
    {
        return this.size == 0;
    }

    @Override
    public void clear()
    {
        this.size = 0;
        this.modCount++;
        this.table = new HashTableNode[this.initialSize];
    }

    @Override
    public Object get(Object element)
    {
        //we know we have enough space for a new element
        int index = Math.abs(element.hashCode() % table.length);
        HashTableNode<T> current = this.table[index];

        //iterate over the nodes and check is the element present or not
        while (current != null)
        {
            if (current.element.equals(element) && !current.isEmpty)
            {
                return current.element;
            }
            current = current.next;
        }

        return null;
    }

    /**
     * Method that rehashes the table when the load factor is greater than we have set up
     */
    private void rehash()
    {
        //resize our table and re-hash our elements in a new table
        HashTableNode<T>[] oldTable = table;
        this.table = new HashTableNode[(int)(oldTable.length * TABLE_INCREASE_COEF)];
        this.size = 0;
        HashTableNode<T> current;
        for (int i = 0; i < oldTable.length; i++)
        {
            current = oldTable[i];
            while (current != null)
            {
                if (!current.isEmpty)
                {
                    add(current.element);//place elements in a new table based on an old table
                }
                current = current.next;
            }
        }
    }

    @Override
    public Iterator<T> iterator()
    {
        return new HashTableIterator(this.table, this.modCount);
    }

    @Override
    public String toString()
    {
        return "MyHashTable{" +
                "table=" + Arrays.toString(table) +
                ", loadFactor=" + loadFactor +
                ", initialSize=" + initialSize +
                ", size=" + size +
                ", usedSpace=" + usedSpace +
                ", modCount=" + modCount +
                '}';
    }

    /**
     * Hash table node
     * @param <T>
     */
    private static class HashTableNode<T>
    {
        private T element;
        private boolean isEmpty;
        private HashTableNode<T> next;

        public HashTableNode()
        {
            this.element = null;
            this.isEmpty = true;
            this.next = null;
        }

        public HashTableNode(T element, boolean isEmpty, HashTableNode<T> next)
        {
            this.element = element;
            this.isEmpty = isEmpty;
            this.next = next;
        }

        public HashTableNode(T element)
        {
            this(element, false, null);
        }

        @Override
        public String toString()
        {
            if (this.isEmpty)
            {
                return "empty";
            }
            else
            {
                return this.element.toString();
            }
        }
    }

    private class HashTableIterator implements Iterator<T>
    {
        private HashTableNode<T>[] table;
        private int nextIndex = -1;
        private HashTableNode<T> current = new HashTableNode<>();
        private int modCountSnapshot;

        public HashTableIterator(HashTableNode<T>[] table, int modCountSnapshot)
        {
            this.table = table;
            this.modCountSnapshot = modCountSnapshot;
            //set current and next index here
            findNextIndex();
        }

        @Override
        public boolean hasNext()
        {
            if (this.modCountSnapshot != HashTable.this.modCount)
            {
                throw new ConcurrentModificationException("you cannot change your table while using an iterator");
            }
            return nextIndex != -1;
        }

        @Override
        public T next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException("There is no element to return");
            }
            T currentElement = this.current.element;
            findNextIndex();
            return currentElement;
        }

        //helper method
        private void findNextIndex()
        {
            for (int i = this.nextIndex + 1; i < table.length; i++)
            {
                if (table[i] != null && !table[i].isEmpty)
                {
                    //if the next element is present, we will assign current to next element
                    if (this.current.next != null)
                    {
                        this.current = this.current.next;
                        return;
                    }
                    //move the index to the next element
                    this.current = table[i];
                    nextIndex = i;
                    return;
                }
            }
            nextIndex = -1;//invalid index. Stop now!
        }

        @Override
        public String toString()
        {
            return "HashTableIterator{" +
                    "table=" + Arrays.toString(table) +
                    ", nextIndex=" + nextIndex +
                    ", current=" + current +
                    ", modCountSnapshot=" + modCountSnapshot +
                    '}';
        }
    }
}
