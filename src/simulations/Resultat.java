package simulations;

import java.util.UUID;

import listes.*;
import outils.Prix;
import stockage.StockElement;

/**
 * @author Aympab
 *
 *<br> Le résultat d'un {@link TestProd} sur <b>un stock</b> ! 
 *
 */
/**
 * @author Aympab
 *
 */
/**
 * @author Aympab
 *
 */
public class Resultat {
	
	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS       						   #
	//##############################################################################
	//##############################################################################

	private UUID idResultat;
	
	/**
	 * Le nom qu'on souhaite donner au résultat
	 */
	private String nomResultat;
	
	/**
	 * Est-ce que ce Test est possible sur un tel stock
	 * <br>possible au niveau de ce qu'il reste de matieres
	 */
	private boolean possible;
	//TODO : calculer possible if listeElemManquant.estVide();
	/**
	 * Une liste générée s'il manque des elements pour la production du test
	 * donc <br>contient au moins 1 element si possible = false </br>!
	 */
	private ListeAchat listeElemManquants;
	
	/**
	 * La liste des elements consommes pour arriver à ce résultat
	 */
	private ListeAchat   listeElemConsommes;
	
	/**
	 * La liste des elements produits par ce test
	 */
	private ListeVente listeElemProduits;
	
	/**
	 * Le stock sur lequel ce resultat se base, au début du test
	 */
	private StockElement stockEntree;
	
	/**
	 * Ce même stock en fin de test
	 */
	private StockElement stockSortie;
	
	/**
	 * L'argent généré par ce test (peut être négatif si c'est une perte)
	 */
	private Prix benefice;
	
	/**
	 * La duree totale mise pour arriver à ce Resultat
	 */
	private int dureeTotale;
	//##############################################################################
	//##############################################################################
	//									CONSTRUCTEURS   						   #
	//##############################################################################
	//##############################################################################

	/**
	 * @param nomResultat Le nom qu'on veut donner au résultat
	 * @param stockEntree Le stock sur lequel on va se baser pour ce résultat
	 */
	public Resultat(String nomResultat, StockElement stockEntree) {
		
		//TODO if(stockEntree == null) { ERROR }
		this.idResultat = UUID.randomUUID();
		this.nomResultat = nomResultat;
		this.stockEntree =  new StockElement(stockEntree);
		
		this.possible = false;
		this.listeElemManquants = new ListeAchat("listeElemManquants_" + this.nomResultat);
		this.listeElemConsommes = new ListeAchat("listeElemConsommes_" + this.nomResultat);
		this.listeElemProduits  = new ListeVente("listeElemProduits_"  + this.nomResultat);
		this.stockSortie        = new StockElement("stockSortie_" + this.nomResultat);
		this.benefice = new Prix(0.0);
	}
	
	
	public Resultat(String idRes, String nomRes, String possible, int dureeTotale, Prix benefice, ListeAchat listeConso, ListeAchat listeManquant, ListeVente listeProduit){
		this.idResultat = UUID.fromString(idRes);
		this.nomResultat = nomRes;
		
		if(possible.equals("true")){
			this.possible = true;
		}
		else{
			this.possible = false;
		}
		
		this.dureeTotale = dureeTotale;
		this.benefice = benefice;
		this.listeElemManquants = listeManquant;
		this.listeElemProduits = listeProduit;
		this.listeElemConsommes = listeConso;
	}
	
