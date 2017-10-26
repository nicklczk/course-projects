package hw8;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import hw6.BookEdge;
import hw7.Path;

/**
 * View handles how the information will be displayed to the client. View decides
 * the formatting of command b (print list of all buildings) and r (printing shortest
 * path between two buildings). 
 * 
 * @author VinnieTheVu
 *
 */
public class View {
	/**
	 * Prints list of all buildings in alphabetical order, in format of:
	 * 	\tabbrevName: fullName
	 * @param buildings list of buildings in alphabetical order
	 */
	public static void printSortedBuildings(List<Location<String, Double>> buildings) {
		System.out.println("Buildings:");
		for (Location<String, Double> building : buildings) {
			System.out.println("\t" + building.getShortName() + ": " + building.getFullName());
		}
		System.out.println();
	}
	
	/**
	 * Prints shortest path between two buildings
	 * @param path shortest path between star and dest
	 * @param start starting building
	 * @param dest destination building
	 */
	public static void printShortestPath(Path<Location<String, Double>> path, String start, String dest) {
		Location<String, Double> startLocation = Model.getLocation(start);
		Location<String, Double> destLocation = Model.getLocation(dest);
		if (startLocation == null) {
			System.out.println("Unknown building: " + start);
			if (destLocation == null) {
				System.out.println("Unknown building: " + dest);
			}
		} else if (destLocation == null) {
			System.out.println("Unknown building: " + dest);
		} else {
			System.out.println("Path from " + Model.getLocation(start).getFullName() + " to " 
								+ Model.getLocation(dest).getFullName() + ":");
			for (BookEdge<Location<String, Double>, Double> edge : path) {
				String direction = Model.getDirection(edge.parent, edge.child);
				System.out.println("\tWalk " + Math.round(edge.label) + " feet "  + direction + " to ("
									+ Math.round(edge.child.getX()) + ", " + Math.round(edge.child.getY()) + ")");
			}
			System.out.println("Total distance: " + Math.round(path.getWeight()) + " feet");
		}
		System.out.println();
	}
	
	/**
	 * Draws the shortest path between two selected buildings
	 * @param g to draw on the map
	 * @param path represents shortest path between two locations
	 * @param widthFactor the ratio between curr width of picture and original width of picture
	 * @param heightFactor the ration between curr height of picture and original height of picture
	 */
	public static void drawPath(Graphics g, Path<Location<String, Double>> path, double widthFactor, double heightFactor) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.CYAN);
		g2.setStroke(new BasicStroke(5));
		for (BookEdge<Location<String, Double>, Double> location : path) {
			Location<String, Double> start = location.parent;
			Location<String, Double> dest = location.child;
			
			double xStart = start.getX() * widthFactor;
			double yStart = start.getY() * heightFactor;
			double xDest = dest.getX() * widthFactor;
			double yDest = dest.getY() * heightFactor;
			
			g2.drawLine((int) xStart, (int) yStart, (int) xDest, (int) yDest); 
		}
	}
}
