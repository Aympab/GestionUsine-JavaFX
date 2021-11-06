package views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;


import gestionFichier.ExportFichier;
import gestionFichier.ImportFichier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import listes.ListeAchat;
import listes.ListeVente;
import machines.ChaineProductionSimple;
import outils.Prix;
import simulations.Resultat;
import simulations.SemaineProd;
import stockage.StockElement;

public class PlanificationSemaineController {
	
	/**
	 * La liste des semaines de productions effectivement sur l'écran.
	 * Avec leurs gridPane de chaines de production
	 */
	private ArrayList<SemaineProd> listeSemaines;

	/**
	 * La liste des gridPane des semaines, qui vont comporter les boutons copyButton avec les chaînes
	 */
	private ArrayList<GridPane>    listeGridSemaines;
	
	/* #############################################
	 * ############						 ###########
	 * ############     BOUTON ACCUEIL   ###########
	 * ############						 ###########
	 * #############################################
	 */
	@FXML
    private Button accueilButton;
    
    @FXML
    void onAccueilButtonClicked(ActionEvent event) throws IOException {
    	SceneBuilderUtils.retourAccueil(event);
    }
    /*
     * #############################################
     * #############################################
     * #############################################
     */
    
    private boolean premierChargement = true;
    
	/* #############################################
	 * ############						 ###########
	 * ############     INITIALISATION   ###########
	 * ############						 ###########
	 * #############################################
	 */
    @FXML
    private void initialize() throws Exception{   	
    	if(this.listeGridSemaines != null){
        	this.razGridPaneSemaines();
    	}
    	
    	this.nbManquants.setText("");
    	this.labelDuree.setText("");
    	this.imagePossible.setImage(null);
    	
    	StockElement stockUsine = (StockElement) ImportFichier.importerElementsCSV();
    	this.chainesCSV = ImportFichier.importerChainesCSV(stockUsine);
    	
    	//Initialisation de la liste semaine
    	this.listeSemaines = new ArrayList<>();
    	this.listeGridSemaines = new ArrayList<>();
    	
    	//Chargement des chaînes avec initialisation du gridPane
    	if(premierChargement){
    		this.chargerChaines();
    		this.premierChargement = false;
    	}
    	//Initialisation de la semaine 0
    	this.initNouvSemaine(0);
    	
    	this.gridPaneSemaines.setStyle("-fx-background-color: #DAC9E1; -fx-grid-lines-visible: true");

    	this.gridPaneSemaines.getRowConstraints().get(0).setVgrow(Priority.NEVER);
    	this.gridPaneSemaines.getRowConstraints().get(1).setMaxHeight(500);
    	
    	//On grise les boutons
    	this.buttonProduire .setDisable(true);
    	this.buttonCommander.setDisable(true);
    	this.buttonCalculBesoins.setDisable(true);
    }
    /*
     * #############################################
     * #############################################
     * #############################################
     */

    
    
	/* #############################################
	 * ############						 ###########
	 * ############  Chargement chaines  ###########
	 * ############						 ###########
	 * #############################################
	 */
    
    private ArrayList<ChaineProductionSimple> chainesCSV;
    
    /**
     * Va chercher les chaînes dans le CSV et les instancier sous forme d'images
     */
    private void chargerChaines() throws Exception{    	

    	Iterator<ChaineProductionSimple> ite = this.chainesCSV.iterator();
    	
    	ChaineProductionSimple currChaine;
    	//On itère sur les chaînes de prod
    	    	
    	while(ite.hasNext()){
    		//Ajouter une image de chaîne pour le futur bouton
    		 currChaine = ite.next();
    		 currChaine.getMethodeProdChaine().setNivAct(1);

    		 currChaine.ajouterImage();
    	}
    	
    	this.initGridPaneChaines();

    }
    
    /**
     * L'anchor pane qui contiendra le grid pane avec les chaines
     */
    @FXML
    private AnchorPane anchorPaneChaines;
    
    /**
     * Les chaînes qui sont affichées, dans une matrice, pour pouvoir les récupérer
     */
    private ChaineProductionSimple[][] chainesAffichees = new ChaineProductionSimple [4][6];
    
