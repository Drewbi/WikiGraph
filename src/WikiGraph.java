import java.util.HashMap;
import java.util.Map;

// Do you receive this message, over.
public class WikiGraph implements CITS2200Project {

    private Map<String, String> wikiGraph;

    public WikiGraph() {
        wikiGraph = new HashMap<>();
    }

    @Override
    public void addEdge(String urlFrom, String urlTo) {

    }

    @Override
    public int getShortestPath(String urlFrom, String urlTo) {

        return 1;
    }

    @Override
    public String[] getCenters() {

        return new String[0];
    }

    @Override
    public String[][] getStronglyConnectedComponents() {

        return new String[0][];
    }

    @Override
    public String[] getHamiltonianPath() {

        return new String[0];
    }
}