package views;

import java.io.IOException;

import gestionFichier.ExportFichier;
import gestionFichier.ImportFichier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import matieres.Element;
import matieres.MPVendable;
import matieres.MatierePremiere;
import matieres.Produit;
import outils.Prix;
import outils.UniteMesure;
import javafx.scene.control.ChoiceBox;

public class NouvElementController {
	//##############################################################################
	//##############################################################################
	//								   PARTIE FXML								   #
	//##############################################################################
	//##############################################################################

	
	
	
    @FXML
    private AnchorPane anchorPanePrincipal;

    @FXML
    private AnchorPane anchorPaneHead;

    @FXML
    private Label titleView;

    @FXML
    private Button retourButton;

    @FXML
    private TabPane tabPaneAddMatiere;

    @FXML
    private Tab tabMP;

    @FXML
    private AnchorPane anchorPaneMP;

    @FXML
    private Label definitionMP;

    @FXML
    private TextField libelleMP;

    @FXML
    private TextField quantiteMP;

    @FXML
    private TextField uniteMP;

    @FXML
    private TextField prixAchatMP;

    @FXML
    private Button validationButton;

    @FXML
    private ImageView check;

    @FXML
    private Tab tabProduit;

    @FXML
    private AnchorPane anchorPaneProduit;

    @FXML
    private Label definitionProduit;

    @FXML
    private TextField libelleProduit;

    @FXML
    private TextField quantiteProduit;

    @FXML
    private TextField uniteProduit;

    @FXML
    private TextField prixVenteProduit;

    @FXML
    private Button validationButtonProduit;

    @FXML
    private ImageView checkProduit;

    @FXML
    private Tab tabElement;

    @FXML
    private AnchorPane anchorPaneElement;

    @FXML
    private Button validationButtonElement;

    @FXML
    private ImageView checkElement;

    @FXML
    private Label definitionElement;

    @FXML
    private TextField libelleElement;

    @FXML
    private TextField quantiteElement;

    @FXML
    private TextField uniteElement;

    @FXML
    private Tab tabMPVendable;

    @FXML
    private AnchorPane anchorPaneMPVendable;

    @FXML
    private Button validationButtonMPVendable;

    @FXML
    private ImageView checkMPVendable;

    @FXML
    private Label definitionMPVendable;

    @FXML
    private TextField libelleMPVendable;

    @FXML
    private TextField quantiteMPVendable;

    @FXML
    private TextField uniteMPVendable;

    @FXML
    private TextField prixAchatMPVendable;

    @FXML
    private TextField prixVenteMPVendable;
    
    @FXML
    private ChoiceBox<UniteMesure> choiceBoxUnite;
    
    @FXML
    private ChoiceBox<UniteMesure> choiceBoxUnite1;
    
    @FXML
    private ChoiceBox<UniteMesure> choiceBoxUnite2;
    
    @FXML
    private ChoiceBox<UniteMesure> choiceBoxUnite3;

    
      
    @FXML
    void onRetourButtonClicked(ActionEvent event) throws IOException {
    	SceneBuilderUtils.retourAccueil(event);
    }
    
    @FXML
    private void initialize() throws IOException{
    	//Une observable list est comme une ArrayList en lecture seule
        ObservableList<UniteMesure> listeUnites = FXCollections.observableArrayList(ImportFichier.importerUnitesCSV());

    	
    	this.choiceBoxUnite.setItems(listeUnites);
    	this.choiceBoxUnite1.setItems(listeUnites);
    	this.choiceBoxUnite2.setItems(listeUnites);
    	this.choiceBoxUnite3.setItems(listeUnites);
    }
    
    
    
    /**
     * @param event
     * @throws IOException
     * Va etre appelée lorsqu'on clique sur le bouton valider dans l'onglet MP
     */
    @FXML
    private void sauvegarderMP(ActionEvent event) throws IOException{
    	//TODO : Utiliser check values 
    	
    	MatierePremiere mpExport = this.instancierMP();
    	ExportFichier.exporterElement(mpExport);
    	
    	this.libelleMP.setText("");
    	this.choiceBoxUnite.setValue(null);
    	this.prixAchatMP.setText("");
    	
    	Alert popUp = this.popUpTerminee("Nouvelle matière première ajoutée.", mpExport);
    	popUp.show();
    }
    
