package aaaessais;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import gestionFichier.ImportFichier;
import outils.UniteMesure;

public class EssaiUnitesCSV {
	public static void main(String[] args) throws IOException {
		
//		UniteMesure u1  = new UniteMesure("abv1");
//		UniteMesure u2  = new UniteMesure("Litres", "L");
//		UniteMesure u3  = new UniteMesure(UUID.randomUUID().toString(), "Libel3", "Abv3");
//		UniteMesure u4  = new UniteMesure(u2);
//		
//		
//		ExportFichier.exporterUnite(u1);
//		ExportFichier.exporterUnite(u2);
//		ExportFichier.exporterUnite(u3);
//		ExportFichier.exporterUnite(u4);
		
		ArrayList<UniteMesure> testArray = ImportFichier.importerUnitesCSV();
		
		Iterator<UniteMesure> it = testArray.iterator();
		
		while(it.hasNext()){
			System.out.println(it.next().toStringCSV());
		}
		
//		System.out.println(testArray.toString());
		
		
	}
}
