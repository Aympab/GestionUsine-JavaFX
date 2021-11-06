package production;

public class StringManuelProd {
		
		private String idElement;
		private String libelle;
		private String qteElement;
		private String uniteQte;
		private String prixAchat;
		private String prixVente;
		
		private String qteUniteElement;
		private String prixAchatTotal;
		private String prixVenteTotal;
		 
		
		public StringManuelProd(String idElement, String libelle, String qteElement, String uniteQte,
				String prixAchat, String prixVente) {
			super();
			this.idElement = idElement;
			this.libelle = libelle;
			this.qteElement = qteElement;
			this.uniteQte = uniteQte;
			this.prixAchat = prixAchat;
			this.prixVente = prixVente;
			
			this.calculerChamps();
		}

		private void calculerChamps(){
			this.qteUniteElement = qteElement + " " + uniteQte;
			
			if(!prixAchat.equals("NA")){
				this.prixAchatTotal  = Double.toString(Double.parseDouble(prixAchat) * Double.parseDouble(qteElement));
			}
			
			if(!prixVente.equals("NA")){
				this.prixVenteTotal  = Double.toString(Double.parseDouble(prixVente) * Double.parseDouble(qteElement));
			}
		}
		
		public StringManuelProd(StringManuelProd cp) {
			this(cp.getIdElement(), cp.getLibelle(), cp.getQteElement(), cp.getUniteQte(), cp.getPrixAchat(), cp.getPrixVente());
		}


		public String getIdElement() {
			return idElement;
		}


		public void setIdElement(String idElement) {
			this.idElement = idElement;
		}


		public String getLibelle() {
			return libelle;
		}


		public void setLibelle(String libelle) {
			this.libelle = libelle;
		}


		public String getQteElement() {
			return qteElement;
		}


		public void setQteElement(String qteElement) {
			this.qteElement = qteElement;
			this.calculerChamps();
		}


		public String getUniteQte() {
			return uniteQte;
		}


		public void setUniteQte(String uniteQte) {
			this.uniteQte = uniteQte;
			this.calculerChamps();	
		}


		public String getPrixAchat() {
			return prixAchat;
		}


		public void setPrixAchat(String prixAchat) {
			this.prixAchat = prixAchat;
			this.calculerChamps();
		}


		public String getPrixVente() {
			return prixVente;
		}


		public void setPrixVente(String prixVente) {
			this.prixVente = prixVente;
			this.calculerChamps();
		}


		public String getQteUniteElement() {
			return qteUniteElement;
		}


		public void setQteUniteElement(String qteUniteElement) {
			this.qteUniteElement = qteUniteElement;
		}


		public String getPrixAchatTotal() {
			return prixAchatTotal;
		}


		public void setPrixAchatTotal(String prixAchatTotal) {
			this.prixAchatTotal = prixAchatTotal;
		}


		public String getPrixVenteTotal() {
			return prixVenteTotal;
		}


		public void setPrixVenteTotal(String prixVenteTotal) {
			this.prixVenteTotal = prixVenteTotal;
		}
		
		
		public String toStringManuel(){
			return "("+this.idElement+","+this.qteElement+")";
		}
		
		

}
