package structures;

import interfaces.ISet;

import java.util.Iterator;

/**
 * @author Alex
 * @version 9/29/18
 *
 * The type Set.
 *
 * @param <T> the type parameter
 */
public class Set<T> implements ISet<T>
{
    private HashTable<T> table;

    /**
     * Instantiates a new Set.
     */
    public Set()
    {
        this.table = new HashTable<>();
    }

    @Override
    public void add(T element)
    {
        table.add(element);
    }

    @Override
    public void remove(T element)
    {
        table.remove(element);
    }

    @Override
    public boolean contains(T element)
    {
        return table.contains(element);
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
    public T get(T element)
    {
        return (T)table.get(element);
    }

    @Override
    public Iterator<T> iterator()
    {
        return table.iterator();
    }

    @Override
    public ISet<T> union(ISet<T> other)
    {
        Set<T> newSet = new Set<>();
        newSet.table = this.table;
        for (T element : other)
        {
            //we add every element from other set to a one common set
            table.add(element);
        }
        return newSet;
    }

    @Override
    public ISet<T> intersects(ISet<T> other)
    {
        //iterate for one set and compare with different
        Set<T> newSet = new Set<>();

        for (T element : this.table)
        {
            //if other set have element from this set, we add it as interested elements
            if(other.contains(element))
            {
                newSet.add(element);
            }
        }
        return newSet;
    }

    @Override
    public ISet<T> difference(ISet<T> other)
    {
        //iterate for one set and compare with different.
        Set<T> newSet = new Set<>();

        for (T element : this.table)
        {
            //if other set doesn't have it, element is added to new set
            if(!other.contains(element))
            {
                newSet.add(element);
            }
        }
        return newSet;
    }

    @Override
    public boolean isSubset(ISet<T> other)
    {
        for (T element : other)
        {
            //if current set doesn't contain at least one element of other set, it's considered that it's not a subset
            if(!table.contains(element))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isDisjoint(ISet<T> other)
    {
        for (T element : other)
        {
            //if at least one element of other set is part of this one, they're not considered disjoint
            if(table.contains(element))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEmptySet()
    {
        return table.isEmpty();
    }

    @Override
    public String toString()
    {
        return "Set{" +
                "table=" + table +
                '}';
    }
}
