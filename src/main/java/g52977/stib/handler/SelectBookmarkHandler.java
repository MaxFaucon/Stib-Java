package g52977.stib.handler;

import g52977.stib.exception.RepositoryException;
import g52977.stib.presenter.Presenter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Update bookmark searchable combobox handler.
 * 
 * @author Maximilien Faucon 52977
 */
public class SelectBookmarkHandler implements EventHandler<ActionEvent> {
	
	private final Presenter presenter;
	
        /**
         * Constructor of SearchHandler.
         * 
         * @param presenter The application presenter.
         */
	public SelectBookmarkHandler(Presenter presenter) {
		this.presenter = presenter;
	}

        /**
         * Ask to the presenter to update the searchable combobox when a
	 * bookmark is selected.
         * 
         * @param t The event.
         */
	@Override
	public void handle(ActionEvent t) {
		try {
			presenter.selectBookmark();
		} catch (RepositoryException ex) {
			presenter.displayError("UPDATE");
		}
	}
	
}
