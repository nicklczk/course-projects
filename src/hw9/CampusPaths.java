package hw9;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hw7.Path;
import hw8.Location;
import hw8.Model;
import hw8.View;

/**
 * CampusPaths finds the shortest path between to buildings on campus. The two
 * selected buildings are circled and the path is drawn on the map.
 * @author VinnieTheVu
 */
public class CampusPaths {
	// Main frame to hold all the components
	JFrame frame;
	
	// Panel containers to hold components
	JPanel map;
	DrawMap map2;
	JPanel menu;
	
	// Buttons
	JButton find, reset;
	
	// Labels
	JLabel startLabel;
	JLabel destLabel;
	
	// Drop down lists
	JComboBox<String> start, dest;
	
	public CampusPaths() {
		// Initialize components
		//	Initialize labels
		startLabel = new JLabel("Start Building");
		startLabel.setForeground(Color.RED);
		destLabel = new JLabel("Destination Building");
		destLabel.setForeground(Color.BLUE);
		
		//	Initialize combo box
		String[] buildings = Model.getShortBuildingNames();
		buildings[0] = "Choose 1";
		BuildingListener buildingListener = new BuildingListener();
		start = new JComboBox<String>(buildings);
		start.addActionListener(buildingListener);
		dest = new JComboBox<String>(buildings);
		dest.addActionListener(buildingListener);
		
		// Initialize map
		map = new DrawMap();
		map.setPreferredSize(new Dimension(1024, 768));
		
		// 	Initialize buttons
		//		Find button
		map2 = (DrawMap) map;
		DrawMap.FindButtonListener findListener = map2.new FindButtonListener();
		find = new JButton("Find Path");
		find.addActionListener(findListener);
		//		Reset button
		ResetButtonListener resetListener = new ResetButtonListener();
		reset = new JButton("Reset");
		reset.addActionListener(resetListener);				
		
		// Initialize menu
		menu = new JPanel(new FlowLayout());
		menu.add(startLabel);
		menu.add(start);
		menu.add(destLabel);
		menu.add(dest);
		menu.add(find);
		menu.add(reset);
		
		// Initialize frame
		frame = new JFrame("CampusPaths");
		frame.add(map, BorderLayout.CENTER);
		frame.add(menu, BorderLayout.SOUTH);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * ResetButtonListener resets the program to its initial state
	 * when the client clicks on the reset button
	 * @author VinnieTheVu
	 */
	class ResetButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			map2.path = null;
			start.setSelectedIndex(0);
			dest.setSelectedIndex(0);
			map.repaint();
		}
	}
	
	/**
	 * BuildingListener circles the selected building when the user
	 * selects the building from the drop down list
	 * @author VinnieTheVu
	 */
	class BuildingListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			map2.path = null;
			map.repaint();
		}
	}
	
	/**
	 * DrawMap draws the map, the path, and the highlights.
	 * @author VinnieTheVu
	 */
	class DrawMap extends JPanel {
		// Image of campus map
		private BufferedImage campusMap;
		
		// Path from start building to destination building
		private Path<Location<String, Double>> path;
		
		public static final String CAMPUS_MAP_PATH = "src/hw8/data/campus_map.jpg";
		
		// Original height and width of picture of campus map
		public static final int ORIGINAL_WIDTH = 4330;
		public static final int ORIGINAL_HEIGHT = 2964;
		
		/**
		 * Initializes map
		 */
		public DrawMap() {
			try {
				campusMap = ImageIO.read(new File(CAMPUS_MAP_PATH));
			} catch (IOException ex) {
				System.out.println("image could not be found");
				ex.printStackTrace();
			}
		}
		
		/**
		 * Draws the map, highlights, and path if searched for.
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			int height = getHeight();
			int width = getWidth();
			
			double widthFactor = (double) width / ORIGINAL_WIDTH;
			double heightFactor = (double) height / ORIGINAL_HEIGHT;
			
			g.drawImage(campusMap, 0, 0, width, height, this);
			
			drawHighlights(g, widthFactor, heightFactor);
			if (path != null) {
				View.drawPath(g, path, widthFactor, heightFactor);
			}
		}
		
		/**
		 * Draws circles around selected start and destination building
		 * @param g to draw on the map
		 * @param widthFactor ratio of curr width and original width
		 * @param heightFactor ratio of curr height and original height
		 */
		private void drawHighlights(Graphics g, double widthFactor, double heightFactor) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));
			drawHighlight(g2, start, startLabel, widthFactor, heightFactor);
			drawHighlight(g2, dest, destLabel, widthFactor, heightFactor);
		}
		
		/**
		 * Draws a single circle around a given location
		 * @param g2 to draw on the map
		 * @param list from which list the building is selected from
		 * @param label the color of the circle will be the same as the label's color
		 * @param widthFactor ratio of curr width and original width
		 * @param heightFactor ratio of curr height and original height
		 */
		private void drawHighlight(Graphics2D g2, JComboBox<String> list, JLabel label, double widthFactor, double heightFactor) {
			if (list.getSelectedIndex() != 0) {
				String listShortName = (String) list.getSelectedItem();
				Location<String, Double> listLocation = Model.getLocation(listShortName);
				double x = listLocation.getX() * widthFactor;
				double y = listLocation.getY() * heightFactor;
				g2.setColor(label.getForeground());
				drawCenteredCircle(g2, (int) x, (int) y, 30);
			}
		}
		
		/**
		 * Draws a circle of radius r centered on given coordinates (x,y)
		 * @param g to draw on the map
		 * @param x the x coordinate
		 * @param y the y coordinate
		 * @param r the radius of the circle
		 */
		private void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
			x = x - (r / 2);
			y = y - (r / 2);
			g.drawOval(x, y, r, r);
		}
		
		/**
		 * When the find button is clicked, the shortest path between selected start and destination buildings will be found
		 * and the path will be drawn on the map
		 * @author VinnieTheVu
		 */
		class FindButtonListener implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().getClass() == JButton.class) {
					if (start.getSelectedIndex() != 0 && dest.getSelectedIndex() != 0) {
						// Start and dest buildings
						String startBuilding = (String) start.getSelectedItem();
						String destBuilding = (String) dest.getSelectedItem();
						
						// Shortest path from start to dest buildings
						Path<Location<String, Double>> path = Model.findShortestPath(startBuilding, destBuilding);
						
						// Draw shortest path from start to dest buildings
						setPath(path);
						map.repaint();
					}
				}			
			}
		}
		
		/**
		 * Sets path to given path
		 * @param path the shortest path from point a to point b
		 */
		public void setPath(Path<Location<String, Double>> path) {
			this.path = path;
		}
	}
}
