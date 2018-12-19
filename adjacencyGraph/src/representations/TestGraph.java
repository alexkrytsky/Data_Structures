package representations;

public class TestGraph
{
    public static void main(String[] args)
    {
        //let's store a graph of social network relatioships
        DirectedGraphAL<String> socialNetwork = new DirectedGraphAL<>();

        //add a few people
        socialNetwork.addVertex("Jose");
        socialNetwork.addVertex("Lisa");
        socialNetwork.addVertex("Bob");
        socialNetwork.addVertex("Kevin");
        socialNetwork.addVertex("Susie");

        //add a few relatioships
        socialNetwork.addEdge("Jose", "Lisa");
        socialNetwork.addEdge("Lisa", "Jose");
        socialNetwork.addEdge("Bob", "Lisa");
        socialNetwork.addEdge("Kevin", "Lisa");
        socialNetwork.addEdge("Lisa", "Susie");
        socialNetwork.addEdge("Susie", "Jose");

        System.out.println("Vertices in the graph: ");
        for (String vertex : socialNetwork.vertices())
        {
            System.out.println("V: " + vertex);
        }
        System.out.println();
        System.out.println("Edges in the graph: ");
        for(DirectedGraphAL.Edge<String> edge : socialNetwork.edges())
        {
            System.out.println("E: " + edge.getSource() + "-->" + edge.getDest());
        }
    }
}
