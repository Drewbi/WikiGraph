import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.lang.System;

public class CITS2200ProjectTester {
	private static void loadGraph(CITS2200Project project, String fileName) {
		// The graph is in the following format:
		// Every pair of consecutive lines represent a directed edge.
		// The edge goes from the URL in the first line to the URL in the second line.
		File file = new File("src");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath() + "/" + fileName));
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
		String GraphFileName = ("medium_graph.txt");
		// Create an instance of your implementation.
		CITS2200Project proj = new WikiGraph();
		// Load the graph into the project.
		loadGraph(proj, GraphFileName);
		long initTime = System.nanoTime();
		String[][] yeet = proj.getStronglyConnectedComponents();
		System.out.println((System.nanoTime() - initTime)/1000);
		System.out.println(Arrays.deepToString(yeet));
	}
}
