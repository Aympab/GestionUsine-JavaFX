package matieres;
import outils.Prix;

/**
 * @author Astrid
 * 
 * Interface d�crivant le comportement d'une mati�re premi�re
 *
 */
public interface IMatierePremiere {
	
	/**
	 * @return prixAchat : le prix � laquelle la MatierePremiere s'ach�te au fournisseur
	 */
	public abstract Prix getPrixAchat();
	
}
