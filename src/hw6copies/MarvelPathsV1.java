package hw6;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import hw5.Graph;
import hw5.GraphNode;
import hw6.MarvelParser.MalformedDataException;

public class MarvelPathsV1 {
	public static void main(String[] args) throws MalformedDataException {
		// Parsing
		String filename = "src/hw6/data/marvel.tsv";
		Set<String> characters = new TreeSet<String>();
		Map<String, List<String>> books = new TreeMap<String, List<String>>();
		MarvelParser.parseData(filename, characters, books);
		
		// Putting characters into the graph first
		Graph graph = new Graph();
		for (String character : characters) {
			graph.addNode(new GraphNode(character));
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
		
	}
}
