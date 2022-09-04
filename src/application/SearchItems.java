package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utilities.DbOperations;

public class SearchItems extends Application implements Initializable {

	@FXML
	private Label search_category, search_result;

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		try {

			Parent root = FXMLLoader.load(getClass().getResource("SearchItem.fxml"));
			Scene scene = new Scene(root);

			stage.setScene(scene);
			stage.show();
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

		DbOperations dba = new DbOperations();
		dba.setTable("Categories");
		ResultSet result = dba.queryDb(dba.getConnected(), "SELECT * FROM " + dba.getTable());

		int count = 0;

		try {
			while (result.next()) {
				String name = (result.getString("NAME"));
				search_category.setText("Searching for \'" + Data.searchQuery + "\' in "+name);

				// searching for data in db
				DbOperations db = new DbOperations();
				db.setTable(Data.tableName);
				ResultSet results = db.queryDb(db.getConnected(),
						"SELECT * FROM " + name + " where NAME LIKE \'%" + Data.searchQuery + "%\';");

				if (results != null) {
					try {
						while (results.next()) {
							// get the complete set of items available
							int id = Integer.parseInt(results.getString("ID"));
							String product = results.getString("NAME");

							search_result.setText("Do you Mean \'" + product + "\'? Open Table " + name.toUpperCase()
									+ " and check Index "+id);
							count++;
							
							return;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						System.out.println(db.getOpText());
						continue;
					}
				}else {
					System.out.println(db.getOpText());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (count == 0) {
			search_result.setText("No Match Found For " + Data.searchQuery);
		}

	}
}
