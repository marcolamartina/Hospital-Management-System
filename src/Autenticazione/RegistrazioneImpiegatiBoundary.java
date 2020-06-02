package Autenticazione;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette l’attivazione dei privilegi di un medico o di un utente amministrativo ad un determinato utente*/
public class RegistrazioneImpiegatiBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Casella di testo dove inserire il codice fiscale*/
	@FXML
	private TextField codiceFiscale;




	/**Metodo che manda alla control i dati inseriti dall'utente
	 * @see RegistrazioneImpiegatiControl#registraImpiegato(String) */
	@FXML
	void clickConferma(ActionEvent event) {
		RegistrazioneImpiegatiControl.registraImpiegato(codiceFiscale.getText());
	}




	/**Metodo per tornare alla schermata precedente
	 * @see RegistrazioneImpiegatiControl#mostraMenuAmministrativo() */
	@FXML
	void clickIndietro(ActionEvent event) {
		RegistrazioneImpiegatiControl.mostraMenuAmministrativo();
	}




	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert codiceFiscale != null : "fx:id=\"codiceFiscale\" was not injected: check your FXML file 'RegistrazioneImpiegatiBoundary.fxml'.";

	}
}

