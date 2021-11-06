package acteurs.internes;

import java.io.IOException;
import java.util.ArrayList;

import acteurs.externes.FournisseurSimple;
import gestionFichier.ExportFichier;
import gestionFichier.ImportFichier;
import listes.ListeAchat;
import machines.ChaineProductionSimple;
import matieres.Element;
import simulations.TestSimple;
import stockage.EnsembleElements;
import stockage.StockElement;

/**
 * @author Aympab
 * 
 * <br>Une usine de production simple, qui ne contient qu'un seul stock, pour commencer.
 *
 */
public class UsineSimple {
	
	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS       						   #
	//##############################################################################
	//##############################################################################

	/**
	 * Le nom de l'usine, sera utilisé pour créer le fichier /data/nomUsine/elements.csv et chaines
	 */
	private String nomUsine;
	
	/**
	 * L'unique stock de l'usine, car c'est une UsineSimple
	 */
	private StockElement stockPrincipal;
	
	
	/**
	 * La liste des chaines de Production que contiennent l'usine
	 */
	private ArrayList<ChaineProductionSimple> listeChaines;
	
	//##############################################################################
	//##############################################################################
	//									CONSTRUCTEURS   						   #
	//##############################################################################
	//##############################################################################
	
	public UsineSimple(){
		this("UsineIconnue");
	}
	
	public UsineSimple(String nomUsine){
		this(nomUsine, new StockElement("StockElement_"+nomUsine));
	}
	
	public UsineSimple(String nomUsine, StockElement stockPrincipal){
		this(nomUsine, stockPrincipal, new ArrayList<>());
	}
	
	public UsineSimple(String nomUsine, StockElement stockPrincipal, ArrayList<ChaineProductionSimple> listeChaines){
		this.nomUsine = nomUsine;
		this.stockPrincipal = new StockElement(stockPrincipal);
		this.listeChaines = new ArrayList<>(listeChaines);
	}
	
	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################
	
	public String getNomUsine(){
		return this.nomUsine;
	}
	
	public StockElement getStockPrincipal(){
		return this.stockPrincipal;
	}
	
	public ArrayList<ChaineProductionSimple> getListeChaines(){
		return this.listeChaines;
	}
	
	public String toString(){
		return this.nomUsine + "\nStock : " + this.stockPrincipal.toString() + "\nNombre de chaines : " + this.listeChaines.size();
	}

	//##############################################################################
	//##############################################################################
	//									   METHODES 							   #
	//##############################################################################
	//##############################################################################

	/**
	 * Permet de charger le stock CSV vers this.StockPrincipal
	 */
	public void chargerStock() throws IOException{
		this.stockPrincipal = new StockElement(ImportFichier.importerElementsCSV());
	}
	
	/**
	 * Permet de charger les chaines CSV vers this.listeChaines
	 * @throws Exception 
	 */
	public void chargerChaines() throws Exception{
		listeChaines = ImportFichier.importerChainesCSV(this.stockPrincipal);
	}
	
	/**
	 * @see {@link EnsembleElements}
	 */
	public void ajouterNouvelElementCSV(Element elemAjout) throws IOException{
		this.stockPrincipal.ajouterNouvelElement(elemAjout);
		ExportFichier.remplacerContenuElement(this.stockPrincipal);
	}
	
	/**
	 * @see {@link EnsembleElements}
	 */
	public void ajouterNouvelQteElementCSV(Element elemAjout, double qteAjout) throws IOException{
		this.stockPrincipal.ajouterQteElement(elemAjout, qteAjout);
		ExportFichier.remplacerContenuElement(this.stockPrincipal);
	}
	
	
	/** TODO : On peut optimiser ça, un test contient une chaîne
	 * @param numeroChaine Le numéro de la chaine qu'on veut faire produire
	 * @param testProd     Le test de production qu'on veut appliquer à cette chaine
	 */
	public void produireEtSauvegarder(int numeroChaine, TestSimple testProd) throws IOException{
		this.stockPrincipal = this.listeChaines.get(numeroChaine).produire(testProd); 
		//Penser à envoyer avec le bon stock, donc le bon test
		ExportFichier.remplacerContenuElement(this.stockPrincipal);
	}
	
	
	/**Permet d'aller se réapprovisionner de toute la listeA auprès d'un fournisseur
	 * @param fourn  Le fournisseur auprès duquel se réappro
	 * @param listeA La liste des MP qu'on veut acheter au fournisseur
	 */
	public void seReapprovisionner(FournisseurSimple fourn, ListeAchat listeA){
		fourn.reapprovisionnement(this.stockPrincipal, listeA);
	}
}
