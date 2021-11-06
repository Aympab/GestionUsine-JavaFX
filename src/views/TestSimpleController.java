package views;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.Map.Entry;

import gestionFichier.ExportFichier;
import gestionFichier.ImportFichier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import machines.ChaineProductionSimple;
import matieres.Element;
import matieres.StringMatiereAvecQuantite;
import simulations.Resultat;
import simulations.TestSimple;
import stockage.StockElement;

public class TestSimpleController {

    @FXML
    private ComboBox<ChaineProductionSimple> comboBoxChaines;
    
    @FXML
    private Spinner<Integer> spinNivAct;
    
    @FXML
    private TextField nomTest;
    
    
    @FXML
    private Button retourButton;
    
    @FXML
    private Button lancerTest;
    
    @FXML
    private Label labelNomChaine;
    
    @FXML
    private Label labelNomMethode;
    
    @FXML
    private Label labelNomManuel;
    
    @FXML
    private TableView<StringMatiereAvecQuantite> listeMatIn;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_libelMatIn;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_libelMatOut;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_qteUniteElementMatIn;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_qteUniteElementMatOut;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_prixAchatTotalMatIn;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_prixVenteTotalMatOut;
    
    @FXML
    private TableView<StringMatiereAvecQuantite> listeMatOut;
    
    @FXML
    private Label nomResultatTest;
    
    @FXML
    private Label dateResultatTest;
    
    @FXML
    private ImageView imagePossible;
    
    @FXML
    private Label labelBenefice;
    
    @FXML
    private TableView<StringMatiereAvecQuantite> tableListeManquants;
    
    @FXML
    private TableView<StringMatiereAvecQuantite> tableListeConsommes;
    
    @FXML
    private TableView<StringMatiereAvecQuantite> tableListeProduits;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Libel_Manquants;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Libel_Consommes;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Libel_Produits;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Qte_Manquants;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Qte_Consommes;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Qte_Produits;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_prixAchatTotal_Manquants;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_prixAchatTotal_Consommes;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_prixVente_Produits;
  
    @FXML
    private Label labelDureeTotale;
    
    @FXML
    private Button sauvegarderTestButton;
    
    private Resultat resEnCours;
    
    private TestSimple testEnCours;
    
    @FXML
    void onRetourButtonClicked(ActionEvent event)throws IOException{
    	SceneBuilderUtils.retourAccueil(event);
    }
    
    private StockElement stockUsine;
    
    @FXML
    private void initialize() throws Exception{
    	this.stockUsine = (StockElement) ImportFichier.importerElementsCSV();
    	
    	//Initialisation de la comboBox
    	ArrayList<ChaineProductionSimple> listeChaines = ImportFichier.importerChainesCSV(stockUsine);
    	
    	for(int i = 0; i < listeChaines.size(); i++){
    		this.comboBoxChaines.getItems().add(listeChaines.get(i));
//    		System.out.println(listeChaines.get(i).getMethodeProdChaine().getNomTest());
    	}
    	
//    	Initi spinner
    	SpinnerValueFactory<Integer> valeursSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
    	this.spinNivAct.setValueFactory(valeursSpinner);    
    	
    	
    	//Init les label à 0
    	this.labelNomChaine.setText("");
    	this.labelNomManuel.setText("");
    	this.labelNomMethode.setText("");
    	
    	//Initialisation de la TableView
    	PropertyValueFactory<StringMatiereAvecQuantite, String> p1 = new PropertyValueFactory<>("libelle");
    	this.col_libelMatIn.setCellValueFactory(p1);
    	this.col_libelMatOut.setCellValueFactory(p1);
    	
    	PropertyValueFactory<StringMatiereAvecQuantite, String> p2 = new PropertyValueFactory<>("qteUniteElement");
    	this.col_qteUniteElementMatIn.setCellValueFactory(p2);
    	this.col_qteUniteElementMatOut.setCellValueFactory(p2);
    	
    	this.col_prixAchatTotalMatIn.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixAchatTotal"));
    	this.col_prixVenteTotalMatOut.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixVenteTotal"));
//    	System.out.println(this.col_libelMatIn.getCellValueFactory().toString());
    	
    	
    	//Initialisation des tables manquants consommes et produits
    	this.col_Libel_Consommes.setCellValueFactory(p1);
    	this.col_Libel_Manquants.setCellValueFactory(p1);
    	this.col_Libel_Produits .setCellValueFactory(p1);
    	
    	this.col_Qte_Consommes.setCellValueFactory(p2);
    	this.col_Qte_Manquants.setCellValueFactory(p2);
    	this.col_Qte_Produits .setCellValueFactory(p2);
    	
    	this.col_prixAchatTotal_Consommes.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixAchatTotal"));
    	this.col_prixAchatTotal_Manquants.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixAchatTotal"));
    	this.col_prixVente_Produits      .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixVenteTotal"));
    	//Initialisation des labels
    	this.labelDureeTotale.setText("");
    	this.dateResultatTest.setText("");
    	this.labelBenefice.setText("");
    	this.nomResultatTest.setText("");
    	this.imagePossible.setImage(null);
    }  
    
