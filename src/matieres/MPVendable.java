package matieres;

import outils.Prix;
import outils.UniteMesure;

/**
 * Une Matiere Premire Vendable est une matière qu'on achete à un certain prix aux fournisseurs,
 * et qu'on revend à un autre prix aux clients, elle posède donc deux Prix.
 * 
 * @author Astrid
 */
public class MPVendable extends Element implements IProduit, IMatierePremiere{
	
	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS       						   #
	//##############################################################################
	//##############################################################################

	private Prix prixVente;
	private Prix prixAchat;

	//##############################################################################
	//##############################################################################
	//									CONSTRUCTEURS   						   #
	//##############################################################################
	//##############################################################################
	
	
	
	/**
	 * Pour générer une MPVendable en ayant <b>toutes</b> ses informations (venant d'un fichier en général)
	 * 
	 * @param libelle   @see {@link Element}
	 * @param id		@see {@link Element}
	 * @param uniteM	@see {@link UniteMesure}
	 * @param prixAchat	@see {@link MatierePremiere}
	 * @param prixVente @see {@link Produit}
	 */
	public MPVendable(String id, String libelle, UniteMesure uniteM, Prix prixAchat, Prix prixVente) {
		super(id, libelle, uniteM);
		affecterPrix(prixAchat, prixVente);
	}

	/**
	 * Constructeur avec <b>seulement le nom</b> de la MPVendable
	 * 
	 * @param libelle : le nom, la description (ex : Gomme)
	 */
	public MPVendable(String libelle){
		super(libelle);
	}
	
	
	/**
	 * Constructeur avec un nom, une unite de mesure, un prixVente et un prixAchat (sans UUID)
	 * 
	 * @param libelle   le nom, la description (ex : Gomme)
	 * @param uniteM    l'unité de mesure de la quantité de cette MPVendable
	 * @param prixVente le prix de vente de cette MPVendable
	 */
	public MPVendable(String libelle, UniteMesure uniteM, Prix prixAchat, Prix prixVente) {
		super(libelle, uniteM);
		affecterPrix(prixAchat, prixVente);
	}
	
	
	/**Constructeur avec un nom et une uniteMesure (sans prix)
	 * 
	 * @param libelle @see {@link MPVendable}
	 * @param uniteM @see {@link MPVendable}
	 */
	public MPVendable(String libelle, UniteMesure uniteM){
		super(libelle, uniteM);
	}
	
	
	/**Constructeur avec un nom et les prix d'achat et de vente (sans Unité)
	 * 
	 * @param libelle 
	 * @param prixAchat @see {@link MPVendable}
	 * @param prixVente @see {@link MPVendable}
	 */
	public MPVendable(String libelle, Prix prixAchat, Prix prixVente){
		super(libelle);
		affecterPrix(prixAchat, prixVente);
	}
	
	
	//Sert juste à ne pas écrire deux fois ces mêmes lignes, on s'en sert dans les constructeurs
	private void affecterPrix(Prix prixAchat, Prix prixVente){
		this.prixAchat = new Prix(prixAchat);
		this.prixVente = new Prix(prixVente);
	}

	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################

	public String toString(){
		String str = super.toString();
		
		if(this.prixAchat != null){
			str += "\nPrix ACHAT: " + this.prixAchat.toString();
		}
		if(this.prixVente != null){
			str += "\nPrix VENTE: " + this.prixVente.toString();
		}
		
		return str;
	}
	
	public String toStringCSV(double quantite){
//		return this.getIdElement().toString() + ";" + this.getLibelle() + ";" + quantite + ";" + this.getUniteQte().getLibelle() + ";" + this.prixAchat.getValeur() + ";" + this.prixVente.getValeur();
		return this.getIdElement().toString() + ";" + this.getLibelle() + ";" + quantite + ";" + this.getUniteQte().getCodeUnite().toString() + ";" + this.prixAchat.getValeur() + ";" + this.prixVente.getValeur();

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
			return null;
		}
	}
	
	@Override
	public Prix getPrixVente() {
		if(this.prixVente != null){
			return this.prixVente;
		}else{
			return null;
		}
	}

}
