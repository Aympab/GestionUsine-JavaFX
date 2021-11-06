package matieres;
import java.util.UUID;

import outils.Prix;
import outils.UniteMesure;


/** 
 * Classe Element : classe abstraite qui englobe :
 * 		<br>- Produits
 * 		<br>- Matieres premières
 * 		<br>- Matieres premières vendables
 * 
 * @author Astrid
 */
public class Element {
	
	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS       						   #
	//##############################################################################
	//##############################################################################
	
	/**
	 *  L'identifiant général de l'element
	 */
	private UUID idElement ;
	
	
	/**
	 *  incrID permet d'instancier un élément avec un numéro auto
	 */
	//private static UUID incrID;
	
	
	/**
	 *  La description de l'element (son nom)
	 */
	private String libelle ;
	
	/**
	 *  L'unité de mesure avec laquelle on compte cet element 
	 *  @see UniteMesure
	 */
	private UniteMesure uniteQte;
	
	
	//##############################################################################
	//##############################################################################
	//									CONSTRUCTEURS   						   #
	//##############################################################################
	//##############################################################################
	
	//TODO : Possibilité d'instancier sans unité et on renseigne new UniteMesure("N/A") ou qqch comme ca
	
	/**
	 * Instancie un Element <b> AVEC UN UUID RANDOM </b>
	 * 
	 * @param libelle Le nom de ce nouvel element
	 */
	public Element(String libelle){
		this(UUID.randomUUID().toString(), libelle, new UniteMesure("ae2e7d44-0eb4-4f8d-a033-dd4fccf5edbe", "inconnue", "inc"));
	}
		
	
	/**
	 * Permet d'instancier un élément <b> en lui attribuant un UUID </b> utilisé pour instancier depuis CSV 
	 * <br> on utilise la méthode fromString de la calsse UUID (c.f. API java) 
	 * 
	 * @param libelle Le nom de cet élement
	 * @param id l'UUID de cet élément, sous forme de String, on le recupère du fichier CSV
	 */
	public Element(String id, String libelle){
		this.idElement = UUID.fromString(id);
		this.libelle   = libelle;
		this.uniteQte  = new UniteMesure("ae2e7d44-0eb4-4f8d-a033-dd4fccf5edbe", "inconnue", "inc");
	}
	
	/**
	 * <b>Permet d'instancier un Element en connsaissant son UUID (notamment pour l'import de donnée)</b>
	 * 
	 * @param libelle @see  {@link Element}
	 * @param id	  @see  {@link Element}
	 * @param uniteQte @see {@link Element}
	 */
	public Element(String id, String libelle,  UniteMesure uniteQte){
		this(id, libelle);
		this.uniteQte = new UniteMesure(uniteQte);
	}
	

	/**
	 * @param libelle   @see s
	 * @param uniteQte  @see {@link Element}
	 */
	public Element(String libelle, UniteMesure uniteQte) {
		this(libelle);
		this.uniteQte = new UniteMesure(uniteQte);
	}
	
	
	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################

	public UUID getIdElement () {
		return this.idElement ;
	}
	
	public String getLibelle () {
		return this.libelle ;
	}
	
	public void setlibelle (String libelle) {
		this.libelle = libelle ;
	}
	
	public UniteMesure getUniteQte(){
		return this.uniteQte;
	}
	
	//Vérifie qu'il existe une UM dans cette classe
	public String toString(){
		String str = "";
		
		if(this.uniteQte == null){
			str = "ID : " + this.idElement.toString() + " - " + this.libelle;
		}else{
			str = "ID : " + this.idElement.toString() + " - " + this.libelle + "\nUnité mesure : " + this.uniteQte.toString();
		} 
		
		return str;
	}
	
	/**
	 * @param quantite la quantite de Elemen que l'on a 
	 * @return une ligne qu'on peut insérer directement dans le fichier CSV
	 */
	public String toStringCSV(double quantite){
//		return this.getIdElement().toString() + ";" + this.getLibelle() + ";" + quantite + ";" + this.getUniteQte().getLibelle() + ";NA" + ";NA";
		return this.getIdElement().toString() + ";" + this.getLibelle() + ";" + quantite + ";" + this.getUniteQte().getCodeUnite().toString() + ";NA" + ";NA";

	}
	
	
	//##############################################################################
	//##############################################################################
	//									   METHODES 							   #
	//##############################################################################
	//##############################################################################
	
	public Prix getPrixAchat(){
		return new Prix(0.0);
	}
	
	public Prix getPrixVente(){
		return new Prix(0.0);
	}
	
}
