package Autenticazione;

import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.InterfacciaGrafica;
import Entity.AmministrativoEntity;
import Entity.MedicoEntity;
import Entity.PazienteEntity;

import java.sql.SQLException;


/**Classe che gestisce tutte le operazioni per quanto riguarda all’accesso di qualsiasi tipo di utente*/
class AccessControl{

	/**Variabile booleana che rappresenta Medico per il valore true e Amministrativo per il valore false*/
	private static boolean scelta;

	/** Costruttore della control che si occupa dell'accesso di un utente amministrativo
	 * @see AccessImpiegatoBoundary*/
	AccessControl(AmministrativoEntity amministrativoEntity){
		InterfacciaGrafica.mostra("/Autenticazione/AccessImpiegatoBoundary.fxml");
		AccessControl.scelta=false;
	}


	/**Costruttore della control che si occupa dell'accesso di un medico
	 * @see AccessImpiegatoBoundary*/
	AccessControl(MedicoEntity medicoEntity){
		InterfacciaGrafica.mostra("/Autenticazione/AccessImpiegatoBoundary.fxml");
		AccessControl.scelta=true;
	}


	/**Costruttore della control che si occupa dell'accesso di un paziente
	 * @see AccessPazienteBoundary*/
	AccessControl(PazienteEntity pazienteEntity){
		InterfacciaGrafica.mostra("/Autenticazione/AccessPazienteBoundary.fxml");
	}


	/** Metodo che si occupa di effettuare l'accesso di un impiegato
	 * @see DBMSospedale#getMedico(String, String)
	 * @see DBMSospedale#getAmministrativo(String, String)
	 * @see AmministrativoEntity#setSelected(AmministrativoEntity)
	 * @see MedicoEntity#setSelected(MedicoEntity) */
	static void accediImpiegato(String codiceFiscale, String password){
		try {
			if(scelta) {
				MedicoEntity.setSelected(DBMSospedale.get().getMedico(codiceFiscale, password));
				if (MedicoEntity.getSelected() != null) {
					InterfacciaGrafica.mostra("/Autenticazione/MenuMedicoBoundary.fxml");
				} else {
					new NotificaBoundary("Nessun medico trovato");
				}
			}else {
				AmministrativoEntity.setSelected(DBMSospedale.get().getAmministrativo(codiceFiscale,password));
				if(AmministrativoEntity.getSelected()!=null){
					InterfacciaGrafica.mostra("/Autenticazione/MenuAmministrativoBoundary.fxml");
				}else{
					new NotificaBoundary("Nessun utente amministrativo trovato");
				}
			}
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}


	/**Metodo che si occupa di effettuare l'accesso di un paziente
	 * @see DBMSospedale#getPaziente(String, String)
	 * @see PazienteEntity#setSelected(PazienteEntity) */
	static void accediPaziente(String codiceFiscale, String password){
		try {
			PazienteEntity.setSelected(DBMSospedale.get().getPaziente(codiceFiscale,password));
			if(PazienteEntity.getSelected()!=null){
				InterfacciaGrafica.mostra("/Autenticazione/MenuPazienteBoundary.fxml");
			}else{
				new NotificaBoundary("Nessun utente trovato");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}


	/**Metodo che serve a mostrare la schermata di benvenuto
	 * @see WelcomeBoundary*/
	static void mostraWelcomeBoundary(){
		InterfacciaGrafica.mostra("/Autenticazione/WelcomeBoundary.fxml");
	}



}