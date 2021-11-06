package aaaessais;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import gestionFichier.ImportFichier;
import simulations.Resultat;

public class EssaiImportResultat {
	public static void main(String[] args) throws NumberFormatException, IOException {
		ArrayList<Resultat> liste = ImportFichier.importerArrayResultat();
		Iterator<Resultat> ite = liste.iterator();
		
		while(ite.hasNext()){
			System.out.println(ite.next().toStringCSV());
		}
	}
}
