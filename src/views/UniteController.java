package views;

import java.io.IOException;

import gestionFichier.ExportFichier;
import gestionFichier.ImportFichier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import outils.UniteMesure;

public class UniteController {

    @FXML
    private AnchorPane anchorPanePrincipal;

    @FXML
    private AnchorPane anchorPaneHead;

    @FXML
    private Label titleView;

    @FXML
    private Button retourButton;

    @FXML
    private TabPane tabPaneUnite;

    @FXML
    private Tab tabMesUnite;

    @FXML
    private AnchorPane anchorPaneUnite;

    @FXML
    private TableView<UniteMesure> tabUnites;

    @FXML
    private TableColumn<UniteMesure, String> col_codeUnite;

    @FXML
    private TableColumn<UniteMesure, String> col_libelleUnite;

    @FXML
    private TableColumn<UniteMesure, String> col_abreviationUnite;

    @FXML
    private Tab tabNouvUnite;

    @FXML
    private AnchorPane anchorPaneProduit;

    @FXML
    private TextField libelNouvUnite;

    @FXML
    private TextField abrevNouvUnite;

    @FXML
    private Button validationButtonUnite;

    @FXML
    private ImageView checkUnite;
  
    @FXML
    void onRetourButtonClicked(ActionEvent event) throws IOException {
    	SceneBuilderUtils.retourAccueil(event);
    }
    
    @FXML
    private void initialize() throws IOException{
    	ObservableList<UniteMesure> listeUnites = FXCollections.observableArrayList(ImportFichier.importerUnitesCSV());
    	
    	this.col_abreviationUnite.setCellValueFactory(new PropertyValueFactory<UniteMesure, String>("abrev"));
    	this.col_codeUnite.setCellValueFactory(new PropertyValueFactory<UniteMesure, String>("codeUnite"));
    	this.col_libelleUnite.setCellValueFactory(new PropertyValueFactory<UniteMesure, String>("libelle"));

    	this.tabUnites.setItems(listeUnites);
    	
    	this.libelNouvUnite.setText("");
    	this.abrevNouvUnite.setText("");
    	
    }
    
    
    
    
    
    
    /**
     * @param event
     * @throws IOException
     * 
     * Enregistre la nouvelle unit� dans le fichier unites.csv;
     * m�thode appell�e quand on clique sur le bouton valider
     */
    @FXML
    private void sauvegarderUnite(ActionEvent event) throws IOException{
    	//TODO : V�rifier valeurs
    	UniteMesure uniteExport = new UniteMesure(this.libelNouvUnite.getText(), this.abrevNouvUnite.getText());
    	ExportFichier.exporterUnite(uniteExport);
    	

    	Alert popUp = new Alert(AlertType.INFORMATION);
    	popUp.setTitle("Op�ration termin�e");
    	popUp.setHeaderText("Une nouvelle unit� de mesure � �t� ajout�e.");
    	popUp.setContentText("L'export s'est d�roul� sans erreur.\nLigne ajout�e dans unites.csv :\n" + uniteExport.toStringCSV());
    	popUp.show();    	
    	
    	this.initialize();
    }
    
//    private void verifierValeurs(){
//    	//TODO : V�rifier types (String et String)
//    	//TODO : V�rifier l'existence dans le CSV (si on est pas en train de dupliquer l'unit� de mesure)
//    		//(On v�rifie par rapport au Libelle, il faut pas qu'on en ai 2 identiques)
//    }

}
