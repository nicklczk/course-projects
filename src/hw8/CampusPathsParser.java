package hw8;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CampusPathsParser {
    /**
     * A checked exception class for bad data files
     */
    @SuppressWarnings("serial")
    public static class MalformedDataException extends Exception {
        public MalformedDataException() { }

        public MalformedDataException(String message) {
            super(message);
        }

        public MalformedDataException(Throwable cause) {
            super(cause);
        }

        public MalformedDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }

  /**
   * Reads the CampusPaths dataset.
   * Each line of the input file contains a character name and a comic
   * book the character appeared in, separated by a tab character
   * 
   * @requires filename is a valid file path
   * @param filename the file that will be read
   * @param buildings map from short name of building to long name and coordinates
   * @modifies buildings
   * @effects fills books with a map from each short name to long name and coordinates
   * @throws MalformedDataException if the file is not well-formed:
   *          each line contains exactly four tokens separated by a tab,
   *          or else starting with a # symbol to indicate a comment line.
   */
  public static void parseData(String filename, List<Location<String, Double>> buildings) throws MalformedDataException {
    // Why does this method accept the Collections to be filled as
    // parameters rather than making them a return value? To allows us to
    // "return" two different Collections. If only one or neither Collection
    // needs to be returned to the caller, feel free to rewrite this method
    // without the parameters. Generally this is better style.
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(filename));

        String inputLine;
        while ((inputLine = reader.readLine()) != null) {

            // Ignore comment lines.
            if (inputLine.startsWith("#")) {
                continue;
            }

            // Parse the data, stripping out quotation marks and throwing
            // an exception for malformed lines.
            inputLine = inputLine.replace("\"", "");
            String[] tokens = inputLine.split("\t");
            if (tokens.length != 4) {
                throw new MalformedDataException("Line should contain exactly three tabs: "
                                                 + inputLine);
            }

            String shortName = tokens[0];
            String longName = tokens[1];
            Double x = Double.parseDouble(tokens[2]);
            Double y = Double.parseDouble(tokens[3]);
            
            // Create location
            Location<String, Double> building = new Location<String, Double>(shortName, longName, x, y);

            // Add the parsed data to building collection.
            buildings.add(building);
        }
    } catch (IOException e) {
        System.err.println(e.toString());
        e.printStackTrace(System.err);
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println(e.toString());
                e.printStackTrace(System.err);
            }
        }
    }
  }

}
