package hw6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import hw5.Graph;
import hw6.MarvelParser.MalformedDataException;

public class MarvelPathsGenericV1 {	
	public static void main(String[] args) throws MalformedDataException {
		Scanner input = new Scanner(System.in);
		String fileName = prompt("File name? ", input);
		String start = prompt("Character 1? ", input);
		String dest = prompt("Character 2? ", input);
		Graph<String, String> graph = createGraph(fileName);
		List<BookEdge<String, String>> path = findPath(graph, start, dest);
		printPath(path, start, dest);
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
	public static Graph<String, String> createGraph(String fileName) throws MalformedDataException {
		// Parsing
		String filename = "src/hw6/data/" + fileName;
		Set<String> characters = new TreeSet<String>();
		Map<String, List<String>> books = new TreeMap<String, List<String>>();
		MarvelParser.parseData(filename, characters, books);
		
		// Putting characters into the graph first
		Graph<String, String> graph = new Graph<String, String>();
		for (String character : characters) {
			graph.addNode(character);
		}
		
		// For each book, get the list of characters in that book.
		// Then for each character in the same book, connect them 
		// to other characters.
		Set<String> bookNames = books.keySet();
		for (String book : bookNames) {
			List<String> charNames = books.get(book);
			for (int i = 0, size = charNames.size(); i < size; i++) {
				String parent = charNames.get(i);
				for (int j = i + 1; j < size; j++) {
					String child = charNames.get(j);
					graph.addEdge(parent, child, book);
					graph.addEdge(child, parent, book);
				}
			}
		} 
		return graph;
	}
	
	/** Finds shortest path between two characters
	 * @param graph specified graph
	 * @param start the first character
	 * @param dest the second character
	 * @return list of edges representing path from start to dest
	 * 		   returns null if no path found
	 */
	public static List<BookEdge<String, String>> findPath(Graph<String, String> graph, String start, String dest) {
		assert graph.containsNode(start);
		assert graph.containsNode(dest);
		Queue<String> toVisit = new LinkedList<String>();
		Map<String, List<BookEdge<String, String>>> nodeToPath = new HashMap<String, List<BookEdge<String, String>>>();
		
		toVisit.add(start);
		nodeToPath.put(start, new ArrayList<BookEdge<String, String>>());
		while (!toVisit.isEmpty()) {
			String currNode = toVisit.remove();
			if (currNode.equals(dest)) {
				return nodeToPath.get(currNode);
			}
			Map<String, Set<String>> edges = graph.getEdges(currNode);
			Set<String> children = new TreeSet<String>(edges.keySet());
			for (String child : children) {
				if (!nodeToPath.containsKey(child)) {
					List<BookEdge<String, String>> path = nodeToPath.get(currNode);
					List<BookEdge<String, String>> newPath = new ArrayList<BookEdge<String, String>>(path);
					TreeSet<String> books = new TreeSet<String>(edges.get(child));
					BookEdge<String, String> edge = new BookEdge<String, String>(currNode, books.first(), child);
					newPath.add(edge);
					nodeToPath.put(child, newPath);
					toVisit.add(child);
				}
			}
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
	public static void printPath(List<BookEdge<String, String>> path, String start, String dest) {
		System.out.println("path from " + start + " to " + dest + ":");
    	if (path == null) {
    		System.out.println("no path found");
    	} else {
    		for (BookEdge<String, String> edge : path) {
    			System.out.println(edge.parent + " to " + edge.child + " via " + edge.label);
    		}
    	}
	}
}
