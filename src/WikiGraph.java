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
                    System.out.println(vertices.get(curr) + ". index " + curr);
                    shortest ++;
                }
                System.out.println(vertices.get(from) + ". index " + from);
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
        System.out.println(vertices);
        System.out.println(wikiGraph);
        int[] path = new int[vertices.size()];
        int currPath = 0;
        boolean vertValid = true;
        while (currPath < vertices.size()) {
            // Make sure candidate hasn't been used before
            for (int i = 0; i < currPath; i ++) {
                if (path[i] == path[currPath]) {
                    vertValid = false;
                    break;
                }
            }

            // Make sure there is a connection from previous to candidate
            if (currPath > 0 && vertValid != false) {
                ArrayList prevConnections = wikiGraph.get(path[currPath - 1]);
                if (prevConnections != null) {
                    for (Object connection : prevConnections) {
                        if ((int) connection == path[currPath]) {
                            vertValid = true;
                            break;
                        }
                        vertValid = false;
                    }
                } else {
                    vertValid = false;
                }
            }
            if (currPath == 0 && path[currPath] >= vertices.size()){
                System.out.println("No solution");
                return new String[0];
            }
            if (vertValid && path[currPath] < vertices.size()) {
                currPath++;
                if (currPath < vertices.size()) {
                    path[currPath] = 0;
                }
            } else {
                path[currPath] ++;
                if (path[currPath] >= vertices.size()){
                    path[currPath] = 0;
                    currPath --;
                    path[currPath] ++;
                }
                vertValid = true;
            }
        }
        // Get result
        String[] stringPaths = new String[path.length];
        for (int i = 0; i <  path.length; i ++) {
            stringPaths[i] = (String) vertices.get(path[i]);
        }
        return stringPaths;
    }
}