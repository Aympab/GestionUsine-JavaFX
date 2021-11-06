package matieres;
import outils.Prix;

/**
 * @author Astrid
 * 
 * Interface décrivant le comportement d'une matière première
 *
 */
public interface IMatierePremiere {
	
	/**
	 * @return prixAchat : le prix à laquelle la MatierePremiere s'achète au fournisseur
	 */
	public abstract Prix getPrixAchat();
	
}
