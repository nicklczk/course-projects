package hw6.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import hw5.Edge;
import hw5.EdgeSet;
import hw5.Graph;
import hw6.BookEdge;
import hw6.MarvelParser.MalformedDataException;
import hw6.MarvelPaths;

/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph, the Marvel parser, and your BFS
 * algorithm.
 **/
public class HW6TestDriver {

    public static void main(String args[]) {
        try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            HW6TestDriver td;

            if (args.length == 0) {
                td = new HW6TestDriver(new InputStreamReader(System.in),
                                       new OutputStreamWriter(System.out));
            } else {

                String fileName = args[0];
                File tests = new File (fileName);

                if (tests.exists() || tests.canRead()) {
                    td = new HW6TestDriver(new FileReader(tests),
                                           new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + tests.toString());
                    printUsage();
                    return;
                }
            }

            td.runTests();

        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        }
    }

    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("to read from a file: java hw6.test.HW6TestDriver <name of input script>");
        System.err.println("to read from standard in: java hw6.test.HW6TestDriver");
    }

    /** String -> Graph: maps the names of graphs to the actual graph **/
    //TODO for the student: Parameterize the next line correctly.
    private final Map<String, Graph<String, String>> graphs = new HashMap<String, Graph<String, String>>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new HW6TestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
    public HW6TestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @effects Executes the commands read from the input and writes results to the output
     * @throws IOException if the input or output sources encounter an IOException
     **/
    public void runTests()
        throws IOException
    {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            }
            else
            {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            if (command.equals("CreateGraph")) {
                createGraph(arguments);
            } else if (command.equals("AddNode")) {
                addNode(arguments);
            } else if (command.equals("AddEdge")) {
                addEdge(arguments);
            } else if (command.equals("ListNodes")) {
                listNodes(arguments);
            } else if (command.equals("ListChildren")) {
                listChildren(arguments);
            } else if (command.equals("LoadGraph")) {
            	loadGraph(arguments);
            } else if (command.equals("FindPath")) {
            	findPath(arguments);
            } else {
                output.println("Unrecognized command: " + command);
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        // Insert your code here.

        graphs.put(graphName, new Graph<String, String>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to addNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        // Insert your code here.

        Graph<String, String> zero = graphs.get(graphName);
        zero.addNode(nodeName, EdgeSet.class);
        graphs.put(graphName, zero);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to addEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
            String edgeLabel) {
        // Insert your code here.

        Graph<String, String> zero = graphs.get(graphName);
        zero.addEdge(parentName, childName, edgeLabel);
        output.println("added edge " + edgeLabel + " from " + parentName + " to " + childName 
        			   + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to listNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        // Insert your code here.

        Graph<String, String> zero = graphs.get(graphName);
        String out = graphName + " contains:";
        Set<String> nodes = new TreeSet<String>(zero.getNodes());
        for (String node: nodes) {
        	out += " " + node;
        }
        output.println(out);
    }

    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to listChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        // Insert your code here.

    	Graph<String, String> zero = graphs.get(graphName);
        String out = "the children of " + parentName + " in " + graphName + " are:";
        Edge<String, String> edges = zero.getEdges(parentName);
        Set<String> children = new TreeSet<String>(edges.getChildren());
        for (String child : children) {
        	Set<String> labels = new TreeSet<String>(edges.getLabels(child));
        	for (String label : labels) {
        		out += " " + child + "(" + label + ")";
        	}
        }
        output.println(out);
    }
    
    private void loadGraph(List<String> arguments) throws MalformedDataException {
    	if (arguments.size() != 2) {
    		throw new CommandException("Bad arguments to loadGraph: " + arguments);
    	}
    	
    	String graphName = arguments.get(0);
    	String fileName = arguments.get(1);
    	loadGraph(graphName, fileName);
    }
    
    private void loadGraph(String graphName, String fileName) throws MalformedDataException {
    	Graph<String, String> graph = MarvelPaths.createGraph(fileName);
    	graphs.put(graphName, graph);
		output.println("loaded graph " + graphName);
    }
    
    private void findPath(List<String> arguments) {
    	if (arguments.size() != 3) {
    		throw new CommandException("Bad arguments to findPath: " + arguments);
    	}
    	
    	String graphName = arguments.get(0);
    	String node1 = arguments.get(1);
    	String nodeN = arguments.get(2);
    	findPath(graphName, node1, nodeN);
    }

    private void findPath(String graphName, String start, String dest) {
    	start = start.replace('_', ' ');
    	dest = dest.replace('_', ' ');
    	Graph<String, String> graph = graphs.get(graphName);
    	List<BookEdge<String, String>> path = MarvelPaths.findPath(graph, start, dest);
    	output.println("path from " + start + " to " + dest + ":");
    	if (path == null) {
    		output.println("no path found");
    	} else {
    		for (BookEdge<String, String> edge : path) {
    			output.println(edge.parent + " to " + edge.child + " via " + edge.label);
    		}
    	}
    }
    
    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }
        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}

