package aaaessais;

import matieres.MPVendable;
import matieres.MatierePremiere;
import matieres.Produit;
import outils.Prix;
import outils.UniteMesure;
import stockage.StockElement;
public class EssaiStock {

	public static void main(String[] args) {
		
		MatierePremiere m1 = new MatierePremiere("Gomme", new UniteMesure("pieces", "pcs"), new Prix(30));
		MatierePremiere m2 = new MatierePremiere("Ciseaux", new UniteMesure("Kilogrammes", "Kg"), new Prix(40));
		MatierePremiere m3 = new MatierePremiere("Crayon");
		Produit m4 = new Produit("Trousse", new UniteMesure("Metres", "M"), new Prix(30));
		
		StockElement stock = new StockElement("stock");
		stock.ajouterNouvelElement(m1);
		stock.ajouterNouvelElement(m2);
		
		System.out.println(stock.toString());
		System.out.println(stock.toStringCSV());
		
		stock.ajouterNouvelElement(m3);
		stock.ajouterNouvelElement(m4);
		
		System.out.println(stock.toString());
		System.out.println(stock.toStringCSV());
		
		System.out.println("\n\nQte totalte : " + stock.getSommeQuantite());
		stock.retirerElement(m3);
		
		System.out.println(stock.toString());
		System.out.println("\nslt\n\n"+stock.toStringCSV()+"\n");
		
		System.out.println("\n\nQte totalte : " + stock.getSommeQuantite());
		
		System.out.println(stock.nbEnStock(m1));
		System.out.println(stock.nbEnStock(m3));
		
		StockElement stock2 = new StockElement("stock2");
		stock2.ajouterNouvelElement(m1);
		stock2.ajouterNouvelElement(m2);
		stock2.ajouterNouvelElement(m3);
		stock2.ajouterNouvelElement(m4);
		stock2.ajouterNouvelElement(new Produit("Pack de trousse"));
		
		System.out.println(stock.comparerEnsembleElements(stock2));
		System.out.println(stock2.comparerEnsembleElements(stock));
		System.out.println(stock.comparerEnsembleElements(stock));
		
		StockElement stock3 = new StockElement(stock2);
		
		stock2.razQteElement();
		stock3.razQteElement();
		stock3.ajouterQteTous(10);
		stock2.ajouterQteTous(13);
		
		stock2.ajouterNouvelElement(new MPVendable("MPVENDABLE", new UniteMesure("K"), new Prix(3), new Prix(6)));
		System.out.println(stock2.toStringCSV());
		
		
		System.out.println("\n\n\nIci");
		System.out.println(stock3.toStringCSV());
		stock3.retirerEnsemble(stock2);
		System.out.println(stock3.toStringCSV());
	}
}
