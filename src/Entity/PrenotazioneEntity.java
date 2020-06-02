package Entity;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**Classe che contiene le informazioni di una determinata prenotazione effettuata da un utente: data, orario, nome della prestazione, urgenza, ambulatorio, prezzo, ed eventuali dati inseriti da un Medico o dal medico curante*/
public class PrenotazioneEntity {
	/**Data e ora della prenotazione*/
	private LocalDateTime dataOra;


	/**Codice fiscale di chi ha prenotato la prestazione*/
	private String Utente_codice_fiscale;


	/**Nome della prestazione prenotata*/
	private String Prestazione_nome;


	/**Urgenza della prestazione, rappresentata dalle lettere U,B,D,P, che identificano i giorni entro i quali devono essere effettuate le prestazioni
	 * U: Prestazione da eseguire entro 24 ore.
	 * B: Prestazione da eseguire entro 10 gg.
	 * D: Prestazione da eseguire entro 60 gg.
	 * P: Prestazione da eseguire entro 180 gg.
	 */
	private String urgenza;


	/**Diagnosi effettuata dal medico specialista a seguito della visita*/
	private String diagnosi;


	/**Anamnesi effettuata dal medico specialista a seguito della visita*/
	private String anamnesi;


	/**Cure proposte dal medico specialista a seguito della visita*/
	private String cure;


	/**Diagnosi effettuata dal medico curante e inserita nella ricetta*/
	private String diagnosi_medico_curante;


	/**Identificativo dell'ambulatorio in cui si svolgerà la prenotazione*/
	private int Ambulatorio_idAmbulatorio;


	/**Identificativo del reparto in cui si svolgerà la prenotazione*/
	private int Ambulatorio_Reparto_idReparto;


	/**Prezzo della prestazione*/
	private double prezzo;


	/**Valore booleano che indica se la prenotazione è stat pagata o no*/
	private boolean pagata;


	/**Istanza di un oggetto di tipo PrenotazioneEntity che rappresenta la prenotazionbe selezionata durante l'utilizzo del sistema*/
	private static PrenotazioneEntity selected;


	/**Metodo getter della variabile selected*/
	public static PrenotazioneEntity getSelected() {
		return selected;
	}


	/**Metodo setter della variabile selected*/
	public static void setSelected(PrenotazioneEntity selected) {
		PrenotazioneEntity.selected = selected;
	}

	/**Costruttore con tutte le variabili settate*/
	public PrenotazioneEntity(LocalDateTime dataOra, String Utente_codice_fiscale, String prestazione_nome, String urgenza, String diagnosi, String anamnesi, String cure, String diagnosi_medico_curante, int ambulatorio_idAmbulatorio, int ambulatorio_Reparto_idReparto, double prezzo, boolean pagata) {
		this.dataOra = dataOra;
		this.Utente_codice_fiscale = Utente_codice_fiscale;
		Prestazione_nome = prestazione_nome;
		this.urgenza = urgenza;
		this.diagnosi = diagnosi;
		this.anamnesi = anamnesi;
		this.cure = cure;
		this.diagnosi_medico_curante = diagnosi_medico_curante;
		Ambulatorio_idAmbulatorio = ambulatorio_idAmbulatorio;
		Ambulatorio_Reparto_idReparto = ambulatorio_Reparto_idReparto;
		this.prezzo = prezzo;
		this.pagata = pagata;
	}


	/**Costruttore con le variabili Utente_codice_fiscale, Prestazione_nome, urgenza, diagnosi_medico_curante e Ambulatorio_Reparto_idReparto settate*/
	public PrenotazioneEntity(String utente_codice_fiscale, String prestazione_nome, String urgenza, String diagnosi_medico_curante, int ambulatorio_Reparto_idReparto) {
		Utente_codice_fiscale = utente_codice_fiscale;
		Prestazione_nome = prestazione_nome;
		this.urgenza = urgenza;
		this.diagnosi_medico_curante = diagnosi_medico_curante;
		Ambulatorio_Reparto_idReparto = ambulatorio_Reparto_idReparto;
	}

	/**Metodo per la creazione di un nuovo PrenotazioneEntity con tutte le variabili settate*/
	public static PrenotazioneEntity create(LocalDateTime dataOra, String Utente_codice_fiscale, String prestazione_nome, String urgenza, String diagnosi, String anamnesi, String cure, String diagnosi_medico_curante, int ambulatorio_idAmbulatorio, int ambulatorio_Reparto_idReparto, double prezzo, boolean pagata){
		return new PrenotazioneEntity(dataOra, Utente_codice_fiscale, prestazione_nome, urgenza, diagnosi, anamnesi, cure, diagnosi_medico_curante, ambulatorio_idAmbulatorio, ambulatorio_Reparto_idReparto, prezzo, pagata);
	}