    /**
     * Instancie le GridPane avec des boutons pour chaque chaine
     */
    private void initGridPaneChaines(){
    	    	
    	//On instancie le gridPane
    	GridPane gridChaines = new GridPane();
    	this.anchorPaneChaines.getChildren().add(gridChaines);
    	SceneBuilderUtils.fitToParent(gridChaines);
    	gridChaines.setHgap(10);
    	gridChaines.setVgap(10);
    	gridChaines.setStyle("-fx-background-color: #DAC9E1");
    	    	
    	Iterator<ChaineProductionSimple> ite = this.chainesCSV.iterator();
    	ChaineProductionSimple currChaine;
    
    	Integer rowNumber    = 0;
    	Integer columnNumber = 0;
    	//On itère sur toutes les chaines :
    	while(ite.hasNext()){
    		currChaine = ite.next();
    		//On a 4 ROWs au maximum et 5 COLUMNS 
    		// FIXME : Ici peut être changer le maximum (dans les paramètres user par exemple)          
                        
            		
    		//Si on est sur une nouvelle colonne
    		if(rowNumber%6 == 0){
                ColumnConstraints column = new ColumnConstraints(100);
                column.setPercentWidth( 100 );
                gridChaines.getColumnConstraints().add(column);
    		}
 
            gridChaines.add(this.creerObjetChaine(currChaine, columnNumber, rowNumber), columnNumber, rowNumber);	
            
            //Ajouter dans la matrie de chaines correspondante
            this.chainesAffichees[columnNumber][rowNumber] = currChaine;
            columnNumber ++;
            
            //TODO : dire ca au prof comme limite, voir avec Astrid
            if(columnNumber == 4){
            	columnNumber = 0;
            	rowNumber ++;
            	
            	if(rowNumber == 6){
                	Alert popUp = new Alert(AlertType.WARNING);
                	popUp.setTitle("Avertissement");
                	popUp.setHeaderText("Attention, toutes les chaînes ne sont pas affichées");
                	popUp.setContentText("Contactez votre informaticien");
                	popUp.show(); 
            	}
            }
            
    	}//while ite.hasNext()
    	
    }
    
    
    /**
     * @param chaine la chaine pour laquelle on veut un bouton
     * @return Un bouton qui va être inséré directement dans le gridPane
     */
    private Node creerObjetChaine(ChaineProductionSimple chaine, int numCol, int numLig){    	
    	AnchorPane anchorPaneRetour = new AnchorPane();
    	
    	Button objetRetour = new Button(chaine.getLibelle(), chaine.getImageViewChaine());
        objetRetour.setContentDisplay(ContentDisplay.TOP);
        objetRetour.setTooltip(new Tooltip(chaine.toStringTooltip()));
//        objetRetour.setPrefSize(50, 100);
        
        anchorPaneRetour.getChildren().add(objetRetour);
    	SceneBuilderUtils.fitToParent(objetRetour);
    	
    	//Ajout du drag
        this.ajouterEventDragButton(objetRetour, numCol, numLig);
        
//        StackPane stackPane = new StackPane(anchorPaneRetour);
        this.ajouterDropHandlingTabChaines(anchorPaneRetour);
        
        objetRetour.setId("B_chaine_"+numCol + "_" + numLig);
    	return anchorPaneRetour;
    }
    
	private static final DataFormat buttonFormat = new DataFormat("MyButton");


	private Button draggingButton;
	
	private Integer draggingCol, draggingLig;
    
	 private void ajouterEventDragButton(Button b, int numCol, int numLig) {
	     b.setOnDragDetected(e -> {
		 Dragboard db = b.startDragAndDrop(TransferMode.MOVE);
		 db.setDragView(b.snapshot(null, null)); 
		 ClipboardContent cc = new ClipboardContent();
		 cc.put(buttonFormat, " "); 
		 db.setContent(cc); 
		 
		 this.draggingButton = b;	
		 //On met a jour la chaine en cours pour pouvoir la récup
		 this.draggingCol = numCol;
		 this.draggingLig = numLig;
     });
	 }
	 
    /*
     * #############################################
     * #############################################
     * #############################################
     */
    
	/* #############################################
	 * ############						 ###########
	 * ############  INITIALISATION Semaines #######
	 * ############						 ###########
	 * #############################################
	 */
    
    /**
     * Le gridPane du bas de l'écran, conteant les différentes semaines
     */
    @FXML
    private GridPane gridPaneSemaines;
    
    @FXML
    private Button buttonAjouterSemaine;
    
