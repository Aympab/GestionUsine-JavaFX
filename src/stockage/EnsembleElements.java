package stockage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import java.util.UUID;

import matieres.Element;


/**
 * @author Aympab
 *
 *<br>Représente un ensemble d'élements, avec une quantité pour chacun de ces elements
 */
public class EnsembleElements {
	
	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS       						   #
	//##############################################################################
	//##############################################################################	
	/**
	 * On a une HashMap qui contient un Element en Key et une Qte en Value
	 */
	private HashMap<Element, Double> listeElement;
		
	private String nomStock;
	
	
	//##############################################################################
	//##############################################################################
	//									CONSTRUCTEURS   						   #
	//##############################################################################
	//##############################################################################
	
	public EnsembleElements(String nomStock, HashMap<Element, Double> listeElement){
		this.nomStock = nomStock;
		this.listeElement = new HashMap<>(listeElement);
	}
	
	public EnsembleElements(String nomStock){
		this(nomStock, new HashMap<Element,Double>());
	}
	
	public EnsembleElements(EnsembleElements ensembleElementsCopie){
		this(ensembleElementsCopie.getNomStock(), ensembleElementsCopie.getListeStock());
	}
	
	public EnsembleElements(){
		this("NomInconnu");
	}
	
	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################
	
	public HashMap<Element, Double> getListeStock(){
		return this.listeElement;
	}
		
	public String getNomStock(){
		return this.nomStock;
	}
	
	/**
	 * @return sommeQte : la somme de toutes les quantités des Elements dans la HashMap (la somme des values)
	 */
	public double getSommeQuantite(){
		double sommeQte = 0.0;
		
		Iterator<Double> ite = listeElement.values().iterator();
		
		while(ite.hasNext()){
			sommeQte += ite.next().doubleValue();
		}
		
		return sommeQte;
	}
	
	
	public String toString(){
		return this.nomStock + "\nNombre d'éléments différents : " + this.listeElement.size() + "\nQuantité totale : " + this.getSommeQuantite();
	}
	
	

	/**
	 * @return Un String qu'on peut insérer directement dans le fichier CSV elements contenant tout le stock
	 */
	public String toStringCSV(){
		String str = "";		
		
		Iterator<Entry<Element, Double>> ite = listeElement.entrySet().iterator();
		//On fait un Iterator sur notre listeElement
		
		while(ite.hasNext()){
			Entry<Element, Double> pair = ite.next(); //On récupère l'entrée de l'itérator
	        str += pair.getKey().toStringCSV(pair.getValue()); //On utilise la méthode toStringCSV sur la premiere ligne
	        str += "\n"; //On passe a la ligne
//	        ite.remove(); // avoids a ConcurrentModificationException 
		}
		
		return str;		
	}
	
	/**
	 * @return une ligne de type (codeElt, qte),(codeElt, qte),(codeElt,qte)....
	 * Représentant this.listeStock
	 */
	public String toStringListeStockCSV(){
		String strRet = "";
		
		if(!this.listeElement.isEmpty()){
			
			
			Iterator<Entry<Element, Double>> ite = this.listeElement.entrySet().iterator();
			Entry<Element, Double> pair;
			
			while(ite.hasNext()){
				pair = ite.next();
				
				strRet = "(";
				strRet += pair.getKey().getIdElement().toString();
				strRet += ",";
				strRet += pair.getValue().toString();
				strRet += "),";
			}
			//Pour enlever la virgule à la fin
			strRet = strRet.substring(0, strRet.length()-1);
		}		
		
		return strRet;
	}
	
	
	/**
	 * @param elem l'element dont on veut vérifier l'existence dans le stock
	 * @return
	 */
	public boolean verifierExistence(Element elem){
		boolean boolReturn = false;
		
		if(this.listeElement.containsKey(elem)){
			boolReturn = true;
		}
		
		if(this.trouverElementAvecID(elem.getIdElement()) != null){
			boolReturn = true;
		}
		
		return boolReturn;
	}
	
	public void setNomEnsembleElements(String nomStock){
		this.nomStock = nomStock;
	}
	
	public String toStringPopUp(){
		String strRet = "";
		
		Iterator<Entry<Element, Double>> ite = this.listeElement.entrySet().iterator();
		Entry<Element, Double> pair;
		
		while(ite.hasNext()){
			pair = ite.next();
			strRet += pair.getKey().getLibelle() + " : " + pair.getValue().toString() + " " + pair.getKey().getUniteQte().getAbrev();
			strRet += ";";
		}
		
		return strRet;
	}
	
	//##############################################################################
	//##############################################################################
	//									   METHODES 							   #
	//##############################################################################
	//##############################################################################
	
	/**
	 * @param ensembleElementsCompare Le Stock à comparer avec this
	 * @return La différence de taille entre les HashMap de stock
	 * 	<br> {@literal >}0 : this est plus grand que stockCompare
	 *  <br> {@literal <}0 : stockCompare est plus grand
	 *  <br> {@literal =}0 : this et stockCompare même taille
	 */
	public int comparerEnsembleElements(EnsembleElements ensembleElementsCompare){
		return this.listeElement.size() - ensembleElementsCompare.getListeStock().size();
	}
	
