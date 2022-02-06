package g52977.stib.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A node representing a station.
 * 
 * @author Maximilien Faucon 52977
 */
public class Node {
	
	private final int id;
	private final String name;
	private final List<Node> adjacents;
	private final List<Integer> lines;
	private List<Node> shortestPath;
	private int distance;	
	
        /**
         * Constructor of Node.
         * 
         * @param id The id of the station.
         * @param name The name of the station.
         */
	public Node(int id, String name) {
		this.id = id;
		this.name = name;
		this.adjacents = new ArrayList<>();
		this.lines = new ArrayList<>();			
	}
	
        /**
         * Add an adjacent node to the list of the adjacent.
         * 
         * @param node The node to add.
         */
	public void addAdjacent(Node node) {
		this.adjacents.add(node);
	}
	
        /**
         * Adds a line to the list of the lines.
         * 
         * @param line The line to add.
         */
	public void addLine(Integer line) {
		this.lines.add(line);
	}
	
        /**
         * Adds a node to the path to get to this node.
         * 
         * @param node The node to add to the path.
         */
	public void addNodeToPath(Node node) {
		this.shortestPath.add(node);		
	}
	
        /**
         * Setter of shortestPath.
         * 
         * @param shortestPath The shortest path to get to this node.
         */
	public void setShortestPath(List<Node> shortestPath) {
		this.shortestPath = shortestPath;
	}
	
        /**
         * Setter of distance.
         * 
         * @param distance The distance of the node.
         */
	public void setDistance(int distance) {
		this.distance = distance;
	}		

        /**
         * Getter of id.
         * 
         * @return The node id.
         */
	public int getId() {
		return id;
	}

        /**
         * Getter of name.
         * 
         * @return The name of the node.
         */
	public String getName() {
		return name;
	}

        /**
         * Getter of adjacents.
         * 
         * @return The list of the adjacent nodes.
         */
	public List<Node> getAdjacents() {
		return adjacents;
	}	

        /**
         * Getter of lines.
         * 
         * @return The list of the lines of the node.
         */
	public List<Integer> getLines() {
		return lines;
	}
	
        /**
         * Getter of shortestPath.
         * 
         * @return The shortest path to get to this node.
         */
	public List<Node> getShortestPath() {
		return this.shortestPath;
	}
	
        /**
         * Getter of distance.
         * 
         * @return The distance of the node.
         */
	public int getDistance() {
		return distance;
	}
	
}
