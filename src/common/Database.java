package common;

import java.sql.DriverManager;
import com.mysql.jdbc.Connection;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Database {
	
	public static final String hostName= "jdbc:mysql://localhost:3306/store";//"jdbc:mysql://108.167.160.75:3306/intecsys_store";
	public static final String userName="root";//"intecsys_manager";
	public static final String password="";//"Abdelsatar1000";
	public static Connection db;
	
	public static void initilalize(){
		try {
			db = (Connection) DriverManager.getConnection(hostName, userName, password);
		} catch (Exception e) {
			
			Alert errotAlert = new Alert(AlertType.ERROR);
	    		errotAlert.setTitle("error message");
	    		errotAlert.setHeaderText("Error!");
	    		errotAlert.setContentText("Server error!");
	    		errotAlert.showAndWait();
			
			e.printStackTrace();
		}
		
	}
	
}
