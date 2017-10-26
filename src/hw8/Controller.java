package hw8;

import java.util.List;
import java.util.Scanner;

import hw7.Path;

/**
 * Controller handles user input for CampusPaths. What functions the program executes is
 * dependent on user input. The possible options are shown in m()
 * 	
 * @author VinnieTheVu
 *
 */
public class Controller {
	public static void main(String[] args) {
		m();
		Scanner console = new Scanner(System.in);
		String data = "";
		while (!data.equals("q")) {
			data = prompt("Enter an option ('m' to see the menu): ", console);
			while (!(data.equals("b") || data.equals("r") || data.equals("q") || data.equals("m"))) {
				System.out.println("Unknown option");
				System.out.println();
				data = prompt("Enter an option ('m' to see the menu): ", console);
			}
			if (data.equals("b")) {
				b();
			} else if (data.equals("r")) {
				r(console);
			} else if (data.equals("m")) {
				m();
			}
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
		while (data.isEmpty() || data.charAt(0) == '#') {
			System.out.println(data);
			data = input.nextLine();
		}
		return data;
	}
	
	/** Prints a list of buildings in alphabetical order */
	public static void b() {
		List<Location<String, Double>> buildings = Model.BUILDINGS;
		View.printSortedBuildings(buildings);
	}
	
	/** Finds the shortest path from one building to another
	 * @param console reads in user input
	 */
	public static void r(Scanner console) {
		String start = prompt("Abbreviated name of starting building: ", console);
		String dest = prompt("Abbreviated name of ending building: ", console);
		Path<Location<String, Double>> path = Model.findShortestPath(start, dest);
		View.printShortestPath(path, start, dest);
	}
	
	/** Displays menu options 
	 * 	r : find shortest route between two specified buildings
	 * 	b : displays list of all buildings in alphabetical order
	 * 	q : quit the program
	 */
	public static void m() {
		System.out.println("Menu:");
		System.out.println("	r to find a route");
		System.out.println("	b to see a list of all buildings");
		System.out.println("	q to quit");
		System.out.println();
	}
}
