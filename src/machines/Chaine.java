package machines;

import java.util.UUID;

/**
 * Les chaînes sont les elements peres des chainesProduction, à ce niveau, elles ont un 
 * ID et un libelle
 * @author Aympab
 *
 */
public abstract class Chaine {
	
	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS       						   #
	//##############################################################################
	//##############################################################################
	
	/**
	 * L'identifiant de la Chaine
	 */
	private UUID idChaine;
	
	/**
	 * Le libelle (nom, description) de la chaine
	 */
	private String libelle;
	

	//##############################################################################
	//##############################################################################
	//									CONSTRUCTEURS   						   #
	//##############################################################################
	//##############################################################################

	public Chaine(){
		this("ChaineInconnue");
	}
	
	public Chaine(String libelle){
		this(UUID.randomUUID().toString(), libelle);
	}
	
	public Chaine(String idChaine, String libelle){
		this.libelle = libelle;
		this.idChaine = UUID.fromString(idChaine);
	}
	
	public Chaine(Chaine chaine){
		this.idChaine = chaine.getIdChaine();
		this.libelle = chaine.getLibelle();
	}

	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################

	public UUID getIdChaine() {
		return idChaine;
	}

	public String getLibelle() {
		return libelle;
	}

	
	//##############################################################################
	//##############################################################################
	//									   METHODES 							   #
	//##############################################################################
	//##############################################################################
	
	/**
	 * @return Une ligne qu'on peut insérer directement dans le CSV
	 */
	public abstract String toStringCSV();
	
}
