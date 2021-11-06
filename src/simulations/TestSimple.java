package simulations;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import gestionFichier.ImportFichier;
import listes.ListeAchat;
import listes.ListeVente;
import machines.ChaineProductionSimple;
import outils.Prix;
import stockage.StockElement;

/**
 * Un TestSimple est un TestProd qui a possède une date et une ChaineProductionSimple 
 * le but étant de simuler la production d'une chaine et de sa/ses méthodes de prod
 * 
 * @author Aympab
 *
 */
public class TestSimple extends TestProd{
	
	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS       						   #
	//##############################################################################
	//##############################################################################

	/**
	 * La date à laquelle le test à été effectué
	 */
	private Date dateTest;
	
	/**
	 * La chaine de production utilisée pour ce Test
	 */
	private ChaineProductionSimple chaineTest;
	
	//##############################################################################
	//##############################################################################
	//									CONSTRUCTEURS   						   #
	//##############################################################################
	//##############################################################################

	public TestSimple(){
		this(new Date(), new ChaineProductionSimple());
	}
	
	public TestSimple(String libelle, Date dateTest, ChaineProductionSimple chaineTest){
		this(UUID.randomUUID().toString(), libelle, dateTest, chaineTest);
	}
	
	public TestSimple(Date dateTest, ChaineProductionSimple chaineTest){
		this(UUID.randomUUID().toString(), "TestSimple inconnu", dateTest, chaineTest);
	}
	
	public TestSimple(String idTest, String libelle, Date dateTest, ChaineProductionSimple chaineTest){
		super(idTest, libelle);
		this.dateTest = dateTest;
		this.chaineTest = new ChaineProductionSimple(chaineTest.getIdChaine().toString(), chaineTest.getLibelle(), chaineTest.getMethodeProdChaine());
	}
	
	public TestSimple(ChaineProductionSimple chaineTest){
		this(new Date(),  chaineTest);
	}
	
	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################

	public String toString(){
		return "TestSimple : " + this.getIdTest().toString() + " : " + this.getNomTest() + "\nDate :" + this.dateTest.toString() + "\nChaineProdSimple : " + this.chaineTest.toString() + "\nResultat :" + this.getResultatProd();
	}
	
	public String toStringCSV(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		String strDate = dateFormat.format(this.dateTest);
		
		String strRes;
		
		if(this.resultatProd == null){
			strRes = "null";
		}
		else{
			strRes = this.resultatProd.getIdResultat().toString();
		}
		
		return this.getIdTest().toString() + ";" + this.getNomTest() + ";" + this.getChaineTest().getIdChaine().toString() + ";" + strDate + ";" + strRes;
	}
	
	public String toStringTooltip() throws IOException{
		String str = this.getNomTest();
		str += "\n" + this.getChaineTest().getLibelle();
		
		if(this.getResultatProd() != null){
			str += "\nPossible : " + this.getResultatProd().isPossible();
		}
		else{
			str += "\nRésultat non testé.";
		}
		
		return str;
	}
	
	//##############################################################################
	//##############################################################################
	//									   METHODES 							   #
	//##############################################################################
	//##############################################################################

	public Date getDateTest() {
		return dateTest;
	}

