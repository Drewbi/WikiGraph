import java.io.*;
import java.util.*;

public class CITS2200ProjectTester {
	private static void loadGraph(CITS2200Project project, String path) {
		// The graph is in the following format:
		// Every pair of consecutive lines represent a directed edge.
		// The edge goes from the URL in the first line to the URL in the second line.
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			while (reader.ready()) {
				String from = reader.readLine();
				String to = reader.readLine();
				project.addEdge(from, to);
			}
		} catch (Exception e) {
			System.out.println("There was a problem:");
			System.out.println(e.toString());
		}
	}

	public static void main(String[] args) {
		// Change this to be the path to the graph file.
		String pathToGraphFile = "/Users/dru/Documents/DSA/WikiDisance/src/example_graph";
		// Create an instance of your implementation.
		CITS2200Project proj = new WikiGraph();
		// Load the graph into the project.
		loadGraph(proj, pathToGraphFile);
		// Write your own tests!
		int res = proj.getShortestPath("/wiki/Flow_network", "/wiki/Approximate_max-flow_min-cut_theorem");
		System.out.println(res);
	}
}
