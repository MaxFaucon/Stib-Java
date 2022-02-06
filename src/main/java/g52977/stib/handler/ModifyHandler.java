package g52977.stib.handler;

import g52977.stib.exception.RepositoryException;
import g52977.stib.presenter.Presenter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Modify bookmark button handler.
 * 
 * @author Maximilien Faucon 52977
 */
public class ModifyHandler implements EventHandler<ActionEvent> {
	
	private Presenter presenter;
	
        /**
         * Constructor of ModifyHandler.
         * 
         * @param presenter The application presenter.
         */
	public ModifyHandler(Presenter presenter) {
		this.presenter = presenter;
	}

        /**
         * Ask to the presenter to modify a bookmark and ask to display an error
         * if it didn't work.
         * 
         * @param t The event.
         */
	@Override
	public void handle(ActionEvent t) {
		try {
			presenter.modifyBookmark();
		} catch (RepositoryException ex) {
			presenter.displayError("UPDATE");
		}
	}
	
}
