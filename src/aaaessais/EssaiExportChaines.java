package aaaessais;

//import java.io.IOException;
import acteurs.internes.UsineSimple;
import simulations.TestSimple;

public class EssaiExportChaines {

	public static void main(String[] args) throws Exception {
		long debut = System.currentTimeMillis();
		 
		//Traitements...
		
//		ExportFichier.creerFichData("chaines");
//		ExportFichier.initialiserFichChaine();
//		
//		ExportFichier.exporterChaineSimple(new ChaineProductionSimple());
		//Export fonctionne, on essaye import now
		
		//ImportFichier.importeChainesCSV();
		
		//System.out.println(ImportFichier.instancierChaine("6cf8aa62-2c90-4a9c-99f1-e13d1756fdce;chaine1;(dca294fd-7bda-409d-ab87-adb8ed985ff4,1.0),(60b691ab-0c8e-43c2-a676-3044a6f2d47d,2.0),(e1d1b32a-f9d7-4718-b7be-2aaa81b8a556,1.0);(a6f10219-67a9-4c8e-a857-d8ed8bc72e9e,1.0)".split(";"), ImportFichier.importerElementsCSV()));
//		
		
		//System.out.println(ImportFichier.instancierChaine("6cf8aa62-2c90-4a9c-99f1-e13d1756fdce;chaine1;(dca294fd-7bda-409d-ab87-adb8ed985ff4,1.0),(60b691ab-0c8e-43c2-a676-3044a6f2d47d,2.0),(e1d1b32a-f9d7-4718-b7be-2aaa81b8a556,1.0);(a6f10219-67a9-4c8e-a857-d8ed8bc72e9e,1.0)".split(";"), ImportFichier.importerElementsCSV()).getMethodeProdChaine().getManuelProd().toString());		
//		String chat = "chat";
//		
//		System.out.println(chat.substring(-1, 3));
		
		UsineSimple u = new UsineSimple("Usine à crayon");
		
		u.chargerStock();
		u.chargerChaines();
		
		u.getListeChaines().get(0).getMethodeProdChaine().incrNivActivation();
		u.getListeChaines().get(0).getMethodeProdChaine().incrNivActivation();
		
		TestSimple test = new TestSimple(u.getListeChaines().get(0));
		
		
		//test.initialiserResultat(u.getStockPrincipal());
		test.calculerResultat(u.getStockPrincipal());
		
		
		
		u.produireEtSauvegarder(0, test);
		//Regarder comportement avec elements.csv et chaines.csv!!!
		
//		MPVendable papier = new MPVendable("papier", new UniteMesure("kg"), new Prix(10), new Prix(20));
//		u.ajouterNouvelElementCSV(papier);
//		u.ajouterNouvelQteElementCSV(papier, 12);
				
		
		
		System.out.println(System.currentTimeMillis()-debut);
		//Affiche la durée d'exécution en millisecondes
		
	}
}
