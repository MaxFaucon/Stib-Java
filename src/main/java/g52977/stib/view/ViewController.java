package g52977.stib.view;

import g52977.stib.dto.BookmarkDto;
import g52977.stib.dto.StationDto;
import g52977.stib.handler.AddBookmarkHandler;
import g52977.stib.handler.DeleteBookmarkHandler;
import g52977.stib.handler.ModifyHandler;
import g52977.stib.handler.SearchBmHandler;
import g52977.stib.handler.SearchHandler;
import g52977.stib.handler.SelectBookmarkHandler;
import g52977.stib.model.Node;
import g52977.stib.presenter.Presenter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

/**
 * Manages the view of the application.
 *
 * @author Maximilien Faucon 52977
 */
public class ViewController {

	private final Stage stage;
	private String name;
	private Alert alert;
	@FXML
	private VBox container;
	@FXML
	private SearchableComboBox sourceSrchblCb;
	@FXML
	private SearchableComboBox sourceSrchblCb1;
	@FXML
	private SearchableComboBox arrivalSrchblCb;
	@FXML
	private SearchableComboBox arrivalSrchblCb1;
	@FXML
	private ComboBox bookmarkCb;
	@FXML
	private ComboBox bookmarkCb1;
	@FXML
	private Button addBookmarkBtn;
	@FXML
	private Button deleteBookmarkBtn;
	@FXML
	private Button searchBtn;
	@FXML
	private Button searchBmBtn;
	@FXML
	private Button modifyBtn;
	@FXML
	private TextField bookmarkNameTfd;
	@FXML
	private TableView pathTable;
	@FXML
	private TableColumn stationColumn;
	@FXML
	private TableColumn lineColumn;

	/**
	 * Constructor of ViewController.
	 *
	 * @param stage The view stage.
	 * @throws IOException Exception thrown if there is a problem with the
	 * inputs/outputs.
	 */
	public ViewController(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stib.fxml"));
		loader.setController(this);
		VBox root = loader.load();
		Scene scene = new Scene(root);
		this.stage = stage;
		stage.setScene(scene);
	}

	/**
	 * Shows the stage of the view.
	 */
	public void showStage() {
		stage.show();
	}

	/**
	 * Initializes the components of the view.
	 *
	 * @param stations A list containing all the stations to fill
	 * components.
	 */
	public void initialize(List<StationDto> stations) {
		for (StationDto station : stations) {
			sourceSrchblCb.getItems().add(station.getName());
			sourceSrchblCb1.getItems().add(station.getName());
			arrivalSrchblCb.getItems().add(station.getName());
			arrivalSrchblCb1.getItems().add(station.getName());
		}

		sourceSrchblCb.setValue(stations.get(0).getName());
		sourceSrchblCb1.setValue(stations.get(0).getName());
		arrivalSrchblCb.setValue(stations.get(0).getName());
		arrivalSrchblCb1.setValue(stations.get(0).getName());		

		stationColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		lineColumn.setCellValueFactory(new PropertyValueFactory<>("lines"));

		alert = new Alert(AlertType.ERROR);
	}

	/**
	 * Fill several components with the bookmarks from the table.
	 *
	 * @param bookmarks The list of the bookmarks.
	 */
	public void fillBookmarks(List<BookmarkDto> bookmarks) {
		bookmarkCb.getItems().clear();
		bookmarkCb1.getItems().clear();

		if (!bookmarks.isEmpty()) {
			for (BookmarkDto bookmark : bookmarks) {
				bookmarkCb.getItems().add(bookmark.getName());
				bookmarkCb1.getItems().add(bookmark.getName());
			}

			bookmarkCb.setValue(bookmarks.get(0).getName());
			bookmarkCb1.setValue(bookmarks.get(0).getName());
		} else {
			bookmarkCb.setValue(null);
			bookmarkCb1.setValue(null);
		}
	}

	/**
	 * Fill the pathTable with all the nodes of the shortest path between
	 * two stations.
	 *
	 * @param node The node containing the list of the nodes of the path.
	 */
	public void displayPath(Node node) {
		pathTable.getItems().clear();
		for (Node n : node.getShortestPath()) {
			pathTable.getItems().add(n);
		}
	}

