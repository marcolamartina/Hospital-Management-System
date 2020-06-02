package Autenticazione;

import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.InterfacciaGrafica;
import Entity.AmministrativoEntity;

import java.sql.SQLException;
import java.time.LocalDate;


/**Classe che gestiste tutte le operazioni necessarie per la registrazione di un utente*/
class RegistrazioneControl{

	/**Costruttore che mostra la finestra RegistrazioneForm
	 * @see RegistrazioneForm
	 */
	RegistrazioneControl(){
		InterfacciaGrafica.mostra("/Autenticazione/RegistrazioneForm.fxml");
		RegistrazioneForm.setRegistrazioneControl(this);
	}


	/**Metodo per ritornare alla schermata precedente*/
	public void indietro(){
		if(AmministrativoEntity.getSelected()!=null){
			InterfacciaGrafica.mostra("/Autenticazione/MenuAmministrativoBoundary.fxml");
		}else{
			InterfacciaGrafica.mostra("/Autenticazione/AccessPazienteBoundary.fxml");
		}
	}


	/**Metodo che si occupa di comunicare al DBMS di registrare un paziente
	 * @see DBMSospedale#inserisciPaziente(String, String, String, LocalDate, String, String, String, String) */
	void registraPaziente(String nome, String cognome, String codiceFiscale, LocalDate DataDiNascita, String luogoDiNascita, String telefono, String email, String password){
		try {
			String err=controllaDati(nome,cognome,codiceFiscale,DataDiNascita,luogoDiNascita,telefono,email,password);
			if(err!=null) {
				new NotificaBoundary(err);
			} else{
				DBMSospedale.get().inserisciPaziente(nome,cognome,codiceFiscale.toUpperCase(),DataDiNascita,luogoDiNascita,telefono,email,password);
				if(AmministrativoEntity.getSelected()==null){
					InterfacciaGrafica.mostra("/Autenticazione/AccessPazienteBoundary.fxml");
				}else{
					InterfacciaGrafica.mostra("/Autenticazione/MenuAmministrativoBoundary.fxml");
				}
				new NotificaBoundary("Utente registrato con successo");
			}
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}


	}


	/**Metodo che controlla la correttezza dei dati inseriti*/
	private String controllaDati(String nome, String cognome, String codiceFiscale, LocalDate DataDiNascita, String luogoDiNascita, String telefono, String email, String password)throws Exception{
		if (DBMSospedale.get().getPaziente(codiceFiscale) != null){
			return "Utente già registrato";
		}
		if (!codiceFiscale.matches("^[a-zA-Z]{6}[a-zA-Z0-9]{2}[a-zA-Z][a-zA-Z0-9]{2}[a-zA-Z][a-zA-Z0-9]{3}[a-zA-Z]$")){
			return "Codice fiscale non valido.";
		}
		if (password.length() < 8){
			return "Password troppo corta. Minimo 8 caratteri";
		}
		if (nome.length() <= 1){
			return "Nome non inserito";
		}
		if (cognome.length() <= 1){
			return "Cognome non inserito";
		}
		if (luogoDiNascita.length() <= 1){
			return "Luogo di nascita non inserito";
		}
		if (telefono.length() <= 1){
			return "Numero di telefono non inserito";
		}
		if (DataDiNascita==null || DataDiNascita.isAfter(LocalDate.now()) ){
			return "Data di nascita non corretta";
		}
		if (!email.matches("^([0-9a-zA-Z]([-\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$")){
			return "L'indirizzo email inserito non è valido";
		}

		return null;
	}

}
