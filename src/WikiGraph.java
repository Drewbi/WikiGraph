import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class WikiGraph implements CITS2200Project {

    private Map<String, ArrayList> wikiGraph;

    @Override
    public void addEdge(String urlFrom, String urlTo) {
        ArrayList linkedTo = new ArrayList();
        if (wikiGraph.containsKey(urlFrom)) {
            linkedTo = wikiGraph.get(urlFrom);
        }
        linkedTo.add(urlTo);
        wikiGraph.put(urlFrom, linkedTo);
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