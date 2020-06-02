package GestionePrenotazioni;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette la ricerca del paziente che deve pagare le prestazioni*/
public class CercaPazientePagamentoBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Casella di testo dove inserire il codice fiscale*/
	@FXML
	private TextField codiceFiscale;

	/**Metodo che manda alla control il codice fiscale inserito
	 * @see PagamentoPrestazioneControl#pagamento(String) */
	@FXML
	void clickConferma(ActionEvent event) {
		PagamentoPrestazioneControl.pagamento(codiceFiscale.getText());
	}

	/**Metodo per tornare alla schermata precedente
	 * @see PagamentoPrestazioneControl#mostraMenuAmministrativo() */
	@FXML
	void clickIndietro(ActionEvent event) {
		PagamentoPrestazioneControl.mostraMenuAmministrativo();
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert codiceFiscale != null : "fx:id=\"codiceFiscale\" was not injected: check your FXML file 'CercaPazientePagamentoBoundary.fxml'.";

	}
}

