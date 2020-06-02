package Entity;

import java.time.LocalDate;


/**Classe che contiene le informazioni personali di un determinato paziente all'interno del sistema, ereditate da Utente. */
public class PazienteEntity extends Utente{
	/**Valore booleano che indica se il paziente è esente o no dal pagamento del ticket*/
	private boolean esenzione;

	/**Istanza di un oggetto di tipo PazienteEntity che rappresenta il medico attualmente loggato nel sistema*/
	private static PazienteEntity selected = null;


	/**Costruttore che setta tutte le variabili*/
	private PazienteEntity(String codiceFiscale, String nome, String cognome, LocalDate dataDiNascita, String luogoDiNascita, String email, String telefono, String password, boolean esenzione) {
		super(codiceFiscale,nome,cognome,dataDiNascita,luogoDiNascita,email,telefono,password);
		this.esenzione = esenzione;
	}


	/**Metodo per la creazione di un nuovo PazienteEntity con tutte le variabili settate*/
	public static PazienteEntity create(String codiceFiscale, String nome, String cognome, LocalDate dataDiNascita, String luogoDiNascita, String email, String telefono, String password, boolean esenzione){
		return new PazienteEntity(codiceFiscale,nome,cognome,dataDiNascita,luogoDiNascita,email,telefono,password,esenzione);
	}


	/**Override del metodo toString della classe Object per ottenere una stringa con nome, cognome, codice fiscale, data e luogo di nascita del paziente
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		String stringa="";
		stringa+=getCognome()+" "+getNome();
		while(stringa.length()<35){
			stringa+=" ";
		}
		stringa+=getCodiceFiscale()+"         Data di nascita:"+getDataDiNascita()+" a "+getLuogoDiNascita();
		return stringa;

	}


	/**Metodo che restituisce una stringa con nome, cognome e codice fiscale del paziente*/
	public String toFormatted() {
		String stringa="";
		stringa+=getCognome()+" "+getNome();
		stringa+="\n"+getCodiceFiscale();
		return stringa;

	}


	/**Metodo getter della variabile esenzione*/
	public boolean getEsenzione() {
		return esenzione;
	}


	/**Metodo getter della variabile selected*/
	public static PazienteEntity getSelected() {
		return selected;
	}


	/**Metodo setter della variabile selected*/
	public static void setSelected(PazienteEntity selected) {
		PazienteEntity.selected = selected;
	}

}
