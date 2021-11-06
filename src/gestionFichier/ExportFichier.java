package gestionFichier;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javafx.scene.control.TableView;
import listes.*;
import machines.ChaineProductionSimple;
import matieres.*;
import outils.UniteMesure;
import production.ManuelProduction;
import simulations.Resultat;
import simulations.TestProd;
import simulations.TestSimple;
import stockage.EnsembleElements;

/**
 * Permet d'exporter des instances de classes vers des fichiers csv 
 * 
 * @author aympa
 */
public abstract class ExportFichier {
	
	//##############################################################################
	//##############################################################################
	//								   METHODES NECESSAIRES 					   #
	//##############################################################################
	//##############################################################################
	
	public static boolean creerRepData(){
		boolean flag = false;
		
		File repData = new File("./data");
		if(!repData.exists()){
			flag = repData.mkdir();
		}
		
		return flag;
	}
	
	/**Permet d'initialiser le fichier elements.csv avec la première ligne
	 * 
	 * @throws IOException si on a un problème dans l'écriture dans le fichir
	 */
	public static void initialiserFichElement() throws IOException{
		File fichier = new File("./data/elements.csv");
		FileWriter fw = new FileWriter(fichier);		
		fw.write("Code;Nom;Quantite;unite;achat;vente\n");
		fw.close();
	}
	
	
	/**
	 * Permet d'initialiser le fichier chaines.csv avec le bon entête
	 */
	public static void initialiserFichChaine() throws IOException{
		File fichier = new File("./data/chaines.csv");
		FileWriter fw = new FileWriter(fichier);
//		fw.write("Code;Nom;Entree (code,qte);Sortie (code,qte)\n");
		fw.write("Code;Nom;CodeManuel\n");

		fw.close();
	}
	
	/**
	 * Initialise l'entête du fichier unites.csv
	 */
	public static void initialiserFichUnites() throws IOException{
		File fichier = new File("./data/unites.csv");
		FileWriter fw = new FileWriter(fichier);
		fw.write("Code;Libelle;Abrev\n");
		fw.close();
	}
	
	public static void initialiserFichManuels() throws IOException{
		File fichier = new File("./data/manuels.csv");
		FileWriter fw = new FileWriter(fichier);
		fw.write("CodeManuel;Duree;Entree (code,qte);Sortie (code,qte)\n");
		fw.close();
	}
	
	public static void initialiserFichMethodes() throws IOException{
		File fichier = new File("./data/methodes.csv");
		FileWriter fw = new FileWriter(fichier);
		fw.write("IdTest;nomTest;codeManuel;nivActiv;nivActivMax\n");
		fw.close();
	}
	
	public static void initialiserFich(String entete, String nomFich){
		//TODO : Combine les méthodes au dessus pour le faire en une seule
	}
	
	/**Permet de créer un fichier dans /data (pour les chaines, les elements, etc)
	 * @param  nomFich le nom du fichier que l'on veut créer (sans le .csv)
	 * @return true si l'écriture s'est bien passée, false sinon
	 * @throws IOException si on a un problème lors de l'écriture
	 */
	public static boolean creerFichData(String nomFich) throws IOException{
		File fichier = new File("./data/" + nomFich + ".csv");
		return fichier.createNewFile();
	}

	
	//##############################################################################
	//##############################################################################
	//								   EXPORTS          						   #
	//##############################################################################
	//##############################################################################	
	