    @FXML
    private void sauvegarderProduit(ActionEvent event) throws IOException{
    	Produit prodExport = this.instancierProduit();
    	ExportFichier.exporterElement(prodExport);
    	
    	this.libelleProduit.setText("");
    	this.choiceBoxUnite1.setValue(null);
    	this.prixVenteProduit.setText("");
    	
    	Alert popUp = this.popUpTerminee("Nouveau produit ajouté.", prodExport);
    	popUp.show();
    }
    
    
    @FXML
    private void sauvegarderElement(ActionEvent event) throws IOException{
    	Element elemExport = this.instancierElement();
    	ExportFichier.exporterElement(elemExport);
    	
    	this.libelleElement.setText("");
    	this.choiceBoxUnite2.setValue(null);
    	
    	Alert popUp = this.popUpTerminee("Nouvel élément ajouté", elemExport);
    	popUp.show();
    }
    
    @FXML
    private void sauvegarderMPVendable(ActionEvent event) throws IOException{
    	MPVendable mpvExport = this.instancierMPV();
    	ExportFichier.exporterElement(mpvExport);
    	
    	this.libelleMPVendable  .setText("");
    	this.choiceBoxUnite3    .setValue(null);
    	this.prixAchatMPVendable.setText("");
    	this.prixVenteMPVendable.setText("");
    	
    	Alert popUp = this.popUpTerminee("Nouvelle matière première vendable ajoutée", mpvExport);
    	popUp.show();
    }
    
	//##############################################################################
	//##############################################################################
	//								   METHODES NECESSAIRES 					   #
	//##############################################################################
	//##############################################################################
    
    
    /**
     * @param phraseElem "Une nouvelle matiere premiere à été ajoutée", "Un nouveau produit, ..sproduit", etc..
     * @param elemExp    L'élement ajouté dans le CSV
     * @return une popUp de type information qui dit que l'export s'est bien déroulé
     */
    private Alert popUpTerminee(String phraseElem, Element elemExp){
    	Alert popUp = new Alert(AlertType.INFORMATION);
    	popUp.setTitle("Opération terminée");
    	popUp.setHeaderText(phraseElem);
    	popUp.setContentText("L'export s'est déroulé sans erreur.\nLigne ajoutée dans éléments.csv :\n" + elemExp.toStringCSV(0.0));
    
    	return popUp;
    }
    
    /**
     * Méthode appelée lorsqu'on clique sur le bouton pour créer un nouvel element
     */
//    private boolean checkValuesCSV(){
//    	//TODO : Vérifier qu'il y ai bien des valeurs
//    	//TODO : Vérifier qu'il y ai un double dans le prix, un string dans Libel sinon on va avoir des pb
//    	//TODO : Vérifier que l'element n'existe pas déjà!! (plus dur!)
//    		
//    	return true;
//    }
    
    private MatierePremiere instancierMP(){
    	Prix prixAchat = new Prix(Double.parseDouble(this.prixAchatMP.getText()));
    	MatierePremiere mpRet = new MatierePremiere(this.libelleMP.getText(), this.choiceBoxUnite.getValue(), prixAchat);
    	
    	return mpRet;
    }
    
    private Element instancierElement(){
    	Element elemRet = new Element(this.libelleElement.getText(), this.choiceBoxUnite2.getValue());
    	
    	return elemRet;
    }
    
    private Produit instancierProduit(){
    	Prix prixVente = new Prix(Double.parseDouble(this.prixVenteProduit.getText()));
    	Produit prodRet = new Produit(this.libelleProduit.getText(), this.choiceBoxUnite1.getValue(), prixVente);
    	
    	return prodRet;
    }
    
    private MPVendable instancierMPV(){
    	Prix prixAchat = new Prix(Double.parseDouble(this.prixAchatMPVendable.getText()));
    	Prix prixVente = new Prix(Double.parseDouble(this.prixVenteMPVendable.getText()));
    	
    	MPVendable mpvRet = new MPVendable(this.libelleMPVendable.getText(), this.choiceBoxUnite3.getValue(), prixAchat, prixVente);
    	
    	return mpvRet;
    }
    


}
