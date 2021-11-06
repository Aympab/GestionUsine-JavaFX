package aaaessais;

import matieres.*;
import outils.*;
import stockage.EnsembleElements;

public class EssaiManuelProd {

	public static void main(String[] args) {
		
		UniteMesure u1 = new UniteMesure("UniteMesureMP");
		UniteMesure u2 = new UniteMesure("UniteMesureProd");
		
		MatierePremiere m1 = new MatierePremiere("gomme", u1, new Prix(5));
		MatierePremiere m2 = new MatierePremiere("bois", u1, new Prix(5));
		MatierePremiere m3 = new MatierePremiere("carbon", u1, new Prix(5));
		
		Produit         p1 = new Produit("crayon à papier", u2, new Prix(10));
		
		EnsembleElements matsIN = new EnsembleElements();
		matsIN.ajouterNouvelElement(m1);
		matsIN.ajouterNouvelElement(m2);
		matsIN.ajouterNouvelElement(m3);
		
		matsIN.ajouterQteElement(m1, 2);
		matsIN.ajouterQteElement(m2, 3);
		matsIN.ajouterQteElement(m3, 2);
		
		EnsembleElements matsOUT = new EnsembleElements();
		matsOUT.ajouterNouvelElement(p1);
		matsOUT.ajouterQteElement(p1, 1);
		
		System.out.println(matsIN.toStringCSV());
		System.out.println(matsOUT.toStringCSV());
//		
//		ManuelProduction manProd = new ManuelProduction("Manuel Production crayon", 40, matsIN, matsOUT);
//		
//		System.out.println(manProd.toString());
		
		

	}

}