	public Resultat(String nomResultat, ListeAchat listeElemConsommes,
			ListeVente listeElemProduits, StockElement stockEntree, StockElement stockSortie, Prix benefice,
			int dureeTotale) {
		
		
//		this(UUID.randomUUID().toString(), nomResultat, );
		this(UUID.randomUUID().toString(), nomResultat, listeElemConsommes, listeElemProduits, stockEntree, stockSortie, benefice, dureeTotale);
		//		this.nomResultat = nomResultat;		
//		this.listeElemConsommes = listeElemConsommes;
//		this.listeElemProduits = listeElemProduits;
//		this.stockEntree = stockEntree;
//		this.stockSortie = stockSortie;
//		this.benefice = benefice;
//		this.dureeTotale = dureeTotale;
//		
//		this.calculerManquant();
//		this.possible = this.listeElemManquants.estVideQuantite();
	}

	
	public Resultat(String idResultat, String nomResultat,
			ListeAchat listeElemConsommes, ListeVente listeElemProduits, StockElement stockEntree,
			StockElement stockSortie, Prix benefice, int dureeTotale) {
//		this(nomResultat, listeElemConsommes, listeElemProduits, stockEntree, stockSortie, benefice, dureeTotale);
		
		this.nomResultat = nomResultat;		
		this.listeElemConsommes = listeElemConsommes;
		this.listeElemProduits = listeElemProduits;
		this.stockEntree = stockEntree;
		this.stockSortie = stockSortie;
		this.benefice = benefice;
		this.dureeTotale = dureeTotale;
		
		this.calculerManquant();
		this.possible = this.listeElemManquants.estVideQuantite();
		
		this.idResultat = UUID.fromString(idResultat);

	}



	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################
	public String getNomResultat() {
		return nomResultat;
	}

	public UUID getIdResultat(){
		return this.idResultat;
	}
	
	public boolean isPossible() {
		return possible;
	}

	public ListeAchat getListeElemManquants() {
		return listeElemManquants;
	}

	public ListeAchat getListeElemConsommes() {
		return listeElemConsommes;
	}

	public ListeVente getListeElemProduits() {
		return listeElemProduits;
	}

	public StockElement getStockEntree() {
		return stockEntree;
	}

	public StockElement getStockSortie() {
		return stockSortie;
	}

	public Prix getBenefice() {
		return benefice;
	}

	public int getDureeTotale() {
		return dureeTotale;
	}
	
	
	public String toString(){
		String str = this.nomResultat;
		
		str += "\nPossible : " + this.possible;
		str += "\nElements Manquants : "   + this.listeElemManquants.toStringCSV();
		str += "\nElements Consommés : "   + this.listeElemConsommes.toStringCSV();
		str += "\nElements Produits  : "   + this.listeElemProduits.toStringCSV();
		str += "\nStock de départ : "      + this.stockEntree.toStringCSV();
		str += "\nStock de fin : "         + this.stockSortie.toStringCSV();
		str += "\nBénéfice calculé : "     + this.benefice.toString();
		str += "\nDurée totale de prod : " + this.dureeTotale;
		
		return str;
	}
	
//idRes;nomRes;possible;dureeTotale;benefice;listeManquants (id, qte);listeConso(id, qte); listeProduits (id, qte)

	public String toStringCSV() {
		String strRet = this.idResultat.toString();
		strRet += ";";
		
		strRet += 		this.nomResultat;
		strRet += ";";
		
		strRet += 		this.possible;
		strRet += ";";
		
		strRet += 		this.dureeTotale;
		strRet += ";";
		
		strRet +=		Double.toString(this.benefice.getValeur());
		strRet += ";";
		
		strRet += 		this.listeElemManquants.toStringListeStockCSV();
		strRet += ";";
		
		strRet += 		this.listeElemConsommes.toStringListeStockCSV();
		strRet += ";";
		
		strRet += 		this.listeElemProduits .toStringListeStockCSV();	
		
		return strRet;
	}
	
	public String toStringPopUp(){
		
		int hours = this.dureeTotale / 60; //since both are ints, you get an int
		int minutes = this.dureeTotale % 60;
		
		
		
		String strRet = "Durée production : " + hours + " h " + minutes + " min.\n";
		
		strRet += "Bénéfice potentiel : " + this.benefice.toString() + "\n";
		
		if(possible){
			strRet += "Consommés : ";
			strRet += this.listeElemConsommes.toStringPopUp();
			strRet += "\nProduits : ";
			strRet += this.listeElemProduits.toStringPopUp();
		}
		else{
			strRet += "Manquants : " + this.listeElemManquants.toStringPopUp();
		}
		
		return strRet;
	}
	
	//##############################################################################
	//##############################################################################
	//									   METHODES 							   #
	//##############################################################################
	//##############################################################################
	
	
	public ListeAchat calculerManquant(){
		//TODO : HANDLE ERRORS SI Il est nullllll
		this.listeElemManquants = new ListeAchat(this.stockSortie.genererStockManquant()); 
		return this.listeElemManquants;
	}


}
