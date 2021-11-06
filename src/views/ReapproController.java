package views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;

import acteurs.externes.FournisseurSimple;
import gestionFichier.ExportFichier;
import gestionFichier.ImportFichier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import listes.ListeAchat;
import matieres.Element;
import matieres.MatierePremiere;
import matieres.StringMatiereAvecQuantite;
import outils.Prix;
import outils.UniteMesure;
import stockage.EnsembleElements;
import javafx.scene.input.MouseEvent;

public class ReapproController {

	
    @FXML
    private Accordion accordion_fournisseurs;

    /**
     * La tableView pour tous les elements, avec ses colonnes, PUBLIC sinon bug au niveau de l'assignement du double clic.... :(
     */ //TODO : Change to private..
    @FXML 
    public TableView<StringMatiereAvecQuantite> tableView_listeAchat=new TableView<>();
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_libelle;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_qteUniteElement;
    
    @FXML
    private TableColumn<StringMatiereAvecQuantite, String> col_prixAchatTotal;
    
    @FXML
    private Label coutTotalListe;
    
    @FXML
    private Button buttonCommander;
    
    @FXML
    private TextField textNomListe;
    
    @FXML
    void initialize() throws IOException{    
    	
    	
    	this.col_libelle        .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("libelle"));
    	this.col_prixAchatTotal .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixAchatTotal"));
    	this.col_qteUniteElement.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("qteUniteElement"));
    	
    	this.coutTotalListe.setText("");
    	
    	this.initialiserAccordionFourn();    	
    }
    
    /**
     * Initialise les accrodion avec le bon nombre de fournisseru (va chercher dans le CSV)
     * @throws IOException 
     */
    private void initialiserAccordionFourn() throws IOException{
    	ArrayList<FournisseurSimple> listeFourn = ImportFichier.importerArrayFourn(ImportFichier.importerElementsCSV());
    	Iterator<FournisseurSimple> ite = listeFourn.iterator();
    	
    	FournisseurSimple currentFourn;
    	
    	//On boucle sur tous les fournisseurs
    	while(ite.hasNext()){
    		currentFourn = ite.next();
    		
    		AnchorPane nouveauPanel = new AnchorPane();
    		
        	ObservableList<StringMatiereAvecQuantite> listeElem = FXCollections.observableArrayList();

        	ListeAchat listeMpDispoCurrent = currentFourn.getListeMpDispo();
        	Iterator<Entry<Element, Double>> iteListeMpDispo = listeMpDispoCurrent.getListeStock().entrySet().iterator();
        	
        	//Pour un fournisseur, on boucle sur l'ensemble des elements qu'il vend
        	Entry<Element, Double> pair;
        	
        	while(iteListeMpDispo.hasNext()){
        		pair = iteListeMpDispo.next();
        		
        		StringMatiereAvecQuantite currMat = new StringMatiereAvecQuantite(pair.getKey().getIdElement().toString(), pair.getKey().getLibelle(), pair.getValue().toString(), pair.getKey().getUniteQte().getAbrev(), Double.toString(pair.getKey().getPrixAchat().getValeur()), Double.toString(pair.getKey().getPrixVente().getValeur()));
        		
        		listeElem.add(currMat);
        	}
        	
    		final TableView<StringMatiereAvecQuantite> nouvTable = new TableView<>();
    		
    		TableColumn<StringMatiereAvecQuantite, String> col_libel = new TableColumn<StringMatiereAvecQuantite, String>("Libelle");
    		col_libel.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("libelle"));
    		nouvTable.getColumns().add(col_libel);
    		
    		TableColumn<StringMatiereAvecQuantite, String> col_qteDispo = new TableColumn<StringMatiereAvecQuantite, String>("Quantité dispo.");
    		col_qteDispo.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("qteUniteElement"));
    		nouvTable.getColumns().add(col_qteDispo);
    		
    		TableColumn<StringMatiereAvecQuantite, String> col_prixAchat = new TableColumn<StringMatiereAvecQuantite, String>("Prix /u");
    		col_prixAchat.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixAchat"));
    		nouvTable.getColumns().add(col_prixAchat);
    		
    		nouvTable.setItems(listeElem);
    		
    		nouveauPanel.getChildren().add(nouvTable);
    		SceneBuilderUtils.fitToParent(nouvTable);
    		
            TitledPane paneElem = new TitledPane(currentFourn.getNomFournisseur(), nouveauPanel);
