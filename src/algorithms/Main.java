package algorithms;

import java.sql.SQLException;
import java.util.Scanner;

import utilities.DbOperations;

public class Main {

    public static void main(String[] args) throws SQLException {
	// write your code here
    	System.out.print("Input Value : ");
    	Scanner sc = new Scanner(System.in);
    	String value = sc.nextLine();
    	sc.close();
    	
    	RStacks<String> stacks = new RStacks<>(100);
    	stacks.push(value);
    	
    	
    	System.out.print("\n\n"+stacks.peak());
    	
    	DbOperations connect = new DbOperations();
    	connect.setTable("Example");
    	
    	connect.queryDb(connect.getConnected(), String.format("INSERT INTO %s(PRODUCT) VALUES('%s')", connect.getTable(),stacks.peak()));
    	
    }
}
