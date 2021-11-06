package views;

import java.io.IOException;
import java.util.ArrayList;

import gestionFichier.ImportFichier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import machines.ChaineProductionSimple;
import machines.StringChainedeProduction;
import matieres.StringMatiereAvecQuantite;
import stockage.EnsembleElements;
import stockage.StockElement;

public class ChaineProdController {

    @FXML
    private AnchorPane anchorPanePrincipal;

    @FXML
    private AnchorPane anchorPaneHead;

    @FXML
    private Label titleView;

    @FXML
    private Button retourButton;

    @FXML
    private SplitPane splitPaneChaineProduction;

    @FXML
    private AnchorPane anchorPanelisteChaineProduction;

   
    //Tableau avec les chaines de prduction
    @FXML
    private TableView<StringChainedeProduction> tableListeChaineProduction;

    @FXML
    private TableColumn<StringChainedeProduction, String> tableColumnChaineName;

    @FXML
    private TableColumn<StringChainedeProduction, String> tableColumnId;

    
    
    @FXML
    private AnchorPane anchorPaneDetails;

    @FXML
    private ImageView plusImage;

    @FXML
    private Button testButton;

    @FXML
    private ImageView test;
    
    //Details de la chaine séléctionnée
    @FXML
    private Label DetailsnomChaine;

    @FXML
    private Label DetailsIdChaine;

    @FXML
    private Label DetailsIdManuel;

    @FXML
    private Label DetailsnomManuel;

    @FXML
    private Label DetailsDureeManuel;

    @FXML
    private TableView<StringMatiereAvecQuantite> DetailsTab;

    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> DetailsColumnNom;

    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> DetailsColumnQuantite;

    @FXML
    private Button buttonAjouterChaine;
    
    @FXML
    void onRetourButtonClicked(ActionEvent event) throws IOException {
    	SceneBuilderUtils.retourAccueil(event);
    }
    
    @FXML
    void onClickButtonAjouterChaine(ActionEvent event) throws IOException{
    	SceneBuilderUtils.changerScene(event, "NouvChaineProdView.fxml", "Nouvelle cha�ne de production");
    }
    
    /**
     * @throws Exception 
     */
    @FXML
    private void initialize() throws Exception{
    	this.initialiserTableView();
    }
    
    /**
     * Remplit la tableView principale (toutes unit�s confondues, touts elements confondus)
     * @throws Exception 
     */
    private void initialiserTableView() throws Exception{
    	EnsembleElements stockUsine = ImportFichier.importerElementsCSV();
    	ArrayList<ChaineProductionSimple> listeCP = ImportFichier.importerChainesCSV((StockElement)stockUsine);
    	
    	//Initialisation des colonnes
    	this.tableColumnChaineName.setCellValueFactory(new PropertyValueFactory<StringChainedeProduction, String>("nomChaine"));
    	this.tableColumnId.setCellValueFactory(new PropertyValueFactory<StringChainedeProduction, String>("idChaine"));

    	
		//Ceci est la liste qui sera envoy�e dans la table directement apr�s
		ObservableList<StringChainedeProduction> listeChaineProd = FXCollections.observableArrayList();
		
		//On boucle sur les �lements du stock de l'usine
		for (int j=0; j<listeCP.size(); j++) {
			ChaineProductionSimple pair = listeCP.get(j);
			
			//On instancie une ligne StringMatiereAvecQuantite pour coincider vec les fonctions JavaFX
			StringChainedeProduction infosChaines = new StringChainedeProduction(pair.getIdChaine().toString(), pair.getLibelle(), pair.getMethodeProdChaine().getManuelProd().getCodeManuel().toString() );
		
			listeChaineProd.add(infosChaines);
		}

		this.tableListeChaineProduction.setItems(listeChaineProd);
    }
    /*
     * Pour afficher les données relatives à la chaine de production séléctionnée
     */
    private void showChaineDetails(StringChainedeProduction scp){
    		this.DetailsnomChaine.setText(scp.getNomChaine());
    		this.DetailsIdChaine.setText(scp.getIdChaine());
    		this.DetailsIdManuel.setText(scp.getIdManuel());
    		
    }

    /*
     * Pour déclencher l'affichage des infos de la chaine de production en 1 clic
     * L'affichage ne se fait qu'après un changement de chaines
     */
    @FXML  
    private void onClickTableViewCP(MouseEvent event){
    	StringChainedeProduction scp = this.tableListeChaineProduction.getSelectionModel().getSelectedItem();
    	
    	this.showChaineDetails(scp);
    }


}
