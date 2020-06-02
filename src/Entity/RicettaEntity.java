package Entity;


/**Classe che contiene le informazioni di una determinata ricetta: codice ricetta, urgenza, nome della prestazione, esenzione dal pagamento, codice fiscale del richiedente, reparto, diagnosi effettuata dal medico curante*/
public class RicettaEntity {
	/**Codice identificativo della ricetta*/
	private String codiceRicetta;

	/**Urgenza della prestazione, rappresentata dalle lettere U,B,D,P, che identificano i giorni entro i quali devono essere effettuate le prestazioni
	 * U: Prestazione da eseguire entro 24 ore.
	 * B: Prestazione da eseguire entro 10 gg.
	 * D: Prestazione da eseguire entro 60 gg.
	 * P: Prestazione da eseguire entro 180 gg.
	 */
	private String urgenza;

	/**Diagnosi effettuata dal medico curante*/
	private String diagnosi;

	/**Nome della prestazione*/
	private String prestazione;

	/**Nome del reparto che effettua la prestazione*/
	private String reparto;

	/**Codice fiscale del paziente che ha richiesto la ricetta*/
	private String codiceFiscale;

	/**Valore booleano che indica se il paziente è esente o no dal pagamento del ticket*/
	private boolean esenzione;

	/**Istanza di un oggetto di tipo RicettaEntity che rappresenta la ricetta della prestazione sanitaria che si vuole prenotare*/
	private static RicettaEntity selected = null;


	/**Metodo getter della variabile selected*/
	public static RicettaEntity getSelected() {
		return selected;
	}


	/**Metodo setter della variabile selected*/
	public static void setSelected(RicettaEntity selected) {
		RicettaEntity.selected = selected;
	}


	/**Costruttore che setta tutte le variabili*/
	private RicettaEntity(String codiceRicetta, String urgenza, String diagnosi, String prestazione, String reparto, String codiceFiscale, boolean esenzione) {
		this.codiceRicetta = codiceRicetta;
		this.urgenza = urgenza;
		this.diagnosi = diagnosi;
		this.prestazione = prestazione;
		this.reparto = reparto;
		this.codiceFiscale = codiceFiscale;
		this.esenzione = esenzione;

	}


	/**Metodo per la creazione di un nuovo RicettaEntity con tutte le variabili settate*/
	public static RicettaEntity create(String codiceRicetta, String urgenza, String diagnosi, String prestazione, String reparto, String codiceFiscale, boolean esenzione){
		return new RicettaEntity(codiceRicetta, urgenza, diagnosi, prestazione, reparto, codiceFiscale, esenzione);
	}


	/**Metodo getter della variabile codiceRicetta*/
	public String getCodiceRicetta() {
		return codiceRicetta;
	}


	/**Metodo getter della variabile urgenza*/
	public String getUrgenza() {
		return urgenza;
	}


	/**Metodo getter della variabile diagnosi*/
	public String getDiagnosi() {
		return diagnosi;
	}


	/**Metodo getter della variabile prestazione*/
	public String getPrestazione() {
		return prestazione;
	}


	/**Metodo getter della variabile reparto*/
	public String getReparto() {
		return reparto;
	}


	/**Metodo getter della variabile codiceFiscale*/
	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	/**Metodo getter della variabile esenzione*/
	public boolean isEsenzione() {
		return esenzione;
	}


	/**Override del metodo toString della classe Object per ottenere una stringa con tutte le informazioni della ricetta
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		String stringa="";
		stringa+="Prestazione ricetta: "+this.prestazione;
		stringa+="\nReparto: "+this.reparto;
		stringa+="\nCodice ricetta: "+this.codiceRicetta;
		stringa+="\nCodice fiscale richiedente: "+this.getCodiceFiscale();
		stringa+="\nLivello di esenzione: ";
		if(esenzione){
			stringa+="esente";
		}else{
			stringa+="non esente";
		}
		stringa+="\nUrgenza: "+this.urgenza;
		stringa+="\nDiagnosi: "+this.diagnosi;
		return stringa;
	}
}