	/**
	 * Add handlers for the several buttons of the interface.
	 *
	 * @param presenter The presenter of the application that will manage
	 * all the actions of the buttons.
	 */
	public void addButtonHandler(Presenter presenter) {
		SearchHandler searchHandler
			= new SearchHandler(presenter);
		AddBookmarkHandler addBookmarkHandler
			= new AddBookmarkHandler(presenter);
		DeleteBookmarkHandler deleteBookmarkHandler
			= new DeleteBookmarkHandler(presenter);
		SearchBmHandler searchBmHandler
			= new SearchBmHandler(presenter);
		ModifyHandler modifyHandler
			= new ModifyHandler(presenter);
		SelectBookmarkHandler selectBookmarkHandler
			= new SelectBookmarkHandler(presenter);

		searchBtn.setOnAction(searchHandler);
		addBookmarkBtn.setOnAction(addBookmarkHandler);
		deleteBookmarkBtn.setOnAction(deleteBookmarkHandler);
		searchBmBtn.setOnAction(searchBmHandler);
		modifyBtn.setOnAction(modifyHandler);
		bookmarkCb1.setOnAction(selectBookmarkHandler);
	}

	/**
	 * Creates a new input dialog to ask to the user for the name of the
	 * bookmark to add.
	 *
	 * @return The name of the bookmark entered by the user.
	 */
	public String enterName() {
		Optional<String> result;
		TilePane r = new TilePane();
		TextInputDialog td = new TextInputDialog("Name of the bookmark");
		td.setHeaderText("Enter the name of your bookmark.");
		result = td.showAndWait();
		result.ifPresent(name -> this.name = name);
		container.getChildren().add(r);
		return this.name;
	}

	/**
	 * Getter of source.
	 *
	 * @return The name of the source station.
	 */
	public String getSource() {
		return (String) sourceSrchblCb.getValue();
	}

	/**
	 * Getter of arrival.
	 *
	 * @return The name of the arrival station.
	 */
	public String getArrival() {
		return (String) arrivalSrchblCb.getValue();
	}

	/**
	 * Getter of bookmarkName.
	 *
	 * @return The name of the selected bookmark.
	 */
	public String getBookmarkName() {
		return (String) bookmarkCb.getValue();
	}

	/**
	 * Getter of the bookmark to update.
	 *
	 * @return The name of the bookmark to update.
	 */
	public String getBookmarkToUpdate() {
		if (!bookmarkCb1.getItems().isEmpty()) {
			return (String) bookmarkCb1.getValue();
		}

		return null;
	}

	/**
	 * Getter of the new bookmark name.
	 *
	 * @return The new name of the bookmark to update.
	 */
	public String getNewBookmarkName() {
		return bookmarkNameTfd.getText();
	}

	/**
	 * Getter of the bookmark source station.
	 *
	 * @return The new source station of the bookmark to update.
	 */
	public String getBookmarkSource() {
		return (String) sourceSrchblCb1.getValue();
	}

	/**
	 * Getter of the bookmark arrival station.
	 *
	 * @return The new arrival station of the bookmark to update.
	 */
	public String getBookmarkArrival() {
		return (String) arrivalSrchblCb1.getValue();
	}

	/**
	 * Display an error if there is an error with the add of a bookmark.
	 */
	public void bookMarkError() {
		alert.setContentText("This bookmark name is already used.");
		alert.show();
	}

	/**
	 * Display an error if there is an error with the deletion of a
	 * bookmark.
	 */
	public void deleteError() {
		alert.setContentText("Error while deleting this bookmark.");
		alert.show();
	}

	/**
	 * Display an error if there is an error with the search of a path.
	 */
	public void searchError() {
		alert.setContentText("Error while searching the path.");
		alert.show();
	}

	/**
	 * Display an error if there is an error with the udpate of a bookmark.
	 */
	public void updateError() {
		alert.setContentText("Error during the update of the bookmark");
		alert.show();
	}

	/**
	 * Update the combobox of the arrival and source on the bookmark 
	 * modification side.
	 * 
	 * @param source The source station name.
	 * @param arrival The arrival station name.
	 */
	public void updateModifyCombobox(String source, String arrival) {
		sourceSrchblCb1.setValue(source);
		arrivalSrchblCb1.setValue(arrival);
	}

}
