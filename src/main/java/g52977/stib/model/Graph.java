package g52977.stib.model;

import g52977.stib.dto.StationDto;
import g52977.stib.dto.StopDto;
import java.util.ArrayList;
import java.util.List;

/**
 * A graph of stations nodes.
 * 
 * @author Maximilien Faucon 52977
 */
public class Graph {

	private final List<Node> nodes;
	private final List<StationDto> stations;	
	private final List<StopDto> stops;

        /**
         * Constructor of Graph. Creates and completes the nodes.
         * 
         * @param stations All the stations from the table.
         * @param stops All the stops from the table.
         */
	public Graph(List<StationDto> stations, List<StopDto> stops) {
		this.nodes = new ArrayList<>();
		this.stations = stations;		
		this.stops = stops;
		createsNodes();
		completeNodes();
	}

        /**
         * Getter of nodes.
         * 
         * @return The list of nodes of the graph.
         */
	public List<Node> getNodes() {
		return this.nodes;
	}

        /**
         * Creates all the nodes of the graph.
         */
	private void createsNodes() {
		stations.forEach(station -> {
			nodes.add(new Node(station.getKey(), station.getName()));			
		});
	}

        /**
         * Complete the nodes by adding for each nodes his adjacent nodes.
         */
	private void completeNodes() {
		StopDto current;
		StopDto previous;
		StopDto next;
		Node currentNode;
		for (int i = 0; i < stops.size(); i++) {
			current = stops.get(i);
			currentNode = getNode(current.getStationName());
			currentNode.addLine(current.getLineId());
			if (i > 0) {
				previous = stops.get(i - 1);
				if (current.getLineId() == previous.getLineId()) {
					addAdjacent(currentNode, previous);
				}
			}
			if (i < stops.size() - 1) {
				next = stops.get(i + 1);
				if (current.getLineId() == next.getLineId()) {
					addAdjacent(currentNode, next);
				}
			}
		}
	}

        /**
         * Getter of a node of the graph.
         * 
         * @param nodeName The name of the node to get.
         * @return The node.
         */
	public Node getNode(String nodeName) {
		Node node = null;
		for (Node n : nodes) {
			if (n.getName().equals(nodeName)) {
				node = n;
			}
		}

		return node;
	}
	
        /**
         * Init the distance and the shortest path of each nodes to prepare the
         * search with dijkstra.
         */
	public void initPathAndDistances() {
		for (Node node : nodes) {
			node.setShortestPath(new ArrayList<>());
			node.setDistance(Integer.MAX_VALUE);
		}
	}
	
        /**
         * Adds an adjacent to the adjacent list of a node.
         * 
         * @param node The node.
         * @param adj The adjacent to add.
         */
	private void addAdjacent(Node node, StopDto adj) {
		boolean exist = false;
		int adjId;
		int i = 0;

		while (i < node.getAdjacents().size() && !exist) {
			adjId = node.getAdjacents().get(i).getId();
			if (adjId == adj.getStationId()) {
				exist = true;
			}
			i++;
		}

		if (!exist) {	
			node.addAdjacent(getNode(adj.getStationName()));
		}
	}		

}
