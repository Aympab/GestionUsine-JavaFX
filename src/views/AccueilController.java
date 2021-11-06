package views;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AccueilController {
	
//	public public ControllerAccueil() {
//		this.getClass().getResource("")
//		// TODO Auto-generated constructor stub
//	}

    @FXML
    private AnchorPane backgroundAnchor;

    @FXML
    private AnchorPane barreHautAnchor;

    @FXML
    private Label labelTitre;

    @FXML
    private Button buttonLinkTest;
    
    @FXML
    private Button buttonStock;

    @FXML
    private ImageView imageTest;

    @FXML
    private Label labelButtonTest;

    @FXML
    private Button NouvelElementButton;

    @FXML
    private Button nouvelleChaineButton;

    @FXML
    private Button listeChainesButton;

    @FXML
    private Button ReapproButton;

    @FXML
    private Button NewManuelButton;

    @FXML
    private Button newMethodeButton;
    
    @FXML
    private Button uniteButton;
    
    @FXML
    private Button planifButton;
    
    @FXML
    private Button planifSemaineButton;
    
    @FXML
    void onPlanifSemaineButtonClicked(ActionEvent event) throws IOException{
    	SceneBuilderUtils.changerScene(event, "PlanificationSemaine.fxml", "Planification de productions hebdomadaires");
    }
    
    @FXML
    void onPlanifButtonClicked(ActionEvent event) throws IOException{
    	SceneBuilderUtils.changerScene(event, "PlanificationView.fxml", "Planification de production");
    }
    
    @FXML 
    void onUniteButtonClicked(ActionEvent event) throws IOException{
    	SceneBuilderUtils.changerScene(event, "UniteView.fxml", "Unités de mesure");
    }
    
    
    @FXML
    void onTestButtonClicked(ActionEvent event) throws IOException{
    	
    	SceneBuilderUtils.changerScene(event, "TestSimpleView.fxml", "Nouveau test simple");
    	
//    	String pageFXML = "TestSimpleView.fxml";
//
//    	Parent parent = FXMLLoader.load(getClass().getResource(pageFXML));
//    	Scene scene = new Scene(parent);
//    	
//    	//Cette ligne récupère l'information du satge 
//    	Stage fenetre = (Stage)((Node)event.getSource()).getScene().getWindow();
//    	
//    	fenetre.setScene(scene);
//    	fenetre.setTitle("Salut");
//    	fenetre.show();
    }
    
    @FXML
    void onStockButtonClicked(ActionEvent event) throws IOException{
    	SceneBuilderUtils.changerScene(event, "StockView.fxml", "Stock");
    }

    @FXML
    void onListeChainesButtonClicked(ActionEvent event) throws IOException {
    	SceneBuilderUtils.changerScene(event, "ChaineProdView.fxml", "Liste chaines production simple");
    }

    @FXML
    void onNewManuelButtonClicked(ActionEvent event) throws IOException {
    	SceneBuilderUtils.changerScene(event, "NouvManuelView.fxml", "Nouveau manuel de production");
    }

    @FXML
    void onNewMethodButtonClicked(ActionEvent event) throws IOException {
    	SceneBuilderUtils.changerScene(event, "NouvMethodeView.fxml", "Nouvelle méthode de production simple");
    }

    @FXML
    void onNouvelElementButtonClicked(ActionEvent event) throws IOException {
    	SceneBuilderUtils.changerScene(event, "NouvElementView.fxml", "Nouvel élément");
    }

    @FXML
    void onNouvelleChaineButtonClicked(ActionEvent event) throws IOException {
    	SceneBuilderUtils.changerScene(event, "NouvChaineProdView.fxml", "Nouvelle chaine de production");
    }

    @FXML
    void onReapproButtonClicked(ActionEvent event) throws IOException {
    	SceneBuilderUtils.changerScene(event, "ReapproView.fxml", "Demande de réapprovisionnement");
    }


    
}
