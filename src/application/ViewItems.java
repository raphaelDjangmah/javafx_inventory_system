package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import algorithms.RLinkedList;
import algorithms.RQueues;
import algorithms.RStacks;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utilities.Alerts;
import utilities.DbOperations;

public class ViewItems extends Application implements Initializable {

	
	@FXML
	private TableView<Shop> table_view_content;
	
	@FXML
	private Label item_category;
	
	@FXML 
	private TextField input_remove;
	
	@FXML
	private Button remove_btn;
	
	@FXML
	private TableColumn<Shop,Integer> column_id, column_qty;
	
	@FXML
	private TableColumn<Shop,String> column_name, column_date;
	
	@FXML
	private TableColumn<Shop,Double> column_buyPrice, column_sellPrice,column_netBuy,column_netSell,column_netProfit;
	
	@FXML
	private TableColumn<Shop,Button> column_removeBtn;
	
	private int rowCount = 0;
	private String selectedTable;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		selectedTable = Data.tableName;
		setItems();
	}

	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		try {

			Parent root = FXMLLoader.load(getClass().getResource("ViewItems.fxml"));
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
	
	public void setItems() {
		
		//get the values from the database;
		DbOperations db   = new DbOperations();
		db.setTable(selectedTable);
		ResultSet result  = db.queryDb(db.getConnected(), "SELECT * FROM "+db.getTable());
		int count = 0;
		Shop shops[] = new Shop[10];
		
		item_category.setText(selectedTable.replaceAll("_", " "));
		item_category.setText(selectedTable.toUpperCase());
		
		RStacks<Shop> stack = new RStacks<>(100);
		RQueues<Shop> queues = new RQueues<>(100);
		@SuppressWarnings("unused")
		RLinkedList linkedList = new RLinkedList();
		
		try {
			while(result.next()) {
				//get the complete set of items available
				int id = Integer.parseInt(result.getString("ID"));
				String name = result.getString("NAME");
				String date = result.getString("DATE");
				int qty = Integer.parseInt(result.getString("QUANTITY"));
				double buyAt = Double.parseDouble(result.getString("BUY_PRICE"));
				double sellAt = Double.parseDouble(result.getString("SELL_PRICE"));
				double buyPrice = Double.parseDouble(result.getString("TOTAL_BUY_COST"));
				double sellPrice = Double.parseDouble(result.getString("TOTAL_SELL_COST"));
				double total = Double.parseDouble(result.getString("NET_PROFIT"));
				
				shops[count] = new Shop(id,name,date,qty,buyAt,sellAt,buyPrice,sellPrice,total);
				
				stack.push(shops[count]);
				queues.enqueue(shops[count]);
				
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		rowCount = count;
		
		ObservableList<Shop> shopList = FXCollections.observableArrayList(
				shops
				);
		
		
		column_id.setCellValueFactory(new PropertyValueFactory<Shop,Integer>("id"));
		column_name.setCellValueFactory(new PropertyValueFactory<Shop,String>("name"));
		column_qty.setCellValueFactory(new PropertyValueFactory<Shop,Integer>("quantity"));
		
		column_date.setCellValueFactory(new PropertyValueFactory<Shop,String>("date"));
		column_buyPrice.setCellValueFactory(new PropertyValueFactory<Shop,Double>("buyAt"));
		column_sellPrice.setCellValueFactory(new PropertyValueFactory<Shop,Double>("sellAt"));
		
		column_netBuy.setCellValueFactory(new PropertyValueFactory<Shop,Double>("netBuy"));
		column_netSell.setCellValueFactory(new PropertyValueFactory<Shop,Double>("netSell"));
		column_netProfit.setCellValueFactory(new PropertyValueFactory<Shop,Double>("netAmount"));
		
		
		table_view_content.setItems(shopList);
	}
	
	public void removeFromTable(ActionEvent e) {
		//now get the index specified
		int index = -1;
		
		try {
			index = Integer.parseInt(input_remove.getText().toString());
		}catch(Exception ex){
			Alerts.alert("inventHUB", "DELETION FROM DB", "Index Not Integer!").show();
			return;
		}
		
		//remove from the database and refresh the application
		
		DbOperations db   = new DbOperations();
		db.setTable(selectedTable);
		db.queryDb(db.getConnected(), "DELETE FROM "+db.getTable()+" WHERE ID="+index);
		
		if(index>rowCount) {
			Alerts.alert("inventHUB", "DELETION FROM DB", "Deletion Failed! Index Out Of Range").show();
			return;
		}else {
			Alerts.alert("inventHUB", "DELETION FROM DB", "DELETE SUCCESSFULL").show();
		}
		
		setItems();
	}
}
