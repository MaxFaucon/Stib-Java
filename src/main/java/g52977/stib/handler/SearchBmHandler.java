package g52977.stib.handler;

import g52977.stib.exception.RepositoryException;
import g52977.stib.presenter.Presenter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Search bookmark path button handler.
 * 
 * @author Maximilien Faucon 52977
 */
public class SearchBmHandler implements EventHandler<ActionEvent>{
	
	private final Presenter presenter;
	
        /**
         * Constructor of SearchBmHandler.
         * 
         * @param presenter The application presenter.
         */
	public SearchBmHandler(Presenter presenter) {
		this.presenter = presenter;
	}

        /**
         * Ask to the presenter to search the bookmark path and ask to display
         * an error if it didn't work.
         * 
         * @param t The event.
         */
	@Override
	public void handle(ActionEvent t) {
		try {
			presenter.searchBmPath();
		} catch (RepositoryException ex) {
			presenter.displayError("SEARCH");
		}
	}
}
