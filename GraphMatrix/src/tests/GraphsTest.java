package tests;

import graphs.Edge;
import graphs.IGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import structures.DirectedGraph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Runs a series of tests to verify a directed graph
 * structure using the IGraph<V> interface.
 *
 * DO NOT ALTER THIS FILE!
 *
 * @author Josh Archer
 * @version 1.0
 */
public class GraphsTest
{
    public static final int LOW_WGT = 1;
    public static final int HIGH_WGT = 20;
    private static String[] testVerts = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L"};
    private static int[] edgePairs;
    private static int[] edgeWeights;

    private IGraph<String> graph;

    /**
     * Assigns test values for edge selection and weights for use in the tests below.
     */
    @BeforeClass
    public static void initializeTest()
    {
        edgePairs = randomRangeNoDups(0, testVerts.length - 1, testVerts.length - 2);
        edgeWeights = randomRangeNoDups(LOW_WGT, HIGH_WGT, testVerts.length - 2);
    }

    private static int[] randomRangeNoDups(int low, int high, int amount)
    {
        Set<Integer> nums = new HashSet<>();
        Random rand = new Random();

        while (nums.size() < amount)
        {
            nums.add(rand.nextInt(high - low + 1) + low);
        }

        return nums.stream().mapToInt(i->i).toArray();
    }

    /**
     * Creates a new graph for each test.
     */
    @Before
    public void setup()
    {
        graph = new DirectedGraph<>();
    }

    private void addFewVertices()
    {
        for (String letter : testVerts)
        {
            graph.addVertex(letter);
        }
    }

    private void addFewEdges()
    {
        for (int i = 0; i < edgePairs.length - 1; i++)
        {
            String source = testVerts[edgePairs[i]];
            String destination = testVerts[edgePairs[i + 1]];
            graph.addEdge(source, destination, 1);
        }
    }

    private void addFewWeightedEdges()
    {
        for (int i = 0; i < edgePairs.length - 1; i++)
        {
            String source = testVerts[edgePairs[i]];
            String destination = testVerts[edgePairs[i + 1]];
            graph.addEdge(source, destination, edgeWeights[i]);
        }
    }
    @Test
    public void testConstructor()
    {
        graph = new DirectedGraph<>(25);
        Assert.assertEquals("Graph size doesn't represent set size '25'", 25,
                ((DirectedGraph<String>) graph).getMatrixSize());
    }
    /**
     * Verifies that vertices are added to the graph.
     */
    @Test
    public void addVertexTest()
    {
        addFewVertices();

        //verify size
        Assert.assertEquals("Size should be " + testVerts.length + " after adding elements",
                testVerts.length, graph.vertexSize());

        //verify contains
        for (String letter : testVerts)
        {
            Assert.assertTrue(letter + " was not found in graph after being added to graph",
                    graph.containsVertex(letter));
        }
    }

    /**
     * Checks the return type of Graph.addVertex(), making sure
     * that duplicate vertices are not added to the graph.
     */
    @Test
    public void testDuplicateVertex()
    {
        addFewVertices();

        Assert.assertTrue("Adding a new vertex returns false", graph.addVertex("M"));
        Assert.assertFalse("Adding a duplicate vertex returns true", graph.addVertex("M"));
    }

    /**
     * Verifies that edges can be added to the graph.
     */
    @Test
    public void addEdgeTest()
    {
        addFewVertices();
        addFewEdges();

        int numEdges = edgePairs.length - 1;

        //verify size
        Assert.assertEquals("Vertex size should be " + testVerts.length + " after adding vertices",
                testVerts.length, graph.vertexSize());
        Assert.assertEquals("Edge size should be " + numEdges + " after adding edges",
                numEdges, graph.edgeSize());

        for (int i = 0; i < edgePairs.length - 1; i++)
        {
            String source = testVerts[edgePairs[i]];
            String destination = testVerts[edgePairs[i + 1]];

            Assert.assertTrue("Edge does not exist when it should " + source + " - " + destination,
                    graph.containsEdge(source, destination));
        }
    }

