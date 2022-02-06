package g52977.stib.handler;

import g52977.stib.exception.RepositoryException;
import g52977.stib.presenter.Presenter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Delete bookmark button handler.
 * 
 * @author Maximilien Faucon 52977
 */
public class DeleteBookmarkHandler implements EventHandler<ActionEvent> {

	private Presenter presenter;
	
        /**
         * Constructor of DeleteBookmarkHandler.
         * 
         * @param presenter The application presenter.
         */
	public DeleteBookmarkHandler(Presenter presenter) {
		this.presenter = presenter;
	}
	
        /**
         * Ask to the presenter to delete a bookmark and ask to display an error
         * if deleting the bookmark didn't work.
         * 
         * @param t The event.
         */
	@Override
	public void handle(ActionEvent t) {
		try {
			presenter.deleteBookmark();
		} catch (RepositoryException ex) {
			System.out.println("DELETE");
		}
	}
	
}
