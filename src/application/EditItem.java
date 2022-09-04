package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import algorithms.RLinkedList;
import algorithms.RQueues;
import algorithms.RStacks;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import utilities.Alerts;
import utilities.DbOperations;

public class EditItem extends Application implements Initializable {

	@FXML
	private ComboBox<String> add_category;
	@FXML
	private TextField add_product,add_quantity,add_date,add_sellprice,add_buyprice; 
	@FXML
	private TableView<String> table_view_content;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//setColums(table_view_content);

		//first categories data from db and using to fill the combo
		List<String> list = new ArrayList<>();
		DbOperations db   = new DbOperations();
		db.setTable("Categories");
		ResultSet result  = db.queryDb(db.getConnected(), "SELECT * FROM "+db.getTable());
		
		try {
			while(result.next()) {
				list.add(result.getString("NAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		add_category.setItems(FXCollections.observableArrayList(list));
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		try {

			Parent root = FXMLLoader.load(getClass().getResource("EditItem.fxml"));
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
	
	public void addProduct(ActionEvent e) {
		String category, name;
		int quantity;
		double sellPrice, buyPrice;
		double sellAmount, buyAmount, netProfit;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime dateNow = LocalDateTime.now();  
		
		try {
			category = add_category.getSelectionModel().getSelectedItem().toString();
			name = add_product.getText().toString();
			quantity = Integer.parseInt(add_quantity.getText().toString());
			sellPrice = Double.parseDouble(add_sellprice.getText().toString());
			buyPrice  = Double.parseDouble(add_buyprice.getText().toString());
			
			sellAmount = quantity*sellPrice;
			buyAmount  = quantity*buyPrice;
			netProfit  = (sellAmount - buyAmount) * quantity;
			
		}catch(Exception ex) {
			System.out.println("An Error Occured! Check Input");
			ex.printStackTrace();
			return;
		}
		
		//Create an instance of the stack, qeueu, list and hashmap
		RStacks<String> stack = new RStacks<String>(100);
		RQueues<String> queue = new RQueues<String>(100);
		RLinkedList lList = new RLinkedList();
		
		//push the input elements onto the stack
		stack.push(category);
		stack.push(name);
		stack.push(Integer.toString(quantity));
		
		//connecting to database
		DbOperations op = new DbOperations();
		Connection conn = op.getConnected();
		
		if(op.getOpCode()==-1) {
			System.out.println("Database Connection Failed!");
			return;
		}else {
			
			op.setTable(category);
			
			//pushing to the database
			String query = String.format("INSERT INTO %s(NAME,DATE,QUANTITY,BUY_PRICE,SELL_PRICE,TOTAL_BUY_COST,TOTAL_SELL_COST,NET_PROFIT) "
										+ "VALUES('%s','%s',%d,%f,%f,%f,%f,%f)",op.getTable(),name,(dateNow.format(dtf)).toString(),
										quantity,buyPrice,sellPrice,buyAmount, sellAmount, netProfit);
			

			op.queryDb(conn, query);
			
			if(op.getOpCode()==-1) {
				Alerts.alert("InventHUB", "INSERTION", "FAILED! Please Enter Right Credentials").show();
				
				System.out.println(op.getOpText());
				return;
			}else {
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert = Alerts.alert("InventHUB", "INSERTION", "Insertion Into Database Successfull");
				
				alert.showAndWait().ifPresent(rs -> {
				    if (rs == ButtonType.OK) {
				    	//platform.close();
				    	Window window =   ((Node)(e.getSource())).getScene().getWindow(); 
			            if (window instanceof Stage){
			                ((Stage) window).close();
			            }
			            
				    }});
			}
		}
		
		
	}
}


