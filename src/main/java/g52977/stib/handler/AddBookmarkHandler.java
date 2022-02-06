package g52977.stib.handler;

import g52977.stib.exception.RepositoryException;
import g52977.stib.presenter.Presenter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Adding bookmark button handler.
 * 
 * @author Maximilien Faucon 52977
 */
public class AddBookmarkHandler implements EventHandler<ActionEvent> {

	private Presenter presenter;
	
        /**
         * Constructor of AddBookmarkHandler.
         * 
         * @param presenter The application presenter.
         */
	public AddBookmarkHandler(Presenter presenter) {
		this.presenter = presenter;
	}
	
        /**
         * Ask to the presenter to add a bookmark and ask to display an error
         * if adding the bookmark didn't work. 
         * 
         * @param t The event.
         */
	@Override
	public void handle(ActionEvent t) {
		try {
			presenter.addBookmark();
		} catch (RepositoryException ex) {
			presenter.displayError("BOOKMARK_NAME");
		}
	}
	
}