	/**Retourne la quantité d'un element
	 * 
	 * @param  elemEnStock représente l'element qu'on cherche dans notre Stock
	 * @return la quantite de cet element dans le stock, -1.0 s'il n'existe pas
	 */
	public double nbEnStock(Element elemEnStock) throws NullPointerException{
		
		try {
			return this.listeElement.get(elemEnStock);
		} catch (Exception e) {
			return -1.0;
		}
		
	}
	
	
	/**Si on ajoute un element qui est déjà dans la liste, on met sa Qte à 0
	 * 
	 * @param elemAjout L'element qu'on souhaite ajouter dans la HashMap
	 * @return vrai ou faux, selon si on a réussi ou non l'ajout
	 */
	public void ajouterNouvelElement(Element elemAjout){
		this.listeElement.put(elemAjout, new Double(0.0));
	}
	
	
	/**
	 * @param  elemRetrait L'element qu'on souhaite retirer de la HashMap
	 * @return vrai ou faux, selon si on a réussi ou non la suppression
	 */
	public void retirerElement(Element elemRetrait){
		this.listeElement.remove(elemRetrait);
	}
	
	
	/**On ajoute une certaine quantité a la qte existante
	 * @param elemAjout L'élement pour lequel ajouter une Qte
	 * @param qteAjout  La qte à ajouter
	 */
	public void ajouterQteElement(Element elemAjout, double qteAjout){
		Double valeur = new Double(this.listeElement.get(this.trouverElementAvecID(elemAjout.getIdElement())).doubleValue() + qteAjout);
		this.listeElement.put(this.trouverElementAvecID(elemAjout.getIdElement()), valeur);
	}

	
	/**Retire une quantité donnée pour un element
	 * ATTENTION ! Ne vérifie pas si le sotck est négatif ou pas /!\ 
	 * @param elemRetrait L'élement pour lequel retirer une Qte
	 * @param qteRetrait  La quantité à ajouter
	 */
	public void retirerQteElement(Element elemRetrait, double qteRetrait){
		Double valeur = new Double(this.listeElement.get(this.trouverElementAvecID(elemRetrait.getIdElement())).doubleValue() - qteRetrait);
		this.listeElement.put(this.trouverElementAvecID(elemRetrait.getIdElement()), valeur);
	}

	
	/**
	 * Permet de RAZ toutes les quantités des Elements à 0
	 */
	public void razQteElement(){
		Iterator<Entry<Element, Double>> ite = listeElement.entrySet().iterator();
		//On fait un Iterator sur notre listeElement
		
		Double doubleZero = new Double(0.0);
		
		while(ite.hasNext()){
			Entry<Element, Double> pair = ite.next(); //On récupère l'entrée de l'itérator
	        pair.setValue(doubleZero);
	        //ite.remove(); // avoids a ConcurrentModificationException 
		}
	}
	
	
	/**
	 * @see TrouverElementAvecID(String UUIDtoString)
	 * @param UUID
	 * @return 
	 */
	public Element trouverElementAvecID(UUID UUID){
		return this.trouverElementAvecID(UUID.toString());
	}
	
	/**Permet de retrouver un element dans la liste grâce à son UUID
	 * @param  UUIDtoString L'UUID de l'element qu'on cherche
	 * @return L'élement correspondant à cet UUID
	 */
	public Element trouverElementAvecID(String UUIDtoString){
		//TODO : les ruptures de flow
		Iterator<Entry<Element, Double>> ite = listeElement.entrySet().iterator();
		//On fait un Iterator sur notre listeElement
			
		Entry<Element, Double> pair;		
		
		if(this.estVideElement()){
			return null;
		}
		
		do{
			pair = ite.next(); //On récupère l'entrée de l'itérator
			if(pair.getKey().getIdElement().toString().equals(UUIDtoString)){
				return pair.getKey();
			}
		}while(/*!pair.getKey().getIdElement().toString().equals(UUIDtoString) && */ite.hasNext());
		
		if(pair.getKey().getIdElement().toString().equals(UUIDtoString)){
			return pair.getKey();
		}else{
			return null;
		}
		
	}
	
	
	/**Permet d'ajouter une même quantité à tous les elements 
	 * (utile pour par exemple initialiser tout à 1 pour un manuel)
	 * 
	 * @param qteAjout la quantité qu'on veut ajouter
	 */
	public void ajouterQteTous(double qteAjout){

		Iterator<Entry<Element, Double>> ite = listeElement.entrySet().iterator();
		//On fait un Iterator sur notre listeElement
			
		Entry<Element, Double> pair;		
		
		while(ite.hasNext()){
			pair = ite.next();
			this.ajouterQteElement(pair.getKey(), qteAjout);
		}			
	}
	
	
	/**On retire les elements contenus dans ensembleRetrait du stock courant
	 * @param ensembleRetrait L'ensemble d'elements qu'on veut retirer
	 */
	public void retirerEnsemble(EnsembleElements ensembleRetrait){
		Iterator<Entry<Element, Double>> ite = ensembleRetrait.getListeStock().entrySet().iterator();			
		Entry<Element, Double> pair;		
		
		while(ite.hasNext()){
			pair = ite.next();
			
			//On verifie si cet Element existe dans la liste
			if(this.verifierExistence(pair.getKey())){
				this.retirerQteElement(pair.getKey(), pair.getValue().doubleValue());
			}else{
			//Si non, on l'ajoute, et on retire sa quantité
				this.ajouterNouvelElement(pair.getKey());
				this.retirerQteElement(pair.getKey(), pair.getValue().doubleValue());
			}
		}
	}
		
