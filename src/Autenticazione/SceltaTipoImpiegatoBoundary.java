package Autenticazione;

import Entity.Ambulatorio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette la scelta del tipo di impiegato che deve essere registrato*/
public class SceltaTipoImpiegatoBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;


	/**URL della finestra*/
	@FXML
	private URL location;



	/**Metodo che comunica alla control di registrare l'utente come utente amministrativo
	 * @see RegistrazioneImpiegatiControl#registraAmministrativo() */
	@FXML
	void clickAmministrativo(ActionEvent event) {
		RegistrazioneImpiegatiControl.registraAmministrativo();
	}



	/**Metodo che comunica alla control di registrare l'utente come medico
	 * @see RegistrazioneImpiegatiControl#registraMedico(Ambulatorio)  */
	@FXML
	void clickMedico(ActionEvent event) {
		RegistrazioneImpiegatiControl.scegliReparto();
	}




	/**Metodo per tornare alla schermata precedente*/
	@FXML
	void clickIndietro(ActionEvent event) {
		new RegistrazioneImpiegatiControl();
	}



	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
	}
}
