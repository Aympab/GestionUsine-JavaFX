package matieres;

import outils.Prix;
import outils.UniteMesure;

public class Produit extends Element implements IProduit{

		//##############################################################################
		//##############################################################################
		//									ATTRIBUTS       						   #
		//##############################################################################
		//##############################################################################

		/**
		 * Le prix à laquelle <b>une</b> matière première est achetée
		 */
		private Prix prixVente = new Prix(0.0);
		
		//##############################################################################
		//##############################################################################
		//									CONSTRUCTEURS   						   #
		//##############################################################################
		//##############################################################################
		
		/**
		 * Pour générer un Produit en ayant <b>toutes</b> ses informations (venant d'un fichier en général)
		 * 
		 * @param libelle   @see {@link Element}
		 * @param id		@see {@link Element}
		 * @param uniteM	@see {@link UniteMesure}
		 * @param prixVente @see {@link Produit}
		 */
		public Produit(String id, String libelle, UniteMesure uniteM, Prix prixVente) {
			super(id, libelle, uniteM);
			this.prixVente = new Prix(prixVente);
		}
		
		/**
		 * Constructeur avec <b>seulement le nom</b> du produit
		 * 
		 * @param libelle : le nom, la description (ex : Gomme)
		 */
		public Produit(String libelle){
			this(libelle, new Prix(0.0));
		}
		
		
		/**
		 * Constructeur avec un nom, une unite de mesure, et un prixVente
		 * 
		 * @param libelle   le nom, la description (ex : Gomme)
		 * @param uniteM    l'unité de mesure de la quantité de ce Produit
		 * @param prixVente le prix de vente de ce Produit
		 */
		public Produit(String libelle, UniteMesure uniteM, Prix prixVente) {
			super(libelle, uniteM);
			this.prixVente = new Prix(prixVente);		
		}
		
		
		/**Constructeur avec un nom et une uniteMesure (sans prix)
		 * 
		 * @param libelle @see {@link Produit}
		 * @param uniteM @see {@link Produit}
		 */
		public Produit(String libelle, UniteMesure uniteM){
			super(libelle, uniteM);
		}
		
		
		/**Constructeur avec un nom et un prix
		 * 
		 * @param libelle 
		 * @param prixVente @see {@link Produit}
		 */
		public Produit(String libelle, Prix prixVente){
			super(libelle);
			this.prixVente = new Prix(prixVente);
		}

		public Produit(Produit prodCopie){
			this(prodCopie.getIdElement().toString(), prodCopie.getLibelle(), prodCopie.getUniteQte(), prodCopie.getPrixVente());
		}


		//##############################################################################
		//##############################################################################
		//									METHODES DE BASE						   #
		//##############################################################################
		//##############################################################################

		public String toString(){
			String str = super.toString() + "\nPrix vente unitaire: ";
			
			//Cause une erreur si prixVente est null, d'où le if
			if(this.prixVente != null){
				str += this.prixVente.toString();
			}else{
				str += "non renseigné";
			}
			return str;
		}
		
		public String toStringCSV(double quantite){
//			return this.getIdElement().toString() + ";" + this.getLibelle() + ";" + quantite + ";" + this.getUniteQte().getLibelle() + ";NA;" + this.prixVente.getValeur();
			return this.getIdElement().toString() + ";" + this.getLibelle() + ";" + quantite + ";" + this.getUniteQte().getCodeUnite().toString() + ";NA;" + this.prixVente.getValeur();

		}

		//##############################################################################
		//##############################################################################
		//									   METHODES 							   #
		//##############################################################################
		//##############################################################################
		
		@Override
		public Prix getPrixVente() {
			if(this.prixVente != null){
				return this.prixVente;
			}else{
				return null;
			}
		}
		
}
