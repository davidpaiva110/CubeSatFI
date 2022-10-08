package sideNavigation;

	
import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	@Override public void start(Stage primaryStage) { 
		try { 
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("SideBar.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setMinWidth(1080);
			primaryStage.setMinHeight(810);
			//primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image(new File("logo_bar.jpeg").toURI().toString()));
			primaryStage.setTitle("CubeSatFI");
			
			primaryStage.setScene(scene);
			primaryStage.show(); 
		}catch(Exception e) { 
			e.printStackTrace(); 
		} 
	}
	 
	 
	public static void main(String[] args) {
		launch(args);
	}
}
