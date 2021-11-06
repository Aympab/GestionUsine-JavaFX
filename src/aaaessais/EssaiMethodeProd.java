package aaaessais;

import java.io.IOException;

import gestionFichier.ImportFichier;
import matieres.Element;

import matieres.MatierePremiere;
import production.MethodeProduction;
import stockage.StockElement;

public class EssaiMethodeProd {
	
	public static void main(String[] args) {
	  MethodeProduction met = new MethodeProduction();
	  
	  System.out.println(met.toString());
	  
	  Element m1 = new MatierePremiere("Mat1");
			  Element m2 = new MatierePremiere("Mat2");
					  Element m3 = new matieres.Element("produitElem");
	  met.getManuelProd().getMatIn().ajouterNouvelElement(m1);
	  met.getManuelProd().getMatIn().ajouterNouvelElement(m2);
	  met.getManuelProd().getMatOut().ajouterNouvelElement(m3);
	  
	  met.getManuelProd().getMatIn().ajouterQteElement(m1, 3);
	  met.getManuelProd().getMatIn().ajouterQteElement(m2, 2);
	  
	  met.getManuelProd().getMatOut().ajouterQteElement(m3, 1);
	  
	  System.out.println(met.toString());
	  
	  //Test de incr et decrNivActivation
	  met.incrNivActivation();
	  System.out.println(met.toString());
	  
	  for(int i = 0; i < 100; i++){
		  met.incrNivActivation();
	  }
	  
	  System.out.println(met.toString());
	  
	  met.decrNivActivation();
	  
	  System.out.println(met.toString());
	  
	  for(int i = 0; i < 104; i ++){
		  met.decrNivActivation();
	  }
	  
	  System.out.println(met.toString());
	  
	  System.out.println();
	  System.out.println();
	  System.out.println();
	  System.out.println();
	  System.out.println();
	  
	  try {
		met.initialiserResultat((StockElement)ImportFichier.importerElementsCSV());
		System.out.println(met.getResultatProd().toString());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}	
	
}
