package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import structures.FourWayHeap;

import java.util.NoSuchElementException;
import java.util.Random;

/**
 * @author Alex Krytskyi
 * @version 1.0
 */
public class FourWayHeapTest
{
    private static Random rand = new Random();
    private static final int ARRAY_SIZE = 100000;
    private static final int RAND_RANGE = 1000;
    private FourWayHeap<Integer> intHeap;

    @Before
    public void setup()
    {
        intHeap = new FourWayHeap<>();
    }
    @Test
    public void testInsertEmpty()
    {
        for (int i = 0; i < ARRAY_SIZE; i++)
        {
            intHeap.insert(rand.nextInt(RAND_RANGE));
        }
        Assert.assertEquals("Array didn't save all the elements", intHeap.size(), ARRAY_SIZE);
    }
    @Test
    public void testInsertNonEmpty()
    {
        Integer array[] = new Integer[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++)
        {
            array[i] = rand.nextInt(RAND_RANGE);
        }
        intHeap = new FourWayHeap<>(array);
        Assert.assertEquals("Array didn't save all the elements on non-empty insertion", intHeap.size(), ARRAY_SIZE);

        int first = 0, second = -99999;

        while (!intHeap.isEmpty())
        {
            first = second;
            second = intHeap.deleteMin();
            Assert.assertTrue("Array isn't in sorted order when inserted in non-empty!", first <= second);
        }
    }
    @Test
    public void testRemoveOnEmpty()
    {
        try
        {
            intHeap.deleteMin();
            Assert.fail("Didn't thrown exception on removal of elements with empty array.");
        }
        catch (NoSuchElementException ex)
        {

        }
        //populate array
        for (int i = 0; i < ARRAY_SIZE; i++)
        {
            intHeap.insert(rand.nextInt(RAND_RANGE));
        }
        //remove all elements
        while (!intHeap.isEmpty())
        {
            intHeap.deleteMin();
        }
        try
        {
            intHeap.deleteMin();
            Assert.fail("Didn't thrown exception on removal of elements with recently cleared array.");
        }
        catch (NoSuchElementException ex)
        {
            //passed the test
        }
    }
    @Test
    public void testRemoveFull()
    {
        for (int i = 0; i < ARRAY_SIZE; i++)
        {
            intHeap.insert(rand.nextInt(RAND_RANGE));
        }
        int first, second = -99999;

        while (!intHeap.isEmpty())
        {
            first = second;
            second = intHeap.deleteMin();
            Assert.assertTrue("Array isn't in sorted order!", first <= second);
        }
    }
    @Test
    public void testSize()
    {
        Assert.assertEquals("Array should have size 0", 0, intHeap.size());
        for (int i = 0; i < ARRAY_SIZE; i++)
        {
            intHeap.insert(rand.nextInt(RAND_RANGE));
        }
        Assert.assertEquals("Array should have size " + ARRAY_SIZE + " elements", ARRAY_SIZE, intHeap.size());
        while (!intHeap.isEmpty())
        {
            intHeap.deleteMin();
        }
        Assert.assertEquals("Array should have size 0 after removal", 0, intHeap.size());
    }
    @Test
    public void testIsEmpty()
    {
        Assert.assertTrue("Array should be indicated as empty", intHeap.isEmpty());
        for (int i = 0; i < ARRAY_SIZE; i++)
        {
            intHeap.insert(rand.nextInt(RAND_RANGE));
        }
        Assert.assertFalse("Array should be indicated as not empty", intHeap.isEmpty());
        while (!intHeap.isEmpty())
        {
            intHeap.deleteMin();
        }
        Assert.assertTrue("Array should be indicated as empty after removing elements", intHeap.isEmpty());
    }
    @Test
    public void testClear()
    {
        for (int i = 0; i < ARRAY_SIZE; i++)
        {
            intHeap.insert(rand.nextInt(RAND_RANGE));
        }
        intHeap.clear();
        Assert.assertTrue("Array should be clear after 'clear'.", intHeap.isEmpty());
        Assert.assertEquals("Array should be clear after 'clear'.", 0, intHeap.size());
    }
    @Test
    public void testContains()
    {
        Integer array[] = new Integer[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++)
        {
            array[i] = rand.nextInt(RAND_RANGE);
        }
        intHeap = new FourWayHeap<>(array);
        for (int i = 0; i < array.length; i++)
        {
            Assert.assertTrue("element should be in the array",intHeap.contains(array[i]));
        }
    }
    @Test
    public void testPeek()
    {

        for (int i = 0; i < ARRAY_SIZE; i++)
        {
            intHeap.insert(rand.nextInt(RAND_RANGE));
        }
        while (!intHeap.isEmpty())
        {
            int tmp = intHeap.peek();
            int removedElement = intHeap.deleteMin();
            Assert.assertEquals("First item peeked doesn't equals to removed min elmeent", removedElement, tmp);
        }
    }
}
