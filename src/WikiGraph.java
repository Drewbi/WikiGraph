import java.util.*;

public class WikiGraph implements CITS2200Project {

    private HashMap<Integer, ArrayList> wikiGraph = new HashMap<>();
    private HashMap<Integer, ArrayList> wikiGraphTranspose = new HashMap<>();
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

        //Transpose graph formation
        int fromT = vertices.indexOf(urlTo);
        int toT = vertices.indexOf(urlFrom);
        ArrayList linkedToT = new ArrayList();
        if (wikiGraphTranspose.containsKey(fromT)) {
            linkedToT = wikiGraphTranspose.get(fromT);
        }
        linkedToT.add(toT);
        wikiGraphTranspose.put(fromT, linkedToT);
    }

    @Override
    public int getShortestPath(String urlFrom, String urlTo) {
        int shortest = -1;
        // checking if urlFrom and urlTo are the same, returning 0 if they are
        if (urlFrom == urlTo) {
            shortest = 0;
            return shortest;
        }
        //retrieving the urls' unique indices
        int from = vertices.indexOf(urlFrom);
        int to = vertices.indexOf(urlTo);
        int numVert = vertices.size();
        int[] parent = new int[numVert];
        int[] colour = new int[numVert];
        //setting all parents to -1 (representing undetermined)
        for (int i = 0; i < numVert; i ++){
            parent[i] = -1;
        }
        //BFS
        ArrayDeque q = new ArrayDeque();
        if (wikiGraph.containsKey(from) && vertices.contains(urlTo)) {
            boolean found = false;
            q.add(from);
            colour[from] = 1;
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
            //if parent[to] == -1 : urlTo was never reached from urlFrom
            //therefore return -1
            if (parent[to] != -1) {
                //Tracing back through parents, adding one to
                // shortest each time
                shortest = 1;
                int curr = to;
                while (parent[curr] != from) {
                    curr = parent[curr];
                    shortest ++;
                }
            }
        }
        else System.out.print(urlFrom + " or " + urlTo + " may not exist in the graph, or " + urlFrom +
                "may not contain any links");
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
        Stack s = new Stack();
        int vSize = vertices.size();
        int[] colour = new int[vSize];
        //ensures all vertices are seen and added to the stack
        //if a path ends, a new one is created
        for(int i = 0; i < colour.length; i ++) {
            if (colour[i] == 0) {
                if (wikiGraph.containsKey(i)){
                    DFS(i, s, colour);
                }
            }
        }
        int[] colour2 = new int[vSize];
        ArrayList<String[]> result = new ArrayList<>();
        while (!s.empty()) {
            int focus = (int) s.pop();
            if (colour2[focus] == 0) {
                //list of indexes of vertices in a given SCC
                // refreshed for each new component
                ArrayList SCC = new ArrayList();
                DFST(focus, colour2, SCC);
                //converting SCC array list of vertices to array of
                // corresponding urls
                int SCCSize = SCC.size();
                String[] SCCString = new String[SCCSize];
                for(int i = 0; i < SCCSize; i++) {
                    SCCString[i] = (String) vertices.get((int) SCC.get(i));
                }
                result.add(SCCString);
            }
        }
        //converting array list of SCCStrings to array of arrays
        int resultSize = result.size();
        String[][] finalResult = new String[resultSize][];
        for (int i = 0; i < resultSize; i ++) {
            finalResult[i] = result.get(i);
        }
        return finalResult;
    }

    //depth-first search method for original graph
    public Stack DFS(int v, Stack s, int[] colour) {
        colour[v] = 1;
        for (int i = 0; i < wikiGraph.get(v).size(); i ++) {
            int child = (int) wikiGraph.get(v).get(i);
            if (colour[child] == 0) {
                if (wikiGraph.containsKey(child)) DFS(child, s, colour);
                else {
                    s.push(child);
                    colour[child] = 1;
                }
            }
        }
        //vertices are added to the stack in order of their finish times
        s.push(v);
        return s;
    }

    //depth-first search method for transpose graph
    public ArrayList DFST(int focus, int[] colour2, ArrayList SCC) {
        colour2[focus] = 1;
        SCC.add(focus);
        if (wikiGraphTranspose.containsKey(focus)) {
            for (int i = 0; i < wikiGraphTranspose.get(focus).size(); i++) {
                int child = (int) wikiGraphTranspose.get(focus).get(i);
                if (colour2[child] == 0) DFST(child, colour2, SCC);
            }
        }
        return SCC;
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
            if (vertValid) {
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

            if (vertValid) {
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

            // If all root nodes have been tried or if more than one pendant is found
            if (currPath == 0 && path[currPath] == numVert || pendants.size() > 1) {
                System.out.println("No solutions found");
                return new String[0];
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