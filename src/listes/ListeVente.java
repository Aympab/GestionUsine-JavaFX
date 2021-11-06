package listes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import matieres.Element;
import matieres.Produit;
import outils.Prix;
import stockage.StockElement;

/**
 * @author aympa
 *
 *
 *<br>Regroupe un ensemble de Produit qu'on veut potentiellement vendre!! sera généré par les chaines de prod
 */
public class ListeVente extends ListesAV{

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
	 * On instancie en envoyant une HashMap<Produit, Double> au super !!!!!
	 */
	public ListeVente(String nomStock, HashMap<Element, Double> listeElement) {
		super(nomStock, listeElement);
	}
	
	/**
	 * @param listeAchat La liste qu'on veut envoyer au constructeur par copie
	 */
	public ListeVente(ListeVente listeVente){
		super(listeVente);
	}
	
	public ListeVente(String nomStock){
		super(nomStock);
	}
	
	public ListeVente(StockElement stockCopie){
		super(stockCopie);
	}
	
	public ListeVente(){
		this("ListeVente inconnue");
	}
	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################

	//##############################################################################
	//##############################################################################
	//									   METHODES 							   #
	//##############################################################################
	//##############################################################################
	
	//On va retirer d'un stock les elements qu'on a dans listeAchat 
	//Pas de gestion des quantités négatives, si l'element n'existe pas, on l'ajoute au 
	//stock pour retirer sa quantité!!!
	@Override
	public StockElement faireBuisness(StockElement stock){
		Iterator<Entry<Element, Double>> ite = this.getListeStock().entrySet().iterator();
		Entry<Element, Double> pair;		
		
		while(ite.hasNext()){
			pair = ite.next();
			
			//S'il existe déjà, on rajoute qte
			if (stock.verifierExistence(pair.getKey())) {
				stock.retirerQteElement(pair.getKey(), pair.getValue());
			}else{
				stock.ajouterNouvelElement(pair.getKey());
				stock.retirerQteElement(pair.getKey(), pair.getValue());
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
			Produit prod = new Produit(elemMP.getIdElement().toString(), elemMP.getLibelle(), elemMP.getUniteQte(), elemMP.getPrixVente()); //On cast l'element Produit
			double prix = prod.getPrixVente().getValeur();
			//On a la prix du prod, on doit juste multiplier par la qte
			prixRetour = new Prix(prix * this.getListeStock().get(elemMP));
		}else{
			prixRetour = new Prix(0.0);
			//TODO : Return une de nos erreurs ? En attendant on return un prix de 0
		}
		
		return prixRetour;
	}
}
