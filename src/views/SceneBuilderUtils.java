package views;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public abstract class SceneBuilderUtils {
	
	/**
	 * @param event		le même que dans SceneBuilder
	 * @param location  l'endroit ou est localisé la ressource fxml
	 * @param titrePage le titre de la page à ouvrir
	 * @throws IOException 
	 * 
	 * Permet de changer de scene
	 * 	 
	 */
	public static void changerScene(ActionEvent event, String location, String titrePage) throws IOException {
    	String pageFXML = location;

    	Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
    	
    	Parent parent = FXMLLoader.load(currentClass.getResource(pageFXML));
    	Scene scene = new Scene(parent);
    	
    	//Cette ligne récupère l'information du satge 
//    	Stage fenetre = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	Stage fenetre = MainApp.getPrimaryStage();
    	
//    	if(location.equals("PlanificationSemaine.fxml")){
//    		fenetre.setMaximized(true);
//    	}
    	
    	fenetre.setScene(scene);
    	fenetre.setTitle(titrePage);
    }

	public static void retourAccueil(ActionEvent event) throws IOException{
		SceneBuilderUtils.changerScene(event, "AccueilView.fxml", "Page d'accueil");
	}

	public static void fitToParent(Node objet){
        AnchorPane.setBottomAnchor(objet, 0.0);
        AnchorPane.setLeftAnchor(objet, 0.0);
        AnchorPane.setRightAnchor(objet, 0.0);
        AnchorPane.setTopAnchor(objet, 0.0);
	}
	
}
