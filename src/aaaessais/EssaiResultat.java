package aaaessais;
import simulations.*;

import java.io.IOException;

import gestionFichier.*;
import stockage.*;

public class EssaiResultat {

	public static void main(String[] args) {
		
		//Essai de l'instanciation du résultat
		StockElement stock;
		try {
			stock = new StockElement(ImportFichier.importerElementsCSV());
		} catch (IOException e) {
			stock = null;
			e.printStackTrace();
		}
		
		Resultat res = new Resultat("Resultat_Test", stock);

		System.out.println(res.toString());
		System.out.println(res.toStringCSV());
		
	}

}
