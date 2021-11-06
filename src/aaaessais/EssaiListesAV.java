package aaaessais;

import listes.ListeAchat;
import listes.ListeVente;
import matieres.MPVendable;
import matieres.MatierePremiere;
import matieres.Produit;
import outils.Prix;
import outils.UniteMesure;
import stockage.StockElement;

public class EssaiListesAV {

	public static void main(String[] args) {
		
		//Test de RAZ
		StockElement stock = new StockElement("LeStock");
		
		Produit p1 = new Produit("50f366a7-f813-4c16-9007-a3f0ae4077d4", "libel", new UniteMesure("ukn"), new Prix(3.0));
		stock.ajouterNouvelElement(p1);
		stock.ajouterQteElement(p1, 5.0);
		
		Produit p2 = new Produit("P2");
		stock.ajouterNouvelElement(p2);
		stock.ajouterQteElement(p2, 4.4);
		
		MPVendable p3 = new MPVendable("P3", new Prix(0.0), new Prix(0.0));
		stock.ajouterNouvelElement(p3);
		stock.ajouterQteElement(p3, 6.0);
		
		
		System.out.println(stock.toStringCSV());
		
//		stock.razQteElement();
		
//		System.out.println(stock.toStringCSV());
		
		//Test de trouverElementAvecID
		System.out.println(stock.trouverElementAvecID("50f366a7-f813-4c16-9007-a3f0ae4077d4").toString());
	
		
		//Test de retire qte element
		stock.retirerQteElement(p3, 8.0);	
		System.out.println(stock.toStringCSV());
		
		
		//Instanciation de matieres
		MatierePremiere m1 = new MatierePremiere("m1", new Prix(1.0));
		MatierePremiere m2 = new MatierePremiere("m2", new Prix(2.0));
		MatierePremiere m3 = new MatierePremiere("m3", new Prix(1.5));
		//Test de coutElemQte
		ListeAchat la = new ListeAchat("la");
		la.ajouterNouvelElement(m1);
		la.ajouterQteElement(m1, 4.0);
		la.ajouterNouvelElement(m2);
		la.ajouterQteElement(m2, 3.0);
		la.ajouterNouvelElement(m3);
		la.ajouterQteElement(m3, 9.0);
		
		//Test de coutElementQte
		System.out.println(la.toStringCSV());
		System.out.println(la.coutElementQte(m1).toString());
		System.out.println(la.coutElementQte(m2).toString());
		System.out.println(la.coutElementQte(m3).toString());
		
		//Test de coutTotal
		la.calculerCoutTotal();
		System.out.println(la.getCoutTotal().toString());
		
		StockElement s1 = new StockElement("leStock");
		
		//Test de faireBuisness
		la.faireBuisness(s1);
		la.faireBuisness(s1);
		la.faireBuisness(s1);
		la.razQteElement();
		System.out.println(la.toString());
		System.out.println(s1.toStringCSV());
		
		//Test des listeVente
		ListeVente lv = new ListeVente("lv");
		lv.ajouterNouvelElement(p1);
		lv.ajouterNouvelElement(p2);
		Produit p4 = new Produit("p4", new Prix(5));
		lv.ajouterNouvelElement(p4);
		lv.ajouterQteElement(p1, 40);
		lv.ajouterQteElement(p2, 4.9);
		lv.ajouterQteElement(p4, 48);
		
		System.out.println(lv.toStringCSV());
		
		//Test de faireBuisness
		StockElement s2 = new StockElement("leStock");
		s2.ajouterNouvelElement(p4);
		s2.ajouterQteElement(p4, 5);
		lv.faireBuisness(s2);
		System.out.println(s2.toStringCSV());
		
		
		System.out.println(lv.coutElementQte(p4).toString());
		
		System.out.println(la.toStringCSV());
	}
	
}