    /**
     * Checks whether adding an edge with a vertex not in the map is recognized
     * by the return type of the addEdge() method. (i.e. the method should return
     * true when the edge is added and false otherwise)
     */
    @Test
    public void addEdgeWithoutVertexTest()
    {
        addFewVertices();
        addFewEdges();

        //get an unused edge starting index
        int unused = missingEdge();

        //add an edge with both vertices
        Assert.assertTrue("Graph does not recognize adding a valid edge",
                graph.addEdge(testVerts[unused], testVerts[unused + 1], 1));

        //add edges with missing vertices
        Assert.assertFalse("Graph does recognizes adding an invalid edge",
                graph.addEdge(testVerts[unused], "M", 1));
        Assert.assertFalse("Graph does recognizes adding an invalid edge",
                graph.addEdge("M", testVerts[unused], 1));
        Assert.assertFalse("Graph does recognizes adding an invalid edge",
                graph.addEdge("P", "M", 1));
    }

    private int missingEdge()
    {
        Set<Integer> choices = new HashSet<>();
        for (int index : edgePairs)
        {
            choices.add(index);
        }

        for (int i = 0; i < testVerts.length - 1; i++)
        {
            if (!choices.contains(i))
            {
                return i;
            }
        }

        throw new IllegalStateException("Index not found that is unused");
    }

    /**
     * Checks whether the graph reports the unsuccessful addition
     * of duplicate edges.
     */
    @Test
    public void addDuplicateEdgeTest()
    {
        addFewVertices();
        addFewEdges();

        graph.addEdge("A", "B", 1);
        int sizeBefore = graph.vertexSize();
        Assert.assertFalse("Graph does not recognize duplicate edges",
                graph.addEdge("A", "B", 1));

        int sizeAfter = graph.vertexSize();
        Assert.assertEquals("Size should be unchanged after adding a duplicate",
                sizeBefore, sizeAfter);
    }

    /**
     * Checks whether the graph recognizes missing vertices.
     */
    @Test
    public void missingVertexTest()
    {
        addFewVertices();

        //verify no false positives
        Assert.assertFalse("Missing vertex was found in graph", graph.containsVertex("M"));
    }

    /**
     * Checks whether the graph recognizes missing edges.
     */
    @Test
    public void missingEdgeTest()
    {
        addFewVertices();
        addFewEdges();

        //verify no false positives
        Assert.assertFalse("Missing edge (A, A) was found in graph", graph.containsEdge("A", "A"));
        Assert.assertFalse("Missing edge (D, F) was found in graph", graph.containsEdge("A", "A"));

        //force a resize and then verify no false positives
        //(leaving 0 in the upper indices after a resize)
        for (int i = 1; i <= 100; i++)
        {
            graph.addVertex(i + ""); //add "1", "2", ... , "100"
        }

        Assert.assertFalse("Missing edge (50, 50) was found in graph", graph.containsEdge("50", "50"));

        //verify that we can't find an edge with one missing vertex
        Assert.assertFalse("Missing edge (A, 50) was found in graph", graph.containsEdge("A", "50"));
        Assert.assertFalse("Missing edge (50, A) was found in graph", graph.containsEdge("50", "A"));
    }

    /**
     * Verifies that edges are added to the graph in a directed way.
     */
    @Test
    public void directednessTest()
    {
        addFewVertices();
        addFewEdges();

        //verify that pairs only work in one direction
        for (int i = 0; i < edgePairs.length - 1; i++)
        {
            String source = testVerts[edgePairs[i]];
            String destination = testVerts[edgePairs[i + 1]];

            Assert.assertTrue("Edge does not exist when it should " + source + " - " + destination,
                    graph.containsEdge(source, destination));
            Assert.assertFalse("Edge does exist when it should not " + source + " - " + destination,
                    graph.containsEdge(destination, source));
        }
    }

    /**
     * Verifies that Graph.vertices() returns all vertices added to the graph.
     */
    @Test
    public void vertexSetTest()
    {
        //empty vertex set
        Assert.assertEquals("Vertex array should be empty before adding any vertices",
                graph.vertices().size(), 0);

        addFewVertices();
        addFewEdges();

        String[] vertices = graph.vertices().toArray(new String[graph.vertexSize()]);
        Arrays.sort(vertices);

        Assert.assertArrayEquals("All vertices are not available through this method", vertices, testVerts);
    }

    /**
     * Verifies that Graph.edges() returns all edges added to the graph.
     */
    @Test
    public void edgeSetTest()
    {
        //empty edge set
        Assert.assertEquals("Edge array should be empty before adding any edges",
                graph.edges().size(), 0);

        addFewVertices();
        addFewEdges();

        Set<Edge<String>> edges = graph.edges();
        Set<Edge<String>> others = new HashSet<>();
        for (int i = 0; i < edgePairs.length - 1; i++)
        {
            String source = testVerts[edgePairs[i]];
            String destination = testVerts[edgePairs[i + 1]];

            others.add(new Edge<>(source, destination, 0));
        }

        Assert.assertEquals("Number of edges returned does is incorrect", edges.size(), others.size());

        for (Edge<String> edge : others)
        {
            Assert.assertTrue("Edge set contains an edge that was NOT previously added to the graph",
                    edges.contains(edge));
        }
    }