	/**
	 * Permet d'exporter un Element vers le fichiers .csv contenant les elements AVEC UNE QUANTITE NULLE !!
	 * 
	 * @param Element elemExport
	 * 
	 * @return succes : true si l'export s'est déroulé correctement, false sinon
	 * @throws IOException Si il y a problème lors de lect/ecriture d'un fichier
	 */
	public static void exporterElement(Element elemExport) throws IOException{		
		File fichier = new File("./data/elements.csv");		
		
		if(!fichier.exists()){
			ExportFichier.creerFichData("elements");
			ExportFichier.initialiserFichElement();
		}
		
		//Le true permet de dire qu'on va écrire en fin de fichierS
		FileWriter fw = new FileWriter(fichier, true);
		
		fw.write(elemExport.toStringCSV(0.0)+"\n");
		
		fw.close();
	}
	
	
	/**
	 * Permet d'exporter le contenu d'un stock vers un fichier CSV, écrit en fin de fichier
	 * 
	 * @param ensembleElementsExp     Le stock qu'on veut exporter en CSV 
	 * @throws IOException Si erreur lors du chargement du fichier
	 */
	public static void exporterEnsembleElements(EnsembleElements ensembleElementsExp) throws IOException{
		File fichier = new File("./data/elements.csv");
		
		if(!fichier.exists()){
			ExportFichier.creerFichData("elements");
			ExportFichier.initialiserFichElement();
		}
		
		//Le true permet de dire qu'on va écrire en fin de fichierS
		FileWriter fw = new FileWriter(fichier, true);
		
		fw.write(ensembleElementsExp.toStringCSV());
		
		fw.close();
	}
	
	/** Ré-initialise le fichier elements et écrit stockExport dedans
	 * @param stockExport le stock qu'on veut écrire dans le CSV
	 */
	public static void remplacerContenuElement(EnsembleElements stockExport) throws IOException{
		File fichier = new File("./data/elements.csv");
		
		if(!fichier.exists()){
			ExportFichier.creerFichData("elements");
			ExportFichier.initialiserFichElement();
		}
		
		
		//Le true permet de dire qu'on va écrire en fin de fichierS
		FileWriter fw = new FileWriter(fichier);
		fw.write("Code;Nom;Quantite;unite;achat;vente\n");
		fw.write(stockExport.toStringCSV());
		
		fw.close();
	}
	
	
	/**
	 * @param chaineExp La chaine qu'on veut exporter vers chaines.csv, en fin de fichier,
	 * sans écraser
	 */
	public static void exporterChaineSimple(ChaineProductionSimple chaineExp) throws IOException{
		File fichier = new File("./data/chaines.csv");
		
		if(!fichier.exists()){
			ExportFichier.creerFichData("chaines");
			ExportFichier.initialiserFichChaine();
		}
		
		//Le true permet de dire qu'on va écrire en fin de fichierS
		FileWriter fw = new FileWriter(fichier, true);
		fw.write(chaineExp.toStringCSV());

		fw.close();
	}
	
	public static void exporterManuelProd(ManuelProduction manExp) throws IOException{
		File fichier = new File("./data/manuels.csv");
		
		if(!fichier.exists()){
			ExportFichier.creerFichData("manuels");
			ExportFichier.initialiserFichManuels();
		}
		
		FileWriter fw = new FileWriter(fichier, true);
		fw.write(manExp.toStringCSV());
		
		fw.close();
	}
	
