package aaaessais;


import gestionFichier.ImportFichier;
import stockage.EnsembleElements;

public class EssaiGetListeComplex {
	public static void main(String[] args) throws Exception {
		
		EnsembleElements stockUsine = new EnsembleElements(ImportFichier.importerElementsCSV());
		
		
		EnsembleElements stockMP = new EnsembleElements("", stockUsine.getListeStock(1, "KG"));
//		EnsembleElements stockProd = stockUsine.getListeStock(2, "KG");
//		EnsembleElements stockElem = stockUsine.getListeStock(3, "KG");
//		EnsembleElements stockMPV = stockUsine.getListeStock(4, "KG");
//		
//		
		System.out.println(stockUsine.toStringCSV());
		System.out.println("\n\n\n############\n\n\n");
		System.out.println(stockMP.toStringCSV());
		System.out.println("\n\n\n############\n\n\n");
//		System.out.println(stockProd.toStringCSV());
		System.out.println("\n\n\n############\n\n\n");
//		System.out.println(stockElem.toStringCSV());
		System.out.println("\n\n\n############\n\n\n");
//		System.out.println(stockMPV.toStringCSV());
		
	}
}
