package Autenticazione;

import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.InterfacciaGrafica;
import Entity.Ambulatorio;
import Entity.Reparto;
import GestioneEmergenze.SceltaAmbulatorioBoundary;
import GestioneEmergenze.SceltaRepartoBoundary;

import java.sql.SQLException;


/**Classe che gestisce tutte le operazioni che un amministrativo deve fare per poter registrare un altro impiegato*/
public class RegistrazioneImpiegatiControl {

	/**Codice fiscale dell'utente che si vuole registrare come impiegato*/
	private static String codiceFiscale;


	/**Costruttore che mostra la RegistrazioneImpiegatiBoundary
	 * @see RegistrazioneImpiegatiBoundary*/
	public RegistrazioneImpiegatiControl() {
		InterfacciaGrafica.mostra("/Autenticazione/RegistrazioneImpiegatiBoundary.fxml");
		SceltaRepartoBoundary.setControl(this);
		SceltaAmbulatorioBoundary.setControl(this);
	}


	/**Metodo che verifica se l'utente è già registrato come impiegato e mostra la finestra SceltaTipoImpiegatoBoundary
	 * @see SceltaTipoImpiegatoBoundary*/
	static void registraImpiegato(String codiceFiscale) {
		try {
			RegistrazioneImpiegatiControl.codiceFiscale=codiceFiscale;
			if(DBMSospedale.get().getPaziente(codiceFiscale)!=null){
				if(DBMSospedale.get().getMedico(codiceFiscale)!=null){
					InterfacciaGrafica.mostra("/Autenticazione/MenuAmministrativoBoundary.fxml");
					new NotificaBoundary("Utente già registrato come medico");
				} else if(DBMSospedale.get().getAmministrativo(codiceFiscale)!=null){
					InterfacciaGrafica.mostra("/Autenticazione/MenuAmministrativoBoundary.fxml");
					new NotificaBoundary("Utente già registrato come amministrativo");
				} else {
					InterfacciaGrafica.mostra("/Autenticazione/SceltaTipoImpiegatoBoundary.fxml");
				}
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

	/**Metodo che comunica al DBMS di registrare l'utente come medico, associandolo all'ambulatorio ricevuto come parametro
	 * @see DBMSospedale#registraMedico(String, Ambulatorio) */
	public static void registraMedico(Ambulatorio ambulatorio) {
		try {
			DBMSospedale.get().registraMedico(codiceFiscale,ambulatorio);
			InterfacciaGrafica.mostra("/Autenticazione/MenuAmministrativoBoundary.fxml");
			new NotificaBoundary("Medico registrato con successo");
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}


	/**Metodo che comunica al DBMS di registrare l'utente come utente amministrativo
	 * @see DBMSospedale#registraAmministrativo(String) */
	static void registraAmministrativo() {
		try {
			DBMSospedale.get().registraAmministrativo(codiceFiscale);
			InterfacciaGrafica.mostra("/Autenticazione/MenuAmministrativoBoundary.fxml");
			new NotificaBoundary("Utente amministrativo registrato\n con successo");
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}


	/**Metodo che mostra la finestra SceltaRepartoBoundary
	 * @see SceltaRepartoBoundary*/
	public static void scegliReparto(){
		InterfacciaGrafica.mostra("/GestioneEmergenze/SceltaRepartoBoundary.fxml");
	}



	/**Metodo che mostra la finestra SceltaAmbulatorioBoundary
	 * @see SceltaAmbulatorioBoundary*/
	public static void scegliAmbulatorio(Reparto reparto){
		SceltaAmbulatorioBoundary.setReparto(reparto);
		InterfacciaGrafica.mostra("/GestioneEmergenze/SceltaAmbulatorioBoundary.fxml");
	}



	/**Metodo che mostra il menu dell'utente amministrativo
	 * @see MenuAmministrativoBoundary*/
	static void mostraMenuAmministrativo(){
		InterfacciaGrafica.mostra("/Autenticazione/MenuAmministrativoBoundary.fxml");
	}


	/**Metodo che mostra la finestra SceltaTipoImpiegatoBoundary
	 * @see SceltaTipoImpiegatoBoundary*/
	public static void mostraSceltaTipoImpiegato(){
		InterfacciaGrafica.mostra("/Autenticazione/SceltaTipoImpiegatoBoundary.fxml");
	}

}
