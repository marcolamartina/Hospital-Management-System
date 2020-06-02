package GestionePrenotazioni;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.InterfacciaGrafica;
import ComponentiEsterne.Mail;
import Entity.PazienteEntity;
import Entity.PrenotazioneEntity;
import javafx.concurrent.Task;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**Classe che permette tutte le operazioni volte all’aggiunta di una prenotazione, alla modifica di una prenotazione, all’eliminazione di una prenotazione o alla visualizzazione dei documenti necessari alla prestazione da effettuare*/
public class GestionePrenotazioniControl {

	/**Lista delle prenotazioni del paziente che ha effettuato l'accesso*/
	private static List<PrenotazioneEntity> prenotazioni;


	/**Metodo getter della variabile prenotazioni*/
	public static List<PrenotazioneEntity> getPrenotazioni() {
		return prenotazioni;
	}

	/**Costruttore che invoca il metodo aggiorna
	 * @see GestionePrenotazioniControl#aggiorna() */
	public GestionePrenotazioniControl() {
		aggiorna();
	}


	/**Metodo per comunicare al DBMS di eliminare la prenotazione passata come parametro
	 * @see DBMSospedale#cancellaPrenotazione(PrenotazioneEntity) */
	static void elimina(PrenotazioneEntity prenotazione) {
		try {
			DBMSospedale.get().cancellaPrenotazione(prenotazione);
			Task task=new Task(){
				@Override
				protected Object call() throws Exception {
					Mail.get().eliminaPrenotazione(prenotazione);
					return null;
				}
			};
			Thread thread=new Thread(task);
			thread.setDaemon(true);
			thread.start();

			aggiorna();
			new NotificaBoundary("Prenotazione eliminata con successo");
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}


	/**Metodo per interrogare il DBMS sui documenti necessari per la prenotazione passata come parametro e per visualizzarli nella finestra ListaDocumentiBoundary
	 * @see DBMSospedale#getDocumenti(PrenotazioneEntity)
	 * @see ListaDocumentiBoundary*/
	static void documenti(PrenotazioneEntity prenotazione) {
		try {
			String documenti="Nessun documento";
			String stringa=DBMSospedale.get().getDocumenti(prenotazione);
			if(stringa!=null){
				documenti=stringa;
			}
			ListaDocumentiBoundary.setDocumenti(documenti);
			InterfacciaGrafica.mostra("/GestionePrenotazioni/ListaDocumentiBoundary.fxml");
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}


	/**Metodo che salva la prenotazione selezionata e mostra la finestra SelezionaDataBoundary
	 * @see SelezionaDataBoundary
	 * @see PrenotazioneEntity#setSelected(PrenotazioneEntity) */
	static void modifica(PrenotazioneEntity prenotazione) {
		PrenotazioneEntity.setSelected(prenotazione);
		new SelezionaDataBoundary("ModificaData");
	}


	/**Metodo che chiede al DBMS le prenotazioni del paziente che ha effettuato l'accesso e le mostra nella finestra GestionePrenotazioniBoundary
	 * @see DBMSospedale#getPrenotazioni(PazienteEntity)
	 * @see GestionePrenotazioniBoundary
	 */
	static void aggiorna(){
		try {
			GestionePrenotazioniControl.prenotazioni=DBMSospedale.get().getPrenotazioni(PazienteEntity.getSelected());
			InterfacciaGrafica.mostra("/GestionePrenotazioni/GestionePrenotazioniBoundary.fxml");
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}


	/**Metodo che mostra il menu del paziente
	 * @see Autenticazione.MenuPazienteBoundary
	 */
	static void mostraMenuPaziente(){
		InterfacciaGrafica.mostra("/Autenticazione/MenuPazienteBoundary.fxml");
	}


	/**Metodo per formattare la visualizzazione della lista prenotazioni
	 * @see GestionePrenotazioniControl#prenotazioni
	 */
	static List<String> parserList(){
		try {
			List<String> lista=new ArrayList<>();
			for(PrenotazioneEntity pren:GestionePrenotazioniControl.getPrenotazioni()){
				String stringa="";
				/*stringa+=pren.getPrestazione_nome();
				while(stringa.length()<30){
					stringa+=" ";
				}
				stringa+=DBMSospedale.get().getReparto(pren.getAmbulatorio_Reparto_idReparto()).getNome();
				while(stringa.length()<55){
					stringa+=" ";
				}
				stringa+="Ambulatorio n°"+pren.getAmbulatorio_idAmbulatorio();
				while(stringa.length()<90){
					stringa+=" ";
				}
				stringa+=pren.getPrezzo()+"0€";
				while(stringa.length()<105){
					stringa+=" ";
				}
				stringa+=pren.getDataOra().withMinute(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));*/


				stringa=String.format("%1s %30s%30s%1s %15s0€ %25s",pren.getPrestazione_nome(), DBMSospedale.get().getReparto(pren.getAmbulatorio_Reparto_idReparto()).getNome(), "Ambulatorio n°",pren.getAmbulatorio_idAmbulatorio(), pren.getPrezzo(),pren.getDataOra().withMinute(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
				lista.add(stringa);
			}
			return lista;
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
		return null;
	}

}
