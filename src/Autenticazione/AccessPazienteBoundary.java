package Autenticazione;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**Classe Controller di una finestra che permette di accedere al sistema ad un determinato paziente oppure permette l’inizializzazione di una registrazione */
public class AccessPazienteBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Casella di testo dove inserire il codice fiscale*/
	@FXML
	private TextField CodiceFiscaleText;

	/**Casella di testo dove inserire una password senza che siano visualizzati i caratteri*/
	@FXML
	private PasswordField PasswordText;

    /**Metodo che comunica alla control di effettuare l'accesso con le credenziali inserite
	 * @see AccessControl#accediPaziente(String, String) */
	@FXML
	void clickAccedi(ActionEvent event) {
		 AccessControl.accediPaziente(CodiceFiscaleText.getText(), PasswordText.getText());
	}

    /**Metodo che crea una control che si occupa della registrazione
	 * @see RegistrazioneControl*/
	@FXML
	void clickRegistrati(ActionEvent event) {
		new RegistrazioneControl();
	}


	/**Metodo per tornare alla schermata precedente
	 * @see AccessControl#mostraWelcomeBoundary() */
	@FXML
	void clickIndietro(ActionEvent event) {
		AccessControl.mostraWelcomeBoundary();
	}


	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert CodiceFiscaleText != null : "fx:id=\"CodiceFiscaleText\" was not injected: check your FXML file 'AccessPazienteBoundary.fxml'.";
		assert PasswordText != null : "fx:id=\"PasswordText\" was not injected: check your FXML file 'AccessPazienteBoundary.fxml'.";
	}
}
