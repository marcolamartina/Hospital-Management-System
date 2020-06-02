package GestioneEmergenze;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.InterfacciaGrafica;
import Entity.Ambulatorio;
import Entity.PrenotazioneEntity;
import Entity.Reparto;
import GestionePrenotazioni.GestisciSpostamentiControl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


/**Classe che si occupa di gestire tutte le emergenze: permette tutte le operazioni che sono necessarie sia per aggiungere un’emergenza, eliminare un’emergenza e modificare un’emergenza*/
public class GestioneEmergenzeControl {

	/**Riferimento all'ambulatorio*/
	private static Ambulatorio ambulatorio;

	/**Riferimento alla variabile obiettivo*/
	private static String obiettivo;

	/**Metodo per impostare l'operazione da effettuare*/
	static void setObiettivo(String obiettivo) {
		GestioneEmergenzeControl.obiettivo = obiettivo;
	}

	/**Metodo che restituisce l'operazione da effettuare*/
	static String getObiettivo() {
		return obiettivo;
	}

	/**Metodo che mostra la finestra GestioneEmergenzeBoundary
	 * @see GestioneEmergenzeBoundary*/
	public GestioneEmergenzeControl() {
		InterfacciaGrafica.mostra("/GestioneEmergenze/GestioneEmergenzeBoundary.fxml");
		SceltaRepartoBoundary.setControl(this);
		SceltaAmbulatorioBoundary.setControl(this);
	}

	/**Metodo che mostra la finestra SceltaRepartoBoundary
	 * @see SceltaRepartoBoundary*/
	static void selezionaReparto() {
		InterfacciaGrafica.mostra("/GestioneEmergenze/SceltaRepartoBoundary.fxml");
	}

	/**Metodo che mostra la finestra SceltaAmbulatorioBoundary dopo aver selezionato il reparto
	 * @see SceltaAmbulatorioBoundary*/
	static void selezionaAmbulatorio(Reparto reparto) {
		SceltaAmbulatorioBoundary.setReparto(reparto);
		InterfacciaGrafica.mostra("/GestioneEmergenze/SceltaAmbulatorioBoundary.fxml");
	}

	/**Metodo che mostra la finestra SceltaAmbulatorioBoundary
	 * @see SceltaAmbulatorioBoundary*/
	static void selezionaAmbulatorio() {
		InterfacciaGrafica.mostra("/GestioneEmergenze/SceltaAmbulatorioBoundary.fxml");
	}

	/**Metodo che mostra la finestra SelezionaDateEmergenzaBoundary dopo aver selezionato l'ambulatorio
	 * @see SelezionaDateEmergenzaBoundary*/
	static void selezionaDate(Ambulatorio ambulatorio) {
		GestioneEmergenzeControl.ambulatorio=ambulatorio;
		InterfacciaGrafica.mostra("/GestioneEmergenze/SelezionaDateEmergenzaBoundary.fxml");
	}

	/**Metodo che comunica al DBMS di aggiungere un emergenza per le date selezionate e di gestire eventuali spostamenti
	 * @see DBMSospedale#aggiungiEmergenza(Ambulatorio, LocalDate, LocalDate)
	 * @see GestisciSpostamentiControl#gestisciSpostamenti(List) */
	public void aggiungiEmergenza(LocalDate dataInizio, LocalDate dataFine) {
		try {
			// potrebbe servire if((dataFine.isBefore(ambulatorio.getDataInizio()) && dataInizio.isBefore(ambulatorio.getDataInizio())) || (dataInizio.isAfter(ambulatorio.getDataFine()) && dataFine.isAfter(ambulatorio.getDataFine()))){
			List<PrenotazioneEntity> lista = DBMSospedale.get().getPrenotazioni(ambulatorio, dataInizio, dataFine);
			DBMSospedale.get().aggiungiEmergenza(ambulatorio, dataInizio, dataFine);
			GestisciSpostamentiControl.gestisciSpostamenti(lista);
			mostraMenuAmministrativo();
			if (GestioneEmergenzeControl.getObiettivo()!=null && GestioneEmergenzeControl.getObiettivo().compareTo("Modifica")==0) {
				new NotificaBoundary("Emergenza modificata correttamente");
			} else {
				new NotificaBoundary("Emergenza aggiunta correttamente");
			}
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}

	/**Metodo che comunica al DBMS di eliminare l'emergenza nell'ambulatorio selezionato
	 * @see DBMSospedale#eliminaEmergenza(Ambulatorio) */
	static void eliminaEmergenza(Ambulatorio ambulatorio) {
		try{
			DBMSospedale.get().eliminaEmergenza(ambulatorio);
			mostraMenuAmministrativo();
			new NotificaBoundary("Emergenza eliminata correttamente");
		}catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}

	/**Metodo che mostra la finestra ListaEmergenzeBoundary
	 * @see ListaEmergenzeBoundary */
	static void selezionaEmergenza() {
		InterfacciaGrafica.mostra("/GestioneEmergenze/ListaEmergenzeBoundary.fxml");
	}

	/**Metodo che consente di modificare le date di inizio e fine emergenza*/
	public void modificaEmergenza(LocalDate dataInizio, LocalDate dataFine) {
		aggiungiEmergenza(dataInizio,dataFine);
	}

	/**Metodo che mostra la finestra MenuAmministrativoBoundary
	 * @see Autenticazione.MenuAmministrativoBoundary*/
	static void mostraMenuAmministrativo(){
		InterfacciaGrafica.mostra("/Autenticazione/MenuAmministrativoBoundary.fxml");
	}

	/**Metodo che mostra la finestra GestioneEmergenzeBoundary
	 * @see GestioneEmergenzeBoundary*/
	static void mostraMenu(){
		InterfacciaGrafica.mostra("/GestioneEmergenze/GestioneEmergenzeBoundary.fxml");
	}

}