    @FXML
    private Button buttonRetirerSemaine;
    
    
    /**
     *  Initialise un label pour une Semaine et ajoute dans l'arrayList une semaine
     */
    private void initNouvSemaine(Integer numSemaine){  	
    	    	
    	//On ajoute dans la HashMap une semaine et un GridPane vide
    	GridPane grid = new GridPane();
    	SemaineProd semaine = new SemaineProd(numSemaine);
    	grid.setId(numSemaine.toString());
    	ColumnConstraints col = new ColumnConstraints();
    	col.setPercentWidth(100);
    	grid.getColumnConstraints().add(col);
    	
    	this.listeSemaines.add(semaine);
    	this.listeGridSemaines.add(grid);
    	
    	AnchorPane anchorLabel = new AnchorPane();
    	//TODO : ici ajouter le on click
    	this.ajouterOnClickPaneSemaine(anchorLabel);
    	
    	//On initialise le label
    	Label labelSemaine = new Label("S"+numSemaine.toString());
    	labelSemaine.setFont(new Font("Monospace", 13));
    	labelSemaine.setAlignment(Pos.CENTER);
    	
    	anchorLabel.getChildren().add(labelSemaine);
    	
    	
    	col = new ColumnConstraints();
    	col.setPercentWidth(100);
    	
    	this.gridPaneSemaines.getColumnConstraints().add(col);
    	

    	this.gridPaneSemaines.add(anchorLabel, numSemaine, 0);
    	SceneBuilderUtils.fitToParent(labelSemaine);
    	
    	
    	//Initialisation de la première ligne du gridPanede l'event pour le drop
    	
    	
    	//Ajout d'un anchor pane dans la 2 eme ligne du tab semaines
    	AnchorPane ligBasTab = new AnchorPane();
    	SceneBuilderUtils.fitToParent(ligBasTab);
    	
    	//Ajout d'une ligne vide à ce gridPane pour laisser un espace en dessous 
    	//TODO : a voir avec Astrid
//    	RowConstraints nouvelleLigne = new RowConstraints();
//    	nouvelleLigne.setPercentHeight(100);
//    	grid.getRowConstraints().add(nouvelleLigne);
    	
    	//On ajoute le gridPane correspondant à la semaine dans ce anchor Pane
    	ligBasTab.getChildren().add(grid);
    	SceneBuilderUtils.fitToParent(grid);
    	
    	//Ajout de l'event drop dans cette première case
    	this.ajouterDropHandlingTabSemaine(this.listeGridSemaines.get(numSemaine));
    	
    	this.gridPaneSemaines.add(ligBasTab, numSemaine, 1);
    	
    	if(numSemaine == 10){
        	Alert popUp = new Alert(AlertType.WARNING);
        	popUp.setTitle("Avertissement");
        	popUp.setHeaderText("Attention, la nombre de semaines devient important.");
        	popUp.setContentText("Préfèrez peut-être faire 2 planifications.");
        	popUp.show();  
    	}
    }
    
    
    /**
     * @param pane Le pane comportant le label avec S0, S1 etc..
     * Ajoute un handler pour le onClick sur ce pane, pour pouvoir avoir le résultat d'une semaine
     */
    private void ajouterOnClickPaneSemaine(Pane pane){
    	pane.setOnMouseClicked(e -> {
    		
    		if(e.getButton() == MouseButton.PRIMARY && e.getClickCount() >= 2){
    			Integer indSemaine = Integer.parseInt(((Label)pane.getChildrenUnmodifiable().get(0)).getText().substring(1,2));
    			
    			//On vérifie si la semaine possède au moins une chaîne
    			if(this.listeSemaines.get(indSemaine).getOrdreAjout().size() > 0){
    			
	    			try {
						this.listeSemaines.get(indSemaine).calculerResultatGlobal((StockElement)ImportFichier.importerElementsCSV());
					} catch (IOException exept) {
						exept.printStackTrace();
					}
	    			
	    			Resultat res = this.listeSemaines.get(indSemaine).getResultatAJour();
	    			Alert popUp;
	    			
	    			if(res.isPossible()){
	    				popUp = new Alert(AlertType.INFORMATION);
	        	     	popUp.setHeaderText("Cette production est possible.\nDétails du résultat ci-dessous.\nAttention, la résultat est calculé à partir du stock courant.");
	
	    			}
	    			else{
	    				popUp = new Alert(AlertType.WARNING);
	        	     	popUp.setHeaderText("Cette production n'est pas possible.\nDétails du résultat ci-dessous.\nAttention, la résultat est calculé à partir du stock actuel.");
	    			}
	    			
	    	     	popUp.setTitle("Test semaine S" + indSemaine);
	    	     	popUp.setContentText(res.toStringPopUp());
	    	     	popUp.show();  
    	     	
    			}
    			//Si il y a 0 chaîne dans la semaine on affiche un popUp erreur
    			else{
    				Alert popUp = new Alert(AlertType.ERROR);
	    	     	popUp.setTitle("Test semaine S" + indSemaine);
	    	     	popUp.setHeaderText("Aucune chaîne de production n'est planifiée pour cette semaine, résultat nul.");
	    	     	popUp.show();
    			}
						
    		}
    		
    	});
    }
    
    
    /**
     * Lorsqu'on clique sur le bouton pour ajouter une semaine
     */
    @FXML
    private void onClickAjouterSemaine(ActionEvent event){
    	
    	this.initNouvSemaine(this.listeSemaines.size());    	
    }
    
