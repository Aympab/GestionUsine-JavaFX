package stockage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import listes.ListeAchat;
import matieres.*;
import outils.*;

/**
 * @author Aympab
 * 
 * <br>Repr�sente le stockage d'un elements, on rajoute en plus une unit� de mesure
 *
 */
public class StockElement extends EnsembleElements{
	
	
	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS       						   #
	//##############################################################################
	//##############################################################################	
	
	/**
	 * Une unit� de mesure par Stock
	 */
	private UniteMesure uniteStock;
	
	//##############################################################################
	//##############################################################################
	//									CONSTRUCTEURS   						   #
	//##############################################################################
	//##############################################################################
	
	public StockElement(String nomStock, UniteMesure uniteStock, HashMap<Element, Double> listeStock){
		super(nomStock, listeStock);
		this.uniteStock = uniteStock;
	}	
	
	/**
	 * Instancie un stock sans connaitre l'unite de mesure, on dis donc qu'il est mutli unites
	 * @param nomStock le nom du stock
	 */
	public StockElement(String nomStock){
		this(nomStock, new UniteMesure("Plusieurs unit�s", "multi-unites"));
	}
	
	public StockElement(String nomStock, UniteMesure uniteStock){
		this(nomStock, uniteStock, new HashMap<>());
	}
	
	
	public StockElement(StockElement stockCopie){
		this(stockCopie.getNomStock(), stockCopie.getUniteStock(), stockCopie.getListeStock());
	}
	
	public StockElement(EnsembleElements stockCopie){
		this(stockCopie.getNomStock(),new UniteMesure("Plusieurs unit�s", "multi-unites"), stockCopie.getListeStock());
	}
	
	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################	
	
	public UniteMesure getUniteStock(){
		if(this.uniteStock != null){
			return this.uniteStock;
		}else{
			return null; //TODO : Handle errors
		}
	}
	
	public String toString(){
		return super.toString() + "\nUnit� de mesure du stock : " + this.uniteStock.toString();
	}

	
	
	
	//##############################################################################
	//##############################################################################
	//									   METHODES 							   #
	//##############################################################################
	//##############################################################################
	
	/**
	 * Cr�er une listeAchat avec les quantit�s qu'il faut pour arriver � z�ro o� les quantit�s sont n�gatives
	 */
	public ListeAchat genererStockManquant() {
		Iterator<Entry<Element, Double>> ite = this.getListeStock().entrySet().iterator();
		//On fait un Iterator sur notre listeElement	
		
		ListeAchat listeManquant = new ListeAchat("ListeManquants_" + this.getNomStock());
		
		
		while(ite.hasNext()){
			Entry<Element, Double> pair = ite.next(); //On r�cup�re l'entr�e de l'it�rator
			//Si la quantit� est en dessous de 0
			if(pair.getValue().doubleValue() < 0){
				listeManquant.ajouterNouvelElement(pair.getKey());
				listeManquant.ajouterQteElement(pair.getKey(), Math.abs(pair.getValue()));
			}
		}
		return listeManquant;
	}

}
