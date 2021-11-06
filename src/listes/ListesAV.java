package listes;
import matieres.*;
import outils.Prix;
import stockage.*;

import java.util.*;

/**
 * @author Aympab
 * 
 * <br> tout comme un ensemble d'elements mais ici ce qui nous intéresse c'est un prix et pas une UM
 *
 */
public abstract class ListesAV extends EnsembleElements{
	
	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS       						   #
	//##############################################################################
	//##############################################################################

	private UUID idListe;
	
	protected Prix coutTotal;
	
	
	//##############################################################################
	//##############################################################################
	//									CONSTRUCTEURS   						   #
	//##############################################################################
	//##############################################################################
	
	public ListesAV(String nomStock, HashMap<Element, Double> listeStock) {
		super(nomStock, listeStock);
		this.idListe = UUID.randomUUID();
	}

	public ListesAV(EnsembleElements ensembleElementsCopie) {
		super(ensembleElementsCopie);
		this.idListe = UUID.randomUUID();
	}
	
	public ListesAV(String nomStock){
		super(nomStock);
		this.idListe = UUID.randomUUID();
	}
	
	public ListesAV(StockElement stockCopie){
		super(stockCopie);
		this.idListe = UUID.randomUUID();
	}
	
	public ListesAV(String idListe, String nomListe, HashMap<Element, Double> listeStock){
		super(nomListe, listeStock);
		this.idListe = UUID.fromString(idListe);
	}
	
	
	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################
	
	public UUID getIdListe() {
		return idListe;
	}

	public Prix getCoutTotal(){
		if(this.coutTotal == null){
			this.calculerCoutTotal();
		}
		return this.coutTotal;
	}
	
	//##############################################################################
	//##############################################################################
	//									   METHODES 							   #
	//##############################################################################
	//##############################################################################
	
	/**
	 * Permet de calculer le coutTotal d'une Liste et de l'affecter à this.coutTotal
	 * Le couplage faible est par la
	 */
	public abstract void calculerCoutTotal();
	
	/**
	 * Renvoie le prix du nombre d'un element de la liste
	 * @param elem l'element qu'on recherche
	 * @return le prix des qte elements qu'il y a dans la liste
	 */
	public abstract Prix coutElementQte(Element elem);

	/**faireBuisness : acheter pour les achat et vendre pour listeVente
	 * @param stock Le stock  rajouter ou enlever ce qu'il y a dans la liste
	 * @return ce même stock
	 * 
	 * <br> ATTENTION /!\ ne gère pas la RAZ de this ! (Il faut manuellement vider la liste)
	 */
	public abstract StockElement faireBuisness(StockElement stock);
	
	//COUPLAGE FAIBLE AVEC LE PRIX des elements !!!! Le cout total se m a j quand le prix se maj
}