    @FXML
    private void onClickRetirerSemaine(ActionEvent event){
    	//On vérifie si on a au moins 2 chaines
//    	System.out.println(this.listeGridSemaines.size());
    	if(this.listeSemaines.size() == 1){
        	Alert popUp = new Alert(AlertType.ERROR);
        	popUp.setTitle("Opération impossible");
        	popUp.setHeaderText("Un seule semaine prévue.");
        	popUp.setContentText("L'opération à été anulée.\nVous devez prévoir au moins une semaine pour pouvoir planifier une production.");
        	popUp.show();   
    	}
    	else{
    		this.listeGridSemaines.get(this.listeSemaines.size() - 1).getChildren().clear();
    		
    		this.listeSemaines.remove(this.listeSemaines.size() - 1);
    		this.listeGridSemaines.remove(this.listeGridSemaines.size() - 1);
    		//Sinon, on retire la dernière semaine de l'array list et du gridPane
    		this.gridPaneSemaines.getColumnConstraints().remove(this.gridPaneSemaines.getColumnConstraints().size() - 1);
    	}
    }
    
    /*
     * #############################################
     * #############################################
     * #############################################
     */
	 private void ajouterDropHandlingTabChaines(Pane pane) {
         pane.setOnDragOver(e -> {
             Dragboard db = e.getDragboard();
             if (db.hasContent(buttonFormat) && draggingButton != null) {
                 e.acceptTransferModes(TransferMode.MOVE);
                
             }
         });

         pane.setOnDragDropped(e -> {
             Dragboard db = e.getDragboard();
             
             if (db.hasContent(buttonFormat)) {
            	 if(this.draggingButton.getId().length() == 2){
            	 
	                 
	                 Integer indSemaine = Integer.parseInt(this.draggingButton.getId().substring(0,1));

	                 //On vide la semaine, c'est dommage
	                 this.listeSemaines.get(indSemaine).viderChaines();
	                 this.listeGridSemaines.get(indSemaine).getChildren().clear();
	                 this.listeGridSemaines.get(indSemaine).getRowConstraints().clear();

	                 
	                 e.setDropCompleted(true);
	
	                 draggingButton = null;
	                 this.draggingCol = null;
	                 this.draggingLig = null;
            	 }
             }           
         });
     }  	 
            	 
            	 
    