	public ChaineProductionSimple getChaineTest() {
		return chaineTest;
	}

	
	/**
	 * @param IDRes L'IDentifiant du résultat qu'on veut affecter au test.
	 * @return
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public Resultat affecterResultat(String IDRes) throws NumberFormatException, IOException{
		ArrayList<Resultat> listeRes = ImportFichier.importerArrayResultat();
		Iterator<Resultat> ite = listeRes.iterator();
		
		while(ite.hasNext()){
			Resultat currRes = ite.next();
			
			if(currRes.getIdResultat().toString().equals(IDRes)){
				this.resultatProd = currRes;
				break;
			}
		}
		
		return this.resultatProd;
	}
	
	public Resultat calculerResultat(StockElement stockSimulation){
		if (this.getResultatProd() == null) {
			//TODO : error Handler, le resultat n'est pas initialisé
			this.initialiserResultat(stockSimulation);
		}
		
		//CalculerMateriauxUtilises = listeMat In avec QTE * nivActivation !
		ListeAchat listeConsomes = new ListeAchat("ListeConsomme_Resultat_" + this.getNomTest());//ListeAchat((ListeAchat)this.chaineTest.getMethodeProdChaine().getManuelProd().getMatIn());
		//CalculerMatsProduits = listeMat Out
		ListeVente listeProduits = new ListeVente("ListeProduits_Resultat_" + this.getNomTest());//ListeVente((ListeVente)this.chaineTest.getMethodeProdChaine().getManuelProd().getMatOut());
		
		for(int i = 0; i < this.chaineTest.getMethodeProdChaine().getNivActivation(); i++){
			listeConsomes.ajouterEnsemble(this.chaineTest.getMethodeProdChaine().getManuelProd().getMatIn());
			listeProduits.ajouterEnsemble(this.chaineTest.getMethodeProdChaine().getManuelProd().getMatOut());
		}
		
		//duree totale : duree x nivActivation
		int dureeTotale = this.chaineTest.getMethodeProdChaine().getNivActivation() * this.chaineTest.getMethodeProdChaine().getManuelProd().getDuree();
		
		//benefice : cout des mats sortant - cout des mats entrant nivActivation fois
		Prix coutMatEntrant = new Prix(this.chaineTest.getMethodeProdChaine().getManuelProd().getMatIn().getCoutTotal().getValeur());
		Prix coutMatSortant = new Prix(this.chaineTest.getMethodeProdChaine().getManuelProd().getMatOut().getCoutTotal().getValeur());
		Prix benefice = new Prix(coutMatSortant.comparerPrix(coutMatEntrant) * (double)this.chaineTest.getMethodeProdChaine().getNivActivation());
		
		//Renvoyer le stock final = stock - consommes + produits
		StockElement stockFinSimulation = new StockElement(stockSimulation);
		
		//NivActivatoin fois
		//for(int i = 0; i < this.chaineTest.getMethodeProdChaine().getNivActivation(); i++){
			stockFinSimulation.retirerEnsemble(listeConsomes);
			stockFinSimulation.ajouterEnsemble(listeProduits);
		//}
		
		this.resultatProd = new Resultat("Resultat_" +this.getNomTest(), listeConsomes, listeProduits, stockSimulation, stockFinSimulation, benefice, dureeTotale);
		
		return this.resultatProd;
		//Manquants : quand on a du négatif dans le stock final
		//this.getResultatProd().calculerManquant();
		//se calculer seul
		
		
		//this.chaineTest.getMethodeProdChaine().consommerProduire(stockSimulation);
		
	}
	
	public Resultat calculerResultat(StockElement stockSimulation, int nivActivation){
		if (this.getResultatProd() == null) {
			//TODO : error Handler, le resultat n'est pas initialisé
			this.initialiserResultat(stockSimulation);
		}
		
		//CalculerMateriauxUtilises = listeMat In avec QTE * nivActivation !
		ListeAchat listeConsomes = new ListeAchat("ListeConsomme_Resultat_" + this.getNomTest());//ListeAchat((ListeAchat)this.chaineTest.getMethodeProdChaine().getManuelProd().getMatIn());
		//CalculerMatsProduits = listeMat Out
		ListeVente listeProduits = new ListeVente("ListeProduits_Resultat_" + this.getNomTest());//ListeVente((ListeVente)this.chaineTest.getMethodeProdChaine().getManuelProd().getMatOut());
		
		for(int i = 0; i < nivActivation; i++){
			listeConsomes.ajouterEnsemble(this.chaineTest.getMethodeProdChaine().getManuelProd().getMatIn());
			listeProduits.ajouterEnsemble(this.chaineTest.getMethodeProdChaine().getManuelProd().getMatOut());
		}
		
		//duree totale : duree x nivActivation
		int dureeTotale = nivActivation * this.chaineTest.getMethodeProdChaine().getManuelProd().getDuree();

		//benefice : cout des mats sortant - cout des mats entrant nivActivation fois
		Prix coutMatEntrant = new Prix(this.chaineTest.getMethodeProdChaine().getManuelProd().getMatIn().getCoutTotal().getValeur()*(double)nivActivation);
		Prix coutMatSortant = new Prix(this.chaineTest.getMethodeProdChaine().getManuelProd().getMatOut().getCoutTotal().getValeur()*(double)nivActivation);
		Prix benefice = new Prix(coutMatSortant.comparerPrix(coutMatEntrant));
		
		//Renvoyer le stock final = stock - consommes + produits
		StockElement stockFinSimulation = new StockElement(stockSimulation);
		
		//NivActivatoin fois
		//for(int i = 0; i < this.chaineTest.getMethodeProdChaine().getNivActivation(); i++){
			stockFinSimulation.retirerEnsemble(listeConsomes);
			stockFinSimulation.ajouterEnsemble(listeProduits);
		//}
		
		this.resultatProd = new Resultat("Resultat_" +this.getNomTest(), listeConsomes, listeProduits, stockSimulation, stockFinSimulation, benefice, dureeTotale);
		
		return this.resultatProd;
		//Manquants : quand on a du négatif dans le stock final
		//this.getResultatProd().calculerManquant();
		//se calculer seul
		
		
		//this.chaineTest.getMethodeProdChaine().consommerProduire(stockSimulation);
		
	}
	
}
