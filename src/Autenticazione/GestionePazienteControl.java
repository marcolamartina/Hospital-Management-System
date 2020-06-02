package Autenticazione;

import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.InterfacciaGrafica;
import Entity.PazienteEntity;

import java.sql.SQLException;


/**Classe che gestisce tutte quelle operazioni che un amministrativo deve fare per poter gestire un determinato paziente*/
class GestionePazienteControl {

	/**Costruttore della control che crea GestionePazienteBoundary*/
	GestionePazienteControl() {
		InterfacciaGrafica.mostra("/Autenticazione/GestionePazienteBoundary.fxml");
	}

	/**Metodo che si occupa di effettuare l'accesso per conto del paziente di cui si è inserito il codice fiscale
	 * @see DBMSospedale#getPaziente(String)
	 * @see PazienteEntity#setSelected(PazienteEntity) */
	static void gestisciPaziente(String codiceFiscale) {
		try {
			PazienteEntity paziente=DBMSospedale.get().getPaziente(codiceFiscale);
			if(paziente!=null){
				PazienteEntity.setSelected(paziente);
				InterfacciaGrafica.mostra("/Autenticazione/MenuPazienteBoundary.fxml");
			}else{
				InterfacciaGrafica.mostra("/Autenticazione/MenuAmministrativoBoundary.fxml");
				new NotificaBoundary("Utente non registrato");
			}
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}

	/**Metodo che mostra il menu dell'utente amministrativo*/
	static void mostraMenuAmministrativo(){
		InterfacciaGrafica.mostra("/Autenticazione/MenuAmministrativoBoundary.fxml");
	}
}
