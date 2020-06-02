package Autenticazione;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette la registrazione di un paziente da parte di un amministrativo o autonomamente*/
public class RegistrazioneForm {

	/**Istanza di un oggetto della classe RegistrazioneControl*/
	private static RegistrazioneControl registrazioneControl;


	/**Metodo setter della variabile registrazioneControl*/
	static void setRegistrazioneControl(RegistrazioneControl registrazioneControl) {
		RegistrazioneForm.registrazioneControl = registrazioneControl;
	}

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Casella di testo dove inserire il nome*/
	@FXML
	private TextField TextNome;

	/**Casella di testo dove inserire il cognome*/
	@FXML
	private TextField TextCognome;

	/**Casella di testo dove inserire il codice fiscale*/
	@FXML
	private TextField TextCodiceFiscale;

	/**Inferfaccia grafica per selezionare la data di nascita*/
	@FXML
	private DatePicker TextData;

	/**Casella di testo dove inserire il luogo di nascita*/
	@FXML
	private TextField TextLuogo;

	/**Casella di testo dove inserire il numero di telefono*/
	@FXML
	private TextField TextNumero;

	/**Casella di testo dove inserire l'indirizzo email*/
	@FXML
	private TextField TextEmail;

	/**Casella di testo dove inserire la password*/
	@FXML
	private TextField TextPassword;


	/**Metodo che manda alla control i dati inseriti dall'utente
	 * @see RegistrazioneControl#registraPaziente(String, String, String, LocalDate, String, String, String, String) */
	@FXML
	void clickConferma(ActionEvent event) {
		registrazioneControl.registraPaziente(TextNome.getText(),TextCognome.getText(),TextCodiceFiscale.getText(),TextData.getValue(),TextLuogo.getText(),TextNumero.getText(),TextEmail.getText(),TextPassword.getText());
	}



	/**Metodo per tornare alla schermata precedente
	 * @see RegistrazioneControl#indietro() */
	@FXML
	void clickIndietro(ActionEvent event) {
		registrazioneControl.indietro();
	}



	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert TextNome != null : "fx:id=\"TextNome\" was not injected: check your FXML file 'RegistrazioneForm.fxml'.";
		assert TextCognome != null : "fx:id=\"TextCognome\" was not injected: check your FXML file 'RegistrazioneForm.fxml'.";
		assert TextCodiceFiscale != null : "fx:id=\"TextCodiceFiscale\" was not injected: check your FXML file 'RegistrazioneForm.fxml'.";
		assert TextData != null : "fx:id=\"TextData\" was not injected: check your FXML file 'RegistrazioneForm.fxml'.";
		assert TextLuogo != null : "fx:id=\"TextLuogo\" was not injected: check your FXML file 'RegistrazioneForm.fxml'.";
		assert TextNumero != null : "fx:id=\"TextNumero\" was not injected: check your FXML file 'RegistrazioneForm.fxml'.";
		assert TextEmail != null : "fx:id=\"TextEmail\" was not injected: check your FXML file 'RegistrazioneForm.fxml'.";
		assert TextPassword != null : "fx:id=\"TextPassword\" was not injected: check your FXML file 'RegistrazioneForm.fxml'.";
		TextData.setShowWeekNumbers(false);
		TextData.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				setDisable(empty || date.compareTo(today) > 0 );
				setDisabled(false);
			}
		});

	}
}
