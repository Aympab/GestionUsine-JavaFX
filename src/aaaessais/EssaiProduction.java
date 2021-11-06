package aaaessais;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import gestionFichier.ExportFichier;
import gestionFichier.ImportFichier;
import listes.ListeAchat;
import listes.ListeVente;
import machines.ChaineProductionSimple;
import matieres.MatierePremiere;
import matieres.Produit;
import outils.Prix;
import outils.UniteMesure;
import production.ManuelProduction;
import production.MethodeProduction;
import simulations.TestSimple;
import stockage.EnsembleElements;
import stockage.StockElement;

public class EssaiProduction {
	
	public static void main(String[] args) throws IOException {
		UniteMesure u1 = new UniteMesure("pieces");
		
		ListeAchat listeEntree = new ListeAchat("listeEntree");
		MatierePremiere gomme = new MatierePremiere("gomme", u1, new Prix(4));
		MatierePremiere bois = new MatierePremiere("bois", new UniteMesure("m2"), new Prix(1));
		MatierePremiere carbon = new MatierePremiere("carbon", new UniteMesure("grammes"), new Prix(2));
		
		listeEntree.ajouterNouvelElement(gomme);
		listeEntree.ajouterNouvelElement(bois);
		listeEntree.ajouterNouvelElement(carbon);
		
		
		listeEntree.ajouterQteElement(gomme, 1);
		listeEntree.ajouterQteTous(1);
		
		Produit crayon = new Produit("crayon papier", u1, new Prix(20.0));
		
		ListeVente listeSortie = new ListeVente("listeSortie");
		listeSortie.ajouterNouvelElement(crayon);
		listeSortie.ajouterQteTous(1);
		//listeSortie.ajouterNouvelElement(bois);
		//listeSortie.ajouterQteElement(bois, 0.5);
		
		
		ManuelProduction manuelFabricationCrayon = new ManuelProduction("ManuelFabricationCrayon", 20, listeEntree, listeSortie);
		MethodeProduction methodeFabChaine1 = new MethodeProduction("fabrication des crayons le jeudi", manuelFabricationCrayon, 1);
		//methodeFabChaine1.incrNivActivation();
		ChaineProductionSimple chaine1 = new ChaineProductionSimple(UUID.randomUUID().toString(), "chaine1", methodeFabChaine1);
		
		TestSimple testProd = new TestSimple("TestChaine1", new Date(), chaine1);
		
		
//		listeEntree.retirerQteElement(gomme, 1);
//		listeEntree.retirerQteElement(bois, 1);

//		listeEntree.retirerQteElement(gomme, 1); // Pour qu'on ai que 1 gomme 1 vois 1 crayon
//		ExportFichier.exporterEnsembleElements(listeEntree);
		EnsembleElements stockCSV = ImportFichier.importerElementsCSV();
		
		
		System.out.println(testProd.calculerResultat(new StockElement(stockCSV)).toStringCSV());
		
		//ExportFichier.exporterEnsembleElements(testProd.calculerResultat(new StockElement(stockCSV)).getStockSortie());
//		chaine1.produire(testProd);
//		System.out.println(testProd.getResultatProd().toStringCSV());
		//Test en cahngeant niv activation, et en changeant qte
		//System.out.println(chaine1.toStringCSV());
//		
//		ExportFichier.initialiserFichChaine();
//		
//		ExportFichier.exporterChaineSimple(chaine1);
	}

}
