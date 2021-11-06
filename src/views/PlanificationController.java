package views;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import gestionFichier.ExportFichier;
import gestionFichier.ImportFichier;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import listes.ListeAchat;
import simulations.TestSimple;
import stockage.EnsembleElements;
import stockage.StockElement;

/**
 * @author Aympab
 *
 */
public class PlanificationController {


    @FXML
    private Label titleView;

    @FXML
    private Button accueilButton;

//    @FXML
//    private GridPane gridPaneTest;
    
    @FXML
    public GridPane gridPanePlanif;
    
    @FXML
    private Button buttonGenererListe;
    
    @FXML
    private Label labelDureeTotale;
    
    @FXML
    private ImageView imagePossible;
    
    /**
     * Le numéro de ligne dans laquelle on met le nv button
     */
    private static int noLigGridPane;
    
    /**
     * Le numéro de col du gridPane dans lequel on met le nv button
     */
    private static int noColGridPane;
    
    public void incrNoLig(){
    	if(noLigGridPane == 4){
    		razNoLig();
    	}
    	else{
    		noLigGridPane++;
    	}
    }
    
    public void incrNoCol(){
    	if(noColGridPane == 7){
    		incrNoLig();
    		razNoCol();
    	}
    	else{
    		noColGridPane++;
    	}
    }
    
    public int getNoLig(){
    	return noLigGridPane;
    }
    
    public int getNoCol(){
    	return noColGridPane;
    }
    
    public void razNoLig(){
    	noLigGridPane = 0;
    }
    
    public void razNoCol(){
    	noColGridPane = 0;
    }
    
    
    /**
     * La liste de tous les tests qu'il y a dans le CSV.
     */
    private ArrayList<TestSimple> listeTests = new ArrayList<TestSimple>();
    
    /**
     * Une matrice représentant le tableau de planification 
     */
    private TestSimple[][] tabPlanifTest = new TestSimple[8][5];
    
    @FXML
    private ScrollPane scrollPaneTest;
    
    @FXML
    private AnchorPane anchorPaneTest;
    
    
    @FXML
    private Button buttonCalculPlanif;
    
    @FXML
    private Button buttonProduire;
    
    /**
     * Le tableau contenant les niveaux d'activation pour chaque test de chaque case du GridPane
     */
    private Label[][] tabLabelNivAct = new Label [8][5];
    
    private AnchorPane[][] tabAnchorPane = new AnchorPane [8][5];
    
    private ListeAchat listeManquantsPlanif;
    
    @FXML
    private Label labelNbManquants;
    
    @FXML
    void onAccueilButtonClicked(ActionEvent event) throws IOException {
    		SceneBuilderUtils.retourAccueil(event);
    }
    
    
    @FXML
    private void initialize() throws Exception{ 
    	this.razNoCol();
    	this.razNoLig();
    	this.gridPanePlanif.getChildren().clear();
    	this.anchorPaneTest.getChildren().clear();
    	
    	this.imagePossible.setImage(null);
    	
    	
    	//Initialisation du tableau d'anchor pane et du tableau de label
    	for(int i = 0; i < 5; i ++){
    		for(int j = 0; j < 8; j ++){
    			this.tabLabelNivAct[j][i] = new Label("1");
    			this.tabLabelNivAct[j][i].setTooltip(new Tooltip("Shift + clic pour décrémenter."));
    			this.tabAnchorPane[j][i]  = new AnchorPane();
    			this.tabAnchorPane[j][i].getChildren().add(this.tabLabelNivAct[j][i]);
    		}
    	}
    	this.instancierGridPanePlanif();    	
    	this.chargerTests();
    	
    	//Griser le bouton Générer liste et mise à "" de labelDuree
    	this.labelDureeTotale.setText("");
    	this.labelNbManquants.setText("");

    	this.buttonProduire.setDisable(true);
    	this.buttonGenererListe.setDisable(true);
    	//On oublie pas qu'elle est la
    	this.listeManquantsPlanif = null;
    }
    
    //TODO : Début de drag n drop
    //TODO : Fin de drag N drop
    