	 private void ajouterDropHandlingTabSemaine(Pane pane) {
         pane.setOnDragOver(e -> {
             Dragboard db = e.getDragboard();
             if (db.hasContent(buttonFormat) && draggingButton != null) {
                 e.acceptTransferModes(TransferMode.MOVE);
                
             }
         });

         pane.setOnDragDropped(e -> {
             Dragboard db = e.getDragboard();
             
             if (db.hasContent(buttonFormat)) {

            	 if(draggingButton.getId().length() != 2){ //Si il ne s'agit pas d'un des bouton générés (leurs ID sont 00 01 02 11 12 21 22etc..)
//                 ((Pane)draggingButton.getParent()).getChildren().remove(draggingButton);
            	 
            	 Button copyButton = new Button(draggingButton.getText());   
            	
            	 
            	 //Ajout de la chaine a la Semaine via semaine.ajouterChaine 
            	 //FIXME : Resultat couplage faible : aller voir dans ajouter Chaine
            	 Integer index = new Integer(pane.getId());
				this.listeSemaines.get(index).ajouterChaine(this.chainesAffichees[this.draggingCol][this.draggingLig]);

				//On set ID du bouton pour pouvoir recupérer l'index dans la semaine
				copyButton.setId(index.toString() + (this.listeSemaines.get(index).getHashMapChainesTests().size()-1));

				try {
					copyButton.setGraphic(this.chainesAffichees[this.draggingCol][this.draggingLig].getCopieImageViewChaine());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				copyButton.setContentDisplay(ContentDisplay.TOP);
//            	 System.out.println("s : "+ this.listeSemaines.get(index).toString());
            	 //Ne pas calculer le résultat global (bouton calcul des planif)
            	 
            	 //ajouter une ligne au grid Pane avec percent 100
            	 RowConstraints row = new RowConstraints();
            	 row.setPercentHeight(100);
            	 
            	 ((GridPane)pane).getRowConstraints().add(row);
            	 
            	 //ajouter à ce bouton un comportement pour le remove
            	 this.ajouterEventDragButton(copyButton, 0, 0);
            	 
            	 //ajouter dans cette ligne le bouton
            	 AnchorPane anchorButton = new AnchorPane();
            	 anchorButton.getChildren().add(copyButton);
            	 
            	 //Ajout du label
            	 Label label = new Label("1");
            	 anchorButton.getChildren().add(label);
            	 AnchorPane.setLeftAnchor(label, 5.0);
            	 AnchorPane.setTopAnchor(label, 5.0);
            	 
            	 
            	 SceneBuilderUtils.fitToParent(copyButton);
            	 
            	 this.ajouterEventCopyButton(copyButton);
            	 
            	 
                 ((GridPane)pane).add(anchorButton, 0, this.listeSemaines.get(index).getHashMapChainesTests().size() - 1);
                 SceneBuilderUtils.fitToParent(anchorButton);
                 
                 e.setDropCompleted(true);

                 draggingButton = null;
                 this.draggingCol = null;
                 this.draggingLig = null;
                 
                 this.buttonCalculBesoins.setDisable(false);
                 
            	 }
             }           
         });
     }
	 
	 private void ajouterEventCopyButton(Button copyButton) {
         copyButton.setOnMouseClicked(e -> {
        	 
        	 //On recupère L'anchorPane qui contient le bouton et le label
        	 //Le label est en position 2
        	Parent p = copyButton.getParent();
	    	Integer chiffreLabel = Integer.parseInt(((Label)p.getChildrenUnmodifiable().get(1)).getText());

	    	//L'index de la chaine dans la semaine
 	    	Integer index = Integer.parseInt(copyButton.getId().substring(1,2));
 	    	
 	    	if(e.getButton() == MouseButton.PRIMARY){
	 	    	if(e.isShiftDown()){ 	        	
	 	    		chiffreLabel --;
	 	 	    	this.listeSemaines.get(Integer.parseInt(p.getParent().getId())).getOrdreAjout().get(index).getMethodeProdChaine().decrNivActivation();
	 	    	}else{
	 	    		chiffreLabel ++;
	 	 	    	this.listeSemaines.get(Integer.parseInt(p.getParent().getId())).getOrdreAjout().get(index).getMethodeProdChaine().incrNivActivation();
	 	    	}
	 	    	
	 	    	if(chiffreLabel == 0 ){
	 	    		chiffreLabel = 1;
	 	    	}

 	    	//On change le texte du label
	 	    	((Label)p.getChildrenUnmodifiable().get(1)).setText(chiffreLabel.toString());
 	    	}
 	    	else if (e.getButton() == MouseButton.SECONDARY){
 	    		ChaineProductionSimple chaineClick = this.listeSemaines.get(Integer.parseInt(copyButton.getId().substring(0,1))).getOrdreAjout().get(Integer.parseInt(copyButton.getId().substring(1,2)));
 	    		
 	    		//Si il fait un clic droit, on sort une Pop up avec des informations sur la chaine
	 	       	 Alert popUp = new Alert(AlertType.INFORMATION);
	 	     	 popUp.setTitle("Informations sur \"" + chaineClick.getLibelle() + "\"");
	 	     	 popUp.setHeaderText("ID chaîne : " + chaineClick.getIdChaine().toString() + "\nManuel : " + chaineClick.getMethodeProdChaine().getManuelProd().getNomManuel());
	 	     	 popUp.setContentText("Entrée : " + chaineClick.getMethodeProdChaine().getManuelProd().getMatIn().toStringPopUp() + "\nSortie : " + chaineClick.getMethodeProdChaine().getManuelProd().getMatOut().toStringPopUp());
	 	     	 popUp.show();   
 	    	}
 	    	
         });
	 }
	 
//	 private void ajouterDropHandlingTabChaine(Pane pane) {
//         pane.setOnDragOver(e -> {
//             Dragboard db = e.getDragboard();
//             if (db.hasContent(buttonFormat) && draggingButton != null) {
//                 e.acceptTransferModes(TransferMode.MOVE);
//                
//             }
//         });
//
//         pane.setOnDragDropped(e -> {
//             Dragboard db = e.getDragboard();
//             
//             if (db.hasContent(buttonFormat)) {
//                 ((Pane)draggingButton.getParent()).getChildren().remove(draggingButton);
//                 pane.getChildren().add(draggingButton);
//                 e.setDropCompleted(true);
//                 
//                 draggingButton = null;
//             }           
//         });
//     }
	 
//	 @FXML
//	    void clickGridPanePlanif(MouseEvent event) {
//	    	Node clickedNode;
//
//	    	if(event.isShiftDown()){
//	        	
//			    clickedNode = event.getPickResult().getIntersectedNode();
//	    	}
//	 }
	 
    
	/* #############################################
	 * ############						 ###########
	 * ############     CBN				 ###########
	 * ############						 ###########
	 * #############################################
	 */
	 
	 @FXML
	 private Button buttonCalculBesoins;
	 
	 private Resultat resultatCalcule;
	 
	 @FXML
	 private Label labelBenefice;
	 
	 @FXML
	 private void onClickCalculBesoins(ActionEvent event) throws IOException{
//		 System.out.println( listeSemaines.size() + " semaines");
//		 System.out.println( listeGridSemaines.size() + " gridSemaines");
		 
		 //On initilise le stock de simulation
		 StockElement stockSimul = (StockElement) ImportFichier.importerElementsCSV();
				 
		 Iterator<SemaineProd> ite = this.listeSemaines.iterator();
		 SemaineProd currSemaine;
		 
//		 Resultat resFinal;
		 Boolean flagFirst = false;
		 
		 Boolean possible    = true;
		 Integer dureeTotale = 0;
		 Prix benefice 		 = new Prix(0);
		 ListeAchat listeConso = new ListeAchat();
		 ListeAchat listeManquant = new ListeAchat();
		 ListeVente listeProduit = new ListeVente();
		 
		 //On itère sur toutes les semaines.
		 while(ite.hasNext()){
			 currSemaine = ite.next();
			 
			 //Si la semaine n'est pas vide
			 if(currSemaine.getOrdreAjout().size() != 0){
				 currSemaine.initialiserResultat();
				 currSemaine.calculerResultatGlobal(stockSimul);
				 //Pour chaque semaine, on recalcule le résultat global et on l'ajoute
				 dureeTotale += currSemaine.getResultatAJour().getDureeTotale();
				 benefice.ajouterPrix(currSemaine.getResultatAJour().getBenefice());
				 listeConso.ajouterEnsemble(currSemaine.getResultatAJour().getListeElemConsommes());
				 listeManquant.ajouterEnsemble(currSemaine.getResultatAJour().getListeElemManquants());
				 listeProduit.ajouterEnsemble(currSemaine.getResultatAJour().getListeElemProduits());

				 
				 //On simule un produire()
				 stockSimul.retirerEnsemble(currSemaine.getResultatAJour().getListeElemConsommes());
				 stockSimul.ajouterEnsemble(currSemaine.getResultatAJour().getListeElemProduits());
				 
				 //Le stock est passé dans le négatif, donc c'est à partir de cette semaine qu'on bloque
				 if(!flagFirst && !stockSimul.genererStockManquant().estVideQuantite()){	 
					 //On notifie le fait que ca bloque à cette semaine si c'est la première
					
			     	 Alert popUp = new Alert(AlertType.WARNING);
			     	 popUp.setTitle("Production impossible.");
			     	 popUp.setHeaderText("La production est impossible.\nVoici des détails sur le premier blocage rencontré :");
			     	 popUp.setContentText("Blocage : Semaine S" + currSemaine.getNumeroSemaine() + "\nElement(s) bloquant(s) : " + stockSimul.genererStockManquant().toStringPopUp() + "\nGénérez une liste d'achat pour plus de détails sur les autres éléments manquants.");
			     	 popUp.show(); 
						 
					 flagFirst = true;
				 }
			 }
		 
		 }
		 possible = listeManquant.estVideQuantite();
		 
		 this.resultatCalcule  = new Resultat(UUID.randomUUID().toString(), "ResFinaleProduction", possible.toString(), dureeTotale, benefice, listeConso, listeManquant, listeProduit);
		 
		 
		 this.reagirResultat();
		 
	 }
	 
	 @FXML
	 private Label labelDuree;
	 
	 @FXML
	 private ImageView imagePossible;
	 
	 @FXML
	 private Label nbManquants;
	 
	 @FXML
	 private Button buttonProduire;
	 
	 @FXML
	 private Button buttonCommander;
	 
	 private void reagirResultat() throws FileNotFoundException{		 
		 if(this.resultatCalcule.isPossible()){
			 //On dégrise le produire
			 this.buttonProduire.setDisable(false);
			 this.buttonCommander.setDisable(true);
			 
			 //On change l'image
	         InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"\\images\\possible.png");
	         Image image = new Image(inputStream);
	         this.imagePossible.setImage(image);
	         
		 }
		 else{
			 this.buttonProduire.setDisable(true);
			 this.buttonCommander.setDisable(false);
			 
			 //On change l'image
	         InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"\\images\\pasPossible.png");
	         Image image = new Image(inputStream);
	         this.imagePossible.setImage(image);
		 }
		 
