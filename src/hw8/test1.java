package hw8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import hw5.Edge;
import hw5.Graph;

public class test1 {
	public static void main(String[] args) {
		Scanner input = new Scanner("");
		try {
			input = new Scanner(new File("src/hw8/data/test.data"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String start = input.nextLine();
		while (input.hasNextLine()) {
			String[] startCoordinates = start.split(",");
			System.out.println("Start coordinates");
			for (String coordinate : startCoordinates) {
				System.out.println(coordinate);
			}
			System.out.println();
			String end = input.nextLine();
			Boolean read = true;
			while (read) {
				String[] tokens = end.split(" ");
				if (tokens.length != 2) {
					throw new IllegalArgumentException("there should be two tokens");
				}
				String[] coordinates = tokens[0].substring(1, tokens[0].length() - 1).split(",");
				System.out.println("coordinates");
				for (String coordinate : coordinates) {
					System.out.println(coordinate);
				}
				Double distance = Double.parseDouble(tokens[1]);
				System.out.println("Distance = " + distance);
				/*
				for (String token : tokens) {
					System.out.println(token.charAt(0));
					System.out.println(token.charAt(token.length() - 1));
					System.out.println(token);
				}
				*/
				if (input.hasNextLine()) {
					end = input.nextLine();
					read = end.charAt(0) == '\t';
				} else {
					read = false;
				}
			}
			start = end;
			System.out.println();
		}
	}
}
