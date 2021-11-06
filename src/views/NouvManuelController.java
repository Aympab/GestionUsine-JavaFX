package views;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import gestionFichier.ExportFichier;
import gestionFichier.ImportFichier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import matieres.Element;
import matieres.StringMatiereAvecQuantite;
import production.ManuelProduction;
import stockage.EnsembleElements;

public class NouvManuelController {

    @FXML
    private TableView<StringMatiereAvecQuantite> tableViewMP;

    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_MP_libelle;

    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_MP_qte;

    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_MP_prixAchat;

    @FXML
    private TableView<StringMatiereAvecQuantite> tableViewProd;

    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Prod_libelle;

    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Prod_qte;

    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Prod_prixVente;

    @FXML
    private TextField inputDuree;
    
    @FXML
    private TextField inputNomManuel;

    @FXML
    private Label nbElements_MatIn;
    
    @FXML
    private Label prixTotal_MatIn;
    
    @FXML
    private Label nbElements_MatOut;
    
    @FXML
    private Label prixTotal_MatOut;
    
    @FXML
    private Button sauvegarderButton;
    
    @FXML
    private Button retourButton;

    
    @FXML
    private TableView<StringMatiereAvecQuantite> tableViewMatIn;
    
    @FXML
    private TableView<StringMatiereAvecQuantite> tableViewMatOut;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_matIn_libelle;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_matOut_libelle;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_matIn_qteUniteElement;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_matOut_qteUniteElement;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_matIn_prixAchat;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_matOut_prixVente;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_matIn_prixAchatTotal;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_matOut_prixVenteTotal;
    
    @FXML
    void onRetourButtonClicked(ActionEvent event) throws IOException {
    	SceneBuilderUtils.retourAccueil(event);
    }
    