		 int t = this.resultatCalcule.getDureeTotale();
		 
		 int hours = t / 60; //since both are ints, you get an int
		 int minutes = t % 60;
		 
		 this.labelDuree .setText(Integer.toString(this.resultatCalcule.getDureeTotale()) +" min.\n"+ hours + " h " + minutes + " min");
		 this.nbManquants.setText(Integer.toString(this.resultatCalcule.getListeElemManquants().getListeStock().size()));
		 

		 
		 this.labelBenefice.setText(this.resultatCalcule.getBenefice().toString());
	 }
	 
	 @FXML
	 private void clickButtonProduire(ActionEvent event) throws Exception{
		 StockElement stockUsine = (StockElement) ImportFichier.importerElementsCSV();
		 
		 stockUsine.ajouterEnsemble(this.resultatCalcule.getListeElemProduits());
		 stockUsine.retirerEnsemble(this.resultatCalcule.getListeElemConsommes());
		 
		 ExportFichier.remplacerContenuElement(stockUsine);
		 
     	 Alert popUp = new Alert(AlertType.CONFIRMATION);
     	 popUp.setTitle("Production prévue");
     	 popUp.setHeaderText("La production à bien été prévue, le stock à été modifié.");
     	 popUp.setContentText("Elements ajoutés dans le stock : " + this.resultatCalcule.getListeElemProduits().toStringPopUp());
     	 popUp.show();   
     	 
     	 this.initialize();
	 }
	 
	 @FXML
	 private void clickButtonCommander(ActionEvent event) throws Exception{
		 
	    	ButtonType save = new ButtonType("Sauvegarder", ButtonBar.ButtonData.OK_DONE);
	    	ButtonType cmd = new ButtonType("Commander", ButtonBar.ButtonData.CANCEL_CLOSE);
	    	ButtonType saveCmd = new ButtonType("Les deux", ButtonBar.ButtonData.OTHER);

	    	
	    	Alert alert = new Alert(AlertType.CONFIRMATION,
	    	        "Voulez-vous sauvegarder la liste d'achats, ou simplement commander les élements manquants?",
	    	        save,
	    	        cmd,
	    	        saveCmd);
	    	alert.setHeaderText("Sauvegarder ou passer la commande?");
	    	alert.setTitle("Confirmation");
	    	TextField inputNomListe = new TextField("nom");
	    	Label label = new Label("Si vous sauvegardez votre liste, pensez à la nommer :");
	    	label.setWrapText(true);
	    	
	    	AnchorPane anchor = new AnchorPane();
	    	anchor.getChildren().add(label);
	    	anchor.getChildren().add(inputNomListe);
	    	AnchorPane.setTopAnchor(inputNomListe, 10.0);
	    	AnchorPane.setLeftAnchor(label, 2.0);
	    	AnchorPane.setLeftAnchor(inputNomListe, 2.0);
	    	
	    	alert.getDialogPane().setContent(anchor);
	    	Optional<ButtonType> result = alert.showAndWait();
	    	
	    	//Initialisation de la nouvelle PopUp
	    	Alert popUp = new Alert(AlertType.INFORMATION);
	    	popUp.setTitle("Opération terminée");
	    	

	    	if(result.get().getButtonData().toString().equals("OK_DONE")){
	    		//Sauvegarder
	        	this.resultatCalcule.getListeElemManquants().setNomEnsembleElements(inputNomListe.getText());
	    		ExportFichier.exporterListeAchat(this.resultatCalcule.getListeElemManquants());
	    		
	        	popUp.setHeaderText("Sauvegarde effectuée");
	        	popUp.setContentText("L'export s'est déroulé sans erreur.\nLigne ajoutée dans listesAchats.csv :\n" + this.resultatCalcule.getListeElemManquants().toStringListeStockCSV());
	        	popUp.show();
	        	
	    		this.initialize();

	    	}else if(result.get().getButtonData().toString().equals("CANCEL_CLOSE")){
	    		//Commander
	    		ReapproController.commanderListeAchat(this.resultatCalcule.getListeElemManquants());
	    		
	        	popUp.setHeaderText("Commande effectuée");
	    		popUp.setContentText("Le réapprovisionnement s'est déroulé correctement, éléments ajoutés à votre stock : " + this.resultatCalcule.getListeElemManquants().toStringPopUp());
	        	popUp.show();
	        	
	    		this.initialize();

	    	}else{
	    		//Les deux
	        	this.resultatCalcule.getListeElemManquants().setNomEnsembleElements(inputNomListe.getText());
	    		ExportFichier.exporterListeAchat(this.resultatCalcule.getListeElemManquants());
	    		ReapproController.commanderListeAchat(this.resultatCalcule.getListeElemManquants());
	    		
	        	popUp.setHeaderText("Sauvegarde et commande effectuées");
	        	popUp.setContentText("Le réapprovisionnement et la sauvegarde se sont déroulés correctement, éléments ajoutés à votre stock : " + this.resultatCalcule.getListeElemManquants().toStringPopUp());

	        	popUp.show();
	        	
	    		this.initialize();
	    	}
	 }
	 
	 
	 private void razGridPaneSemaines(){
		 Iterator<GridPane> ite = this.listeGridSemaines.iterator();
	 
		 GridPane currGrid;
		 while(ite.hasNext()){
			 currGrid = ite.next();
			 
//			 currGrid.getChildren().get(index)
			 //FIXME
//			 ((GridPane)currGrid.getChildren().get(1)).getColumnConstraints().clear();
			 currGrid.getChildren().clear();
			 currGrid.getColumnConstraints().clear();
		 }
		 
		 this.gridPaneSemaines.getColumnConstraints().clear();
		 this.gridPaneSemaines.getChildren().clear();
		 
		 this.gridPaneSemaines.setStyle("-fx-grid-lines-visible: true");
	 }
	 
    /*
     * #############################################
     * #############################################
     * #############################################
     */
	 
	 
	 
		/* #############################################
		 * ############						 ###########
		 * ############     POP up aide		 ###########
		 * ############						 ###########
		 * #############################################
		 */
	 @FXML
	 private Button buttonHelp;
	 
	 @FXML
	 private void onClickButtonHelp(ActionEvent event){
     	 Alert popUp = new Alert(AlertType.INFORMATION);
     	 popUp.setTitle("Commandes possibles");
     	 popUp.setHeaderText("Voici une liste des différentes commandes possibles.");
     	 popUp.setContentText("- Drag N Drop sur une chaîne pour la planifier\n- Clic pour agmenter le niv act, Shift + clic pour le décrémenter\n- Clic droit sur une chaîne pour obtenir des informations\n- Double clic sur une semaine pour la tester unitairement");
     	 popUp.show();  
	 }
	    /*
	     * #############################################
	     * #############################################
	     * #############################################
	     */
}
