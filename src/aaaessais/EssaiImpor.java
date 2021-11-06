package aaaessais;

import java.io.IOException;

import gestionFichier.ImportFichier;
import stockage.*;

public class EssaiImpor {
	
	public static void main(String[] args) {		
		
		try {
			EnsembleElements ensembleElements = new StockElement(ImportFichier.importerElementsCSV());
			System.out.println(ensembleElements.toStringCSV());
			System.out.println("\n\n\n\n");
			
//			System.out.println(ensembleElements.getListeStock().);
			System.out.println(ensembleElements.trouverElementAvecID("d114ace6-4594-497f-a5c3-110446745572").toString());

			System.out.println(ensembleElements.trouverElementAvecID("984e6f8f-0092-450e-9a75-896f467a111e").toString());
		} catch (IOException e) {
			System.out.println("Fail");
			e.printStackTrace();
		}		
		
		
	}
}
