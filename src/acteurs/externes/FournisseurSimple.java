package acteurs.externes;

import java.util.UUID;
import listes.ListeAchat;
import stockage.StockElement;

/**
 * @author Aympab
 *<br> c'est aupr�s des fournisseurs qu'on va se r�approvisionner en MP
 */
public class FournisseurSimple {
	//TODO : classe pas du tout test�e
	//##############################################################################
	//##############################################################################
	//									ATTRIBUTS       						   #
	//##############################################################################
	//##############################################################################

	private UUID idFournisseur;
	
	private String nomFournisseur;
	
	/**
	 * Un stock compos� des MatierePremires disponibles chez tel fournisseur
	 */
	private ListeAchat listeMpDispo;	

	//##############################################################################
	//##############################################################################
	//									CONSTRUCTEURS   						   #
	//##############################################################################
	//##############################################################################

	public FournisseurSimple(){
		this(UUID.randomUUID().toString(), "Fournisseur_Inconnu", new ListeAchat());
	}
	
//	public FournisseurSimple(String idFournisseur){
//		this(idFournisseur, "Fournisseur_Inconnu", new ListeAchat());
//	}
//	
	public FournisseurSimple(String nomFournisseur, ListeAchat listeMpDispo){
		this(UUID.randomUUID().toString(), nomFournisseur, listeMpDispo);
	}
	
	public FournisseurSimple(String id, String nomFournisseur, ListeAchat listeMpDispo){
		this.idFournisseur  = UUID.fromString(id);
		this.nomFournisseur = nomFournisseur;
		this.listeMpDispo   = new ListeAchat(listeMpDispo);
	}
	
	//##############################################################################
	//##############################################################################
	//									METHODES DE BASE						   #
	//##############################################################################
	//##############################################################################

	public UUID getIdFournisseur() {
		return idFournisseur;
	}

	public String getNomFournisseur() {
		return nomFournisseur;
	}

	public ListeAchat getListeMpDispo() {
		return listeMpDispo;
	}	
	
	//##############################################################################
	//##############################################################################
	//									   METHODES 							   #
	//##############################################################################
	//##############################################################################
	
	public StockElement reapprovisionnement(StockElement stockReappro, ListeAchat listeA){
		//TODO : Handle errors si l'element de listeA n'existe pas dans la listeMpDispo
		stockReappro.ajouterEnsemble(listeA);
		return stockReappro;
		//TODO : Le fournisseur ne poss�de pas cet element.
	}

}
