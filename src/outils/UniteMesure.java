package outils;

import java.util.UUID;

/**
 *	Classe UniteMesure qui va regrouper toutes les UM, tels que Litres, Kg, Prix, etc..
 *
 * @author Astrid
 */

//TODO : Ajouter excpetions dans les constructeurs par exemple
public class UniteMesure {
	
	
	//##############################################################################
	//##############################################################################
	//								ATTRIBUTS       							   #
	//##############################################################################
	//##############################################################################
	/**
	 *  Le nom <b>complet</b> de l'unité de mesure (exemple "Litres")
	 */
	private String libelle;
	
	/**
	 * L'<b>abbréviation</b> de l'unité de mesure (exemple "L"),
	 * sera utilisé pour l'affichage des quantités (xxx L)
	 * Un seul char n'est pas suffisant si jamais on a par exemple Kg
	 */
	private String abrev;
	
	
	/**
	 * Le code identifiant l'unité dans le fichier CSV
	 */
	private UUID codeUnite;
	
	//##############################################################################
	//##############################################################################
	//								CONSTRUCTEURS   							   #
	//##############################################################################
	//##############################################################################	
	
	/**
	 * @param libelle Le nom complet de l'UM
	 * @param abrev   L'abreviation de l'UM
	 */
	public UniteMesure(String libelle, String abrev) {
		this(UUID.randomUUID().toString(), libelle, abrev);
	} 
	
	
	/**
	 * @param libelle
	 * @param abrev
	 * @param codeUnite Le code (UUID) de l'unité, en forme de String
	 */
	public UniteMesure(String codeUnite, String libelle, String abrev){
		this.libelle   = libelle;
		this.abrev     = abrev;
		this.codeUnite = UUID.fromString(codeUnite);
	}

	/**
	 * Cette méthode permet d'instancier une UM sans renseigner de libelle complet
	 * 
	 * @param abrev l'abreviation ET le libelle de l'UM
	 */
	public UniteMesure(String abrev){
		this(abrev, abrev);
	}
	
	
	/**
	 * Constructeur par copie
	 *  
	 * @param um UniteMesure a copier
	 */
	public UniteMesure(UniteMesure um){
		this( um.getCodeUnite().toString(), um.getLibelle(), um.getAbrev());
	}
	
	
	//##############################################################################
	//##############################################################################
	//								METHODES DE BASE							   #
	//##############################################################################
	//##############################################################################
	
	//On vérifie juste si libelle et abrev sont identiques,
	//S'ils le sont, on ne sort qu'un seul des deux 
	public String toString(){
		String str = "";
		
		if(this.libelle.equalsIgnoreCase(this.abrev)){
			str = this.abrev;
		}else{
			str = this.libelle + " (" + this.abrev + ")";
		}
		
		return str;
	}
	
	public String toStringComplex(){
		String str = "";
		
		str += "CodeUnite : " + this.codeUnite.toString();
		str += "\nLibelle : " + this.libelle;
		str += "\nAbrev   : " + this.abrev;
		
		return str;
	}
	
	/**
	 * @return la ligne CSV correspondant à this unité de mesure
	 */
	public String toStringCSV(){
		String str = this.codeUnite.toString();
		str += ";";
		str += this.libelle.toString();
		str += ";";
		str += this.abrev.toString();
		str += "\n";
		
		return str;
	}

	public String getLibelle() {
		return this.libelle;
	}


	public String getAbrev() {
		return this.abrev;
	}
	
	public UUID getCodeUnite(){
		return this.codeUnite;
	}
	
	
	
	
	//##############################################################################
	//##############################################################################
	//								METHODES        							   #
	//##############################################################################
	//##############################################################################
		
	
}