//            paneElem.setId(curUnite.getCodeUnite().toString()+"_3");
            this.accordion_fournisseurs.getPanes().add(paneElem);
            
            nouvTable.setOnMousePressed(new javafx.event.EventHandler<Event>() {
				@Override
				public void handle(Event event) {
                	if(((MouseEvent) event).isPrimaryButtonDown() && ((MouseEvent) event).getClickCount() >= 2){
                    	int indexElem;
                    		indexElem = this.elemExiste(tableView_listeAchat, nouvTable.getSelectionModel().getSelectedItem());
                        	//TODO :C'est la que ca fonctionne pas si il est en private, on arrive bien à tout faire mais on ne peut pas AFFICHER a l'écran la table qui se maj si on est pas en public
                    		
                    		//TODO : gestion de la qté disponible chez le fournisseur?
                    		
                    		if(indexElem == -1){
                	    		col_libel	      .setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("libelle"));
                	    		col_prixAchat.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("prixAchat"));
                	    		col_qteDispo.setCellValueFactory(new PropertyValueFactory<StringMatiereAvecQuantite, String>("qteElement"));
                	    		
                	    		StringMatiereAvecQuantite nouvElem = new StringMatiereAvecQuantite(nouvTable.getSelectionModel().getSelectedItem());
                	    		nouvElem.setQteElement("1.0");
                	    		tableView_listeAchat.getItems().add(nouvElem);
                    		}else{
                    			tableView_listeAchat.getItems().get(indexElem).setQteElement(Double.toString(Double.parseDouble(tableView_listeAchat.getItems().get(indexElem).getQteElement()) + 1.0));
//                    			this.tableViewMatIn.setItems(this.tableViewMatIn.getItems());
                    		}
                    	}
        			tableView_listeAchat.refresh();
        			majEntete();
				}

				private int elemExiste(TableView<StringMatiereAvecQuantite> tabRecherche,
						StringMatiereAvecQuantite selectedItem) {
					Iterator<?> ite = tabRecherche.getItems().iterator();
					StringMatiereAvecQuantite elemCourant;
					boolean trouve = false;
					int i = 0;  	
					while(ite.hasNext()){
						elemCourant = (StringMatiereAvecQuantite)ite.next();
						if(elemCourant.getIdElement().equals(selectedItem.getIdElement())){
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
				
			}); //Ca c'était la déclaration du nouvTable.setOnMousePressed(new javafx.event.EventHandler<Event>() {
    	}    	
    }

    /**
     * Mise à jour du label en entête pour avoir le bon prix 
     */
    protected final void majEntete() {
    	double prixTotal = Double.parseDouble("0.0");
    	StringMatiereAvecQuantite mat;
    	
    	Iterator<StringMatiereAvecQuantite> ite = this.tableView_listeAchat.getItems().iterator();
    	
    	while(ite.hasNext()){
    		mat = ite.next();
    		
    		prixTotal += Double.parseDouble(mat.getPrixAchatTotal());
    	}
    	
    	this.coutTotalListe.setText(Double.toString(prixTotal));    
    }    	

    
    /**
     * @param event
     * Ouvre un popUp et demande si on veut juste enregistrer la liste achat ou passer la commande (ou les deux)
     * @throws IOException 
     */
    @FXML
    private void onClickButtonCommander(ActionEvent event) throws IOException{
    	
    	ButtonType save = new ButtonType("Sauvegarder", ButtonBar.ButtonData.OK_DONE);
    	ButtonType cmd = new ButtonType("Commander", ButtonBar.ButtonData.CANCEL_CLOSE);
    	ButtonType saveCmd = new ButtonType("Les deux", ButtonBar.ButtonData.OTHER);

    	
    	Alert alert = new Alert(AlertType.CONFIRMATION,
    	        "Voulez-vous sauvegarder cette liste d'achats, ou simplement commander ces élements au fournisseur?",
    	        save,
    	        cmd,
    	        saveCmd);
    	alert.setHeaderText("Sauvegarder ou passer la commande?");
    	alert.setTitle("Confirmation");
    	Optional<ButtonType> result = alert.showAndWait();
    	
    	ListeAchat listeUser = this.instancierListeAchat();

//    	System.out.println(result.get().toString());
    	if(result.get().getButtonData().toString().equals("OK_DONE")){
//    		System.out.println("btnSauvegarder");
    		ExportFichier.exporterListeAchat(listeUser);
    		this.clearListeAchat();
    	}else if(result.get().getButtonData().toString().equals("CANCEL_CLOSE")){
//    		System.out.println("btnCommander");
    		ReapproController.commanderListeAchat(listeUser);
    		this.clearListeAchat();
    	}else{
//    		System.out.println("btnLesDeux");
    		ExportFichier.exporterListeAchat(listeUser);
    		ReapproController.commanderListeAchat(listeUser);
    		this.clearListeAchat();
    	}
    	
    	
    }
    
    
    /**
     * MàJ du stock de l'usine (donc elements.csv), en fonction de ce qu'il y a dans la listeAchat
     * (il passe commande quoi)
     * @throws IOException 
     */
    public static void commanderListeAchat(ListeAchat la) throws IOException{
    	EnsembleElements stockUsine = ImportFichier.importerElementsCSV();
    	stockUsine.ajouterEnsemble(la);
    	
    	ExportFichier.remplacerContenuElement(stockUsine);
//    	System.out.println(stockUsine.toStringCSV());
    }
    
    /**
     * Sauvegarde la tableView (à droite sur l'écran) vers le CSV
     */
    private ListeAchat instancierListeAchat(){    	
    	//exporter la liste vers listesAchats.csv
    	HashMap<Element, Double> listeStock = new HashMap<>();
    	
    	Iterator<StringMatiereAvecQuantite> ite = this.tableView_listeAchat.getItems().iterator();
    	
    	StringMatiereAvecQuantite currentSMAQ;
    	
    	while(ite.hasNext()){
    		currentSMAQ = ite.next();
    		//FIXME : Attention ici on instancie que des MP, risque de bug, ca pue
    		MatierePremiere mat = new MatierePremiere(currentSMAQ.getIdElement(), currentSMAQ.getLibelle(), new UniteMesure(""), new Prix(Double.parseDouble(currentSMAQ.getPrixAchat())));
    		Double qte 	        = Double.parseDouble(currentSMAQ.getQteElement());
    		
    		listeStock.put(mat, qte);
    		
    		}
    	
    	
    	ListeAchat listeA = new ListeAchat(this.textNomListe.getText(), listeStock);
    	
    	return listeA;
    }
    
    /**
     * RAZ du contenu de la liste à la sauvegarde 
     */
    private void clearListeAchat(){
    	this.tableView_listeAchat.setItems(null);
    	this.textNomListe.setText("");
    }
    
    
	@FXML
    void onRetourButtonClicked(ActionEvent event) throws IOException {
    	SceneBuilderUtils.retourAccueil(event);
    }

}
