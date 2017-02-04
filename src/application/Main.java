package application;
	
import java.util.Locale;

import common.Database;
import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	
	private Stage primaryStage;
	@Override
	public void start(Stage stage) {
		this.primaryStage = stage;
		
		Database.initilalize();
		
		mainWindow();		
	}
	
	/***********************************************************************************************/
	public void mainWindow(){
		try{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MainWindowView.fxml"));
			AnchorPane pane = loader.load();
			
			MainWindowController mainWindowController = loader.getController();
			mainWindowController.setMain(this, primaryStage);
			
			Scene scene = new Scene(pane);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("iManager");
			primaryStage.getIcons().add(new Image("/assets/icon.png"));
			primaryStage.show();
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
	}
	
	
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.CANADA);
		launch(args);
	}
}
