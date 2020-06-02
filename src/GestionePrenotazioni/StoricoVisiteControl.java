package GestionePrenotazioni;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.InterfacciaGrafica;
import Entity.PazienteEntity;

import java.sql.SQLException;
import java.util.List;


/**Classe che si occupa delle operazioni per garantire la visualizzazione dello storico delle visite di un determinato paziente*/
public class StoricoVisiteControl {

	/**Lista delle visite già effettuate del paziente che ha effettuato l'accesso*/
	private static List<String> storicoVisite;

	/**Metodo getter di storicoVisite*/
	public static List<String> getStoricoVisite() {
		return storicoVisite;
	}

	/**Metodo che mostra la finestra StoricoVisiteBoundary
	 * @see StoricoVisiteBoundary*/
	public StoricoVisiteControl() {
		try {
			StoricoVisiteControl.storicoVisite=DBMSospedale.get().getStoricoVisite(PazienteEntity.getSelected());
			InterfacciaGrafica.mostra("/GestionePrenotazioni/StoricoVisiteBoundary.fxml");
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}

	/**Metodo che mostra la finestra MenuPazienteBoundary
	 * @see Autenticazione.MenuPazienteBoundary*/
	static void indietro() {
		InterfacciaGrafica.mostra("/Autenticazione/MenuPazienteBoundary.fxml");
	}

}