    /**Charge les test de testsSimples.csv et les affiche sous forme de petits boutons
     * @throws Exception
     */
    private void chargerTests() throws Exception{
    	GridPane gridPaneTest = new GridPane();
    	
    	//Recup arrayList de test
    	ArrayList<TestSimple> listeTests = ImportFichier.importerArrayListTestSimple();
    	Iterator<TestSimple> ite = listeTests.iterator();
    	
    	TestSimple currTest;
    	
    	int numLig = 0, numCol = 0;
    	
    	while(ite.hasNext()){
    		currTest = ite.next();
    		
    		Button buttonTest   = new Button(currTest.getNomTest());
    		
	        FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"\\images\\test.png");
	        Image imageTest = new Image(inputStream);
    		
	        buttonTest.setId(currTest.getIdTest().toString());
	        buttonTest.setGraphic(new ImageView(imageTest));
	        buttonTest.setTooltip(new Tooltip(currTest.toStringTooltip()));
	        buttonTest.setPrefSize(100, 100);
	        buttonTest.setContentDisplay(ContentDisplay.TOP);
	        buttonTest.setOnMousePressed(new EventHandler<Event>() {
				
				@Override
				public void handle(Event event) {
					//ici la méthode qui ajoute un carré dans la GridPanePlanif lorsqu'on clique
					Button copyButton = new Button(((Button)event.getSource()).getText());
					
					Tooltip toolCopyButton = new Tooltip(((Button)event.getSource()).getTooltip().getText());
					copyButton.setTooltip(toolCopyButton);
					
					gridPanePlanif.add(copyButton, noColGridPane, noLigGridPane);
					
					try {
						tabPlanifTest[noColGridPane][noLigGridPane] = ImportFichier.trouverTestAvecID(((Button)event.getSource()).getId());
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Erreur lors de la récupération d'un test avec son ID.");
					}
					
					incrNoCol();
				}
			} );
	        
	        ColumnConstraints column = new ColumnConstraints(50);
	        RowConstraints    row    = new RowConstraints(50);
	        
	        //TODO: Faire un beau gridPane avec des beaux boutons
//	        gridPaneTest.setPrefSize(prefWidth, prefHeight);
	        gridPaneTest.getColumnConstraints().add(column);
        	gridPaneTest.getRowConstraints().add(row);
	        gridPaneTest.setHgap(5);
	        gridPaneTest.add(buttonTest, numCol, numLig);
//	        GridPane.setMargin(buttonTest, new Insets(2));
	        
	        
	        if(numCol == 6){
	        	numCol = 0;
	        	numLig++;

	        }
	        else{
	        	numCol++;
	        }
	        
    		
	        this.listeTests.add(currTest);
    	}//while ite.hashNext();

    	SceneBuilderUtils.fitToParent(anchorPaneTest);
    	this.anchorPaneTest.getChildren().add(gridPaneTest);
    	SceneBuilderUtils.fitToParent(gridPaneTest);
    	//Pour chaque test :
    		//Ajouter un row au grid pane
	    	//Instanciation d'un button avec une image view dedans
	    	//On l'ajoute dans un grid pane
	    	//Instanciation du nom du button
    	
