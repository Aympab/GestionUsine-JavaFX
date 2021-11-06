package gestionFichier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import acteurs.externes.FournisseurSimple;
import listes.*;
import machines.ChaineProductionSimple;
import matieres.*;
import outils.*;
import production.ManuelProduction;
import production.MethodeProduction;
import simulations.Resultat;
import simulations.TestSimple;
import stockage.EnsembleElements;
import stockage.StockElement;

public abstract class ImportFichier {
	
	//##############################################################################
	//##############################################################################
	//								   METHODES NECESSAIRES 					   #
	//##############################################################################
	//##############################################################################	
	
	/**
	 * @param uneLigneCSV un tableau de String contenant la ligne CSV, chaque case représente une colonne du CSV
	 * @return l'element instancié, un Produit, une MPVendable, ou une MatierePremiere
	 * @throws IOException 
	 */
	private static Element instancierElement(String[] uneLigneCSV) throws IOException{
		Element elem;
		UniteMesure uniteInstance;
		
//		if(uneLigneCSV[3].length() == 36){
			uniteInstance = ImportFichier.trouverUniteAvecID(uneLigneCSV[3]);
//		}else{
//			uniteInstance = 
//		}
//		
		
		if(uneLigneCSV[4].equals("NA")){ //On a pas prix achat
			if(uneLigneCSV[5].equals("NA")){ //On a pas prix vente
				elem = new Element(uneLigneCSV[0], uneLigneCSV[1], uniteInstance);
				//Si on a ni l'un ni l'autre on instancie un Element
			}else{ 							 //On a prix Vente
				elem = new Produit(uneLigneCSV[0], uneLigneCSV[1], uniteInstance, new Prix(Double.parseDouble(uneLigneCSV[5])));
			}
		}else{ 							 //On a prix achat
			if(uneLigneCSV[5].equals("NA")){ //On a pas prix vente
				elem = new MatierePremiere(uneLigneCSV[0], uneLigneCSV[1], uniteInstance, new Prix(Double.parseDouble(uneLigneCSV[4])));
			}else{ 							 //On a prix Vente
				elem = new MPVendable(uneLigneCSV[0], uneLigneCSV[1], uniteInstance, new Prix(Double.parseDouble(uneLigneCSV[4])), new Prix(Double.parseDouble(uneLigneCSV[5])));
			}
		}
		return elem;
	}
	
	
	/**Permet d'instancier une ListeAchat à partir d'un String de type
	 * <b>(3hg4-ef456,8.9),(3456-efb3,5.0)</b>
	 * @param listeAchatCSV
	 * @param stockUsine le stock sur lequel on se base pour récupérer les noms des matieres
	 * @return Une ListeAchat, correspondant à MatIN d'un Manuel
	 */
	public static ListeAchat instancierListeAchat(String listeAchatCSV, EnsembleElements stockUsine){
		
		//listeAchatCSV = listeAchatCSV.replace(",", "");

		//System.out.println(sub);
		//System.out.println(listeAchatCSV);

		String[] subS;
		ListeAchat liste = new ListeAchat("ListeAchatGenerée");
		
		
		while(listeAchatCSV.length() != 0){
			//Sub : ce qu'il y a entre des parenthèses
			String sub = listeAchatCSV.substring(listeAchatCSV.indexOf("(") +1,listeAchatCSV.indexOf(")"));
			//sub = SANS LES PARENTHESES
			subS = sub.split(",");	
			//subS[0] = 1234-abcd-56789-efff
			//subS[1] = 1.8
			//System.out.println(subS[0] + " - "+  subS[1]);
			
			Element elem = stockUsine.trouverElementAvecID(subS[0]);
			if(elem instanceof MatierePremiere){
				elem = new MatierePremiere(subS[0], elem.getLibelle(), elem.getUniteQte(), elem.getPrixAchat());
			}else if(elem instanceof Produit){
				elem = new Produit(subS[0], elem.getLibelle(), elem.getUniteQte(), elem.getPrixVente());
			}else if(elem instanceof MPVendable){
				elem = new MPVendable(subS[0], elem.getLibelle(), elem.getUniteQte(), elem.getPrixAchat(), elem.getPrixVente());
			}else{
				elem = new Element(subS[0], elem.getLibelle(), elem.getUniteQte());
			}
			//!! On instancie pas forcément un Element! mais PE MP ou Produit
			//elem = new Element(subS[0], elem.getLibelle(), elem.getUniteQte());
			
			liste.ajouterNouvelElement(elem);
			liste.ajouterQteElement(elem, Double.parseDouble(subS[1]));
			
			// +2 car il y a en plus les deux parenthèses
			//listeAchatCSV.substring(sub.length() + 2, listeAchatCSV.length()) nous donne la nouvelle ligne sans le sub
			listeAchatCSV = listeAchatCSV.substring(sub.length() + 2, listeAchatCSV.length());
			
			if(listeAchatCSV.length() >0 && listeAchatCSV.charAt(0) == ','){
				//On vérifie si le premier caractère est une virgule
				listeAchatCSV = listeAchatCSV.substring(1, listeAchatCSV.length());
			}
		}
		
		return liste;
	}
	
