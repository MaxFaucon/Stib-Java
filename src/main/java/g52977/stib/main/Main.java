package g52977.stib.main;

import g52977.stib.config.ConfigManager;
import g52977.stib.model.Model;
import g52977.stib.presenter.Presenter;
import g52977.stib.view.ViewController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class that starts the application.
 * 
 * @author Maximilien Faucon 52977
 */
public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
        /**
         * Starts the application.
         * 
         * @param stage The stage view.
         * @throws Exception Exception thrown if the start failed.
         */
	@Override
	public void start(Stage stage) throws Exception {
		ConfigManager.getInstance().load();
		Model model = new Model();
		ViewController view = new ViewController(stage);
		Presenter presenter = new Presenter(model, view);				
		view.showStage();			
		presenter.initialize();
		view.addButtonHandler(presenter);
	}
	
}
