package Autenticazione;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette di scegliere all’amministrativo il paziente che si vuole gestire*/
public class GestionePazienteBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Casella di testo dove inserire il codice fiscale dell'utente che si vuole gestire*/
	@FXML
	private TextField codiceFiscale;


	/**Metodo che manda alla control i dati inseriti dall'utente
	 * @see GestionePazienteControl#gestisciPaziente(String) */
	@FXML
	void clickConferma(ActionEvent event) {
		GestionePazienteControl.gestisciPaziente(codiceFiscale.getText());
	}

	/**Metodo per tornare alla schermata precedente
	 * @see GestionePazienteControl#mostraMenuAmministrativo() */
	@FXML
	void clickIndietro(ActionEvent event) {
		GestionePazienteControl.mostraMenuAmministrativo();
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert codiceFiscale != null : "fx:id=\"codiceFiscale\" was not injected: check your FXML file 'GestionePazienteBoundary.fxml'.";
	}

}