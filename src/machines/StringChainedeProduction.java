package machines;


/**
 *
 *Permet de remplir les tables de ChaineProdView.fxml rapidement et facilement
 */
public class StringChainedeProduction {
	
	private String idChaine;
	private String nomChaine;
	private String idManuel;
	
	public StringChainedeProduction(String idChaine, String nomChaine, String idManuel) {
		super();
		this.idChaine = idChaine;
		this.nomChaine = nomChaine;
		this.idManuel = idManuel;
	}

	public StringChainedeProduction(StringChainedeProduction cp) {
		this(cp.getIdChaine(), cp.getNomChaine(), cp.getIdManuel());
	}

	public String getIdChaine() {
		return idChaine;
	}

	public void setIdChaine(String idChaine) {
		this.idChaine = idChaine;
	}

	public String getNomChaine() {
		return nomChaine;
	}

	public void setNomChaine(String nomChaine) {
		this.nomChaine = nomChaine;
	}

	public String getIdManuel() {
		return idManuel;
	}

	public void setIdManuel(String idManuel) {
		this.idManuel = idManuel;
	}
	
}