	public static ListeVente instancierlisteVente(String listeVenteCSV, EnsembleElements stockUsine) throws NumberFormatException, IOException{
		//listeVenteCSV = listeVenteCSV.replace(",", "");
		//System.out.println(sub);
		//System.out.println(listeVenteCSV);

		String[] subS;
		ListeVente liste = new ListeVente("listeVenteGenerée");
		Element elem;
		
		while(listeVenteCSV.length() != 0){
			//Sub : ce qu'il y a entre des parenthèses
			String sub = listeVenteCSV.substring(listeVenteCSV.indexOf("(") +1,listeVenteCSV.indexOf(")"));
			//sub = SANS LES PARENTHESES
			subS = sub.split(",");	
			//subS[0] = 1234-abcd-56789-efff
			//subS[1] = 1.8
			//System.out.println(subS[0] + " - "+  subS[1]);

//			elem = instancierElement((stockUsine.trouverElementAvecID(subS[0]).toStringCSV(Double.parseDouble(subS[1])).split(";")));
			elem = stockUsine.trouverElementAvecID(subS[0]);
			
			if(elem instanceof MatierePremiere){
				elem = new MatierePremiere(subS[0], elem.getLibelle(), elem.getUniteQte(), elem.getPrixAchat());
			}else if(elem instanceof Produit){
				elem = new Produit(subS[0], elem.getLibelle(), elem.getUniteQte(), elem.getPrixVente());
			}else if(elem instanceof MPVendable){
				elem = new MPVendable(subS[0], elem.getLibelle(), elem.getUniteQte(), elem.getPrixAchat(), elem.getPrixVente());
			}else{
				elem = new Element(subS[0], elem.getLibelle(), elem.getUniteQte());
			}
			liste.ajouterNouvelElement(elem);
			liste.ajouterQteElement(elem, Double.parseDouble(subS[1]));
			
			// +2 car il y a en plus les deux parenthèses
			//listeVenteCSV.substring(sub.length() + 2, listeVenteCSV.length()) nous donne la nouvelle ligne sans le sub
			listeVenteCSV = listeVenteCSV.substring(sub.length() + 2, listeVenteCSV.length());
			
			if(listeVenteCSV.length() >0 && listeVenteCSV.charAt(0) == ','){
				//On vérifie si le premier caractère est une virgule
				listeVenteCSV = listeVenteCSV.substring(1, listeVenteCSV.length());
			}
		}
		
		return liste;
	}
	
	public static ChaineProductionSimple instancierChaine(String[] uneLigneCSV, EnsembleElements stockUsine) throws Exception{
		MethodeProduction methode = ImportFichier.instancierMethodeAvecCode(uneLigneCSV[2], stockUsine);
		
		ChaineProductionSimple chaine = new ChaineProductionSimple(uneLigneCSV[0], uneLigneCSV[1], methode);
		
		return chaine;
	}
	
