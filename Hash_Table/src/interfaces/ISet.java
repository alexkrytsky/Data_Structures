package interfaces;

/**
 * Provides an extension of the ICollection which includes
 * methods for set interactions. It is assumed that all
 * interactions with a class using this interface will
 * ensure that there are no duplicate elements in the
 * structure.
 *
 * DO NOT ALTER THIS FILE!
 *
 * @author Josh Archer
 * @version 1.0
 */
public interface ISet<T> extends ICollection<T>
{
    /**
     * Returns a new set object with the union of
     * the current set and the input parameter.
     *
     * @param other the other set to join with this set
     * @return the union of two sets
     */
    public ISet<T> union(ISet<T> other);

    /**
     * Returns a new set object with the intersection
     * of the current set and the input parameter.
     *
     * @param other the other set to join with this set
     * @return a intersection of two sets
     */
    public ISet<T> intersects(ISet<T> other);

    /**
     * Returns a new set object with the difference
     * between this set and the input set.
     * (i.e. this - other)
     *
     * @param other the other set to use in the difference
     *              operation
     * @return a difference of two sets
     */
    public ISet<T> difference(ISet<T> other);

    /**
     * Reports whether the input set is a subset of this
     * set.
     * @param other the subset candidate
     * @return true if other is a subset of this set,
     * otherwise false
     */
    public boolean isSubset(ISet<T> other);

    /**
     * Reports whether this set and the input set are
     * disjoint.
     * @param other the other set to consider
     * @return true if both sets are disjoint, otherwise
     * false
     */
    public boolean isDisjoint(ISet<T> other);

    /**
     * Reports whether this set is an empty set.
     * @return true if this set is empty, otherwise false
     */
    public boolean isEmptySet();
}
