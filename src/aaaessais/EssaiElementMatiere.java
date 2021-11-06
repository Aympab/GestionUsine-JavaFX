package aaaessais;

import matieres.Element;
import matieres.MPVendable;
import matieres.MatierePremiere;
import matieres.Produit;
import outils.*;

public class EssaiElementMatiere {
	
	public static void main(String[] args) {
		
		//Test des matieres premieres
		MatierePremiere mat = new MatierePremiere("Matiere1");
		System.out.println(mat.toString());
		Prix p1 = new Prix(30, "€");
		UniteMesure u1 = new UniteMesure("Litres", "L");
		MatierePremiere mat2 = new MatierePremiere("Matiere2", u1,  p1);
		System.out.println(mat2.toString());
		MatierePremiere mat3 = new MatierePremiere("Matiere3", p1);
		MatierePremiere mat4 = new MatierePremiere("Matiere4", u1);
		System.out.println(mat3.toString());
		System.out.println(mat4.toString());
		
		System.out.println("");
		
		//Test des produits
		Prix p2 = new Prix(40, "Dollars", "$");
		UniteMesure u2 = new UniteMesure("Kilogrammes", "Kg");
		
		Produit prod = new Produit("Prod1");
		Produit prod2 = new Produit("Prod2", p2);
		Produit prod3 = new Produit("Prod3");
		Produit prod4 = new Produit("Prod4", u2, p2);
		
		System.out.println(prod.toString());
		System.out.println(prod2.toString());
		System.out.println(prod3.toString());
		System.out.println(prod4.toString());
		
		System.out.println("");
		System.out.println("");
		
		//Test des MPVendable
		MPVendable mpv1 = new MPVendable("MPV1");
		MPVendable mpv2 = new MPVendable("MPV2", u1);
		MPVendable mpv3 = new MPVendable("MPV3", p1, p2);
		MPVendable mpv4 = new MPVendable("MPV4", u2, p1, p2);
		MPVendable mpv5 = new MPVendable(mpv1.getIdElement().toString(), "MPV5",  u1, p1, p2);
		
		System.out.println(mpv1.toString());
		System.out.println(mpv2.toString());
		System.out.println(mpv3.toString());
		System.out.println(mpv4.toString());
		System.out.println(mpv5.toString());
		
		 System.out.println("");
		 System.out.println("");
		//Test de génération de produit et MP en donnant un UUID comme paramètre
		//Equivalent à MPV5
		Produit pp = new Produit(prod.getIdElement().toString(), "Pproduit1", u1, p1);
		System.out.println(pp.toString());
		
		MatierePremiere mm = new MatierePremiere("MmatiereP", p1);
		System.out.println(mm.toString());
		
		Element elem = new Element("Je Suis un élément");
		System.out.println(elem.toStringCSV(13.0));
		
	}

}

//##############################################################################
//##############################################################################
//								ATTRIBUTS       							   #
//##############################################################################
//##############################################################################


//##############################################################################
//##############################################################################
//								CONSTRUCTEURS   							   #
//##############################################################################
//##############################################################################


//##############################################################################
//##############################################################################
//								METHODES DE BASE							   #
//##############################################################################
//##############################################################################


//##############################################################################
//##############################################################################
//								   METHODES 								   #
//##############################################################################
//##############################################################################