	/**
	 * @param codeUnite Le code de l'unité qu'on est en train de chercher
	 * @return l'unité correspondante à ce code unique
	 * 
	 * Méthode qui va chercher dans le fichier unites.csv si une unité correspond 
	 * à ce code
	 * @throws IOException 
	 */
	public static UniteMesure trouverUniteAvecID(String codeUnite) throws IOException{
		UniteMesure uniteRet = null;
		
		ArrayList<UniteMesure> listeUnitesCSV = ImportFichier.importerUnitesCSV();
		
		Iterator<UniteMesure> it = listeUnitesCSV.iterator();
		
		//TODO : Vérifier que codeUnite soit bien de type UUID (36 char)
		
		while(it.hasNext()){
			UniteMesure uCourante = it.next();
			
			if(uCourante.getCodeUnite().compareTo(UUID.fromString(codeUnite)) == 0){
				uniteRet = new UniteMesure(uCourante.getCodeUnite().toString(), uCourante.getLibelle(), uCourante.getAbrev());
			}
		}
		
		if(uniteRet == null){
			//TODO : Error handle
			uniteRet = new UniteMesure("13fe07e1-6f93-4bc9-8974-38aeea71d746", "UniteInconnue", "?");
		}
		
		
		return uniteRet;
	}
	
	
	public static TestSimple trouverTestAvecID(String ID) throws Exception{
		TestSimple testRet = null;
		
		ArrayList<TestSimple> listeTestCSV = ImportFichier.importerArrayListTestSimple();
		
		Iterator<TestSimple> it = listeTestCSV.iterator();
		
		//TODO : Vérifier que codeUnite soit bien de type UUID (36 char)
		
		while(it.hasNext()){
			TestSimple currTest = it.next();
			
			if(currTest.getIdTest().compareTo(UUID.fromString(ID)) == 0){
				testRet = currTest;
				break;
			}
		}
		
		if(testRet == null){
			//TODO : Error handle
		}
		
		
		return testRet;
	}
	
	//##############################################################################
	//##############################################################################
	//								   IMPORTS          						   #
	//##############################################################################
	//##############################################################################
	
	
	/**Appelle simplement les autres méthodes selon l'option
	 * 
	 * @param option correspond à ce que l'on veut importe 
	 * <br> 1 : fichier elements
	 * <br> 2 : fichier chaines
	 * <br> etc..
	 */
	public abstract /*enlever abstract et mettre static*/ void importerCSV(int option);
	
	
	/**
	 * Cette méthode importe tous les fichiers CSV 
	 */
	public abstract /*idem static*/ void importerDonnesCSV();
	
	
	
	/**Méthode qui importe le fichier elements.csv et instancie des classes
	 * 
	 * @return un ensembleElement contenant les Elements idiqués dans le CSV
	 * @throws IOException si erreur lors de la lecture du fichier
	 */
	public static EnsembleElements importerElementsCSV() throws IOException{
		File fichier = new File("./data/elements.csv");		
		BufferedReader br = new BufferedReader(new FileReader(fichier));
		
		
		EnsembleElements ensembleElementsElemCSV = new StockElement("StockCSV");

		String ligneCSV = br.readLine(); // Pour passer la première ligne avec la définition des colonnes
		
		while ((ligneCSV = br.readLine()) != null) {
			String tabLigne[] = ligneCSV.split(";");  //On split ligneCSV dans tabLigne[] avec le separateur ;
//			System.out.println(tabLigne[0]); //ID
//			System.out.println(tabLigne[1]); //Libelle element
//			System.out.println(tabLigne[2]); //Quantité dans le stock
//			System.out.println(tabLigne[3]); //Libelle de l'unité de mesure
//			System.out.println(tabLigne[4]); //Valeur du prix d'achat, NA si c'est un produit
//			System.out.println(tabLigne[5]); //Valeur du prix d evente, NA si c'est une MP
			Element elem = ImportFichier.instancierElement(tabLigne);
			ensembleElementsElemCSV.ajouterNouvelElement(elem); //Afin de reset la quantité à 0.0 pour pouvoir y ajouter la vraie qte
			ensembleElementsElemCSV.ajouterQteElement(elem, Double.parseDouble(tabLigne[2]));  
		}
		br.close();		
		
		return ensembleElementsElemCSV;
	}
	
