package listes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import matieres.*;
import outils.Prix;
import stockage.StockElement;

/**
 * @author aympa
 *
 *
 *<br>Regroupe un ensemble de MatierePremiere qu'on veut potentiellement acheter, sera généré par les chaines de prod
 */
public class ListeAchat extends ListesAV{
	
	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS       						   #
	//##############################################################################
	//##############################################################################

	//##############################################################################
	//##############################################################################
	//									CONSTRUCTEURS   						   #
	//##############################################################################
	//##############################################################################
	
	/**
	 * On instancie en envoyant une HashMap<MatierePremiere, Double> au super !!!!! A voir avec le prof
	 */
	public ListeAchat(String nomStock, HashMap<Element, Double> listeElement) {
		super(nomStock, listeElement);
	}
	
	/**
	 * @param listeAchat La liste qu'on veut envoyer au constructeur par copie
	 */
	public ListeAchat(ListeAchat listeAchat){
		super(listeAchat);
	}
	
	public ListeAchat(String nomStock){
		super(nomStock);
	}
	
	public ListeAchat(StockElement stockCopie){
		super(stockCopie);
	}
	
	public ListeAchat(){
		this("ListeAchat inconnue");
	}

	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################

	public String toStringCSV(){
		String strRet=this.getIdListe().toString() + ";" + this.getNomStock()+";";
		
		Iterator<Entry<Element, Double>> ite = this.getListeStock().entrySet().iterator();
		Entry<Element, Double> pair;

		
		while(ite.hasNext()){
			pair = ite.next();
			
			strRet += "(";
			strRet += pair.getKey().getIdElement().toString() + ",";
			strRet += pair.getValue().toString();
			strRet += "),";
		}
			strRet = strRet.substring(0, strRet.length()-1); //Pour enlever la , a la fin
		
			strRet += "\n";
			
		return strRet;
	}
	
	

	//##############################################################################
	//##############################################################################
	//									   METHODES 							   #
	//##############################################################################
	//##############################################################################
	
	@Override
	public StockElement faireBuisness(StockElement stock){
		Iterator<Entry<Element, Double>> ite = this.getListeStock().entrySet().iterator();
		Entry<Element, Double> pair;		
		
		while(ite.hasNext()){
			pair = ite.next();
			
			//S'il existe déjà, on rajoute qte
			if (stock.verifierExistence(pair.getKey())) {
				stock.ajouterQteElement(pair.getKey(), pair.getValue());
			}else{
				stock.ajouterNouvelElement(pair.getKey());
				stock.ajouterQteElement(pair.getKey(), pair.getValue());
			}
		}
		//TODO : faire la partie avec coutTotal (on enleve le cout de qqPart)
		return stock;
	}
	
	
	@Override //TODO : Mettre la visibilité protected et s'arranger avec cout total?
	public void calculerCoutTotal() {
		//On va itérer sur listeElement et on va apel coutElementQte pour chaque ité
		Iterator<Entry<Element, Double>> ite = this.getListeStock().entrySet().iterator();
		
		Entry<Element, Double> pair;		
		
		Prix somme = new Prix(0.0);
		
		while(ite.hasNext()){
			pair = ite.next(); //On récupère l'entrée de l'itérator
			somme.ajouterPrix(this.coutElementQte(pair.getKey()));
		}
		
		this.coutTotal = somme;
	}
	
	@Override
	public Prix coutElementQte(Element elemMP) {
		Prix prixRetour;
		
		if (this.verifierExistence(elemMP)) {
			MatierePremiere mat = new MatierePremiere(elemMP.getIdElement().toString(), elemMP.getLibelle(), elemMP.getUniteQte(), elemMP.getPrixAchat()); //On cast l'element en MP 
			double prix = mat.getPrixAchat().getValeur();
			//On a la prix de la matiere, on doit juste multiplier par la qte
			prixRetour = new Prix(prix * this.getListeStock().get(elemMP));
		}else{
			prixRetour = new Prix(0.0);
			//TODO : Return une de nos erreurs ? En attendant on return un prix de 0
		}
		
		return prixRetour;
	}

}
