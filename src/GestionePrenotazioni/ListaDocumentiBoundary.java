package GestionePrenotazioni;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette la visualizzazione della lista dei documenti da portare all’atto della prestazione*/
public class ListaDocumentiBoundary {

	/**Lista dei documenti necessari alla prestazione selezionata*/
	private static String listaDocumenti;

	/**Metodo setter della variabile listaDocumenti*/
	public static void setDocumenti(String documenti) {
		ListaDocumentiBoundary.listaDocumenti = documenti;
	}

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Oggetto che serve a visualizzare i documenti richiesti*/
	@FXML
	private TextArea documenti;

	/**Metodo che invoca il metodo aggiorna della control GestionePrenotazioniControl
	 * @see GestionePrenotazioniControl#aggiorna() */
	@FXML
	void clickOk(ActionEvent event) {
		GestionePrenotazioniControl.aggiorna();
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert documenti != null : "fx:id=\"documenti\" was not injected: check your FXML file 'ListaDocumentiBoundary.fxml'.";
		documenti.setText(listaDocumenti);
	}
}
