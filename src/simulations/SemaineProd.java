package simulations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import gestionFichier.ImportFichier;
import listes.ListeAchat;
import listes.ListeVente;
import machines.ChaineProductionSimple;
import outils.Prix;
import stockage.StockElement;

/**
 * @author Aympab
 *
 *Correspond à une semaine de production, telles qu'elles le sont
 *dans PlanificationSemaine.fxml
 */
public class SemaineProd {
	
	/**
	 * Le numéro de la semaine de production, S0, S1, S2, etc...
	 */
	private Integer numeroSemaine;
	
	/**
	 * La liste des chaines qu'on veut faire produire pour cette semaine,
	 * avec leur testSimple correspondant
	 * <br> idée d'amélioration : faire des TestComplexes qui prennent plusieurs chaines.
	 */
	private HashMap<ChaineProductionSimple, TestSimple> hashMapChainesTests;
	
	private ArrayList<ChaineProductionSimple> ordreAjout;
	
	/**
	 * Un resultat global de la semaine, qui se met à jour lorsqu'on ajoute
	 * un element dans la semaine
	 */
	private Resultat resultatAJour;
	
	
	//CONSTRUCTORS
	/**
	 * @param numeroSemaine numéro de la semaine par rapport au gridPane
	 */
	public SemaineProd(Integer numeroSemaine){
		this.numeroSemaine = numeroSemaine;
		this.hashMapChainesTests = new HashMap<>();
		this.ordreAjout = new ArrayList<>();
	}
	
	
	
	//M2THODES
	public void initialiserResultat() throws IOException{
		StockElement stockUsine = (StockElement) ImportFichier.importerElementsCSV();
		this.resultatAJour = new Resultat("RES_S" + this.numeroSemaine.toString(), stockUsine);
	}
	
	/**
	 * Calcule le Resultat en prenant compte toutes les chaines de production
	 * @throws IOException 
	 */
	public void calculerResultatGlobal(StockElement stockUsine) throws IOException{
//		StockElement stockUsine;
//		stockUsine = (StockElement) ImportFichier.importerElementsCSV();


		
		Iterator<Entry<ChaineProductionSimple, TestSimple>> ite = this.hashMapChainesTests.entrySet().iterator();
		Entry<ChaineProductionSimple, TestSimple> pair;
		
		
		//possible, dureeTotale, benefice, listeConso, listeManquant, listeProduit
		boolean possible         = false;
		Integer dureeTotale      = 0;
		Prix    benefice         = new Prix(0);
		ListeAchat listeConso    = new ListeAchat();
		ListeAchat listeManquant = new ListeAchat();
		ListeVente listeProduit  = new ListeVente();
		
		while(ite.hasNext()){
			pair = ite.next();
			
			//Recalculer le resultat de chaque chaine
			pair.getValue().calculerResultat(stockUsine, pair.getKey().getMethodeProdChaine().getNivActivation());
			
			dureeTotale   += pair.getValue().getResultatProd().getDureeTotale();
			benefice      .ajouterPrix(pair.getValue().getResultatProd().getBenefice());
			listeConso    .ajouterEnsemble(pair.getValue().getResultatProd().getListeElemConsommes());
			listeManquant.ajouterEnsemble(pair.getValue().getResultatProd().getListeElemManquants());
			listeProduit .ajouterEnsemble(pair.getValue().getResultatProd().getListeElemProduits());			
		}		
		
		if(listeManquant.estVideQuantite()){
			possible = true;
		}
		
		if(this.resultatAJour == null){
			this.initialiserResultat();
		}
		
		this.resultatAJour = new Resultat(this.resultatAJour.getIdResultat().toString(), this.resultatAJour.getNomResultat(), Boolean.toString(possible), dureeTotale.intValue(), benefice, listeConso, listeManquant, listeProduit);

	}
	
	
	/**
	 * @param chaineAjout La chaîne qu'on veut ajouter à la semaine
	 * Ajoute une chaîne et recalcule le résultat global
	 * @throws IOException 
	 */
	public void ajouterChaine(ChaineProductionSimple chaineAjout) {
		ChaineProductionSimple chaine = new ChaineProductionSimple(chaineAjout);
		TestSimple testAjout = new TestSimple(chaine);		
		
		this.hashMapChainesTests.put(chaine, testAjout);
		this.ordreAjout.add(chaine);
		
		//FIXME : Erreur ici le try catch casse tout
		//Recalcul du resultat global
//		try {
//			this.calculerResultatGlobal();
//		} catch (IOException e) {
//			System.out.println("Pb lors du calcul du résultat global");
//			e.printStackTrace();
//		}
		
//		System.out.println("Ajout chaine");
	}



	public Integer getNumeroSemaine() {
		return numeroSemaine;
	}



	public HashMap<ChaineProductionSimple, TestSimple> getHashMapChainesTests() {
		return hashMapChainesTests;
	}



	public Resultat getResultatAJour() {
		return resultatAJour;
	}
	
	
	public String toString(){
		return this.numeroSemaine + " : nb chaines : " + this.hashMapChainesTests.size();
	}
	
	public ArrayList<ChaineProductionSimple> getOrdreAjout(){
		return this.ordreAjout;
	}
	
	public void retirerChaine(int indAjoutChaine){
		this.hashMapChainesTests.remove(this.ordreAjout.get(indAjoutChaine));
		this.ordreAjout.remove(indAjoutChaine);
		
		
	}
	
	public void viderChaines(){
		this.hashMapChainesTests.clear();
		this.ordreAjout.clear();
	}
	
	
}
