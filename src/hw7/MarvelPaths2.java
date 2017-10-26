package hw7;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import hw5.Edge;
import hw5.EdgeSingle;
import hw5.Graph;
import hw6.BookEdge;
import hw6.MarvelParser;
import hw6.MarvelParser.MalformedDataException;

public class MarvelPaths2 {	
	public static void main(String[] args) throws MalformedDataException {
		boolean again = true;
		while (again) {
			Scanner input = new Scanner(System.in);
			String fileName = prompt("File name? ", input);
			String start = prompt("Character 1? ", input);
			String dest = prompt("Character 2? ", input);
			Graph<String, Integer> graph = createGraph(fileName);
			Path<String> path = findPath(graph, start, dest);
			printPath(path, start, dest);
			again = prompt("Do you want to find another path? ", input).toLowerCase().startsWith("y");
		}
	}
	
	/** Prompts user for information
	 * @param prompt what information the client will provide
	 * @param input used to read the input
	 * @return user's response
	 */
	public static String prompt(String prompt, Scanner input) {
		System.out.print(prompt);
		String data = input.nextLine();
		return data;
	}
	
	/** Creates graph based on a file
	 * @param fileName the specified file
	 * @requires fileName != null
	 * @return graph based on the file
	 * @throws MalformedDataException if file not in proper format, with character
	 * 		   and book separated by one tab or if file cannot be found
	 */
	public static Graph<String, Integer> createGraph(String fileName) throws MalformedDataException {
		// Parsing
		String filename = "src/hw7/data/" + fileName;
		Set<String> characters = new TreeSet<String>();
		Map<String, List<String>> books = new TreeMap<String, List<String>>();
		MarvelParser.parseData(filename, characters, books);
		
		// Putting characters into the graph first
		Graph<String, Integer> graph = new Graph<String, Integer>();
		for (String character : characters) {
			graph.addNode(character, EdgeSingle.class);
			assert graph.containsNode(character) : character + " should be in the graph but it is not";
			assert graph.getEdges(character).getClass() == EdgeSingle.class : "character's class should be " +
					EdgeSingle.class + " but was " + graph.getEdges(character).getClass();
		}
		
		// For each book, get the list of characters in that book.
		// Then for each character in the same book, connect them 
		// to other characters.
		Set<String> bookNames = books.keySet();
		for (String book : bookNames) {
			List<String> charNames = books.get(book);
			for (int i = 0, size = charNames.size(); i < size; i++) {
				String parent = charNames.get(i);
				// Assumptions for line below
				// 1. Each child in charNames is unique
				// 2. As a result, you won't have to worry about encountering
				//	  the same child more than once. 
				// If code does not work, this may be a possible error. Then, 
				// try putting it inside the inner loop.
				Edge<String, Integer> edge = graph.getEdges(parent);
				for (int j = i + 1; j < size; j++) {
					String child = charNames.get(j);
					int weight;
					if (edge.containsChild(child)) {
						weight = edge.getLabel(child);
						weight++;
					} else {
						weight = 1;
					}
					graph.addEdge(parent, child, weight);
					graph.addEdge(child, parent, weight);
				}
			}
		} 
		return graph;
	}
	
	/** Finds shortest path between two characters based on Dijkstra's Algorithm
	 * @param graph specified graph
	 * @param start the first character
	 * @param dest the second character
	 * @return list of edges representing path from start to dest
	 * 		   returns null if no path found
	 */
	public static Path<String> findPath(Graph<String, Integer> graph, String start, String dest) {
		Queue<Path<String>> active = new PriorityQueue<Path<String>>();
		Set<String> finished = new HashSet<String>();
		Path<String> startToStart = new Path<String>();
		startToStart.add(new BookEdge<String, Double>(start, 0.0, start));
		active.add(startToStart);
		while (!active.isEmpty()) {
			Path<String> minPath = active.remove();
			String minDest = minPath.get(minPath.size() - 1).child;
			
			if (minDest.equals(dest)) {
				minPath.remove(0);
				return minPath; 
			}
			if (finished.contains(minDest)) { continue; }
			
			Edge<String, Integer> minDestEdges = graph.getEdges(minDest);
			Set<String> minDestChildren = minDestEdges.getChildren();
			for (String child : minDestChildren) {
				if (!finished.contains(child)) {
					Path<String> newPath = new Path<String>(minPath);
					int weight = minDestEdges.getLabel(child);
					BookEdge<String, Double> e = new BookEdge<String, Double>(minDest, (double) 1 / weight, child);
					newPath.add(e);
					active.add(newPath);
				}
			}
			finished.add(minDest);
		}
		return null;
	}
	
	
	/** Prints out path from start character to destination character
	 * @param path the specified path
	 * @param start the starting character
	 * @param dest the destination character
	 * @requires path != null && start != null & dest != null
	 * @effects prints out the path from start character to destination character 
	 */
	public static void printPath(Path<String> path, String start, String dest) {
		System.out.println("path from " + start + " to " + dest + ":");
    	if (path == null) {
    		System.out.println("no path found");
    	} else {
    		for (BookEdge<String, Double> edge : path) {
    			System.out.println(edge.parent + " to " + edge.child + " with weight " + String.format("%.3f", edge.label));
    		}
    		System.out.println("total cost: " + String.format("%.3f", path.getWeight()));
    	}
	}
}
