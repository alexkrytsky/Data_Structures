package structures;

import graphs.Edge;
import graphs.IGraph;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @author Alex K.
 * @version 11/10/18
 * @param <V>
 */
public class DirectedGraph<V> implements IGraph<V>
{
    private int matrixSize = 10;
    private int verticesSize;
    private int edgesSize;
    private int [][]matrix;
    private Bijection<Integer, V> bijection;
    private Stack<Integer> emptyIndexes;

    /**
     * Constructor that create matrix with initial size of elements
     */
    public DirectedGraph()
    {
        matrix = new int[matrixSize][matrixSize];
        emptyIndexes = new Stack<>();
        bijection = new Bijection<>();
        this.verticesSize = 0;
        this.edgesSize = 0;
        emptyMatrix(this.matrix);
    }

    /**
     * Constructor that create matrix with Size elements
     * @param size int initial size
     */
    public DirectedGraph(int size)
    {
        this.matrixSize = size;
        matrix = new int[size][size];
        emptyIndexes = new Stack<>();
        bijection = new Bijection<>();
        this.verticesSize = 0;
        this.edgesSize = 0;
        emptyMatrix(this.matrix);
    }

    //clear matrix by setting all elements to -1
    private void emptyMatrix(int[][] matrixRef)
    {
        for(int i = 0; i < matrixRef.length; i++)
        {
            for(int j = 0; j < matrixRef[i].length; j++)
            {
                //initialize matrix with -1. -1 means edge is missing
                matrixRef[i][j] = -1;
            }
        }
    }

    //resize the matrix if it's out of space
    private void resize()
    {
        matrixSize *= 2;
        int [][]newMatrix = new int[matrixSize][matrixSize];
        emptyMatrix(newMatrix);
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix.length; j++)
            {
                //initialize matrix with -1. -1 means edge is missing
                newMatrix[i][j] = matrix[i][j];
            }
        }
        matrix = newMatrix;
    }

    @Override
    public boolean addVertex(V vertex)
    {
        if(verticesSize >= matrixSize)
        {
            resize();
        }
        if(!containsVertex(vertex))
        {
            int index = nextIndex();
            bijection.add(index, vertex);
            verticesSize++;
            return true;
        }
        else
        {
            return false;
        }
    }
    //returns next index to use. Looks at empty elements and fills them out
    private int nextIndex()
    {
        if(!emptyIndexes.isEmpty())
        {
            return emptyIndexes.pop();
        }
        else
        {
            return verticesSize;
        }
    }

    @Override
    public boolean addEdge(V source, V destination, int weight)
    {
        if(weight < 0)
        {
            throw new IllegalArgumentException("Negative weight is passed");
        }
        if(!containsEdge(source, destination))
        {
            Integer sourceIndex = bijection.getKey(source);
            Integer destIndex = bijection.getKey(destination);
            if(sourceIndex != null && destIndex != null)
            {
                matrix[sourceIndex][destIndex] = weight;
                edgesSize++;
                return true;
            }
            return false;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int vertexSize()
    {
        return this.verticesSize;
    }

    @Override
    public int edgeSize()
    {
        return this.edgesSize;
    }

    @Override
    public boolean containsVertex(V vertex)
    {
        return bijection.containsValue(vertex);
    }

    @Override
    public boolean containsEdge(V source, V destination)
    {
        Integer sourceIndex = bijection.getKey(source);
        Integer destIndex = bijection.getKey(destination);
        if (sourceIndex != null && destIndex != null && sourceIndex < matrixSize && destIndex < matrixSize)
        {
            return matrix[sourceIndex][destIndex] != -1;
        }

        return false;
    }

    @Override
    public int edgeWeight(V source, V destination)
    {
        Integer sourceIndex = bijection.getKey(source);
        Integer destIndex = bijection.getKey(destination);
        if(sourceIndex != null && destIndex != null && sourceIndex <= matrixSize && destIndex <= matrixSize)
        {
            return matrix[sourceIndex][destIndex];
        }
        return -1;
    }

    @Override
    public Set<V> vertices()
    {
        return new HashSet<>(bijection.valueSet());
    }

    @Override
    public Set<Edge<V>> edges()
    {
        Set<Edge<V>> edges = new HashSet<>();
        for (int i = 0; i < verticesSize; i++)
        {
            for (int j = 0; j < verticesSize; j++)
            {
                if(matrix[i][j] != -1)
                {
                    V source = bijection.getValue(i);
                    V dest = bijection.getValue(j);
                    edges.add(new Edge<>(source, dest, matrix[i][j]));
                }
            }
        }
        return edges;
    }

    @Override
    public boolean removeVertex(V vertex)
    {
        if (containsVertex(vertex))
        {
            Integer vertexIndex = bijection.getKey(vertex);
            if(vertexIndex != null)
            {
                for (int i = 0; i < matrixSize; i++)
                {
                    matrix[i][vertexIndex] = -1;
                }
                for (int j = 0; j < matrix[vertexIndex].length; j++)
                {
                    matrix[vertexIndex][j] = -1;
                }
                bijection.removeValue(vertex);
                emptyIndexes.push(vertexIndex);
                verticesSize--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeEdge(V source, V destination)
    {
        Integer sourceIndex = bijection.getKey(source);
        Integer destIndex = bijection.getKey(destination);
        if (sourceIndex != null && destIndex != null && sourceIndex <= matrixSize && destIndex <= matrixSize)
        {
            this.matrix[sourceIndex][destIndex] = -1;
            edgesSize--;
            return true;
        }
        return false;
    }

    @Override
    public void clear()
    {
        emptyMatrix(this.matrix);
        this.edgesSize = 0;
        this.verticesSize = 0;
        bijection.clear();
    }

    @Override
    public String toString()
    {
        return "DirectedGraph{" +
                "matrixSize=" + matrixSize +
                ", verticesSize=" + verticesSize +
                ", edgesSize=" + edgesSize +
                ", matrix=" + Arrays.toString(matrix) +
                ", bijection=" + bijection +
                ", emptyIndexes=" + emptyIndexes +
                '}';
    }

    /**
     * Returns size of the matrix
     * @return int size of matrix
     */
    public int getMatrixSize()
    {
        return this.matrix.length;
    }
}
