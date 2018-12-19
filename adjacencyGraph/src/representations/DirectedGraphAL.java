package representations;


import java.util.*;

/**
 * @author Alex Krytskyi
 * @version 1.0
 *
 * This is a directed, unweighted graph that uses adjacency lists.
 * @param <V>
 */
public class DirectedGraphAL<V>
{
    public Map<V, LinkedList<Edge<V>>> adjacencyLists;
    public DirectedGraphAL()
    {
        adjacencyLists = new HashMap<>();
    }

    public boolean addVertex(V element)
    {
        if(!hasVertex(element))
        {
            //add
            adjacencyLists.put(element, new LinkedList<Edge<V>>());
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean addEdge(V source, V dest)
    {
        //check that the vertices are preset
        if(!hasVertex(source) || !hasVertex(dest))
        {
            throw new IllegalArgumentException("One of the following vertices doesn't exist: " + source + ", " + dest);
        }
        //double check that the edge does not exist
        Edge<V> newEdge = new Edge<>(source, dest);
        LinkedList<Edge<V>> adjacencyList = adjacencyLists.get(source);
        if(adjacencyList.contains(newEdge))
        {
            return false;
        }
        else
        {
            adjacencyList.add(newEdge);
            return true;
        }
    }

    public boolean hasVertex(V element)
    {
        return adjacencyLists.containsKey(element);
    }

    public Set<V> vertices()
    {
        return  adjacencyLists.keySet();
    }
    public Set<Edge<V>> edges()
    {
        Set<Edge<V>> results = new HashSet<>();

        //loop over each adj. list
        for(LinkedList<Edge<V>> adjacencyList : adjacencyLists.values())
        {
            results.addAll(adjacencyList);
        }

        return results;
    }

    @Override
    public String toString()
    {
        return "DirectedGraphAL{" +
                "adjacencyLists=" + adjacencyLists +
                '}';
    }

    public static class Edge<V>
    {
        private V source;
        private V dest;

        public Edge(V source, V dest)
        {
            this.source = source;
            this.dest = dest;
        }

        public V getSource()
        {
            return source;
        }

        public V getDest()
        {
            return dest;
        }

        @Override
        public boolean equals(Object other)
        {
            if (this == other)
            {
                return true;
            }
            if (other == null || getClass() != other.getClass())
            {
                return false;
            }
            Edge<V> otherEdge = (Edge<V>) other;
            return Objects.equals(source, otherEdge.source) &&
                    Objects.equals(dest, otherEdge.dest);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(source, dest);
        }
    }
}
