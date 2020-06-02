package GestionePrenotazioni;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.InterfacciaGrafica;
import Entity.PazienteEntity;
import Entity.PrenotazioneEntity;
import Entity.Prestazione;
import Entity.Reparto;
import GestioneEmergenze.SceltaRepartoBoundary;

import java.sql.SQLException;


/**Classe che si occupa di permettere ad un medico di scegliere una prestazione da fare effettuare ad un paziente ove necessario*/
public class SceltaPrestazioneControl {

	/**Riferimento al reparto del medico che ha effettuato l'accesso*/
	private static Reparto reparto;

	/**Metodo getter della variabile reparto*/
	public static Reparto getReparto() {
		return reparto;
	}

	/**Metodo setter della variabile reparto*/
	public static void setReparto(Reparto reparto) {
		SceltaPrestazioneControl.reparto = reparto;
	}

	/**Costruttore che invoca il metodo aggiorna
	 * @see SceltaPrestazioneControl#aggiorna() */
	public SceltaPrestazioneControl() {
		SceltaRepartoBoundary.setControl(this);
		aggiorna();
	}

	/**Metodo che mostra la finestra SceltaPrestazioneBoundary
	 * @see SceltaPrestazioneBoundary*/
	public static void sceltaPrestazione() {
		try {
			SceltaPrestazioneBoundary.setPrestazioni(DBMSospedale.get().getPrestazioni(SceltaPrestazioneControl.reparto));
			InterfacciaGrafica.mostra("/GestionePrenotazioni/SceltaPrestazioneBoundary.fxml");
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}

	/**Metodo che setta la prenotazione e mostra la finestra SelezioneDataBoundary
	 * @see SelezionaDataBoundary*/
	static void conferma(Prestazione prestazione, String urgenza) {
		PrenotazioneEntity.setSelected(new PrenotazioneEntity(PazienteEntity.getSelected().getCodiceFiscale(),prestazione.getNome(),urgenza,null,prestazione.getReparto_idReparto()));
		new SelezionaDataBoundary("SceltaPrestazione");
	}

	/**Metodo che mostra la finestra SceltaRepartoBoundary
	 * @see SceltaRepartoBoundary*/
	static void aggiorna(){
		InterfacciaGrafica.mostra("/GestioneEmergenze/SceltaRepartoBoundary.fxml");
	}


}
