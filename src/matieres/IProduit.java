package matieres;

import outils.Prix;

/**
 * Interface d�crivant le comportement d'un Element qu'on peut vendre, un produit
 * @author Astrid
 */
public interface IProduit {
	
	/**
	 * @return prixVente : le prix de vente d'un produit � un client
	 */
	public abstract Prix getPrixVente();

}