    		//Lui ajouter un toolTip
    }
    
    
    /**
     * Lorsqu'on veut calculer le besoin d'une quite de test, qu'on clique sur le button calculer besoins
     */
    @FXML
    void clickButtonCalculPlanif(ActionEvent event) throws IOException{
    	EnsembleElements stockUsine = ImportFichier.importerElementsCSV();
    	
    	ListeAchat listeSommeManquant = new ListeAchat();
    	Integer dureeTotale = 0;
    	
    	
    	//Calculer les résultats de chaque test avec le niveau d'activation corrspondant
    	for(int i = 0; i < 5; i ++){
    		for(int j = 0; j < 8; j++){
    			if(this.tabPlanifTest[j][i] != null){
    				//Calcul du resultat
    				this.tabPlanifTest[j][i].calculerResultat((StockElement) stockUsine, Integer.parseInt(this.tabLabelNivAct[j][i].getText()));
    				
    				if(!this.tabPlanifTest[j][i].getResultatProd().getListeElemManquants().estVideQuantite()){
    					//Si la liste manquant n'est pas vide, on l'ajoute a la liste manquantSomme
    					listeSommeManquant.ajouterEnsemble(this.tabPlanifTest[j][i].getResultatProd().getListeElemManquants());
    				}
    				
    				dureeTotale += this.tabPlanifTest[j][i].getChaineTest().getMethodeProdChaine().getManuelProd().getDuree() * Integer.parseInt((tabLabelNivAct[j][i].getText()));
    				
    			}//Le test n'est pas null (l'user a rajouté un test dans la grille)    			
    		}//for j
    	}//for i
    	
    	
    	//Si somme est vide la planif est possible, sinon non : on change l'image en fonction
        if(listeSommeManquant.estVideQuantite()){
   	         InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"\\images\\possible.png");
   	         Image image = new Image(inputStream);
   	         this.imagePossible.setImage(image);
   	         
   	         this.buttonProduire.setDisable(false);
             this.buttonGenererListe.setDisable(true);
            
       	}
       	else{
           InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"\\images\\pasPossible.png");
           Image image = new Image(inputStream);
           this.imagePossible.setImage(image);
           
           //On dégrise le bouton pour générer une liste
           this.buttonGenererListe.setDisable(false);
           this.buttonProduire.setDisable(true);
       	}
        
        this.labelDureeTotale.setText(dureeTotale.toString() + " min");
        this.labelNbManquants.setText(Integer.toString(listeSommeManquant.getListeStock().size()));
        //Affectation de this.ListeManquant pour qu'elle soit dispo dans l'autre méthode GénérerListe
        this.listeManquantsPlanif = new ListeAchat(listeSommeManquant);
    }
    
    /**
     * Lorsqu'on clique sur le GridPane du bas (pour incr/decr le niv act)
     */
    @FXML
    void clickGridPanePlanif(MouseEvent event) {
    	Node clickedNode;

    	if(event.isShiftDown()){
        	
		    clickedNode = event.getPickResult().getIntersectedNode();
		    if (clickedNode != this.gridPanePlanif) {

		    	Node parent = clickedNode.getParent();
		        while (parent != this.gridPanePlanif) {
		            clickedNode = parent;
		            parent = clickedNode.getParent();
		        }
		        Integer colIndex = GridPane.getColumnIndex(clickedNode);
		        Integer rowIndex = GridPane.getRowIndex(clickedNode);
		    	
		    		        
		        Integer nivActCase = Integer.parseInt(this.tabLabelNivAct[colIndex][rowIndex].getText());
		        nivActCase --;
		        
		        if(nivActCase == -1){
		        	nivActCase = 0;
		        }
		        
		        this.tabLabelNivAct[colIndex][rowIndex].setText(nivActCase.toString());
		    }
    	}
    	else{
		    clickedNode = event.getPickResult().getIntersectedNode();
		    if (clickedNode != this.gridPanePlanif) {
		    	
		    	Node parent = clickedNode.getParent();
		        while (parent != this.gridPanePlanif) {
		            clickedNode = parent;
		            parent = clickedNode.getParent();
		        }
		        Integer colIndex = GridPane.getColumnIndex(clickedNode);
		        Integer rowIndex = GridPane.getRowIndex(clickedNode);

		        Integer nivActCase = Integer.parseInt(this.tabLabelNivAct[colIndex][rowIndex].getText());
		        nivActCase ++;
		        
		        this.tabLabelNivAct[colIndex][rowIndex].setText(nivActCase.toString());
		    }
    	}
    }
    

    private void instancierGridPanePlanif(){
    	for(int i = 0; i < 5; i ++){
    		for(int j = 0; j < 8; j ++){
    	    	this.gridPanePlanif.add(this.tabAnchorPane[j][i], j, i);
//    	    	this.gridPanePlanif.add(new Label("1"), j, i);
    		}
    	}
    	this.gridPanePlanif.setGridLinesVisible(true);
    }
    
    @FXML
    private void clickButtonGenererListe(ActionEvent event) throws Exception{
    	
    	ButtonType save = new ButtonType("Sauvegarder", ButtonBar.ButtonData.OK_DONE);
    	ButtonType cmd = new ButtonType("Commander", ButtonBar.ButtonData.CANCEL_CLOSE);
    	ButtonType saveCmd = new ButtonType("Les deux", ButtonBar.ButtonData.OTHER);

    	
    	Alert alert = new Alert(AlertType.CONFIRMATION,
    	        "Voulez-vous sauvegarder cette liste d'achats, ou simplement commander les élements manquants?",
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
        	this.listeManquantsPlanif.setNomEnsembleElements(inputNomListe.getText());
    		ExportFichier.exporterListeAchat(this.listeManquantsPlanif);
    		
        	popUp.setHeaderText("Sauvegarde effectuée");
        	popUp.setContentText("L'export s'est déroulé sans erreur.\nLigne ajoutée dans listesAchats.csv :\n" + this.listeManquantsPlanif.toStringListeStockCSV());
        	popUp.show();
        	
    		this.initialize();

    	}else if(result.get().getButtonData().toString().equals("CANCEL_CLOSE")){
    		//Commander
    		ReapproController.commanderListeAchat(this.listeManquantsPlanif);
    		
        	popUp.setHeaderText("Commande effectuée");
    		popUp.setContentText("Le réapprovisionnement s'est déroulé correctement, elements en plus dans votre stock : " + this.listeManquantsPlanif.toStringPopUp());
        	popUp.show();
        	
    		this.initialize();

    	}else{
    		//Les deux
        	this.listeManquantsPlanif.setNomEnsembleElements(inputNomListe.getText());
    		ExportFichier.exporterListeAchat(this.listeManquantsPlanif);
    		ReapproController.commanderListeAchat(this.listeManquantsPlanif);
    		
        	popUp.setHeaderText("Sauvegarde et commande effectuées");
        	popUp.setContentText("Le réapprovisionnement et la sauvegarde se sont déroulés correctement, elements ajoutés à votre stock : " + this.listeManquantsPlanif.toStringPopUp());

        	popUp.show();
        	
    		this.initialize();

    	}
    	//Ouvrir une popUp 
    		//Demande : de nommer la liste
    		//De la commander directement
    		//De la sauvegarder
    	
    	// On envoie la liste dans listesAchats.csv si il veut la sauvegarder
    	//On ajoute au stock les manquants si il veut la commander directement
    	
    	//TODO : On affiche une popUp disant que c'est terminé
    	//On réinitialise l'écran
    }
    
    
    /**
     * Lance la production acutellement planifiée
     */
    @FXML
    void clickButtonProduire(ActionEvent event) throws IOException{
    	
    	EnsembleElements stockUsine = ImportFichier.importerElementsCSV();
//    	System.out.println("StockUsine : \n " + stockUsine.toStringCSV());
//    	System.out.println("ListeManquants : " + this.listeManquantsPlanif.toStringCSV());

    	//Itérer sur tous les tests avec chaque niv activation et simuler une production
    	
    	TestSimple curTest;
    	int curNivAct;
    	
    	for(int i = 0; i < 5; i ++){
    		for(int j = 0; j < 8; j++){
    			
    			curTest = this.tabPlanifTest[j][i];
    			curNivAct = Integer.parseInt(this.tabLabelNivAct[j][i].getText());
    			
    			if(curTest != null){
    				curTest.calculerResultat((StockElement) stockUsine, curNivAct);
    				
    				stockUsine.retirerEnsemble(curTest.getResultatProd().getListeElemConsommes());
    				stockUsine.ajouterEnsemble(curTest.getResultatProd().getListeElemProduits());
    				
    				
    			}
    		}
    	}
    	
    	System.out.println("\n\n\n AJOUT \n\n\n");

    	System.out.println(stockUsine.toStringCSV());
    	
    	//On change le CSV
    	ExportFichier.remplacerContenuElement(stockUsine);
    	
    	
    	Alert popUp = new Alert(AlertType.INFORMATION);
    	popUp.setTitle("Opération terminée");
    	popUp.setHeaderText("Planification valide, lancement de la production.");
    	popUp.setContentText("La production se termine dans " + labelDureeTotale.getText() + "ute(s).");
    	popUp.show(); 
    	//TODO: Sortir une jolie PopUp pour dire qu'on a ajouter les elements
    }
    
}
