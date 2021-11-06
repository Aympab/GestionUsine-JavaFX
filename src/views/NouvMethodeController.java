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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import production.ManuelProduction;
import production.MethodeProduction;

public class NouvMethodeController {

    @FXML
    private Button sauvegarderButton;

    @FXML
    private TextArea texteDescription;
    
    @FXML
    private ComboBox<ManuelProduction> comboBoxManuel;
    
    @FXML
    private Spinner<Integer> nivActiv;
    
    @FXML
    private Button retourButton;
    
    @FXML
    void onRetourButtonClicked(ActionEvent event) throws IOException {
    	SceneBuilderUtils.retourAccueil(event);
    }

    
    @FXML
    private void initialize() throws IOException{
    	
    	//Initialise la ComboBox
    	ArrayList<ManuelProduction> listeManuels = ImportFichier.importerArrayListManuelsCSV();
    	
    	for(int i = 0; i < listeManuels.size(); i++){
    		this.comboBoxManuel.getItems().add(listeManuels.get(i)/*.getNomManuel()*/);
    	}
    	
    	//Initialise le Spinner
    	SpinnerValueFactory<Integer> valeursSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
    	this.nivActiv.setValueFactory(valeursSpinner);
    }
    
    
    @FXML
    private void onClickButtonSauvegarder(ActionEvent event) throws IOException{
    	//TODO : Vérifier véracité dse valeurs
    	MethodeProduction metExport = new MethodeProduction(this.texteDescription.getText(), this.comboBoxManuel.getValue(), this.nivActiv.getValue().intValue());
    	
    	ExportFichier.exporterTestProd(metExport);
    	
    	//Réinitialise les valeurs des input
    	this.comboBoxManuel.setValue(null);
    	this.nivActiv.decrement(100);
    	this.texteDescription.setText("");
    	
    	//PopUp d'information
    	Alert popUp = new Alert(AlertType.INFORMATION);
    	popUp.setTitle("Opération terminée");
    	popUp.setHeaderText("Une nouvelle méthode de production à été ajoutée.");
    	popUp.setContentText("L'export s'est déroulé sans erreur.\nLigne ajoutée dans methodes.csv :\n" + metExport.toStringCSV());
    	popUp.show();
    }
}