	/**
	 * @param uniteExp    L'unité de mesure qu'on souhaite exporter vers le CSV
	 * @throws IOException
	 */
	public static void exporterUnite(UniteMesure uniteExp) throws IOException{
		File fichier = new File("./data/unites.csv");
		
		//TODO : Verifier si l'UNite existe déjà !!! 
		if(!fichier.exists()){
			ExportFichier.creerFichData("unites");
			ExportFichier.initialiserFichUnites();
		}
		
		FileWriter fw = new FileWriter(fichier, true);
		fw.write(uniteExp.toStringCSV()+"\n");
		
		fw.close();
	}
	
	
	/**
	 * @param nomManuel Le nom du manuel (champs inputNomManuel)
	 * @param duree     La duree de production du manuel (inputDuree)
	 * @param tableViewMatIn La tableView de la liste Achat (même nom)
	 * @param tableViewMatOut Idem pour liste vente
	 * @throws IOException
	 * 
	 * Ajoute une ligne dans manuels.csv, appelé quand on clique sur valider dans NouvManuel.fxml
	 */
	public static ManuelProduction exporterManuelFromFX(String nomManuel, int duree, TableView<StringMatiereAvecQuantite> tableViewMatIn, TableView<StringMatiereAvecQuantite> tableViewMatOut) throws IOException{
		String strListeAchat = "";
		String strListeVente = "";
		
		Iterator<StringMatiereAvecQuantite> ite = tableViewMatIn.getItems().iterator();
		StringMatiereAvecQuantite matLigne;
		
		//On boucle sur la table des matIn
		while(ite.hasNext()){
			matLigne = ite.next();
			
			strListeAchat += matLigne.toStringManuel();
		}
		
		ite = tableViewMatOut.getItems().iterator();
		
		while(ite.hasNext()){
			matLigne = ite.next();
					
			strListeVente += matLigne.toStringManuel();
		}
		
		EnsembleElements stockUsine = ImportFichier.importerElementsCSV();
		
		ListeAchat matIn = ImportFichier.instancierListeAchat(strListeAchat, stockUsine);
		ListeVente matOut = ImportFichier.instancierlisteVente(strListeVente, stockUsine);
		ManuelProduction manExp = new ManuelProduction(nomManuel, duree, matIn, matOut);
		
		ExportFichier.exporterManuelProd(manExp);		
		return manExp;
	}
	
	
	/**
	 * Exporte un testProd en testant d'abord si c'est une méthode ou un testSimple
	 * @throws IOException 
	 */
	public static void exporterTestProd(TestProd testExp) throws IOException{
		String nomClasse = testExp.getClass().getCanonicalName();
		File fichier;
		
		//On exporte une methode
		if(nomClasse.equals("production.MethodeProduction")){
			fichier = new File("./data/methodes.csv");
		}
		else if(nomClasse.equals("simulations.TestSimple")){
			fichier = new File("./data/testsSimples.csv");
		}else{
			fichier = null;
			//TODO : error
		}
		
		
		//TODO : Verifier si le manuel existe déjà !!! 
		if(!fichier.exists()){
			ExportFichier.creerFichData("methodes");
			ExportFichier.initialiserFichMethodes();
		}
		
		FileWriter fw = new FileWriter(fichier, true);
		fw.write(testExp.toStringCSV());
		
		fw.close();		
	}
	
	
	/**
	 * @param listeAchat la liste qu'on veut exporter dans listesAchats.csv
	 * @throws IOException 
	 */
	public static void exporterListeAchat(ListeAchat listeAchat) throws IOException{
		File fichier = new File("./data/listesAchats.csv");
		
		if(!fichier.exists()){
			ExportFichier.creerFichData("listesAchats");
		}
		
		FileWriter fw = new FileWriter(fichier, true);
		fw.write(listeAchat.toStringCSV());
		fw.close();
	}
	
	
	/**
	 * @param resExport Le resultat qu'on veut exporter dans resultats.csv
	 * (rajoute une ligne, n'efface rien)
	 */
	public static void exporterResultat(Resultat resExport) throws IOException{
		File fichier = new File("./data/resultats.csv");
		
		if(!fichier.exists()){
			ExportFichier.creerFichData("resultats");
		}
		
		FileWriter fw = new FileWriter(fichier, true);
		fw.write(resExport.toStringCSV() + "\n");
		fw.close();
	}
	
	/**
	 * @param testExport le test qu'on veut exporter vers testSimples.csv
	 * @throws IOException
	 */
	public static void exporterTestSimple(TestSimple testExport) throws IOException{
		File fichier = new File("./data/testsSimples.csv");
		
		if(!fichier.exists()){
			ExportFichier.creerFichData("testsSimples");
		}
		
		FileWriter fw = new FileWriter(fichier, true);
		fw.write(testExport.toStringCSV() + "\n");
		fw.close();
	}
}