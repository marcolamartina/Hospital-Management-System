package GestionePrenotazioni;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che consente di inserire il codice di una ricetta per prenotare una prestazione*/
public class InserimentoDatiRicettaBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Casella di testo dove inserire il codice della ricetta*/
	@FXML
	private TextField codiceRicetta;

	/**Metodo che manda alla control il codice della ricetta inserito dall'utente e invoca il metodo verifica della control InserimentoDatiRicettaControl
	 * @see InserimentoDatiRicettaControl#verifica(String) */
	@FXML
	void clickConferma(ActionEvent event) {
		InserimentoDatiRicettaControl.verifica(codiceRicetta.getText());
	}

	/**Metodo per tornare alla schermata precedente
	 * @see InserimentoDatiRicettaControl#mostraMenuPaziente() */
	@FXML
	void clickIndietro(ActionEvent event) {
		InserimentoDatiRicettaControl.mostraMenuPaziente();
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert codiceRicetta != null : "fx:id=\"codiceRicetta\" was not injected: check your FXML file 'InserimentoDatiRicettaBoundary.fxml'.";

	}
}
