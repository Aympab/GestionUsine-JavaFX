package aaaessais;

import java.util.ArrayList;
import java.util.Iterator;

import gestionFichier.ImportFichier;
import simulations.TestSimple;

public class EssaiImportTest {

	public static void main(String[] args) throws Exception {
		ArrayList<TestSimple> tests = ImportFichier.importerArrayListTestSimple();

		Iterator<TestSimple> ite = tests.iterator();
		
		while(ite.hasNext()){
			System.out.println(ite.next().toStringCSV());
		}
	}

}