	/**Metodo setter della variabile dataOra*/
	public void setDataOra(LocalDateTime dataOra) {
		this.dataOra = dataOra;
	}


	/**Metodo setter della variabile diagnosi*/
	public void setDiagnosi(String diagnosi) {
		this.diagnosi = diagnosi;
	}


	/**Metodo setter della variabile anamnesi*/
	public void setAnamnesi(String anamnesi) {
		this.anamnesi = anamnesi;
	}


	/**Metodo setter della variabile cure*/
	public void setCure(String cure) {
		this.cure = cure;
	}


	/**Metodo setter della variabile Ambulatorio_idAmbulatorio*/
	public void setAmbulatorio_idAmbulatorio(int ambulatorio_idAmbulatorio) {
		Ambulatorio_idAmbulatorio = ambulatorio_idAmbulatorio;
	}


	/**Metodo setter della variabile prezzo*/
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}


	/**Metodo getter della variabile dataOra*/
	public LocalDateTime getDataOra() {
		return dataOra;
	}


	/**Metodo getter della variabile Utente_codice_fiscale*/
	public String getUtente_codice_fiscale() {
		return Utente_codice_fiscale;
	}


	/**Metodo getter della variabile Prestazione_nome*/
	public String getPrestazione_nome() {
		return Prestazione_nome;
	}


	/**Metodo getter della variabile Ambulatorio_Reparto_idReparto*/
	public int getAmbulatorio_Reparto_idReparto() {
		return Ambulatorio_Reparto_idReparto;
	}


	/**Metodo getter della variabile urgenza*/
	public String getUrgenza() {
		return urgenza;
	}


	/**Metodo getter della variabile diagnosi*/
	public String getDiagnosi() {
		return diagnosi;
	}


	/**Metodo getter della variabile anamnesi*/
	public String getAnamnesi() {
		return anamnesi;
	}


	/**Metodo getter della variabile cure*/
	public String getCure() {
		return cure;
	}


	/**Metodo getter della variabile diagnosi_medico_curante*/
	public String getDiagnosi_medico_curante() {
		return diagnosi_medico_curante;
	}


	/**Metodo getter della variabile Ambulatorio_idAmbulatorio*/
	public int getAmbulatorio_idAmbulatorio() {
		return Ambulatorio_idAmbulatorio;
	}


	/**Metodo getter della variabile prezzo*/
	public double getPrezzo() {
		return prezzo;
	}


	/**Metodo getter della variabile pagata*/
	public boolean isPagata() {
		return pagata;
	}


	/**Metodo setter della variabile pagata*/
	public void setPagata(boolean pagata) {
		this.pagata = pagata;
	}


	/**Metodo per ottenere una stringa con tutte le informazioni della prenotazione*/
	public String toFormatted()throws Exception{
        String stringa= "Prestazione: " +Prestazione_nome+"\nReparto: "+ DBMSospedale.get().getReparto(Ambulatorio_Reparto_idReparto).getNome()+"\nNumero di ambulatorio: " +Ambulatorio_idAmbulatorio+"\nData e ora: "+dataOra.withMinute(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))+"\nUrgenza: " +urgenza;
        if(anamnesi!=null) stringa+="\nAnamnesi: "+anamnesi;
        if(diagnosi_medico_curante!=null) stringa+="\nDiagnosi del medico curante: "+diagnosi_medico_curante;
        if(diagnosi!=null) stringa+="\nDiagnosi: "+diagnosi;
        if(cure!=null) stringa+="\nCure proposte: "+cure;
        stringa+="\n-----------------------------------------------------------------\n";
        return stringa;
    }


	/**Override del metodo toString della classe Object per ottenere una stringa con tutte le informazioni della prenotazione
	 * @see Object#toString()
	 */
	@Override
	public String toString(){
		try {
			return this.toFormatted();
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
		return null;
	}


	/**Metodo per creare una nuova istanza di PrenotazioneEntity con le stesse variabili dell'istanza che richiama il metodo*/
	public PrenotazioneEntity copia(){
		return new PrenotazioneEntity(dataOra, Utente_codice_fiscale, Prestazione_nome, urgenza, diagnosi, anamnesi, cure, diagnosi_medico_curante, Ambulatorio_idAmbulatorio, Ambulatorio_Reparto_idReparto, prezzo, pagata);
	}
}
