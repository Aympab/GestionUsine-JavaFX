package machines;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import production.MethodeProduction;
import simulations.TestSimple;
import stockage.StockElement;

/**C'est une Chaine avec une méthode de production, 
 * idée d'implémentation : faire une ChaineComplexe avec une ArrayList de MethodeProduction !!
 * 
 * @author Aympab
 *
 */
public class ChaineProductionSimple extends Chaine{

	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS       						   #
	//##############################################################################
	//##############################################################################
	
	private MethodeProduction methodeProdChaine;
	
	//Pour le javaFX
	private ImageView imageViewChaine;
	private String urlImage;
	
	public ImageView getImageViewChaine() {
		return imageViewChaine;
	}

	public ImageView getCopieImageViewChaine() throws FileNotFoundException{
        
		FileInputStream inputStream = new FileInputStream(this.urlImage);
        Image imageChaine = new Image(inputStream);
    	ImageView imageRet = new ImageView(imageChaine);
    	imageRet.setFitHeight(40);
    	imageRet.setFitWidth(40);
		return imageRet;
	}
    /**
     * @param chaine la chaîne à laquelle on veut ajouter une imageView
     * @return cette même chaîne avec l'attribut "imageViewChaine" avec une image random
     * @throws FileNotFoundException l'image de la chaîne n'a pas été trouvée 
     */
    public void ajouterImage() throws FileNotFoundException{
    
    	Random rd = new Random();
    	Integer numChaine = rd.nextInt(7) + 1;
//    	System.out.println(System.getProperty("user.dir")+"\\images\\chaine" + numChaine.toString() +".png");
    	this.urlImage = System.getProperty("user.dir")+"\\images\\chaine" + numChaine.toString() +".png";
        FileInputStream inputStream = new FileInputStream(this.urlImage);
        Image imageChaine = new Image(inputStream);
    	this.imageViewChaine = new ImageView(imageChaine);
    	this.imageViewChaine.setFitHeight(40);
    	this.imageViewChaine.setFitWidth(40);
    }

	//##############################################################################
	//##############################################################################
	//									CONSTRUCTEURS   						   #
	//##############################################################################
	//##############################################################################

    public ChaineProductionSimple(ChaineProductionSimple chaine){
    	super(chaine);
    	this.imageViewChaine = chaine.getImageViewChaine();
    	this.methodeProdChaine = chaine.getMethodeProdChaine();
    	
    }


	public ChaineProductionSimple(){
		this(new MethodeProduction());
	}
	
	public ChaineProductionSimple(MethodeProduction methodeProdChaine){
		this(UUID.randomUUID().toString(), "ChaineProductionSimple inconnue", methodeProdChaine);
	}
	
	public ChaineProductionSimple(String libelle, MethodeProduction methodeProdChaine){
		super(libelle);
		this.methodeProdChaine = methodeProdChaine;
	}
	
	public ChaineProductionSimple(String ID, String libelle, MethodeProduction methodeProdChaine){
		super(ID, libelle);
		this.methodeProdChaine = new MethodeProduction(methodeProdChaine);
	}
	
	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################

	@Override
//	public String toString() {
//		return this.getIdChaine().toString() + " : " + this.getLibelle() + "\n"+ this.methodeProdChaine.toString();
//	}
	
	public String toString(){
		return this.getLibelle() + "\nMéthode prod : " + this.getMethodeProdChaine().getNomTest();
	}
	
	
	public MethodeProduction getMethodeProdChaine(){
		return this.methodeProdChaine;
	}
	
	public String toStringTooltip(){
		return this.getLibelle() + "\nManuel : "+this.getMethodeProdChaine().getManuelProd().getNomManuel()+"\nDurée : " + this.getMethodeProdChaine().getManuelProd().getDuree() + " min.";
	}
	
	//##############################################################################
	//##############################################################################
	//									   METHODES 							   #
	//##############################################################################
	//##############################################################################

	public String toStringCSV(){
		String str = "";
		
		str += this.getIdChaine().toString() + ";";
		str += this.getLibelle() + ";";
		str += this.methodeProdChaine.getIdTest().toString() + "\n";
		
		return str;
	}
	
	/**Permet de produire, renvoie le stock de fin, si on produit vraiment
	 * 
	 * @param testProd un test contenant un <b>resultat possible </b>!! 
	 * @throws IOException 
	 */
	public StockElement produire(TestSimple testProd) throws IOException{
		//TODO : handle errors, si resultat = null ou si resultat pas possible etc
		return testProd.getResultatProd().getStockSortie();
	}
	
}
