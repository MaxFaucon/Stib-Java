package g52977.stib.model;

import g52977.stib.dto.BookmarkDto;
import g52977.stib.exception.RepositoryException;
import g52977.stib.repository.BookmarkRepository;
import g52977.stib.repository.StationRepository;
import g52977.stib.repository.StopRepository;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.util.Pair;

/**
 * The model of the application.
 * 
 * @author Maximilien Faucon 52977
 */
public class Model {

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private final StationRepository stations;
	private final StopRepository stops;
	private final BookmarkRepository bookmark;
	private final Graph graph;

        /**
         * Constructor of Model.
         * 
         * @throws RepositoryException Exception thrown if the access to the database
         * failed.
         */
	public Model() throws RepositoryException {
		stations = new StationRepository();
		stops = new StopRepository();
		bookmark = new BookmarkRepository();
		graph = new Graph(stations.getAll(), stops.getAll());
	}

        /**
         * Returns all the stations and bookmarks from the database for the 
         * initialization of the view.
         * 
         * @throws RepositoryException Exception thrown if the access to the database
         * failed.
         */
	public void initialize() throws RepositoryException {
		List<BookmarkDto> bm = bookmark.getAll();
		pcs.firePropertyChange("INIT", null, stations.getAll());
		pcs.firePropertyChange("UPDATE_BOOKMARKS", null, bm);
		if (bm.size() > 0) {			
			this.selectBookmark(bm.get(0).getName());
		}				
	}

        /**
         * Add a bookmark to the bookmarks table.
         * 
         * @param name The name of the bookmark.
         * @param source The source station of the path.
         * @param arrival The arrival station of the path.
         * @throws RepositoryException Exception thrown if the access to the database
         * failed.
         */
	public void addBookmark(String name, String source, String arrival)
		throws RepositoryException {
		bookmark.add(new BookmarkDto(0, name, source, arrival));
		pcs.firePropertyChange("UPDATE_BOOKMARKS", null, bookmark.getAll());
	}

        /**
         * Delete a bookmark from the bookmarks table.
         * 
         * @param name The name of the bookmark to delete.
         * @throws RepositoryException Exception thrown if the access to the database
         * failed.
         */
	public void deleteBookmark(String name) throws RepositoryException {
		for (BookmarkDto bm : bookmark.getAll()) {
			if (bm.getName().equals(name)) {
				bookmark.remove(bm.getKey());
			}
		}
		
		List<BookmarkDto> bm = bookmark.getAll();
		pcs.firePropertyChange("UPDATE_BOOKMARKS", null, bm);
	}

        /**
         * Search the shortest path between two stations.
         * 
         * @param source The source station.
         * @param arrival The arrival station.
         */
	public void searchPath(String source, String arrival) {
		graph.initPathAndDistances();

		Node startNode = graph.getNode(source);
		Node arrivalNode = graph.getNode(arrival);
		startNode.setDistance(0);
		startNode.addNodeToPath(startNode);

		Node n = calculateShortestPathFromSource(startNode, arrivalNode);
		pcs.firePropertyChange("SEARCH", null, n);
	}

        /**
         * Calculate the shortest path between two stations using dijkstra algorithm.
         * 
         * Inspired by <https://www.baeldung.com/java-dijkstra>
         * 
         * @param startNode The starting node.
         * @param arrivalNode The arrival node.
         * @return The arrival node containing the shortest path list.
         */
	private Node calculateShortestPathFromSource(Node startNode, Node arrivalNode) {
		Set<Node> settledNodes = new HashSet<>();
		Set<Node> unsettledNodes = new HashSet<>();
		unsettledNodes.add(startNode);

		while (!unsettledNodes.isEmpty()) {
			Node currentNode = getLowestDistanceNode(unsettledNodes);
			unsettledNodes.remove(currentNode);

			if (currentNode.equals(arrivalNode)) {
				return currentNode;
			}

			for (Node adjNode : currentNode.getAdjacents()) {
				if (!settledNodes.contains(adjNode)) {
					adjNode.setDistance(
						currentNode.getDistance() + 1);

					adjNode.setShortestPath(currentNode.
						getShortestPath().stream().
						collect(Collectors.toList()));

					adjNode.addNodeToPath(adjNode);
					unsettledNodes.add(adjNode);
				}
			}
			settledNodes.add(currentNode);
		}

		return null;
	}

        /**
         * Search the node with the lowest distance in the unsettledNodes list.
         * 
         * Inspired by <https://www.baeldung.com/java-dijkstra>
         * 
         * @param unsettledNodes The unsettled nodes list.
         * @return The node from the list with the lowest distance.
         */
	private Node getLowestDistanceNode(Set<Node> unsettledNodes) {
		Node lowestDistanceNode = null;
		int lowestDistance = Integer.MAX_VALUE;

		for (Node node : unsettledNodes) {
			int nodeDistance = node.getDistance();
			if (nodeDistance < lowestDistance) {
				lowestDistance = nodeDistance;
				lowestDistanceNode = node;
			}
		}

		return lowestDistanceNode;
	}
	
        /**
         * Search the shortest path between two stations from a bookmark.
         * 
         * @param bookmarkName The bookmark name.
         * @throws RepositoryException Exception thrown if the access to the database
         * failed.
         */
	public void searchBmPath(String bookmarkName) throws RepositoryException {
		for (BookmarkDto bm : bookmark.getAll()) {
			if (bm.getName().equals(bookmarkName)) {
				searchPath(bm.getSource(), bm.getArrival());
			}
		}
	}

	/**
	 * Add a listener.
	 *
	 * @param listener A listener of the model.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}	

        /**
         * Updates a bookmark from the bookmarks table.
         * 
         * @param bmToUpdate The name of the bookmark to update.
         * @param source The new name of the source station.
         * @param arrival The new name of the arrival station.
         * @param newName The new name of the bookmark to update.
         * @throws RepositoryException 
         */
	public void updateBookmark(String bmToUpdate, String source, 
		String arrival, String newName) throws RepositoryException {
		Integer key = null;
		
		for (BookmarkDto bm : bookmark.getAll()) {
			if (bm.getName().equals(bmToUpdate)) {
				key = bm.getKey();
			}
		}
		
		bookmark.add(new BookmarkDto(key, newName, source, arrival));
		pcs.firePropertyChange("UPDATE_BOOKMARKS", null, bookmark.getAll());
	}

	/**
	 * Search the source and arrival station according to a bookmark name.
	 * Then ask to update the view.
	 * 
	 * @param bookmarkToUpdate The name of the bookmark.
	 * @throws RepositoryException Exception thrown if the access to the
	 * bookmarks table failed.
	 */
	public void selectBookmark(String bookmarkToUpdate) throws RepositoryException {		
		
		for (BookmarkDto bm : bookmark.getAll()) {
			if (bm.getName().equals(bookmarkToUpdate)) {
				Pair pair = 
					new Pair(bm.getSource(), bm.getArrival());
				pcs.firePropertyChange("UPDATE_BOOKMARKS_CB", null, pair);
			}
		}
				
	}

}
