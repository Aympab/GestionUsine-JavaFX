package aaaessais;

//import java.io.IOException;

//import gestionFichier.ExportFichier;
import gestionFichier.ImportFichier;
//import listes.ListeAchat;
//import listes.ListeVente;
//import machines.ChaineProductionSimple;
//import matieres.MatierePremiere;
//import matieres.Produit;
//import outils.Prix;
//import outils.UniteMesure;
//import production.ManuelProduction;
//import production.MethodeProduction;
import stockage.StockElement;

public class EssaiManuelsCSV {
	public static void main(String[] args) throws Exception {
		
//		UniteMesure kg = new UniteMesure("kg");
//		
//		MatierePremiere bois = new MatierePremiere("bois", kg, new Prix(10.2));
//		MatierePremiere carbon = new MatierePremiere("carbon", kg, new Prix(12.2));
//		
//		Produit crayon = new Produit("crayon", kg, new Prix(50.0));
//		
//		ListeAchat matIn = new ListeAchat();
//		matIn.ajouterNouvelElement(bois);
//		matIn.ajouterQteElement(bois, 10);
//		
//		matIn.ajouterNouvelElement(carbon);
//		matIn.ajouterQteElement(carbon, 2);
//		
//		ListeVente matOut = new ListeVente();
//		matOut.ajouterNouvelElement(crayon);
//		matOut.ajouterQteElement(crayon, 1);
//		
//		
//		ManuelProduction manCrayon = new ManuelProduction("ManCrayon", 13, matIn, matOut);
//		
//		MethodeProduction methCrayonLundi = new MethodeProduction("methode", manCrayon, 1);
//		
//		ChaineProductionSimple chaine = new ChaineProductionSimple(methCrayonLundi);
//		
//		ExportFichier.exporterEnsembleElements(matIn);
//		ExportFichier.exporterEnsembleElements(matOut);
//		
//		ExportFichier.exporterChaineSimple(chaine);
//		ExportFichier.exporterManuelProd(manCrayon);

		System.out.println((ImportFichier.importerChainesCSV((StockElement)ImportFichier.importerElementsCSV())).get(0).getMethodeProdChaine().getManuelProd().getMatIn().getListeStock().toString());
		
		
	}
}
