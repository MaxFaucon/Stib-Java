package g52977.stib.presenter;

import g52977.stib.dto.BookmarkDto;
import g52977.stib.dto.StationDto;
import g52977.stib.exception.RepositoryException;
import g52977.stib.model.Model;
import g52977.stib.model.Node;
import g52977.stib.view.ViewController;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javafx.util.Pair;

/**
 * The presenter of the application.
 * 
 * @author Maximilien Faucon 52977
 */
public class Presenter implements PropertyChangeListener {

	private final Model model;
	private final ViewController view;

        /**
         * Constructor of Presenter.
         * 
         * @param model The model of the application.
         * @param view The view of the application.
         */
	public Presenter(Model model, ViewController view) {
		this.model = model;
		this.view = view;
		model.addPropertyChangeListener(this);
	}

        /**
         * Ask to the model to initialize the stations and the bookmarks.
         * 
         * @throws RepositoryException Exception thrown if the access to the
         * database failed.
         */
	public void initialize() throws RepositoryException {
		model.initialize();
	}

        /**
         * Search the shortest path between two stations.
         */
	public void searchPath() {
		model.searchPath(view.getSource(), view.getArrival());
	}

        /**
         * Add a bookmark to the bookmarks table.
         * 
         * @throws RepositoryException Exception thrown if the access
         * to the database failed.
         */
	public void addBookmark() throws RepositoryException {
		String name = view.enterName();
		if (name != null) {
			model.addBookmark(name, view.getSource(), view.getArrival());
		}
	}

        /**
         * Delete a bookmark from the bookmarks table.
         * 
         * @throws RepositoryException Exception thrown if the access to the
         * database failed.
         */
	public void deleteBookmark() throws RepositoryException {
		model.deleteBookmark(view.getBookmarkName());
	}

        /**
         * Receive a notification from the subject if a property changed. And
	 * ask to the view to update according to the notification.
         * 
         * @param pce The value sent by the subject.
         */
	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if (pce.getPropertyName().equals("INIT")) {
			List<StationDto> stations
				= (List<StationDto>) pce.getNewValue();
			view.initialize(stations);
		} else if (pce.getPropertyName().equals("SEARCH")) {
			view.displayPath((Node) pce.getNewValue());
		} else if (pce.getPropertyName().equals("UPDATE_BOOKMARKS")) {
			List<BookmarkDto> bookmarks
				= (List<BookmarkDto>) pce.getNewValue();
			view.fillBookmarks(bookmarks);
		} else if (pce.getPropertyName().equals("UPDATE_BOOKMARKS_CB")) {
			Pair pair = (Pair) pce.getNewValue();
			view.updateModifyCombobox((String) pair.getKey(), 
				(String)pair.getValue());
		}
	}

        /**
         * Asks to the view to display an error according to the error name.
         * 
         * @param errorName The name of the error to display.
         */
	public void displayError(String errorName) {
		if (errorName.equals("BOOKMARK_NAME")) {
			view.bookMarkError();
		} else if (errorName.equals("DELETE")) {
			view.deleteError();
		} else if (errorName.equals("SEARCH")) {
			view.searchError();
		} else if (errorName.equals("UPDATE")) {
			view.updateError();
		} 
	}

        /**
         * Search the shortest path between two stations according to a 
         * bookmark.
         * 
         * @throws RepositoryException 
         */
	public void searchBmPath() throws RepositoryException {
		model.searchBmPath(view.getBookmarkName());
	}

        /**
         * Modify a bookmark in the bookmarks table.
         * 
         * @throws RepositoryException Exception thrown if the access to the
         * database failed.
         */
	public void modifyBookmark() throws RepositoryException {
		String bmToUpdate = view.getBookmarkToUpdate();
		String source = view.getBookmarkSource();
		String arrival = view.getBookmarkArrival();
		String newName = view.getNewBookmarkName();
		if (bmToUpdate != null) {
			model.updateBookmark(bmToUpdate, source, arrival, newName);
		} else {
			displayError("UPDATE");
		}	
	}

	/**
	 * Ask to the model to find the arrival and source stations according
	 * to a bookmark name.
	 * 
	 * @throws RepositoryException Exception thrown if the 
	 * modification failed.
	 */
	public void selectBookmark() throws RepositoryException {
		model.selectBookmark(view.getBookmarkToUpdate());
	}

}