    /**
     * Tests whether edges weights passed to Graph.addEge() are stored with
     * the correct weights.
     */
    @Test
    public void weightednessTest()
    {
        addFewVertices();
        addFewWeightedEdges();

        //verify weighted edges
        for (int i = 0; i < edgePairs.length - 1; i++)
        {
            String source = testVerts[edgePairs[i]];
            String destination = testVerts[edgePairs[i + 1]];

            Assert.assertEquals("Edge weight " + edgeWeights[i] + " is incorrect for edge (" +
                            source + ", " + destination + ")",
                    edgeWeights[i], graph.edgeWeight(source, destination));
        }

        //make sure we can't add an invalid weight
        try
        {
            graph.addEdge("A", "B", -10);
        }
        catch (IllegalArgumentException ex)
        {
            assert true; //do nothing
        }
    }

    /**
     * Verifies that vertices can reliably be removed from the graph.
     */
    @Test
    public void removeVertexTest()
    {
        addFewVertices();
        addFewEdges();

        //make sure you cannot remove missing vertex elements
        Assert.assertFalse("Vertex reported as removed, when it is not in the graph",
                graph.removeVertex("M"));

        //make sure you can remove vertices in graph
        Assert.assertTrue("Vertex reported as not removed, when it is in the graph",
                graph.removeVertex("L"));

        //remove all vertices in the graph
        Set<String> vertices = graph.vertices();
        Assert.assertEquals("Number of vertices in vertex set after removing an element is incorrect",
                testVerts.length - 1, vertices.size());
        Assert.assertEquals("Number of vertices in graph after removing an element is incorrect",
                testVerts.length - 1, graph.vertexSize());

        for (String vertex : vertices)
        {
            Assert.assertTrue("Element reported as not removed, when it is in the graph",
                    graph.removeVertex(vertex));
        }

        Assert.assertEquals("Number of vertices in graph after removing all elements is incorrect",
                0, graph.vertexSize());
    }

    /**
     * Verifies that edges can reliably be removed from the graph.
     */
    @Test
    public void removeEdgeTest()
    {
        addFewVertices();
        addFewEdges();

        //make sure you cannot remove missing edge elements (one missing vertex, or both)
        Assert.assertFalse("Edge reported as removed, when it is not in the graph",
                graph.removeEdge("A", "M"));
        Assert.assertFalse("Edge reported as removed, when it is not in the graph",
                graph.removeEdge("M", "A"));
        Assert.assertFalse("Edge reported as removed, when it is not in the graph",
                graph.removeEdge("N", "M"));

        //make sure you can remove edges in graph
        Set<Edge<String>> edges = graph.edges();
        Edge<String> first = edges.iterator().next();

        Assert.assertTrue("Edge reported as not removed, when it is in the graph",
                graph.removeEdge(first.getSource(), first.getDestination()));
        Assert.assertEquals("Number of edges in graph after removing an element is incorrect",
                edgePairs.length - 2, graph.edgeSize());

        //add the edge back
        graph.addEdge(first.getSource(), first.getDestination(), 1);

        //remove all edges in the graph
        for (Edge<String> edge : edges)
        {
            Assert.assertTrue("Edge reported as not removed, when it is in the graph",
                    graph.removeEdge(edge.getSource(), edge.getDestination()));
        }

        Assert.assertEquals("Number of edges in graph after removing all elements is incorrect",
                0, graph.edgeSize());
    }

    /**
     * Tests whether the Graph.clear() method removes all vertices
     * and edges from the graph.
     */
    @Test
    public void clearTest()
    {
        addFewVertices();
        addFewEdges();

        graph.clear();

        //verify size
        Assert.assertEquals("Number of vertices reported in the graph should be zero",
                0, graph.vertexSize());
        Assert.assertEquals("Number of edges reported in the graph should be zero",
                0, graph.edgeSize());

        //verify sets are empty
        Assert.assertEquals("Number of vertices returned by the graph should be zero",
                0, graph.vertices().size());
        Assert.assertEquals("Number of edges returned by the graph should be zero",
                0, graph.edges().size());
    }
}