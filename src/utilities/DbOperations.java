package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbOperations {

	// create connection to the db using code below..using xampp for this
	private Connection connect;
	private String table;
	private String opText = "";
	private int opCode = 0;
	public static final String DB_NAME = "dsa_inventory";

	/// getters and setters for setting the database name
	public void setTable(String table) {
		this.table = table;
	}

	public String getTable() {
		return table;
	}

	public Connection getConnected() {
		String user = "root";
		String pass = "";
		String url = "jdbc:mysql://localhost/";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(url + DB_NAME, user, pass);
			opText = "DB connected!";
			opCode = 1;
			
		} catch (Exception e) {
			opText = e.getMessage().toString();
			opCode = -1;
		}

		return connect;
	}
	
	
	public ResultSet queryResult(Connection connection, String query) {
		Statement sQuery;
		ResultSet result = null;
		
		try {
			sQuery = connection.createStatement();
			result = sQuery.executeQuery(query);
			
			opText = "Query successfull!";
			opCode = 1;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			opText = e.getMessage().toString();
			opCode = -1;
		}
		
		return result;
	}
	
	
	public String getOpText() {
		return opText;
	}

	public int getOpCode() {
		return opCode;
	}

	public ResultSet queryDb(Connection connection, String query) {
		// state sql query
		ResultSet result = null;
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			result = stmt.executeQuery(query);
			opText = "Query successfull!";
			opCode = 1;
			
		}catch (Exception e) {
			
			try {
				stmt = connection.createStatement();
				stmt.execute(query);
			}catch(Exception ex){
				opText = e.getMessage().toString();
				opCode = -1;
			}
			
		}
		
		return result;
	}

	
	
	
	
	
	public static void main(String[] args) throws SQLException {
		DbOperations con = new DbOperations();
		Connection connect = con.getConnected();
		con.setTable("Beverages");

//		// state sql query
//		String sql = String.format("INSERT INTO %s(NAME,DATE,QUANTITY,BUY_PRICE,SELL_PRICE)"
//								+ " VALUES ('TAMPICO','2019-10-18',40,12.9,60)",con.getTable());
//		Statement query = connect.createStatement();
//
//		// execute mysql statement and save the result in Result set
//		// in DDL where there will not be any results, just query.execute
//		try {
//			query.execute(sql);
//			System.out.println("DATA INSERT SUCCESSFULL");
//		}catch (Exception e) {
//			e.printStackTrace();
//		}

		
		String SELECT = "SELECT * FROM CATEGORIES";
		Statement sQuery = connect.createStatement();
		ResultSet result = sQuery.executeQuery(SELECT);
		
		while (result.next()) {
			System.out.println(result.getString("NAME"));
//			System.out.println(result.getString("ID") + " " + result.getString("NAME") + result.getString("DATE") + " "
//					+ result.getString("QUANTITY") + result.getString("BUY_PRICE") + " " + result.getString("SELL_PRICE"));
		}

	}

	
}
