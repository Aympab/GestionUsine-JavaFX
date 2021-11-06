package simulations;

import java.util.UUID;

import stockage.StockElement;

/**Un TestProd permet de simuler des productions en focntion de chaines et de stocks
 * 
 * @author Aympab
 *
 */
public abstract class TestProd {

	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS       						   #
	//##############################################################################
	//##############################################################################
	
	private UUID idTest;
	
	private String nomTest;

	/**
	 * @see Resultat
	 */
	protected Resultat resultatProd;
	
	//##############################################################################
	//##############################################################################
	//									CONSTRUCTEURS   						   #
	//##############################################################################
	//##############################################################################
	
	public TestProd(){
		this("");
	}
	
	public TestProd(String idTest, String nomTest){
		this.idTest = UUID.fromString(idTest);
		this.nomTest = nomTest;
		//this.resultatProd = null; On instancie pas le résultat tout de suite
	}
	
	public TestProd(String nomTest){
		this(UUID.randomUUID().toString(), nomTest);
	}

	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################
	
	public Resultat getResultatProd(){
		return this.resultatProd;
	}
	
	public String getNomTest(){
		return this.nomTest;
	}
	
	public UUID getIdTest(){
		return this.idTest;
	}
	
	public String toStringCSV(){
		return this.idTest.toString() + ";" + this.nomTest + "\n";
	}

	//##############################################################################
	//##############################################################################
	//									   METHODES 							   #
	//##############################################################################
	//##############################################################################
	
	public Resultat initialiserResultat(StockElement stockDepart){
		this.resultatProd = new Resultat("Resultat_"+this.nomTest, stockDepart);
		return this.resultatProd;
	}
	
	public abstract Resultat calculerResultat(StockElement stockSimulation);
	
}
