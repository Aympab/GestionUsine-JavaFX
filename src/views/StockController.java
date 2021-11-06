package views;
//Salut astrid
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import gestionFichier.ImportFichier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import matieres.Element;
import matieres.StringMatiereAvecQuantite;
import outils.UniteMesure;
import stockage.EnsembleElements;

public class StockController {

    /**
     * Le TitledPane contenant la table view avec toutes les unités pour les MP
     */
    @FXML
    private TitledPane tildPane_MP_multiU;
    
    /**
     * Le TitledPane pour la table de toutes les unités, dans l'onglet tout le stock
     */
    @FXML
    private TitledPane tildPane_All_multiU;
    
    
    
    /**
     * La tableView pour tous les elements, avec ses colonnes
     */
    @FXML 
    private TableView<StringMatiereAvecQuantite> tableView_All_multiU;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_all_multiU_idElement;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_all_multiU_libelle;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_all_multiU_uniteQte;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_all_multiU_prixAchat;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_all_multiU_prixVente;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_all_multiU_qteElem;
    
    /**
     * Les accordions auxquels on va rajouter des TitledPane
     */
    @FXML
    private Accordion accordion_tous;
    
    @FXML
    private Accordion accordion_MP;
   
    @FXML
    private Accordion accordion_Produit;
    
    @FXML
    private Accordion accordion_Element;
    
    @FXML
    private Accordion accordion_MPV;
    
    
    
    /**
     * Table des MP avec toutes ses colonnes
     */
    @FXML
    private TableView<StringMatiereAvecQuantite> tableView_MP;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_MP_idElement;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_MP_libelle;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_MP_uniteQte;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_MP_prixAchat;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_MP_qteElem;

    @FXML
    private TableView<StringMatiereAvecQuantite> tableView_Prod;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Prod_idElement;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Prod_libelle;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Prod_uniteQte;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Prod_prixVente;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Prod_qteElem;
    
    
    @FXML
    private TableView<StringMatiereAvecQuantite> tableView_Elem;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Elem_idElement;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Elem_libelle;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Elem_uniteQte;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_Elem_qteElem;
    
    
    /**
     * La tableView pour les MPVendable
     */
    @FXML 
    private TableView<StringMatiereAvecQuantite> tableView_MPV;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_MPV_idElement;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_MPV_libelle;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_MPV_uniteQte;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_MPV_prixAchat;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_MPV_prixVente;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_MPV_qteElem;
    
    
    @FXML
    void clickRetourButton(ActionEvent event) throws IOException {
    	SceneBuilderUtils.retourAccueil(event);
    }

    
    /**
     * @throws Exception 
     */
    @FXML
    private void initialize() throws Exception{
    	this.initialiserTableView_All_multiU();
    	this.initialiserTableView_MP();
    	this.initialiserTableView_Prod();
    	this.initialiserTableView_Elem();
    	this.initialiserTableView_MPV();
    	    	
    	//Initialiser l'Accordion avec la liste des unités !
    	this.initNbUniteAccordion();
    	
    }


