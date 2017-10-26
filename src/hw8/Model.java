package hw8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import hw5.Edge;
import hw5.EdgeSingle;
import hw5.Graph;
import hw6.BookEdge;
import hw7.Path;
import hw8.CampusPathsParser.MalformedDataException;

/**
 * Model handles the computation for creating the graph representing the campus,
 * finding the shortest path between two buildings, and generating a sorted
 * list of all the buildings on campus
 * 
 * @author VinnieTheVu
 *
 */
public class Model {
	// GRAPH: graph representation of map of campus
	public static final Graph<Location<String, Double>, Double> GRAPH = createGraph();
	
	// BUILDINGS: list of all the buildings on campus
	public static final List<Location<String, Double>> BUILDINGS = getBuildings();
	
	/**
	 * LocationComparator gives relative ordering to Location, where the abbreviated name
	 * of a building will determine its ordering, lexigraphically.
	 * @author VinnieTheVu
	 *
	 */
	private class LocationComparator implements Comparator<Location<String, Double>> {
		@Override
		public int compare(Location<String, Double> a, Location<String, Double> b) {
			return a.getShortName().compareTo(b.getShortName());
		}
	}
	
	/**
	 * Creates graph representing campus
	 * @return graph representing campus
	 * @throws IllegalArgument exception if file is malformed
	 */
	public static Graph<Location<String, Double>, Double> createGraph() {
		Scanner input = new Scanner("");
		try {
			input = new Scanner(new File("src/hw8/data/campus_paths.dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Graph<Location<String, Double>, Double> graph = 
				new Graph<Location<String, Double>, Double>();
		
		// Starting point (parent)
		String start = input.nextLine();
		while (input.hasNextLine()) {
			String[] startCoordinates = start.split(",");
			if (startCoordinates.length != 2) {
				throw new IllegalArgumentException("there should be an x and y coordinate for starting point "
						+ startCoordinates);
			}
			// Coordinates of starting point 
			Double startX = Double.parseDouble(startCoordinates[0]);
			Double startY = Double.parseDouble(startCoordinates[1]);
			
			// Add starting point to graph
			Location<String, Double> startingPoint = new Location<String, Double>("", "", startX, startY);
			if (!graph.containsNode(startingPoint)) { graph.addNode(startingPoint, EdgeSingle.class); }
			
			// Processing ending points (children)
			String end = input.nextLine();
			Boolean read = true;
			while (read) {
				String[] tokens = end.split(" ");
				if (tokens.length != 2) {
					throw new IllegalArgumentException("there should be two tokens, one for the end "
							+ "point coordinates and one for the distance: " + tokens);
				}
				
				String[] coordinates = tokens[0].substring(1, tokens[0].length() - 1).split(",");
				if (coordinates.length != 2) {
					throw new IllegalArgumentException("there should be an x and y coordinate for"
							+ "the end point: " + coordinates);
				}
				// Coordinates of end point and distance from starting point to ending point
				Double endX = Double.parseDouble(coordinates[0]);
				Double endY = Double.parseDouble(coordinates[1]);
				Double distance = Double.parseDouble(tokens[1]);
				
				// Add edge from start point to end point with label distance
				Location<String, Double> endPoint = new Location<String, Double>("", "", endX, endY);
				if (!graph.containsNode(endPoint)) { graph.addNode(endPoint, EdgeSingle.class); }
				graph.addEdge(startingPoint, endPoint, distance);
				
				if (input.hasNextLine()) {
					end = input.nextLine();
					read = end.charAt(0) == '\t';
				} else {
					read = false;
				}
			}
			start = end;
		}
		return graph;
	}
	
	/**
	 * Returns Location representing specified building
	 * @param target specified building by user
	 * @return Location representing specified building, null if not found
	 */
	public static Location<String, Double> getLocation(String target) {
		int low = 0;
        int high = BUILDINGS.size() - 1;
         
        while(high >= low) {
            int middle = (low + high) / 2;
            Location<String, Double> building = BUILDINGS.get(middle);
            String shortName = building.getShortName();
            if(shortName.equals(target)) {
                return building;
            } else if(shortName.compareTo(target) < 0) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return null;
	}
	
	/**
	 * Finds shortest path between two buildings
	 * @param start the starting building
	 * @param dest the destination building
	 * @return Path representing the shortest path between two buildings. Returns null if either 
	 * 		   of the buildings are not in the map.
	 */
	public static Path<Location<String, Double>> findShortestPath(String start, String dest) {
		Location<String, Double> startLocation = getLocation(start);
		Location<String, Double> destLocation = getLocation(dest);
		if (startLocation == null || destLocation == null) return null;
		
		startLocation = new Location<String, Double>("", "", startLocation.getX(), startLocation.getY());
		destLocation = new Location<String, Double>("", "", destLocation.getX(), destLocation.getY());
		
		Queue<Path<Location<String, Double>>> active = new PriorityQueue<Path<Location<String, Double>>>();
		Set<Location<String, Double>> finished = new HashSet<Location<String, Double>>();
		Path<Location<String, Double>> startToStart = new Path<Location<String, Double>>();
		startToStart.add(new BookEdge<Location<String, Double>, Double>(startLocation, 0.0, startLocation));
		active.add(startToStart);
		while (!active.isEmpty()) {
			Path<Location<String, Double>> minPath = active.remove();
			Location<String, Double> minDest = minPath.get(minPath.size() - 1).child;
			
			if (minDest.equals(destLocation)) {
				minPath.remove(0);
				return minPath; 
			}
			if (finished.contains(minDest)) { continue; }
			
			Edge<Location<String, Double>, Double> minDestEdges = GRAPH.getEdges(minDest);
			Set<Location<String, Double>> minDestChildren = minDestEdges.getChildren();
			for (Location<String, Double> child : minDestChildren) {
				if (!finished.contains(child)) {
					Path<Location<String, Double>> newPath = new Path<Location<String, Double>>(minPath);
					double weight = minDestEdges.getLabel(child);
					BookEdge<Location<String, Double>, Double> e = new BookEdge<Location<String, Double>, Double>
																				(minDest, weight, child);
					newPath.add(e);
					active.add(newPath);
				}
			}
			finished.add(minDest);
		}
		return null;
	}
	
	/**
	 * Returns direction from one location to the next based on their coordinates
	 * @param start the beginning point of travel
	 * @param dest the end point of travel
	 * @return direction from start to dest
	 */
	public static String getDirection(Location<String, Double> start, Location<String, Double> dest) {
		Double xDifference = dest.getX() - start.getX();
		Double yDifference = dest.getY() - start.getY();
		Double arctan = Math.atan2(yDifference, xDifference);
		if (arctan <= Math.PI * -7 / 8) {
			return "W";
		} else if (arctan < Math.PI * -5 / 8) {
			return "NW";
		} else if (arctan <= Math.PI * -3 / 8) {
			return "N";
		} else if (arctan < Math.PI * -1 / 8) {
			return "NE";
		} else if (arctan <= Math.PI / 8) {
			return "E";
		} else if (arctan < Math.PI * 3 / 8) {
			return "SE";
		} else if (arctan <= Math.PI * 5 / 8) {
			return "S";
		} else if (arctan < Math.PI * 7 / 8) {
			return "SW";
		} else {
			return "W";
		}
	}
	
	/**
	 * Creates sorted list of all buildings on campus
	 * @return sorted list of all buildings on campus
	 */
	public static List<Location<String, Double>> getBuildings() {
		List<Location<String, Double>> buildings = new ArrayList<Location<String, Double>>();
		try {
			CampusPathsParser.parseData("src/hw8/data/campus_buildings.dat", buildings);
		} catch (MalformedDataException e) {
			e.printStackTrace();
		}
		Model mod = new Model();
		Collections.sort(buildings, mod.new LocationComparator());
		return buildings;
	}
	
	/**
	 * Creates array of abbreviated building names
	 * @return array of abbreviated building names
	 */
	public static String[] getShortBuildingNames() {
		String[] shortBuildingNames = new String[BUILDINGS.size() + 1];
		for (int i = 1; i < shortBuildingNames.length; i++) {
			shortBuildingNames[i] = BUILDINGS.get(i - 1).getShortName();
		}
		return shortBuildingNames;
	}
}
