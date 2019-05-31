import java.util.*;

public class WikiGraph implements CITS2200Project {

    private HashMap<Integer, ArrayList> wikiGraph = new HashMap<>();
    private ArrayList vertices = new ArrayList();

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
        int numVert = vertices.size();
        int[] parent = new int[numVert];
        int[] colour = new int[numVert];
        for (int i = 0; i < numVert; i ++){
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
                    shortest ++;
                }
            }
        }
        return shortest;
    }

    private int getLongestPath(int root){
        int numVert = vertices.size();
        int[] parent = new int[numVert];
        int[] colour = new int[numVert];
        int[] distance = new int[numVert];
        for (int i = 0; i < numVert; i ++){
            parent[i] = -1;
        }
        ArrayDeque q = new ArrayDeque();
        if (wikiGraph.containsKey(root)) {
            q.add(root);
            while (!q.isEmpty()) {
                int k = (int) q.pop();
                if (wikiGraph.containsKey(k)) {
                    for (int i = 0; i < wikiGraph.get(k).size(); i++) {
                        int child = (int) wikiGraph.get(k).get(i);
                        if (colour[child] == 0) {
                            q.add(child);
                            colour[child] = 1;
                            parent[child] = k;
                            distance[child] = distance[parent[child]] + 1;
                        }
                    }
                }
            }
        } else return -1;
        int max = 0;
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] > max) {
                max = distance[i];
            }
        }
        return max;
    }

    @Override
    public String[] getCenters() {
        System.out.println(wikiGraph);
        System.out.println(vertices);
        int minMax = vertices.size();
        ArrayList<Integer> middles = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i ++) {
            int max = getLongestPath(i);
            if (max < minMax && max > 0) {
                minMax = max;
                middles.clear();
                middles.add(i);
            } else if (max == minMax) middles.add(i);
        }
        String[] result = new String[middles.size()];
        for (int i = 0; i < middles.size(); i++) {
            result[i] = (String) vertices.get(middles.get(i));
        }
        return result;
    }

    @Override
    public String[][] getStronglyConnectedComponents() {

        return new String[0][];
    }

    @Override
    public String[] getHamiltonianPath() {
        int numVert = vertices.size();
        int[] path = new int[numVert];
        int currPath = 0;
        boolean vertValid = true;
        HashSet<Integer> pendants = new HashSet<>();

        while (currPath < numVert) {

            // Make sure candidate hasn't been used before
            for (int i = 0; i < currPath; i ++) {
                if (path[i] == path[currPath]) {
                    vertValid = false;
                    break;
                }
            }

            // Check to see if vertex has edges that aren't just itself
            if (vertValid && currPath < numVert) {
                ArrayList currConnections = wikiGraph.get(path[currPath]);
                if (currConnections != null && currConnections.size() == 1) {
                        int onlyEdge = (int) currConnections.get(0);
                        if (onlyEdge == path[currPath]) pendants.add(path[currPath]); // Only edge is to itself
                }
            }

            // Make sure there is a connection from previous to candidate
            if (vertValid && currPath != 0) {
                ArrayList prevConnections = wikiGraph.get(path[currPath - 1]);
                if (prevConnections != null) {
                    for (Object connection : prevConnections) {
                        if ((int) connection == path[currPath]) {
                            vertValid = true;
                            break;
                        }
                        vertValid = false;
                    }
                } else vertValid = false;
            }

            // If all root nodes have been tried or if more than one pendant is found
            if (currPath == 0 && path[currPath] == numVert || pendants.size() > 1) {
                System.out.println("No solutions found");
                return new String[0];
            }

            if (vertValid && path[currPath] < numVert) {
                currPath++;
                if (currPath < numVert) {
                    path[currPath] = 0;
                }
            } else {
                while (true) {
                    path[currPath]++;
                    if (path[currPath] == numVert && currPath > 0) {
                        path[currPath] = 0;
                        currPath--;
                    } else break;
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