	/**
	 * @param codeManuel
	 * @param stockUsine
	 * @return
	 * @throws IOException
	 * 
	 * Renvoie le manuel correspondant au code codeManuel, Exception si il n'existe pas (TODO)
	 */
	private static ManuelProduction instancierManuelAvecCode(String codeManuel, EnsembleElements stockUsine) throws IOException{
		File fichier = new File("./data/manuels.csv");		
		BufferedReader br = new BufferedReader(new FileReader(fichier));

		
		String ligneCSV = br.readLine(); 
		
		while ((ligneCSV = br.readLine()) != null) {
			String tabLigne[] = ligneCSV.split(";");
//			System.out.println(tabLigne[0]);  //CODE
//			System.out.println(tabLigne[1]);  //Nom
//			System.out.println(tabLigne[2]);  //DUREE
//			System.out.println(tabLigne[3]);  //matIn   
//			System.out.println(tabLigne[4]);  //matOut (ararara, 0.0),(0rezrez,2.0)
			
			if(tabLigne[0].equals(codeManuel)){
				ListeAchat matIn = instancierListeAchat(tabLigne[3], stockUsine);
				ListeVente matOut = instancierlisteVente(tabLigne[4], stockUsine);
				br.close();
				return new ManuelProduction(codeManuel, tabLigne[1], Integer.parseInt(tabLigne[2]), matIn, matOut);
			}
			
		}
		br.close();
		System.out.println("Manuel inexistant : erreur");
		//TODO: Exception si on arive ici (ce codemanuel n'existe pas)
		return null;
	}
	
	private static MethodeProduction instancierMethodeAvecCode(String codeMet, EnsembleElements stockUsine) throws Exception{
		ArrayList<MethodeProduction> listeMet = ImportFichier.importerArrayListMethodesCSV();
		boolean existe = false;
		int i;
		
		for(i = 0; i < listeMet.size(); i++){
			if(listeMet.get(i).getIdTest().toString().equals(codeMet)){
				existe = true;
				break;
			}
		}
		
		if(existe){
			return listeMet.get(i);
		}
		else{
			throw new Exception("Methode : " + codeMet + " n'existe pas dans methodes.csv");
			//TODO : Erreur à renvoyer
		}
		
	}
	
	//TODO : Importer le tout vers un tableau de chaineProduction
	/**
	 * @param stockUsine stock de référence sur lequel se baser pour les ID des Elements
	 * @return
	 * @throws Exception 
	 */
	public static ArrayList<ChaineProductionSimple> importerChainesCSV(StockElement stockUsine) throws Exception{
		
		File fichier = new File("./data/chaines.csv");		
		BufferedReader br = new BufferedReader(new FileReader(fichier));

		
		String ligneCSV = br.readLine(); // Pour passer la première ligne avec la définition des colonnes
	
		ArrayList<ChaineProductionSimple> listeRetour = new ArrayList<>();
		
		
		while ((ligneCSV = br.readLine()) != null) {
			String tabLigne[] = ligneCSV.split(";");  //On split ligneCSV dans tabLigne[] avec le separateur ;
			//System.out.println(tabLigne[0]); //ID Chaine
			//System.out.println(tabLigne[1]); //Nom de la chaine
			//tabLigne[2] : CodeMethode
			
			
			ChaineProductionSimple chaineCSV = instancierChaine(tabLigne, stockUsine);
			listeRetour.add(chaineCSV);
		}
		br.close();	
	
		return listeRetour;
	}
	
	
	/**
	 * @return Une ArrayList contenant les UniteMesure qui sont dans le CSV
	 * @throws IOException 
	 */
	public static ArrayList<UniteMesure> importerUnitesCSV() throws IOException{
		ArrayList<UniteMesure> listeRetour = new ArrayList<>();
		
		File fichier = new File("./data/unites.csv");
		BufferedReader br = new BufferedReader(new FileReader(fichier));
		
		String  ligneCSV = br.readLine();
		
		
		while ((ligneCSV = br.readLine()) != null) {
			String tabLigne[] = ligneCSV.split(";");
			
//			System.out.println(tabLigne[0]); //UUID
//			System.out.println(tabLigne[1]); //Libelle
//			System.out.println(tabLigne[2]); //Abrev
			
			UniteMesure uniteCSV = new UniteMesure(tabLigne[0], tabLigne[1], tabLigne[2]);
			listeRetour.add(uniteCSV);
		}
		br.close();	
		
		return listeRetour;
	}
	
	
	
