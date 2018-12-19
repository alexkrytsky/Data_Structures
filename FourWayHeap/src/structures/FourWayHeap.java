package structures;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * @author Alex Krytskyi
 * @version 1.0
 *
 * @param <T>
 */
public class FourWayHeap <T extends Comparable<T>>
{
    private static final int INITIAL_SIZE = 10;

    private T[] binaryHeap;
    private int size = 0;
    private int heapWidth = 4;
    private  int nextIndex = 0; //start at index 0


    /**
     * Instantiates a new Four way heap.
     */
    public FourWayHeap()
    {
        //use a default size for the complete binary tree
        binaryHeap = (T[]) new Comparable[INITIAL_SIZE];
    }

    /**
     * Constructor that take an array of elements and build a heap based of them
     * @param initialElements Array initial elements for the heap
     */
    public FourWayHeap(T[] initialElements)
    {
        //copy over all element from the inout array to a new array
        binaryHeap = (T[]) new Comparable[initialElements.length + 1];

        System.arraycopy(initialElements, 0, binaryHeap, 0, initialElements.length);
        //housekeeping
        size = initialElements.length;
        nextIndex = size + 1;

        //build heap operation - O(n)
        buildHeap();
    }

    /**
     * Inserts element to the binary heap
     * @param element T new element
     */
    public void insert(T element)
    {
        //have we run out of space in our internal array?
        if(nextIndex == binaryHeap.length-1)
        {
            resize();//make some more room in our array
        }
        //place the element at the end of the complete binary tree
        //tree and percolate up
        binaryHeap[nextIndex] = element;
        swim(nextIndex);

        //moe to the next index and increment size
        nextIndex++;
        size++;
    }

    private void resize()
    {
        T[] newBinaryHeap = (T[])new Comparable[binaryHeap.length * 2];

        //copy element to a new heap
        System.arraycopy(binaryHeap, 0, newBinaryHeap, 0, binaryHeap.length);
        this.binaryHeap = newBinaryHeap;
    }

    /**
     * Deletes an element from the table
     * @return T element in minHeap
     */
    public T deleteMin()
    {
        //make sure we don't call deleteMin() with no elements
        if(isEmpty())
        {
            throw new NoSuchElementException("The heap is empty!");
        }
        T element = binaryHeap[0];
        //move the last element in the heap(size)to the first spot
        swap(0, this.size-1);
        //alter our indices and size
        this.size--;
        this.nextIndex--;

        //sink element to the correct position
        sink(0);
        return element;
    }

    private void swim(int index)
    {
        while (index > 0)
        {
            int parentIndex = (index - 1) / this.heapWidth;

            //check whether the parent and child node are out of rder
            if(binaryHeap[index].compareTo(binaryHeap[parentIndex]) < 0)
            {
                swap(index, parentIndex);
            }
            //move up to the parent and do the same thing
            index = parentIndex;
        }
    }

    //sinks the element to it's spot
    private void sink(int index)
    {
        //the last node with a child node is index at size / m
        while (index <= size / this.heapWidth)
        {
            int[] children = getChildrenIndicies(index);
            int indexToCheck = getIndexToCheck(children);

            //is the parent smaller than the smallest child
            if(indexToCheck < this.size && binaryHeap[indexToCheck].compareTo(binaryHeap[index]) < 0)
            {
                swap(indexToCheck, index);
                //move to that child node and do the same
                index = indexToCheck;
            }
            else
            {
                break; //exit our loop, heap order is satisfied
            }
        }
    }

    private int[] getChildrenIndicies(int index)
    {
        int childrenIndecies[] = new int[this.heapWidth];
        for (int i = 1; i <= this.heapWidth ; i++)
        {
            childrenIndecies[i-1] = index * this.heapWidth + i;
        }
        return childrenIndecies;
    }

    private int getIndexToCheck(int[] children)
    {
        int smallestIndex = children[0];
        for (int i = 0; i < children.length; i++)
        {
            if(children[i] < size && binaryHeap[children[i]].compareTo(binaryHeap[smallestIndex]) < 0)
            {
                smallestIndex = children[i];
            }
        }

        return smallestIndex;
    }

    //build heap by sinking all the elements in the heap
    private void buildHeap()
    {
        //start with the highest index node with a child node
        for (int i = (size-1) / this.heapWidth; i >= 0 ; i--)
        {
            sink(i);
        }
    }

    //swaps two elements in the array
    private void swap(int first, int second)
    {
        T temp = binaryHeap[first];
        binaryHeap[first] = binaryHeap[second];
        binaryHeap[second] = temp;
    }


    /**
     * Resets the binary heap
     */
    public void clear()
    {
        this.size = 0;
        this.nextIndex = 0;
        this.binaryHeap = (T[]) new Comparable[INITIAL_SIZE];
    }

    /**
     * Returns the minimum element in the heap. If heap is empty, throws new Exception
     * @return T element
     */
    public T peek()
    {
        if(size == 0)
        {
            throw new NoSuchElementException("The heap is empty");
        }
        return binaryHeap[0];
    }

    /**
     *
     * @param element T element to search for
     * @return boolean true/false does element exist or not
     */
    public boolean contains(T element)
    {
        for (int i = 0; i < this.size; i++)
        {
            if(binaryHeap[i].equals(element))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the size of the heap
     * @return int size of heap
     */
    public int size()
    {
        return this.size;
    }

    /**
     * checks is the heap empty or not
     * @return boolean returns empty or not.
     */
    public boolean isEmpty()
    {
        return this.size == 0;
    }

    @Override
    public String toString()
    {
        return "FourWayHeap{" +
                "binaryHeap=" + Arrays.toString(binaryHeap) +
                ", size=" + size +
                ", heapWidth=" + heapWidth +
                ", nextIndex=" + nextIndex +
                '}';
    }
}
