package views;

import java.io.IOException;
import java.util.ArrayList;

import gestionFichier.ExportFichier;
import gestionFichier.ImportFichier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import machines.ChaineProductionSimple;
import production.MethodeProduction;

public class NouvChaineProdController {

	@FXML
	private TextField nomChaine;
	
	@FXML
	private ComboBox<MethodeProduction> comboBoxMethodes;
	
	@FXML
	private Button buttonAjouterMethode;
	
	@FXML
	private Button buttonSauvegarderChaine;

    @FXML
    void onRetourButtonClicked(ActionEvent event) throws IOException {
    	SceneBuilderUtils.retourAccueil(event);
    }

    @FXML
    void onClickButtonAjouterMethode(ActionEvent event) throws IOException{
    	SceneBuilderUtils.changerScene(event, "NouvMethodeView.fxml", "Nouvelle méthode de production simple");
    }
    
    @FXML
    private void initialize() throws IOException{
    	//Initialisation de la comboBox
    	ArrayList<MethodeProduction> listeMethodes = ImportFichier.importerArrayListMethodesCSV();
    	
    	for(int i = 0; i < listeMethodes.size(); i++){
    		this.comboBoxMethodes.getItems().add(listeMethodes.get(i));
    	}
    }
    
    @FXML
    private void sauvegarderChaine() throws IOException{
    	//TODO : Vérifier les valeurs.
    	
    	ChaineProductionSimple chaineExport = new ChaineProductionSimple(this.nomChaine.getText(), this.comboBoxMethodes.getValue());
    	ExportFichier.exporterChaineSimple(chaineExport);
    	
    	this.nomChaine.setText("");
    	this.comboBoxMethodes.setValue(null);
    	
    	Alert popUp = new Alert(AlertType.INFORMATION);
    	popUp.setTitle("Opération terminée");
    	popUp.setHeaderText("Une nouvelle chaine de production à été ajoutée.");
    	popUp.setContentText("L'export s'est déroulé sans erreur.\nLigne ajoutée dans chaines.csv :\n" + chaineExport.toStringCSV());
    	popUp.show();  
    }
    
}
