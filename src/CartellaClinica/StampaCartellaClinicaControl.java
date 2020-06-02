package CartellaClinica;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.InterfacciaGrafica;
import ComponentiEsterne.PDF;
import Entity.PazienteEntity;
import Entity.PrenotazioneEntity;

import java.sql.SQLException;
import java.util.List;


/**Classe che si occupa di tutte le operazioni affinchè un paziente riesca a visualizzare e stampare la propria cartella clinica a fronte di un pagamento*/
public class StampaCartellaClinicaControl {


	/**Cartella clinica del paziente che ha effettuato l'accesso*/
	private static List<PrenotazioneEntity> cartellaClinica;


	/**Metodo getter della variabile cartellaClinica*/
	public static List<PrenotazioneEntity> getCartellaClinica() {
		return cartellaClinica;
	}


	/**Costruttore che richiede la cartella clinica al DBMS, setta la variabile cartellaClinica con i dati ottenuti e mostra la finestra CartellaClinicaPazienteBoundary
	 * @see DBMSospedale#getCartellaClinica(PazienteEntity)
	 * @see CartellaClinicaPazienteBoundary
	 */
	public StampaCartellaClinicaControl(){
		try {
			StampaCartellaClinicaControl.cartellaClinica= DBMSospedale.get().getCartellaClinica(PazienteEntity.getSelected());
			InterfacciaGrafica.mostra("/CartellaClinica/CartellaClinicaPazienteBoundary.fxml");
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}

	/**Metodo che richiama creaPDF e, successivamente alla creazione del PDF della cartella clinica, mostra un messaggio di avvenuta creazione
	 * @see StampaCartellaClinicaControl#creaPDF()
 	 */
	static void pagamento() {
		//inserire un eventuale pagamento
		try {
			creaPDF();
			new NotificaBoundary("PDF creato con successo");
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}


	/**Metodo che mostra la finestra PrezzoCartellaClinicaBoundary
	 * @see PrezzoCartellaClinicaBoundary */
	static void stampa() {
		new PrezzoCartellaClinicaBoundary("5,00€");
	}


	/**Metodo che crea il PDF della cartella clinica, richiamando il metodo stampaCC della classe PDF
	 * @see PDF#stampaCC(String)
	 */
	private static void creaPDF()throws Exception {
		String stringa="";
		stringa+=PazienteEntity.getSelected().toString()+"\n-----------------------------------------------------------------\n";
		for(PrenotazioneEntity prenotazione:cartellaClinica){
			stringa+=prenotazione.toFormatted();
			stringa+="\n";
		}
		PDF.get().stampaCC(stringa);
	}


	/**Metodo per tornare alla schermata precedente, ovvero il menu del paziente
	 * @see Autenticazione.MenuPazienteBoundary
	 */
	static void indietro() {
		InterfacciaGrafica.mostra("/Autenticazione/MenuPazienteBoundary.fxml");
	}

}
