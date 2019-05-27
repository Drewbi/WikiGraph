import java.util.HashMap;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Arrays;

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
        System.out.println("From index: " + from);
        int to = vertices.indexOf(urlTo);
        System.out.println("To index: " + to);
        int[] colour = new int[vertices.size()];
        for (int i = 0; i < vertices.size(); i ++){
            colour[i] = 0;
        }
        int[] parent = new int[vertices.size()];
        for (int i = 0; i < vertices.size(); i ++){
            parent[i] = -1;
        }
        ArrayDeque q = new ArrayDeque();
        System.out.println(wikiGraph);
        System.out.println(vertices);
        if (wikiGraph.containsKey(from) && vertices.contains(urlTo)) {
            boolean found = false;
            q.add(from);
            while (!q.isEmpty()) {
                int k = (int) q.pop();
                if (wikiGraph.containsKey(k)) {
                    for (int i = 0; i < wikiGraph.get(k).size(); i++) {
                        int child = (int) wikiGraph.get(k).get(i);
                        if (colour[child] == 0) {
                            q.add(child);
                            colour[child] = 1;
                            parent[child] = k;
                        }
                        if (child == to) found = true;
                    }
                    if (found) break;
                }
            }
            if (parent[to] != -1) {
                if (from == to) {
                    shortest = 0;
                    return shortest;
                }
                shortest = 1;
                int curr = to;
                while (parent[curr] != from) {
                    curr = parent[curr];
                    shortest ++;
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