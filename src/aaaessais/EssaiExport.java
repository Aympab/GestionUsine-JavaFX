package aaaessais;

import java.io.IOException;

import gestionFichier.ExportFichier;
import matieres.Element;
import matieres.MPVendable;
import matieres.MatierePremiere;
import matieres.Produit;
import outils.Prix;
import outils.UniteMesure;
import stockage.StockElement;

public class EssaiExport {

	public static void main(String[] args) {
		
		
		//Création du repertoire data et du fich elements.csv
//		try {
//			ExportFichier.creerRepData();
//			ExportFichier.creerFichData("elements");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
		//Test des exports Element et Stock
		//Le but est d'obtenir un CSV element.csv qui va bien. Il est dans le fichier /data
		
		StockElement stock = new StockElement("Le Stock", new UniteMesure("U"));
		
		
		//Création de nouveaux Elements
		MatierePremiere m1 = new MatierePremiere("Gomme", new UniteMesure("pieces", "pcs"), new Prix(30));
		MatierePremiere m2 = new MatierePremiere("Ciseaux", new UniteMesure("Kilogrammes", "Kg"), new Prix(40));
		MatierePremiere m3 = new MatierePremiere("Crayon", new UniteMesure("Litres", "L"), new Prix(30));
		Produit m4 = new Produit("Trousse", new UniteMesure("Metres", "M"), new Prix(30));
		Element m5 = new MPVendable("MPVENDABLE", new UniteMesure("K"), new Prix(3), new Prix(6));
		
		//Ajout dans le stock
		stock.ajouterNouvelElement(m1);
		stock.ajouterNouvelElement(m2);
		stock.ajouterNouvelElement(m3);
		stock.ajouterNouvelElement(m4);
		stock.ajouterNouvelElement(m5);
		
		
		//On initialise le FIchElement, et on exporte le stock
		try {
			ExportFichier.initialiserFichElement();
			ExportFichier.exporterEnsembleElements(stock);
		} catch (IOException e) {
			System.out.println("Fail export stock");
			e.printStackTrace();
		}
		
		
		try {
			ExportFichier.exporterElement(new MPVendable("Argent", new UniteMesure("€"), new Prix(0.01), new Prix(0.02)));
			ExportFichier.exporterElement(new Produit("Ordinateur", new UniteMesure("€"), new Prix(200)));
		} catch (IOException e) {
			System.out.println("Fail export element");
			e.printStackTrace();
		}
		
		StockElement stock2 = new StockElement("Stock2");
		
		MatierePremiere mm1 = new MatierePremiere("Souris", new UniteMesure("pieces", "pcs"), new Prix(30));
		MatierePremiere mm2 = new MatierePremiere("Clavier", new UniteMesure("Kilogrammes", "Kg"), new Prix(40));
		MatierePremiere mm3 = new MatierePremiere("Touche", new UniteMesure("Litres", "L"), new Prix(30));
		Produit mm4 = new Produit("Unité Centrale", new UniteMesure("Metres", "M"), new Prix(30));
		Element mm5 = new MPVendable("MP vendable", new UniteMesure("K"), new Prix(3), new Prix(6));
		
		stock2.ajouterNouvelElement(mm1);
		stock2.ajouterNouvelElement(mm2);
		stock2.ajouterNouvelElement(mm3);
		stock2.ajouterNouvelElement(mm4);
		stock2.ajouterNouvelElement(mm5);
		stock2.ajouterNouvelElement(new Element("L'element"));
		
		try {
			ExportFichier.exporterEnsembleElements(stock2);
		} catch (IOException e) {
			System.out.println("Fail export second stock");
			e.printStackTrace();
		}
		
		
	}
}