	/**
	 * @return une ArrayList des manuels contenus dans manuels.csv
	 * @throws IOException
	 */
	public static ArrayList<ManuelProduction> importerArrayListManuelsCSV() throws IOException{
		EnsembleElements stockUsine = ImportFichier.importerElementsCSV();
		ArrayList<ManuelProduction> listeRetour = new ArrayList<>();
		
		File fichier = new File("./data/manuels.csv");
		BufferedReader br = new BufferedReader(new FileReader(fichier));
		
		String  ligneCSV = br.readLine();
		
		
		while ((ligneCSV = br.readLine()) != null) {
			String tabLigne[] = ligneCSV.split(";");
			
//			System.out.println(tabLigne[0]); //CodeManuel
//			System.out.println(tabLigne[1]); //Nom
//			System.out.println(tabLigne[2]); //Duree
//			System.out.println(tabLigne[3]); //Entree (code,qte)
//			System.out.println(tabLigne[4]); //Sortie (code, qte)
			
			ManuelProduction manuelCSV = ImportFichier.instancierManuelAvecCode(tabLigne[0], stockUsine);
			listeRetour.add(manuelCSV);
		}
		br.close();	
		
		return listeRetour;
	}
	
	/**
	 * @return une arrayList comportant les méthodes de methodes.csv
	 * @throws IOException
	 */
	public static ArrayList<MethodeProduction> importerArrayListMethodesCSV() throws IOException{
		
		EnsembleElements stockUsine = ImportFichier.importerElementsCSV();
		ArrayList<MethodeProduction> listeRetour = new ArrayList<>();
		
		File fichier = new File("./data/methodes.csv");
		BufferedReader br = new BufferedReader(new FileReader(fichier));
		
		String  ligneCSV = br.readLine();
		
		
		while ((ligneCSV = br.readLine()) != null) {
			String tabLigne[] = ligneCSV.split(";");
			
//			System.out.println(tabLigne[0]); //IdTest
//			System.out.println(tabLigne[1]); //nomTest
//			System.out.println(tabLigne[2]); //codeManuel
//			System.out.println(tabLigne[3]); //nivActiv
//			System.out.println(tabLigne[4]); //nivActivMax
			
			ManuelProduction manuelMet = ImportFichier.instancierManuelAvecCode(tabLigne[2], stockUsine);
			MethodeProduction methodeCSV = new MethodeProduction(tabLigne[0], tabLigne[1], manuelMet, Integer.parseInt(tabLigne[3]));
			
			listeRetour.add(methodeCSV);
		}
		br.close();	
		
		return listeRetour;
	}
	
	
	