		/**@see {@link this.retirerEnsemble}
		 * 
		 * @param ensembleAjout l'ensemble à ajouter
		 */
	public void ajouterEnsemble(EnsembleElements ensembleAjout){
		Iterator<Entry<Element, Double>> ite = ensembleAjout.getListeStock().entrySet().iterator();			
		Entry<Element, Double> pair;		
			
		while(ite.hasNext()){
			pair = ite.next();
				
			//On verifie si cet Element existe dans la liste
			if(this.verifierExistence(pair.getKey())){
				this.ajouterQteElement(pair.getKey(), pair.getValue().doubleValue());
			}else{
			//Si non, on l'ajoute, et on ajoute sa qte
				this.ajouterNouvelElement(pair.getKey());
				this.ajouterQteElement(pair.getKey(), pair.getValue().doubleValue());
			}
		}
	}
	
	public boolean estVideQuantite(){
		boolean bool = false;
		
		if(this.getSommeQuantite() == 0.0){
			bool = true;
		}
		return bool;
	}
	
	public boolean estVideElement(){
		return this.listeElement.size() == 0;
	}
	
	
	/**
	 * @param optionType : 1 = MP 
	 *<br>				   2 = Prod
	 *<br>				   3 = Element
	 *<br>				   4 = MPVendable
	 * @param codeUnite : Le code de l'unité qu'on cherche ("" si on veut toutes unités confondues)
	 * @return Un la listeStock contenant seulement les éléments d'un certain type et avec une certaine unité
	 * @throws Exception 
	 */
	public HashMap<Element, Double> getListeStock(int optionType, String codeUnite) throws Exception{
		//TODO : Vérifier si l'unité existe !! sinon throws an error
		HashMap<Element, Double> listeStock = new HashMap<>();
		EnsembleElements elemRet;
		
		switch (optionType){
			case 0 :
				listeStock = this.getListeSpecif(codeUnite, "");
				break;
			case 1 :
				listeStock = this.getListeSpecif(codeUnite, "MatierePremiere");
				break;
			case 2 :
				listeStock = this.getListeSpecif(codeUnite, "Produit");
				break;
			case 3 :
				listeStock = this.getListeSpecif(codeUnite, "Element");
				break;
			case 4 :
				listeStock = this.getListeSpecif(codeUnite, "MPVendable");
				break;
			default :
				throw new Exception("optionType n'est pas valide (1-4");
		}
		
		if(codeUnite.equals("")){
			elemRet = new EnsembleElements("Meth_getList_optionType_"+optionType, listeStock);
		}else{
			elemRet = new EnsembleElements("Meth_getList_optionType_"+optionType+"codeUnite_"+codeUnite, listeStock);
		}
		
		return elemRet.getListeStock();
	}
	
	
	
	private HashMap<Element, Double> getListeSpecif(String codeUnite, String typeElem){
		HashMap<Element, Double> hashMapReturn = new HashMap<>();
		
		Iterator<Entry<Element, Double>> ite = this.getListeStock().entrySet().iterator();			
		Entry<Element, Double> pair;		
			
		//On cherche une Unite si abrev est différent de ""
		boolean chercherUnite = (codeUnite != "");
		
		//On rentre tant que ite a un suivant et qu'on ne cherche pas d'unité
		while(ite.hasNext()){
			pair = ite.next();
			
			if(!chercherUnite){
				//Si le nom de la classe = le typeElem "MatierePremiere" "Element" "Produit" "MPVendable"
				if(pair.getKey().getClass().getCanonicalName().equals("matieres."+typeElem)){
					//On ajoute dans la hashMapReturn
					hashMapReturn.put(pair.getKey(), pair.getValue());
				}
			}
			else{
				//On vérifie le nom de la classe et l'abréviation de l'unité
				if((pair.getKey().getClass().getCanonicalName().equals("matieres."+typeElem)) && (pair.getKey().getUniteQte().getCodeUnite().toString().equals(codeUnite))){
					//On ajoute dans la hashMapReturn
					hashMapReturn.put(pair.getKey(), pair.getValue());
				}
				else if((typeElem.equals("")) && (pair.getKey().getUniteQte().getCodeUnite().toString().equals(codeUnite))){
					hashMapReturn.put(pair.getKey(), pair.getValue());
				}
				
			}
		}
		
		return hashMapReturn;		
	}
	

}
