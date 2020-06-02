package GestionePrenotazioni;

import Entity.RicettaEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette di visualizzare una ricetta composta da un pannello con all’interno i dettagli della ricetta*/
public class RicettaBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Dati della ricetta
	 * @see RicettaEntity#toString() */
	@FXML
	private Label label;

	/**Metodo che invoca il metodo creaPrenotazioni della control InserimentoDatiRicettaControl
	 * @see InserimentoDatiRicettaControl#creaPrenotazione() */
	@FXML
	void clickOk(ActionEvent event) {
		InserimentoDatiRicettaControl.creaPrenotazione();
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'RicettaBoundary.fxml'.";
		label.setText(RicettaEntity.getSelected().toString());
	}
}

