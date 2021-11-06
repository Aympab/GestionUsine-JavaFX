package outils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

//TODO : Ajouter excpetions dans les constructeurs par exemple
/**
 *	La classe prix est une classe qui h�rite de UniteMesure, c'est la m�me chose
 *	avec une valeur en plus.
 *
 *@author Astrid
 */
public class Prix extends UniteMesure{
	
	
	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS								   #
	//##############################################################################
	//##############################################################################
	
	/**
	 * La valeur d'un prix 
	 * Un prix est donc compos� d'une devise (UniteMesure et ses attributs)
	 * et d'une valeur
	 */
	private double valeur;

	
	
	//##############################################################################
	//##############################################################################
	//								CONSTRUCTEURS								   #
	//##############################################################################
	//##############################################################################	
	
	/**
	 * @param valeur   La valeur du prix (Combien ca co�te)
	 * @param libelle  Le libelle de la devise
	 * @param abrev    Le signe utilise pour la devise (�, $, ...)
	 * 			
	 * 		@see UniteMesure
	 */
	public Prix(double valeur, String libelle, String abrev) {
		super(libelle, abrev);
		this.valeur = valeur ;
	}
	
	
	/** 
	 * Permet d'instancier un prix avec seulement une valeur, sans regarder les devises
	 * 
	 * @param valeur La valeur du prix (combien ca co�te)
	 */
	public Prix(double valeur){
		this(valeur, "Euros", "�"); //FIXME : A changer dans lse parametres users!
	}
	
	/**
	 *  Permet d'instancier un prix avec seulement une abrev (ex �) et une valeur
	 * 
	 * @param valeur La valeur du prix (combien �a co�te)
	 * @param abrev  Le signe utilise pour la devise
	 */
	public Prix(double valeur, String abrev){
		this(valeur, abrev, abrev);
	}
	
	
	/**
	 * Il s'agit d'un constructeur par copie
	 * 
	 * @param prixCopie le prix qu'on veut utiliser pour instancier ce nouveau prix
	 */
	public Prix(Prix prixCopie){
		super(prixCopie.getLibelle(), prixCopie.getAbrev());
		this.valeur = prixCopie.getValeur();
	}
	
	
	//##############################################################################
	//##############################################################################
	//								METHODES DE BASE							   #
	//##############################################################################
	//##############################################################################
	public String toString(){
		 //Formattage du Benefice
		 DecimalFormat formatterUS = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		 DecimalFormatSymbols symbols = formatterUS.getDecimalFormatSymbols();
		 DecimalFormat formatter = new DecimalFormat("###,###.##", symbols);
		 symbols.setGroupingSeparator(' ');
		 symbols.setDecimalSeparator(',');
		 formatter.setDecimalFormatSymbols(symbols);
		 
		 

		return  formatter.format(this.valeur) + " " + super.getAbrev();
	}
	
	
	public double getValeur(){
		return this.valeur;
	}
	
	
	//##############################################################################
	//##############################################################################
	//								   METHODES 								   #
	//##############################################################################
	//##############################################################################	
	
	/**
	 * @param aComparer Le prix qu'on veut comparer avec notre prix
	 * @return La diff�rence de prix entre les deux :
	 * 		<br> - {@literal >} 0 : this est plus grand que aComparer
	 * 		<br> - {@literal <} 0 : this est plus petit que aComparer
	 * 		<br> - = 0 : this et aComparer co�tent autant
	 */
	public double comparerPrix(Prix aComparer){
		return this.getValeur() - aComparer.getValeur();
	}
	
	/**Ajoute un prix (valeur double) � this
	 * 
	 * @param aAjouter Le prix qu'on veut ajouter au prix initial
	 */
	public void ajouterPrix(Prix aAjouter){
		this.valeur += aAjouter.getValeur();
	}

}
