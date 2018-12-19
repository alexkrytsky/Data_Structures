package tests;

import helpers.KeyValuePair;
import interfaces.IMap;
import org.junit.Before;
import org.junit.Test;
import structures.Map;

import java.util.Arrays;

/**
 * Tests the Map<T> class to determine if it follows
 * the contract of the IMap<T> interface.
 *
 * DO NOT ALTER THIS FILE!
 *
 * @author Josh Archer
 * @version 1.0
 */
public class MapTests extends TestFacade
{
    private String[] names = {"Joan", "Jeremy", "Jim", "Jill", "Jonas"};
    private int[] heightInInches = {67, 70, 73, 60, 64};
    private IMap<String, Integer> map;

    /**
     * Prepares each test before execution.
     */
    @Before
    public void setup()
    {
        map = new Map<>();
    }

    //adds some initial elements to the set for tests
    private void addTestableElements()
    {
        for (int i = 0; i < names.length; i++)
        {
            map.add(names[i], heightInInches[i]);
        }
    }

    /**
     * Verifies that pairs of elements are located in the structure
     * after calling Map.add(key, value).
     */
    @Test
    public void pairsExistAfterAdding()
    {
        addTestableElements();

        //check if elements are present
        for (String name : names)
        {
            isTrue("Name (" + name + ") not found after adding to map as key", map.keyExists(name));
        }

        for (int i = 0; i < names.length; i++)
        {
            int height = map.get(names[i]);
            equals("Not able to retrieve correct height (" + height + ") for key (" + names[i] + ")",
                    heightInInches[i], height);
        }
    }

    /**
     * Verifies that associated values can be found using a key and
     * the Map.get(key) method.
     */
    @Test
    public void getValueWithKey()
    {
        addTestableElements();

        //check that we can retrieve an existing value from a key in the map
        equals("Cannot retrieve a value (" + heightInInches[2] +") associated with a key (" +
                map.get(names[2]) + ")", heightInInches[2], map.get(names[2]));

        //check that we get null back for a missing key in the map
        equals("Calling Map.get(x) for a missing element does not return null",
                null, map.get("Sally"));
    }

    /**
     * Verifies that the number of elements reported in the structure
     * are consistent when calling size(), clear(), add() or remove().
     */
    @Test
    public void numberOfElementsConsistent()
    {
        //size should initially be empty
        equals("Map.size() does not return zero for an empty map", 0, map.size());

        //size should represent the number of pairs in the map
        addTestableElements();
        equals("Number of elements in the map does not match the number of pairs added" +
                " to the map", names.length, map.size());

        //size should change after more additions or removals
        map.add("Sally", 70);
        equals("Map.size() has not incremented after adding a new element",
                names.length + 1, map.size());
        map.remove("Jeremy");
        equals("Map.size() has not decremented after adding a new element",
                names.length, map.size());

        map.clear();
        equals("Map.size() does not return zero after calling Map.clear()",
                0, map.size());
    }

    /**
     * Verifies that Map.keyExists(key) correctly identifies keys in the map.
     */
    @Test
    public void keysExist()
    {
        addTestableElements();

        //find existing key
        isTrue("Existing key is not found in map (Jonas)", map.keyExists("Jonas"));

        //look for missing key
        isFalse("Map is reporting a key that has not been added to map (Sam)",
                map.keyExists("Sam"));
    }

    /**
     * Verifies that Map.valueExists(key) correctly identifies values in the map.
     */
    @Test
    public void valuesExist()
    {
        addTestableElements();

        //find existing value
        isTrue("Existing value is not found in map (60)", map.valueExists(60));

        //look for missing value
        isFalse("Map is reporting a value that has not been added to map (50)",
                map.valueExists(50));
    }

    /**
     * Verifies that the iterator in the Map class returns all pairs in the map.
     */
    @Test
    public void iteratingOverPairs()
    {
        addTestableElements();

        //see if we can identify all pairs added
        String[] namesSeen = new String[names.length];
        int[] heightsSeen = new int[heightInInches.length];
        int index = 0;
        for (KeyValuePair<String, Integer> pair : map)
        {
            namesSeen[index] = pair.getKey();
            heightsSeen[index] = pair.getValue();

            index++;
        }

        //sort and verify
        verifyNames(names, namesSeen);
        verifyHeights(heightInInches, heightsSeen);
    }

    /**
     * Verifies that the iterator from Map.keyset() returns all keys in the map.
     */
    @Test
    public void iteratingOverKeys()
    {
        addTestableElements();

        //see if we can identify all pairs added
        String[] namesSeen = new String[names.length];
        int index = 0;
        for (String name : map.keyset())
        {
            namesSeen[index] = name;

            index++;
        }

        //sort and verify
        verifyNames(names, namesSeen);
    }

    /**
     * Verifies that the iterator from Map.values() returns all values in the map.
     */
    @Test
    public void iteratingOverValues()
    {
        addTestableElements();

        //see if we can identify all pairs added
        int[] heightsSeen = new int[heightInInches.length];
        int index = 0;
        for (int height : map.values())
        {
            heightsSeen[index] = height;

            index++;
        }

        //sort and verify
        verifyHeights(heightInInches, heightsSeen);
    }

    private void verifyNames(String[] expected, String[] actual)
    {
        Arrays.sort(actual);
        Arrays.sort(expected);

        arrayEquals("All names retrieved from the map " + Arrays.toString(expected) +
                        " do not match the names added previously " + Arrays.toString(actual),
                expected, actual);
    }

    private void verifyHeights(int[] expected, int[] actual)
    {
        Arrays.sort(actual);
        Arrays.sort(expected);

        arrayEquals("All heights retrieved from the map " + Arrays.toString(expected) +
                        " do not match the heights added previously " + Arrays.toString(actual),
                expected, actual);
    }
}
