package production;

import java.util.UUID;

import simulations.Resultat;
import simulations.TestProd;
import stockage.StockElement;

/**
 * @author Aympab
 *
 *<br> Une methode de production est composé d'un ManuelProduction et d'un niveau d'activation
 */
public class MethodeProduction extends TestProd{
	
	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS       						   #
	//##############################################################################
	//##############################################################################
	
	/**
	 * Le manuel associé à cette méthode
	 */
	private ManuelProduction manuelProd;
	
	/**
	 * Le multiplicateur de production, va s'appliquer sur le manuelProd
	 */
	private int nivActivation;

	/**
	 * Le niveau d'activation maximal d'une methode de production,
	 *  à voir comment changer //TODO:
	 */
	private int nivActivationMax = 100;
	
	//##############################################################################
	//##############################################################################
	//									CONSTRUCTEURS   						   #
	//##############################################################################
	//##############################################################################
	
	
	
	
	/**
	 * @param idTest       l'identifiant du tes
	 * @param description  description du test
	 * @param manuelProd   le manuel de production associé au test
	 * @param nivActivation le niveau d'activation du test
	 */
	public MethodeProduction(String idTest, String description, ManuelProduction manuelProd, int nivActivation){
		super(idTest, description);
		this.manuelProd = new ManuelProduction(manuelProd);
		this.nivActivation = nivActivation;
	}
	

	public MethodeProduction(String description, ManuelProduction manuelProd, int nivActivation){
		this(UUID.randomUUID().toString(), description, manuelProd, nivActivation);
	}
	
	public MethodeProduction(ManuelProduction manuelProd, int nivActivation) {
		super();
		this.manuelProd = new ManuelProduction(manuelProd);
		this.nivActivation = nivActivation;
	}
	
	public MethodeProduction(){
		this(new ManuelProduction());
	}
	
	public MethodeProduction(ManuelProduction manuelProd){
		this(manuelProd, 1);
	}
	
	public MethodeProduction(MethodeProduction metCopie){
		this(metCopie.getIdTest().toString(), metCopie.getNomTest(), new ManuelProduction(metCopie.getManuelProd()), metCopie.getNivActivation());
	}

	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################
	
	public ManuelProduction getManuelProd() {
		return manuelProd;
	}

	public int getNivActivation() {
		return nivActivation;
	}	
	
//	public String toString(){
//		return "Méthode de production : " + this.getNomTest() + "\nniveau d'activation = " + this.nivActivation + "\n" + this.manuelProd.toString();
//	}
	
	public String toString(){
		return this.getNomTest() + "\nManuel : "+this.getManuelProd().getNomManuel()+"\nNiv. activ : " + this.nivActivation;
	}
	
	public String toStringCSV(){
		return this.getIdTest().toString() + ";" + this.getNomTest() + ";" + this.getManuelProd().getCodeManuel().toString() + ";" + this.nivActivation + ";" + this.nivActivationMax + "\n";
	}
	//##############################################################################
	//##############################################################################
	//									   METHODES 							   #
	//##############################################################################
	//##############################################################################

	
	/**Modifie si on ne dépasse pas le seuil
	 * 
	 * @return le niveau d'activation nivActivation après maj
	 */
	public int incrNivActivation(){
		if(this.nivActivation < this.nivActivationMax){
			this.nivActivation += 1;
		}
		return this.nivActivation;
	}
	
	/**
	 * @return @see {@link incrNivActivation()}
	 */
	public int decrNivActivation(){
		if(this.nivActivation > 0){
			this.nivActivation -= 1;
		}
		return this.nivActivation;
	}
	
	/**Permet de produire nivActivatoin fois le manuel de production et de
	 * soustraire/ajouter les MP et Produits au stock
	 * 
	 * @param stockProd Le stock sur lequel on veut aller chercher les MP et rajouter les Produits
	 * @return stockProd ce même stock modifié des elements
	 */
	public StockElement consommerProduire(StockElement stockProd){
		for(int i = 0; i < this.nivActivation; i++){
			stockProd.retirerEnsemble(this.manuelProd.getMatIn());
			//TODO : Vérifier la nullité du stock
			stockProd.ajouterEnsemble(this.manuelProd.getMatOut());
		}
		
		return stockProd;
	}

	@Override
	public Resultat calculerResultat(StockElement stockSimulation) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setNivAct(int nivAct){
		this.nivActivation = nivAct;
	}
	
}
