package production;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

import listes.*;
import matieres.Element;

/**
 * @author Aympab
 *
 *<br>Cette classe représente les elements E/S pour une Methode de production ainsi que la durée
 *
 *<br>On imagine donc que si on envoie les matIns elements et on attend duree temps, on aura les matOut
 */
public class ManuelProduction {
	
	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS       						   #
	//##############################################################################
	//##############################################################################
	
	/**
	 * L'ID unique identifiant le manuel de prod
	 */
	private UUID codeManuel;
	
	/**
	 * La liste des elements en entrée
	 */
	private ListeAchat matIn;
	
	/**
	 * La liste des éléments en sortie
	 */
	private ListeVente matOut;
	
	/**
	 * La temps de production de ce manuel
	 */
	private int duree;
	
	/**
	 * Le nom du manuel
	 */
	private String nomManuel;

	//##############################################################################
	//##############################################################################
	//									CONSTRUCTEURS   						   #
	//##############################################################################
	//##############################################################################
	
	public ManuelProduction(){
		this("ManuelProduction inconnu");
	}
	
	/**
	 * quand on connaît pas la durée, on met à 0 
	 * 
	 * @param nomManuel le nom du manuel
	 */
	public ManuelProduction(String nomManuel){
		this(nomManuel, 0);
	}
	
	public ManuelProduction(String nomManuel, int duree){
		this(nomManuel, duree, new ListeAchat(), new ListeVente());
	}
	
	public ManuelProduction(int duree){
		this("ManuelProduction inconnu", duree);
		}
	
	public ManuelProduction(String nomManuel, ListeAchat matIn, ListeVente matOut){
		this(nomManuel, 0, matIn, matOut);
	}
	
	public ManuelProduction(ListeAchat matIn, ListeVente matOut){
		this("ManuelProduction inconnu", matIn, matOut);
	}
	
	public ManuelProduction(String nomManuel, int duree, ListeAchat matIn, ListeVente matOut){
		this(UUID.randomUUID().toString(), nomManuel, duree, matIn, matOut);
	}
	
	public ManuelProduction(String codeManuel, String nomManuel, int duree, ListeAchat matIn, ListeVente matOut){
		this.codeManuel = UUID.fromString(codeManuel);
		this.nomManuel = nomManuel;
		this.duree = duree;
		this.matIn = new ListeAchat(matIn);
		this.matOut = new ListeVente(matOut);
	}
	
	public ManuelProduction(ManuelProduction manCopie){
		this(manCopie.getCodeManuel().toString(), manCopie.getNomManuel(), manCopie.getDuree(), manCopie.getMatIn(), manCopie.getMatOut());
	}

	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################
	
	public String getNomManuel(){
		return this.nomManuel;
	}
	
	public int getDuree(){
		return this.duree;
	}

	public ListeAchat getMatIn(){
		return this.matIn;
	}
	
	public ListeVente getMatOut(){
		return this.matOut;
	}
	
//	public String toString(){
//		return this.nomManuel + "\nDurée de production : " + this.duree + "\nmatIn : " + this.matIn.toString() + "\nmatOut : " + this.matOut.toString();
//	}
	
	public String toString(){
		return this.nomManuel + "\nDurée de production : " + this.duree + " min.";
	}
	
	public UUID getCodeManuel(){
		return this.codeManuel;
	}
	
	//##############################################################################
	//##############################################################################
	//									   METHODES 							   #
	//##############################################################################
	//##############################################################################

	/**Renvoie "(elemIN1,qte1),(elemIN2,qte2);(elemOUT1,qteO1)"
	 * 
	 * @return une ligne contenant les matIn et matOut avec qte comme on le veut dans chaines.CSV
	 */
	public String toStringCSV(){
		String elemIN = "";
		String elemOUT = "";
		
		Iterator<Entry<Element, Double>> iteIn = this.matIn.getListeStock().entrySet().iterator();
		//On fait un Iterator sur matIn			
		Entry<Element, Double> pair;
		
		while(iteIn.hasNext()){
			pair = iteIn.next();
			elemIN += "(" + pair.getKey().getIdElement().toString() + "," + pair.getValue().toString() + ")";
			
			if(iteIn.hasNext()){
				elemIN += ",";
			}
		}
		
		Iterator<Entry<Element, Double>> iteOut = this.matOut.getListeStock().entrySet().iterator();
		
		while(iteOut.hasNext()){
			pair = iteOut.next();
			elemOUT += "(" + pair.getKey().getIdElement().toString() + "," + pair.getValue().toString() + ")";
			if (iteOut.hasNext()) {
				elemOUT += ",";				
			}
		}
		
		return this.codeManuel.toString() + ";" + this.nomManuel + ";" + this.duree + ";" + elemIN + ";" + elemOUT + "\n";
	}

	
}
