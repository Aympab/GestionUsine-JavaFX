package aaaessais;

import java.io.IOException;
import java.util.ArrayList;
import acteurs.externes.FournisseurSimple;
import gestionFichier.ImportFichier;


public class EssaiFournisseurs {

	public static void main(String[] args) throws IOException {
		
		ArrayList<FournisseurSimple> listeFourn = ImportFichier.importerArrayFourn(ImportFichier.importerElementsCSV());
//		System.out.println(UUID.randomUUID().toString());
		
		System.out.println(listeFourn.get(0).getIdFournisseur().toString());
		System.out.println(listeFourn.get(0).getListeMpDispo().toStringCSV());


	}

}