    /**
     * @throws Exception 
     */
    private void initNbUniteAccordion() throws Exception{
    	
    	ArrayList<UniteMesure> listeUnites = new ArrayList<>(ImportFichier.importerUnitesCSV());
    	Iterator<UniteMesure> it = listeUnites.iterator();
    	
    	while(it.hasNext()){
    		UniteMesure curUnite = it.next();
    		
            AnchorPane nouveauPanel = new AnchorPane();
            TableView<?> tab = this.remplirAccordion(0, curUnite.getCodeUnite().toString());
            
            nouveauPanel.getChildren().add(tab);

            SceneBuilderUtils.fitToParent(tab);
            
            TitledPane pane = new TitledPane(curUnite.toString(), nouveauPanel);
            pane.setId(curUnite.getCodeUnite().toString()+"_0");
//            System.out.println(pane.getId());           
            this.accordion_tous   .getPanes().add(pane);
            
            
            //Tab MP
            AnchorPane nouveauPanelMP = new AnchorPane();
            TableView<?> tabMP = this.remplirAccordion(1, curUnite.getCodeUnite().toString());
            nouveauPanelMP.getChildren().add(tabMP);
            SceneBuilderUtils.fitToParent(tabMP);
            TitledPane paneMP = new TitledPane(curUnite.toString(), nouveauPanelMP);
            paneMP.setId(curUnite.getCodeUnite().toString()+"_1");
            this.accordion_MP     .getPanes().add(paneMP);
            
            
            //tab Produit
            AnchorPane nouveauPanelProd = new AnchorPane();
            TableView<?> tabProd = this.remplirAccordion(2, curUnite.getCodeUnite().toString());
            nouveauPanelProd.getChildren().add(tabProd);
            SceneBuilderUtils.fitToParent(tabProd);
            TitledPane paneProd = new TitledPane(curUnite.toString(), nouveauPanelProd);
            paneProd.setId(curUnite.getCodeUnite().toString()+"_2");
            this.accordion_Produit.getPanes().add(paneProd);
           
            
            //Tab Elem
            AnchorPane nouveauPanelElem = new AnchorPane();
            TableView<?> tabElem = this.remplirAccordion(3, curUnite.getCodeUnite().toString());
            
            nouveauPanelElem.getChildren().add(tabElem);
            SceneBuilderUtils.fitToParent(tabElem);   
            
            TitledPane paneElem = new TitledPane(curUnite.toString(), nouveauPanelElem);
            paneElem.setId(curUnite.getCodeUnite().toString()+"_3");
            this.accordion_Element.getPanes().add(paneElem);
            
            //Tab MPV
            AnchorPane nouveauPanelMPV = new AnchorPane();
            TableView<?> tabMPV = this.remplirAccordion(4, curUnite.getCodeUnite().toString());
            
            nouveauPanelMPV.getChildren().add(tabMPV);
            SceneBuilderUtils.fitToParent(tabMPV);   
            
            TitledPane paneMPV = new TitledPane(curUnite.toString(), nouveauPanelMPV);
            paneMPV.setId(curUnite.getCodeUnite().toString()+"_4");
            this.accordion_MPV    .getPanes().add(paneMPV);
    	}

    }
    
