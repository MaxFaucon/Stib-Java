package g52977.stib.handler;

import g52977.stib.presenter.Presenter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Search path button handler.
 * 
 * @author Maximilien Faucon 52977
 */
public class SearchHandler implements EventHandler<ActionEvent> {
	
	private final Presenter presenter;
	
        /**
         * Constructor of SearchHandler.
         * 
         * @param presenter The application presenter.
         */
	public SearchHandler(Presenter presenter) {
		this.presenter = presenter;
	}

        /**
         * Ask to the presenter to search the path.
         * 
         * @param t The event.
         */
	@Override
	public void handle(ActionEvent t) {
		presenter.searchPath();
	}
	
}