	/**
	 * @param stockRef Le stockUsine, celui sur lequel se baser pour les UUID
	 * @return Un liste de tous les fournisseurs connus dans fournisseurs.csv
	 * @throws IOException
	 */
	public static ArrayList<FournisseurSimple> importerArrayFourn(EnsembleElements stockRef) throws IOException{
		
		ArrayList<FournisseurSimple> listeRetour = new ArrayList<>();
		
		File fichier = new File("./data/fournisseurs.csv");
		BufferedReader br = new BufferedReader(new FileReader(fichier));
		
		String  ligneCSV = br.readLine();
		
		
		while ((ligneCSV = br.readLine()) != null) {
			String tabLigne[] = ligneCSV.split(";");
			
//			System.out.println(tabLigne[0]); //ID fournisseur
//			System.out.println(tabLigne[1]); //Nom Fournisseur
//			System.out.println(tabLigne[2]); //(IDarticle1, prix1),(IDarticle2, prix2)...

			ListeAchat listeMpDispo = ImportFichier.instancierListeFournisseur(tabLigne[2], stockRef);
			
			FournisseurSimple fourn = new FournisseurSimple(tabLigne[0], tabLigne[1], listeMpDispo);

			listeRetour.add(fourn);
		}
		
		br.close();
		return listeRetour;
	}
	
	
public static ListeAchat instancierListeFournisseur(String ligneCSV, EnsembleElements stockUsine){
		
		//listeAchatCSV = listeAchatCSV.replace(",", "");

		//System.out.println(sub);
		//System.out.println(listeAchatCSV);

		String[] subS;
		ListeAchat liste = new ListeAchat("ListeAchatGenerée");
		
		
		while(ligneCSV.length() != 0){
			//Sub : ce qu'il y a entre des parenthèses
			String sub = ligneCSV.substring(ligneCSV.indexOf("(") +1,ligneCSV.indexOf(")"));
			//sub = SANS LES PARENTHESES
			subS = sub.split(",");	
			//subS[0] = 1234-abcd-56789-efff
			//subS[1] = 1.8
			//System.out.println(subS[0] + " - "+  subS[1]);
			
			Element elem = stockUsine.trouverElementAvecID(subS[0]);
			Prix prixElem = new Prix(Double.parseDouble(subS[1]));
			//prixElem correspond au prix de l'élement chez ce fournisseur
			
			//On génère à chaque fois des MP ou MPVendable car on veut un prix achat
			if(elem instanceof MatierePremiere){
				elem = new MatierePremiere(subS[0], elem.getLibelle(), elem.getUniteQte(), prixElem);
			}else if(elem instanceof Produit){
//				elem = new Produit(subS[0], elem.getLibelle(), elem.getUniteQte(), elem.getPrixVente());
				elem = new MPVendable(subS[0], elem.getLibelle(), elem.getUniteQte(), prixElem, elem.getPrixVente());
			}else if(elem instanceof MPVendable){
				elem = new MPVendable(subS[0], elem.getLibelle(), elem.getUniteQte(), prixElem, elem.getPrixVente());
			}else{
//				elem = new Element(subS[0], elem.getLibelle(), elem.getUniteQte());
				elem = new MatierePremiere(subS[0], elem.getLibelle(), elem.getUniteQte(), prixElem);
			}
			
			liste.ajouterNouvelElement(elem);
			
			//Quel quantité dispo chez le fournisseur? On va dire 100.0 pour l'instant //TODO
			liste.ajouterQteElement(elem, 100.0);
			
			// +2 car il y a en plus les deux parenthèses
			//listeAchatCSV.substring(sub.length() + 2, listeAchatCSV.length()) nous donne la nouvelle ligne sans le sub
			ligneCSV = ligneCSV.substring(sub.length() + 2, ligneCSV.length());
			
			if(ligneCSV.length() >0 && ligneCSV.charAt(0) == ','){
				//On vérifie si le premier caractère est une virgule
				ligneCSV = ligneCSV.substring(1, ligneCSV.length());
			}
		}
		
		return liste;
	}


