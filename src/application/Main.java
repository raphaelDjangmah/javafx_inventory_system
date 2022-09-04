package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utilities.Alerts;
import utilities.DbOperations;

public class Main extends Application implements Initializable {

	@FXML
	private ComboBox<String> inventory_combo;

	@FXML
	private BorderPane HomePanel;

	@FXML
	private TextField fx_input;

	@Override
	public void start(Stage primaryStage) {
		try {

			Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		// first categories data from db and using to fill the combo
		List<String> list = new ArrayList<>();
		DbOperations db = new DbOperations();
		db.setTable("Categories");
		ResultSet result = db.queryDb(db.getConnected(), "SELECT * FROM " + db.getTable());

		try {
			while (result.next()) {
				list.add(result.getString("NAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		inventory_combo.setItems(FXCollections.observableArrayList(list));
	}

	public void addBtn(ActionEvent e) {
		launchApp();
	}
	
	public void editBtn(ActionEvent e) {
		try {
			Stage primaryStage = new Stage();

			Parent root = FXMLLoader.load(getClass().getResource("EditItem.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void searching(ActionEvent e) {
		Data.searchQuery = fx_input.getText();

		PageLoader loader = new PageLoader();
		Pane view = loader.getPage("SearchItem");
		HomePanel.setCenter(view);
	}

	public void comboItemClick(ActionEvent e) {
		String selected = inventory_combo.getSelectionModel().getSelectedItem().toString();

		Data.tableName = selected;

		PageLoader loader = new PageLoader();
		Pane view = loader.getPage("ViewItems");
		HomePanel.setCenter(view);
	}

	public void launchApp() {
		try {
			Stage primaryStage = new Stage();

			Parent root = FXMLLoader.load(getClass().getResource("AddItems.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
