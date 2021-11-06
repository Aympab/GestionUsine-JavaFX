package aaaessais;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import gestionFichier.ImportFichier;
import listes.ListeAchat;
import listes.ListeVente;
import machines.ChaineProductionSimple;
import matieres.Produit;
import outils.Prix;
import outils.UniteMesure;
import production.ManuelProduction;
import production.MethodeProduction;
import simulations.TestSimple;
import stockage.StockElement;

public class EssaiChaineProd {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		
//		ChaineProductionSimple c = new ChaineProductionSimple();
//		
//		//System.out.println(c.toString());
//		
//		TestSimple t1 = new TestSimple(c);
//		
//		System.out.println(t1.toString());
		
		//On va essayer de créer une chaine qui produit des crayons
	
		/* Il nous faut :
		 * 			- Un stock
		 * 			- Des matieres premieres et un produit (crayon)
		 * 			- Un manuel de production
		 * 			- Une méthode de production
		 * 		On va commencer par ca déjà
		 */
		

		StockElement stock = new StockElement(ImportFichier.importerElementsCSV());
		stock.ajouterQteTous(1);
		
		StockElement stockCrayon = new StockElement("Stock_Crayon");
		Produit crayon = new Produit("Crayon à papier", new UniteMesure("pieces"), new Prix(20));
		
		stockCrayon.ajouterNouvelElement(crayon);
		//stockCrayon.ajouterQteElement(crayon, 1);
		

		
		ListeAchat listeEnt = new ListeAchat(stock);
		ListeVente listeSort = new ListeVente(stockCrayon); 
		
		listeSort.ajouterQteElement(crayon, 1);
		
		ManuelProduction man = new ManuelProduction("Manuel_Crayon", 40, listeEnt, listeSort);
		
//		System.out.println(man.toString());
		
		MethodeProduction methode = new MethodeProduction("MethodeProd_CrayonPapier", man, 1);
		
//		System.out.println(methode.toString());
		
		//TODO : tester la chaine
		ChaineProductionSimple chaine = new ChaineProductionSimple(UUID.randomUUID().toString(), "ChaineProduction_Crayon", methode);
		
		System.out.println(chaine.toString());
		
		TestSimple test = new TestSimple(new Date(2019, 02, 12), chaine);

		System.out.println();
		System.out.println(test.toString());
		

		System.out.println(stockCrayon.toStringCSV());
		System.out.println("LISTESORTIE : "  + listeSort.toStringCSV());
		System.out.println("Ici");
		System.out.println(stock.toStringCSV());
		stock.ajouterEnsemble(chaine.getMethodeProdChaine().consommerProduire(stock));
		System.out.println(stock.toStringCSV());
		
	}
}