    @FXML
    private void initialize() throws Exception{
    	
    	this.nbElements_MatIn.setText("0");
    	this.prixTotal_MatIn.setText("0.0");
    	
    	this.nbElements_MatOut.setText("0");
    	this.prixTotal_MatOut.setText("0.0");
    	
    	
    	this.col_MP_libelle    .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("libelle"));
    	this.col_MP_qte        .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("qteUniteElement"));
    	this.col_MP_prixAchat  .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixAchat"));
    	
    	EnsembleElements stockUsine = ImportFichier.importerElementsCSV();
    	
    	
    	//Chargement table MP
    	ObservableList<StringMatiereAvecQuantite> listeMP = FXCollections.observableArrayList();
    	
    	Iterator<Entry<Element, Double>> iteMP = stockUsine.getListeStock(1, "").entrySet().iterator();
    	Entry<Element, Double> pairMP;		
    	
		while(iteMP.hasNext()){
			pairMP = iteMP.next();
			listeMP.add(new StringMatiereAvecQuantite(pairMP.getKey().getIdElement().toString(), pairMP.getKey().getLibelle(), pairMP.getValue().toString(), pairMP.getKey().getUniteQte().getAbrev(), Double.toString(pairMP.getKey().getPrixAchat().getValeur()), Double.toString(pairMP.getKey().getPrixVente().getValeur())));
		}    	
    	this.tableViewMP  .setItems(listeMP);
    	
    	
    	//Chargement table Produits
    	
    	this.col_Prod_libelle  .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("libelle"));
    	this.col_Prod_qte      .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("qteUniteElement"));
    	this.col_Prod_prixVente.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixVente"));
    	
    	ObservableList<StringMatiereAvecQuantite> listeProd = FXCollections.observableArrayList();
    	
    	Iterator<Entry<Element, Double>> iteProd = stockUsine.getListeStock(2, "").entrySet().iterator();
    	Entry<Element, Double> pairProd;		
    	
		while(iteProd.hasNext()){
			pairProd = iteProd.next();
			listeProd.add(new StringMatiereAvecQuantite(pairProd.getKey().getIdElement().toString(), pairProd.getKey().getLibelle(), pairProd.getValue().toString(), pairProd.getKey().getUniteQte().getAbrev(), Double.toString(pairProd.getKey().getPrixAchat().getValeur()), Double.toString(pairProd.getKey().getPrixVente().getValeur())));
		}    	
    	this.tableViewProd.setItems(listeProd);
    }
    
    
    @FXML
    private void onClickTableViewProd(MouseEvent event){
    	int indexElem;
    	
    	if(event.isPrimaryButtonDown() && event.getClickCount() >= 2){
    		indexElem = this.elemExiste(this.tableViewMatOut, this.tableViewProd.getSelectionModel().getSelectedItem());
    		
    		if(indexElem == -1){
	    		this.col_matOut_libelle	      .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("libelle"));
	    		this.col_matOut_qteUniteElement.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("qteUniteElement"));
	    		this.col_matOut_prixVente      .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixVente"));
	    		this.col_matOut_prixVenteTotal .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixVenteTotal"));
	    		
	    		StringMatiereAvecQuantite nouvElem = new StringMatiereAvecQuantite(this.tableViewProd.getSelectionModel().getSelectedItem());
	    		nouvElem.setQteElement("1.0");
	    		
	    		this.tableViewMatOut.getItems().add(nouvElem);
    		}else{
    			this.tableViewMatOut.getItems().get(indexElem).setQteElement(Double.toString(Double.parseDouble(this.tableViewMatOut.getItems().get(indexElem).getQteElement()) + 1.0));
    			this.tableViewMatOut.refresh();
    		}
        	//Maj des infos en tête
        	this.calculerInfos_matOut();
    	}
    }
    
    @FXML
    private void onClickTableViewMP(MouseEvent event){
    	int indexElem;
    	
    	if(event.isPrimaryButtonDown() && event.getClickCount() >= 2){
    		indexElem = this.elemExiste(this.tableViewMatIn, this.tableViewMP.getSelectionModel().getSelectedItem());
    		
    		if(indexElem == -1){
	    		this.col_matIn_libelle	      .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("libelle"));
	    		this.col_matIn_qteUniteElement.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("qteUniteElement"));
	    		this.col_matIn_prixAchat      .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixAchat"));
	    		this.col_matIn_prixAchatTotal .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixAchatTotal"));
	    		
	    		StringMatiereAvecQuantite nouvElem = new StringMatiereAvecQuantite(this.tableViewMP.getSelectionModel().getSelectedItem());
	    		nouvElem.setQteElement("1.0");
	    		
	    		this.tableViewMatIn.getItems().add(nouvElem);
    		}else{
    			this.tableViewMatIn.getItems().get(indexElem).setQteElement(Double.toString(Double.parseDouble(this.tableViewMatIn.getItems().get(indexElem).getQteElement()) + 1.0));
//    			this.tableViewMatIn.setItems(this.tableViewMatIn.getItems());
    			this.tableViewMatIn.refresh();
    		}
        	//Maj des infos en tête
        	this.calculerInfos_matIn();
    	}
    }
    
    @FXML
    private void onClickSauvegarderButton(ActionEvent event) throws NumberFormatException, IOException{
    	//TODO : Vérifier qu'il y ai au moins 1 element dans tableViewMatIn et dans tableViewMatOut
    	//TODO : Vérifier valeur dans durée
    	//TODO : Lancer un popUp si qqch ne va pas !
    	
    	ManuelProduction manExp = ExportFichier.exporterManuelFromFX(this.inputNomManuel.getText(), Integer.parseInt(this.inputDuree.getText()), this.tableViewMatIn, this.tableViewMatOut);
    	
    	Alert popUp = new Alert(AlertType.INFORMATION);
    	popUp.setTitle("Opération terminée");
    	popUp.setHeaderText("Un nouveau manuel de production à été créé.");
    	popUp.setContentText("L'export s'est déroulé sans erreur.\nLigne ajoutée dans manuels.csv :\n" + manExp.toStringCSV());
    
    	popUp.show();
    	
    	this.inputDuree.clear();
    	this.inputNomManuel.clear();
    	this.tableViewMatIn.getItems().remove(0, this.tableViewMatIn.getItems().size());
    	this.tableViewMatOut.getItems().remove(0, this.tableViewMatOut.getItems().size());
    }
    
    
    
    
    
    
	//##############################################################################
	//##############################################################################
	//								  AUTRES METHODES          					   #
	//##############################################################################
	//##############################################################################	   
    
    
    /**
	 * @param tabRecherche
	 * @param elem
	 * @return L'index de l'élement, -1 s'il n'existe pas
	 */
	private int elemExiste(TableView<?> tabRecherche, StringMatiereAvecQuantite elem){
		Iterator<?> ite = tabRecherche.getItems().iterator();
		StringMatiereAvecQuantite elemCourant;
		boolean trouve = false;
		int i = 0;  	
		while(ite.hasNext()){
			elemCourant = (StringMatiereAvecQuantite)ite.next();
			if(elemCourant.getIdElement().equals(elem.getIdElement())){
				trouve = true;
				break;
			}
			i++;
		}
		
		if(!trouve){
			i = -1;
		}
		
		return i;
	}
    
    
    /**
     * Met à jout les informations du tableau pour matIn (nbElements et prix Total)
     */
    private void calculerInfos_matIn(){
    	double prixTotal = Double.parseDouble("0.0");
    	double nbElem = Double.parseDouble("0.0");
    	StringMatiereAvecQuantite mat;
    	
    	Iterator<StringMatiereAvecQuantite> ite = this.tableViewMatIn.getItems().iterator();
    	
    	while(ite.hasNext()){
    		mat = ite.next();
    		
    		prixTotal += Double.parseDouble(mat.getPrixAchatTotal());
//    		System.out.println(mat.getPrixAchatTotal());
    		nbElem    += Double.parseDouble(mat.getQteElement());
//    		System.out.println(mat.getQteElement());
    	}
    	
    	this.prixTotal_MatIn.setText(Double.toString(prixTotal));
    	this.nbElements_MatIn.setText(Double.toString(nbElem));
    }
    
    
    /**
     * Met à jout les informations du tableau pour matOut (nbElements et prix Total)
     */
    private void calculerInfos_matOut(){
    	double prixTotal = Double.parseDouble("0.0");
    	double nbElem = Double.parseDouble("0.0");
    	StringMatiereAvecQuantite mat;
    	
    	Iterator<StringMatiereAvecQuantite> ite = this.tableViewMatOut.getItems().iterator();
    	
    	while(ite.hasNext()){
    		mat = ite.next();
    		
    		prixTotal += Double.parseDouble(mat.getPrixVenteTotal());
//    		System.out.println(mat.getPrixAchatTotal());
    		nbElem    += Double.parseDouble(mat.getQteElement());
//    		System.out.println(mat.getQteElement());
    	}
    	
    	this.prixTotal_MatOut.setText(Double.toString(prixTotal));
    	this.nbElements_MatOut.setText(Double.toString(nbElem));
    }

}