	public static ArrayList<TestSimple> importerArrayListTestSimple() throws Exception{
		
		ArrayList<TestSimple> listeRetour = new ArrayList<>();
		
		File fichier = new File("./data/testsSimples.csv");
		BufferedReader br = new BufferedReader(new FileReader(fichier));
		
		String  ligneCSV = br.readLine();
		
		ArrayList<ChaineProductionSimple> listeChaines = importerChainesCSV((StockElement)importerElementsCSV());

				
		while ((ligneCSV = br.readLine()) != null) {
			String tabLigne[] = ligneCSV.split(";");
//			idTest;nomTest;idChaine;dateTest;idResultat
			
//			System.out.println(tabLigne[0]); //ID Test
//			System.out.println(tabLigne[1]); //NomTest
//			System.out.println(tabLigne[2]); //idChaine
//			System.out.println(tabLigne[3]); //dateTest 23/04/19
//			System.out.println(tabLigne[4]); //idResultat

			//Trouver le résultat qui a cet ID, l'instancier
			UUID idTest    = UUID.fromString(tabLigne[0]);
			String libelle = tabLigne[1];
			
			String[] date  = tabLigne[3].split("/");
			
			@SuppressWarnings("deprecation")
			Date dateTest  = new Date(Integer.parseInt("20"+date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
			ChaineProductionSimple chaineTest  = trouverChaine(tabLigne[2], listeChaines);
			
			TestSimple currentTest = new TestSimple(idTest.toString(), libelle, dateTest, chaineTest);

			if(tabLigne[4] != "null"){
				currentTest.affecterResultat(tabLigne[4]);
			}
			
			
			listeRetour.add(currentTest);
		}
		
		br.close();
		return listeRetour;
	}
	
	public static ArrayList<Resultat> importerArrayResultat() throws NumberFormatException, IOException{
		ArrayList<Resultat> listeRetour = new ArrayList<>();
		
		File fichier = new File("./data/resultats.csv");
		BufferedReader br = new BufferedReader(new FileReader(fichier));
		
		String  ligneCSV = br.readLine();
		StockElement stockUsine = (StockElement) importerElementsCSV();
			
		while ((ligneCSV = br.readLine()) != null) {
			String tabLigne[] = ligneCSV.split(";");
//idRes;nomRes;possible;dureeTotale;benefice;listeManquants (id, qte);listeConso(id, qte); listeProduits (id, qte)
			
//			System.out.println(tabLigne[0]); //idRes
//			System.out.println(tabLigne[1]); //nomRes
//			System.out.println(tabLigne[2]); //possible
//			System.out.println(tabLigne[3]); //dureeTotale
//			System.out.println(tabLigne[4]); //benefice
//			System.out.println(tabLigne[5]); //listeManquant (id,qte)
//			System.out.println(tabLigne[6]); //listeConso (id, qte)
//			System.out.println(tabLigne[7]); //listeProduit (id, qte)

			//Trouver le résultat qui a cet ID, l'instancier
			UUID idRes    = UUID.fromString(tabLigne[0]);
			String nomRes = tabLigne[1];
			
			String possible = tabLigne[2];
			int dureeTotale = Integer.parseInt(tabLigne[3]);
			Prix benefice = new Prix(Double.parseDouble(tabLigne[4]));
						
			ListeAchat listeManquant = instancierListeAchat(tabLigne[5], stockUsine);
			ListeAchat listeConso    = instancierListeAchat(tabLigne[6], stockUsine);
			ListeVente listeProduit  = instancierlisteVente(tabLigne[7], stockUsine);
			
			Resultat currentRes = new Resultat(idRes.toString(), nomRes, possible, dureeTotale, benefice, listeConso, listeManquant, listeProduit);
			
			listeRetour.add(currentRes);
		}
		
		br.close();
		return listeRetour;
		
	}
	
	
	private static ChaineProductionSimple trouverChaine(String idChaine, ArrayList<ChaineProductionSimple> listeChaines){
		ChaineProductionSimple chaineRet = null;
		ChaineProductionSimple currChaine;
		Iterator<ChaineProductionSimple> ite = listeChaines.iterator();
		
		while(ite.hasNext()){
			currChaine = ite.next();
			
			if(currChaine.getIdChaine().toString().equals(idChaine)){
				//L'ID de la chaine en cours est le même que idChaine
				chaineRet = currChaine;
				break;
			}
		}
		return chaineRet;
	}

}