    /**Quand on clique sur la comboBox pour changer la chaine de production,
     * vient charger les informations dans la partie basse du splitPane
     * 
     * @param event
     */
    @FXML
    private void onActionComboBox(ActionEvent event){
    	ChaineProductionSimple chaineSelect = this.comboBoxChaines.getSelectionModel().getSelectedItem();
    	
    	this.labelNomChaine.setText(chaineSelect.getLibelle());
    	this.labelNomManuel.setText(chaineSelect.getMethodeProdChaine().getManuelProd().getNomManuel());
    	this.labelNomMethode.setText(chaineSelect.getMethodeProdChaine().getNomTest());
    	
    	
    	ObservableList<StringMatiereAvecQuantite> itemsMatIn = FXCollections.observableArrayList();
    	
    	//On parcours la lsite achat du manuel de production
		Iterator<Entry<Element, Double>> ite = chaineSelect.getMethodeProdChaine().getManuelProd().getMatIn().getListeStock().entrySet().iterator();			
		Entry<Element, Double> pair;
		
		while(ite.hasNext()){
			pair = ite.next();
			
			StringMatiereAvecQuantite nouvElem = new StringMatiereAvecQuantite(pair.getKey().getIdElement().toString(), pair.getKey().getLibelle(), pair.getValue().toString(), pair.getKey().getUniteQte().getAbrev(), Double.toString(pair.getKey().getPrixAchat().getValeur()), "NA");
			itemsMatIn.add(nouvElem);
		}
    	this.listeMatIn.setItems(itemsMatIn);
    	
    	
    	ObservableList<StringMatiereAvecQuantite> itemsMatOut = FXCollections.observableArrayList();
    	//On fait la même chose pour la liste de vente
    	ite = chaineSelect.getMethodeProdChaine().getManuelProd().getMatOut().getListeStock().entrySet().iterator();
    	
    	while(ite.hasNext()){
    		pair = ite.next();
    		
    		StringMatiereAvecQuantite nouvElem = new StringMatiereAvecQuantite(pair.getKey().getIdElement().toString(), pair.getKey().getLibelle(), pair.getValue().toString(), pair.getKey().getUniteQte().getAbrev(), "NA", Double.toString(pair.getKey().getPrixVente().getValeur()));
    		itemsMatOut.add(nouvElem);
    	}
    	this.listeMatOut.setItems(itemsMatOut);
    }
    
    
    @FXML
    private void clickLancerTest() throws IOException{
    	TestSimple testEnCours = new TestSimple(this.nomTest.getText(), Date.from(Instant.now()), this.comboBoxChaines.getSelectionModel().getSelectedItem());
    	this.testEnCours = testEnCours;
    	
    	Resultat res = testEnCours.calculerResultat(this.stockUsine, this.spinNivAct.getValue().intValue());

    	this.nomResultatTest.setText(testEnCours.getNomTest());
    	this.labelDureeTotale.setText(Integer.toString(res.getDureeTotale()));
    	this.dateResultatTest.setText(testEnCours.getDateTest().toString());
    	
    	this.labelBenefice.setText(Double.toString(res.getBenefice().getValeur()));
    	if(res.getBenefice().getValeur() < 0){
    		this.labelBenefice.setTextFill(Color.web("#FF0000"));
    	}
    	else{
    		this.labelBenefice.setTextFill(Color.web("#008000"));
    	}
    	

    	//Changement image
    	if(res.isPossible()){
	         InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"\\images\\possible.png");
	         Image image = new Image(inputStream);
	         this.imagePossible.setImage(image);
         
    	}
    	else{
            InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"\\images\\pasPossible.png");
            Image image = new Image(inputStream);
            this.imagePossible.setImage(image);
    	}


    	//Remplir valeurs tables

    	Iterator<Entry<Element, Double>> ite;
    	Entry<Element, Double> pair;
		StringMatiereAvecQuantite matEnCours;
		ObservableList<StringMatiereAvecQuantite> itemsManq = FXCollections.observableArrayList();
		ObservableList<StringMatiereAvecQuantite> itemsCons = FXCollections.observableArrayList();
		ObservableList<StringMatiereAvecQuantite> itemsProd = FXCollections.observableArrayList();

		
    	//Remplissage tab manquants
    	ite = res.getListeElemManquants().getListeStock().entrySet().iterator();
    	while(ite.hasNext()){
    		pair = ite.next();
    		
    		matEnCours = new StringMatiereAvecQuantite(pair.getKey().getIdElement().toString(), pair.getKey().getLibelle(), pair.getValue().toString(), pair.getKey().getUniteQte().getAbrev(), Double.toString((pair.getKey().getPrixAchat().getValeur())), "NA");
    		
    		itemsManq.add(matEnCours);
    	}
    	this.tableListeManquants.setItems(itemsManq);
    	

    	//Remplissage tab consommes
    	ite = res.getListeElemConsommes().getListeStock().entrySet().iterator();
    	while(ite.hasNext()){
    		pair = ite.next();
    		
    		matEnCours = new StringMatiereAvecQuantite(pair.getKey().getIdElement().toString(), pair.getKey().getLibelle(), pair.getValue().toString(), pair.getKey().getUniteQte().getAbrev(), Double.toString((pair.getKey().getPrixAchat().getValeur())), "NA");
    		
    		itemsCons.add(matEnCours);
    	}
    	this.tableListeConsommes.setItems(itemsCons);
    	
    	
    	//Remplissage tab produits
    	ite = res.getListeElemProduits().getListeStock().entrySet().iterator();
    	while(ite.hasNext()){
    		pair = ite.next();
    		
    		matEnCours = new StringMatiereAvecQuantite(pair.getKey().getIdElement().toString(), pair.getKey().getLibelle(), pair.getValue().toString(), pair.getKey().getUniteQte().getAbrev(), "NA", Double.toString(pair.getKey().getPrixVente().getValeur()) );    		itemsProd.add(matEnCours);
    	}
    	this.tableListeProduits.setItems(itemsProd);
    	
    	this.resEnCours = res;
    	
