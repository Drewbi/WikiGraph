import java.util.HashMap;
import java.util.ArrayList;
import java.util.ArrayDeque;

public class WikiGraph implements CITS2200Project {

    private HashMap<Integer, ArrayList> wikiGraph = new HashMap<>();
    private ArrayList vertices = new ArrayList();
    @Override
    public void addEdge(String urlFrom, String urlTo) {
        if(!vertices.contains(urlFrom)) {
            vertices.add(urlFrom);
        }
        int from = vertices.indexOf(urlFrom);
        if(!vertices.contains(urlTo)) {
            vertices.add(urlTo);
        }
        int to = vertices.indexOf(urlTo);
        ArrayList linkedTo = new ArrayList();
        if (wikiGraph.containsKey(from)) {
            linkedTo = wikiGraph.get(from);
        }
        linkedTo.add(to);
        wikiGraph.put(from, linkedTo);
    }

    @Override
    public int getShortestPath(String urlFrom, String urlTo) {
        int shortest = -1;
        int from = vertices.indexOf(urlFrom);
        int to = vertices.indexOf(urlTo);
        int[] colour = new int[vertices.size()];
        for (int i = 0; i < vertices.size(); i ++){
            colour[i] = 0;
        }
        int[] parent = new int[vertices.size()];
        for (int i = 0; i < vertices.size(); i ++){
            parent[i] = -1;
        }
        ArrayDeque q = new ArrayDeque();
        if (wikiGraph.containsKey(from) && vertices.contains(to)) {
            q.add(from);
            while (!q.isEmpty()) {
                Object k = q.pop();
                for (int i = 0; i < wikiGraph.get(k).size(); i++) {
                    if (colour[i] == 0) {
                        q.add(wikiGraph.get(k).get(i));
                        colour[i] = 1;
                        parent[i] = (int) k;
                    }
                }
            }
            if (parent[to] != -1) {
                int count =
                while (parent[k] != from) {

                }
            }
        }
        return shortest;
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