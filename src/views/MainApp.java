package views;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static Stage primaryStage;


    @Override
    
    public void start(Stage primaryStage) throws IOException {
    	String pageFXML = "AccueilView.fxml";
    	Parent panel = FXMLLoader.load(getClass().getResource(pageFXML));
    	Scene scene = new Scene(panel);
 	

    	primaryStage.setScene(scene);
    	primaryStage.setTitle("Application de Gestion de Production de Drones");
//    	primaryStage.setMaximized(true);
    	primaryStage.setX(10);
    	primaryStage.setY(10);
    	primaryStage.show();
    
    	MainApp.primaryStage = primaryStage;
    	//On charge les données du CSV ici directement
    }
    
    public static Stage getPrimaryStage(){
    	return primaryStage;
    }
    
	public static void main(String[] args) {
		launch(args);
	}
    
}
	