//    	System.out.println(testEnCours.toString());
//    	System.out.println(testEnCours.calculerResultat(this.stockUsine));
    	
    }

    /**
     * Sauvegarde le résultat vers resultats.csv
     * @throws IOException 
     */
    @FXML
    private void sauvegarderResultat(ActionEvent event) throws IOException{
    	
    	ButtonType oui = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
    	ButtonType non = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

    	
    	Alert alert = new Alert(AlertType.CONFIRMATION,
    	        "Sauvegarder résultat vers resultats.csv ?",
    	        oui, non);
    	alert.setHeaderText("Sauvegarder résultat");
    	alert.setTitle("Confirmation sauvegarde");
    	Optional<ButtonType> result = alert.showAndWait();


//    	System.out.println(result.get().toString());
    	if(result.get().getButtonData().toString().equals("OK_DONE")){
//    		System.out.println("btnSauvegarder");
        	ExportFichier.exporterResultat(this.resEnCours);
        	
        	Alert popUp = new Alert(AlertType.INFORMATION);
        	popUp.setTitle("Opération terminée");
        	popUp.setHeaderText("L'export du résultat est terminé.");
        	popUp.setContentText("L'export s'est déroulé sans erreur.\nLigne ajoutée dans resultats.csv :\n" + this.resEnCours.toStringCSV());
      
        	popUp.show();
    	}else{
        	Alert popUp = new Alert(AlertType.INFORMATION);
        	popUp.setTitle("Opération anulée");
        	popUp.setHeaderText("L'export du résultat à été annulé.");      
        	popUp.show();
    	}
    }
    
    @FXML
    private void sauvegarderTest(ActionEvent event) throws IOException{    	
    	ExportFichier.exporterTestSimple(this.testEnCours);
    	
    	Alert popUp = new Alert(AlertType.INFORMATION);
    	popUp.setTitle("Opération terminée");
    	popUp.setHeaderText("L'export du test simple est terminé.");
    	popUp.setContentText("L'export s'est déroulé sans erreur.\nLigne ajoutée dans testsSimples.csv :\n" + this.testEnCours.toStringCSV());
  
    	popUp.show();
    }

}
