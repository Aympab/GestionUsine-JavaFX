      package matieres;
import outils.*;

/**
 * Classe qui regroupe les Matières premières, elles sont des Element, avec un prix d'achat en plus
 * 
 * @author aympa
 */
public class MatierePremiere extends Element implements IMatierePremiere{
	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS       						   #
	//##############################################################################
	//##############################################################################

	/**
	 * Le prix à laquelle <b>une</b> matière première est achetée
	 */
	private Prix prixAchat;
	
	//##############################################################################
	//##############################################################################
	//									CONSTRUCTEURS   						   #
	//##############################################################################
	//##############################################################################
	
	/**
	 * Pour générer une MP en ayant <b>toutes</b> ses informations (venant d'un fichier en général)
	 * 
	 * @param libelle   @see {@link Element}
	 * @param id		@see {@link Element}
	 * @param uniteM	@see {@link UniteMesure}
	 * @param prixAchat @see {@link MatierePremiere}
	 */
	public MatierePremiere(String id, String libelle, UniteMesure uniteM, Prix prixAchat) {
		super(id, libelle, uniteM);
		this.prixAchat = new Prix(prixAchat);
	}
	
	/**
	 * Constructeur avec seulement le nom de la MP
	 * 
	 * @param libelle le nom, la description (ex : Gomme)
	 */
	public MatierePremiere(String libelle){
		this(libelle, new Prix(0.0));
	}
	
	
	/**
	 * Constructeur avec un nom, une unite de mesure, et un prixAchat
	 * 
	 * @param libelle   le nom, la description (ex : Gomme)
	 * @param uniteM    l'unité de mesure de la quantité de cette MP
	 * @param prixAchat le prix d'achat d'une MP
	 */
	public MatierePremiere(String libelle, UniteMesure uniteM, Prix prixAchat) {
		super(libelle, uniteM);
		this.prixAchat = new Prix(prixAchat);		
	}
	
	
	/**Constructeur avec un nom et une uniteMesure (sans prix)
	 * 
	 * @param libelle @see {@link MatierePremiere}
	 * @param uniteM @see {@link MatierePremiere}
	 */
	public MatierePremiere(String libelle, UniteMesure uniteM){
		super(libelle, uniteM);
	}
	
	
	/**Constructeur avec un nom et un prix
	 * 
	 * @param libelle 
	 * @param prixAchat @see {@link MatierePremiere}
	 */
	public MatierePremiere(String libelle, Prix prixAchat){
		super(libelle);
		this.prixAchat = new Prix(prixAchat);
	}

	
	public MatierePremiere(MatierePremiere matCopie){
		this(matCopie.getIdElement().toString(), matCopie.getLibelle(), matCopie.getUniteQte(), matCopie.getPrixAchat());
	}

	

	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################

	public String toString(){
		String str = super.toString();
		
		//Cause une erreur si prixAchat est null, d'où le if
		if(this.prixAchat != null){
			str += "\nPrix achat: " + this.prixAchat.toString();
		}else{
			str += "\nPrix achat: non renseigné";
		}
		return str;
	}
	
	
	public String toStringCSV(double quantite){
//		return this.getIdElement().toString() + ";" + this.getLibelle() + ";" + quantite + ";" + this.getUniteQte().getLibelle() + ";" + this.prixAchat.getValeur() + ";NA";
		return this.getIdElement().toString() + ";" + this.getLibelle() + ";" + quantite + ";" + this.getUniteQte().getCodeUnite().toString() + ";" + this.prixAchat.getValeur() + ";NA";

	}
	

	//##############################################################################
	//##############################################################################
	//									   METHODES 							   #
	//##############################################################################
	//##############################################################################
	
	@Override
	public Prix getPrixAchat() {
		if(this.prixAchat != null){
			return this.prixAchat;
		}else{
			return this.prixAchat; //TODO : Error Handler
		}
	}
	
}