    /**
     * Remplit la tableView principale (toutes unités confondues, touts elements confondus)
     * @throws IOException 
     */
    private void initialiserTableView_All_multiU() throws IOException{
    	EnsembleElements stockUsine = ImportFichier.importerElementsCSV();
    	
    	//Initialisation des colonnes
    	this.col_all_multiU_idElement.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("idElement"));
    	this.col_all_multiU_libelle  .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("libelle"));
    	this.col_all_multiU_qteElem  .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("qteElement"));
    	this.col_all_multiU_uniteQte .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("uniteQte"));
    	this.col_all_multiU_prixAchat.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixAchat"));
    	this.col_all_multiU_prixVente.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixVente"));
    	
    	//Remplissage des colonnes
		Iterator<Entry<Element, Double>> ite = stockUsine.getListeStock().entrySet().iterator();			
		Entry<Element, Double> pair;		
		
		//Ceci est la liste qui sera envoyée dans la table directement après
		ObservableList<StringMatiereAvecQuantite> listeStockUsine = FXCollections.observableArrayList();
		
		//On boucle sur les élements du stock de l'usine
		while(ite.hasNext()){
			pair = ite.next();
			
			//On instancie une ligne StringMatiereAvecQuantite pour coincider vec les fonctions JavaFX
			StringMatiereAvecQuantite infosElements = new StringMatiereAvecQuantite(pair.getKey().getIdElement().toString(), pair.getKey().getLibelle(), pair.getValue().toString(), pair.getKey().getUniteQte().getAbrev(), Double.toString(pair.getKey().getPrixAchat().getValeur()),  Double.toString(pair.getKey().getPrixVente().getValeur()) );
		
			listeStockUsine.add(infosElements);
		}

		this.tableView_All_multiU.setItems(listeStockUsine);
    }
    
    
    /**
     * Remplit la tableView des MP
     * @throws Exception 
     */
    private void initialiserTableView_MP() throws Exception{
    	EnsembleElements stockUsine = ImportFichier.importerElementsCSV();
    	
    	//Initialisation des colonnes
    	this.col_MP_idElement.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("idElement"));
    	this.col_MP_libelle  .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("libelle"));
    	this.col_MP_qteElem  .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("qteElement"));
    	this.col_MP_uniteQte .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("uniteQte"));
    	this.col_MP_prixAchat.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixAchat"));
    	
    	//Remplissage des colonnes
		Iterator<Entry<Element, Double>> ite = stockUsine.getListeStock(1, "").entrySet().iterator();			
		Entry<Element, Double> pair;		
		
		//Ceci est la liste qui sera envoyée dans la table directement après
		ObservableList<StringMatiereAvecQuantite> listeStockUsine = FXCollections.observableArrayList();
		
		//On boucle sur les élements du stock de l'usine
		while(ite.hasNext()){
			pair = ite.next();
			
			//On instancie une ligne StringMatiereAvecQuantite pour coincider vec les fonctions JavaFX
			StringMatiereAvecQuantite infosElements = new StringMatiereAvecQuantite(pair.getKey().getIdElement().toString(), pair.getKey().getLibelle(), pair.getValue().toString(), pair.getKey().getUniteQte().getAbrev(), Double.toString(pair.getKey().getPrixAchat().getValeur()), Double.toString(pair.getKey().getPrixVente().getValeur()) );
		
			listeStockUsine.add(infosElements);
		}

		this.tableView_MP.setItems(listeStockUsine);
    }
    
    /**
     * Remplit la tableView des Produits
     * @throws Exception 
     */
    private void initialiserTableView_Prod() throws Exception{
    	EnsembleElements stockUsine = ImportFichier.importerElementsCSV();
    	
    	//Initialisation des colonnes
    	this.col_Prod_idElement.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("idElement"));
    	this.col_Prod_libelle  .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("libelle"));
    	this.col_Prod_qteElem  .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("qteElement"));
    	this.col_Prod_uniteQte .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("uniteQte"));
    	this.col_Prod_prixVente.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixVente"));
    	
    	//Remplissage des colonnes
		Iterator<Entry<Element, Double>> ite = stockUsine.getListeStock(2, "").entrySet().iterator();			
		Entry<Element, Double> pair;		
		
		//Ceci est la liste qui sera envoyée dans la table directement après
		ObservableList<StringMatiereAvecQuantite> listeStockUsine = FXCollections.observableArrayList();
		
		//On boucle sur les élements du stock de l'usine
		while(ite.hasNext()){
			pair = ite.next();
			
			//On instancie une ligne StringMatiereAvecQuantite pour coincider vec les fonctions JavaFX
			StringMatiereAvecQuantite infosElements = new StringMatiereAvecQuantite(pair.getKey().getIdElement().toString(), pair.getKey().getLibelle(), pair.getValue().toString(), pair.getKey().getUniteQte().getAbrev(), Double.toString(pair.getKey().getPrixAchat().getValeur()), Double.toString(pair.getKey().getPrixVente().getValeur()) );
		
			listeStockUsine.add(infosElements);
		}

		this.tableView_Prod.setItems(listeStockUsine);
    }
    
    
    /**
     * Remplit la tableView des Elements
     * @throws Exception 
     */
    private void initialiserTableView_Elem() throws Exception{
    	EnsembleElements stockUsine = ImportFichier.importerElementsCSV();
    	
    	//Initialisation des colonnes
    	this.col_Elem_idElement.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("idElement"));
    	this.col_Elem_libelle  .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("libelle"));
    	this.col_Elem_qteElem  .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("qteElement"));
    	this.col_Elem_uniteQte .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("uniteQte"));
    	
    	//Remplissage des colonnes
		Iterator<Entry<Element, Double>> ite = stockUsine.getListeStock(3, "").entrySet().iterator();			
		Entry<Element, Double> pair;		
		
		//Ceci est la liste qui sera envoyée dans la table directement après
		ObservableList<StringMatiereAvecQuantite> listeStockUsine = FXCollections.observableArrayList();
		
		//On boucle sur les élements du stock de l'usine
		while(ite.hasNext()){
			pair = ite.next();
			
			//On instancie une ligne StringMatiereAvecQuantite pour coincider vec les fonctions JavaFX
			StringMatiereAvecQuantite infosElements = new StringMatiereAvecQuantite(pair.getKey().getIdElement().toString(), pair.getKey().getLibelle(), pair.getValue().toString(), pair.getKey().getUniteQte().getAbrev(), "NA", "NA" );
		
			listeStockUsine.add(infosElements);
		}

		this.tableView_Elem.setItems(listeStockUsine);
    }
    
    
    /**
     * Remplit la tableView des MPVendable
     * @throws Exception 
     */
    private void initialiserTableView_MPV() throws Exception{
    	EnsembleElements stockUsine = ImportFichier.importerElementsCSV();
    	
    	//Initialisation des colonnes
    	this.col_MPV_idElement.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("idElement"));
    	this.col_MPV_libelle  .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("libelle"));
    	this.col_MPV_qteElem  .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("qteElement"));
    	this.col_MPV_uniteQte .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("uniteQte"));
    	this.col_MPV_prixAchat.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixAchat"));
    	this.col_MPV_prixVente.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixVente"));

    	//Remplissage des colonnes
		Iterator<Entry<Element, Double>> ite = stockUsine.getListeStock(4, "").entrySet().iterator();			
		Entry<Element, Double> pair;		
		
		//Ceci est la liste qui sera envoyée dans la table directement après
		ObservableList<StringMatiereAvecQuantite> listeStockUsine = FXCollections.observableArrayList();
		
		//On boucle sur les élements du stock de l'usine
		while(ite.hasNext()){
			pair = ite.next();
			
			//On instancie une ligne StringMatiereAvecQuantite pour coincider vec les fonctions JavaFX
			StringMatiereAvecQuantite infosElements = new StringMatiereAvecQuantite(pair.getKey().getIdElement().toString(), pair.getKey().getLibelle(), pair.getValue().toString(), pair.getKey().getUniteQte().getAbrev(), Double.toString(pair.getKey().getPrixAchat().getValeur()),  Double.toString(pair.getKey().getPrixVente().getValeur())  );
		
			listeStockUsine.add(infosElements);
		}

		this.tableView_MPV.setItems(listeStockUsine);
    }
    
    
   
    /**
     * Permet de remplir le contenu des différents TitledPane de l'accorion_tous
     * @param optionType : voir EnsembleElement 
     * @throws Exception 
     */
    private TableView<StringMatiereAvecQuantite> remplirAccordion(int optionType, String codeU) throws Exception{

    	EnsembleElements stockUsine = new EnsembleElements(ImportFichier.importerElementsCSV());
    	
		   
    	ObservableList<StringMatiereAvecQuantite> listeElem = FXCollections.observableArrayList();
	   
    	//Dans le getId de currentPane on a le code unité!
    	Iterator<Entry<Element, Double>> ite = stockUsine.getListeStock(optionType, codeU).entrySet().iterator();
    	Entry<Element, Double> pair;		
					
		//On boucle sur les élements du stock de l'usine qui ont une unité en particulier
		while(ite.hasNext()){
			pair = ite.next();
			StringMatiereAvecQuantite nouvelElem = new StringMatiereAvecQuantite(pair.getKey().getIdElement().toString(), pair.getKey().getLibelle(), pair.getValue().toString(), pair.getKey().getUniteQte().getAbrev(), Double.toString(pair.getKey().getPrixAchat().getValeur()), Double.toString(pair.getKey().getPrixVente().getValeur()) );
			listeElem.add(nouvelElem);
//			System.out.println(nouvelElem.getIdElement() + " " + nouvelElem.getLibelle());
		}
				
		TableView<StringMatiereAvecQuantite> tableRet = new TableView<>();
		
		TableColumn<StringMatiereAvecQuantite, String> col_ID = new TableColumn<StringMatiereAvecQuantite, String>("ID");
		col_ID.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("idElement"));
		tableRet.getColumns().add(col_ID);
		
		TableColumn<StringMatiereAvecQuantite, String> col_libelle = new TableColumn<StringMatiereAvecQuantite, String>("Libelle");
		col_libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
		tableRet.getColumns().add(col_libelle);
		
		TableColumn<StringMatiereAvecQuantite, String> col_qte = new TableColumn<StringMatiereAvecQuantite, String>("Quantité");
		col_qte.setCellValueFactory(new PropertyValueFactory<>("qteElement"));
		tableRet.getColumns().add(col_qte);
		
		TableColumn<StringMatiereAvecQuantite, String> col_unite = new TableColumn<StringMatiereAvecQuantite, String>("Unité");
		col_unite.setCellValueFactory(new PropertyValueFactory<>("uniteQte"));
		tableRet.getColumns().add(col_unite);
		
		
		if(optionType == 0 || optionType == 1 || optionType == 4){
			TableColumn<StringMatiereAvecQuantite, String> col_prixAchat = new TableColumn<StringMatiereAvecQuantite, String>("Achat");
			col_prixAchat.setCellValueFactory(new PropertyValueFactory<>("prixAchat"));
			tableRet.getColumns().add(col_prixAchat);
		}
		
		if(optionType == 0 || optionType == 2 || optionType == 4){
			TableColumn<StringMatiereAvecQuantite, String> col_prixVente = new TableColumn<StringMatiereAvecQuantite, String>("Vente");
			col_prixVente.setCellValueFactory(new PropertyValueFactory<>("prixVente"));
			tableRet.getColumns().add(col_prixVente);
		}
		
		tableRet.setItems(listeElem);
		return tableRet;
		
   }
    
}
