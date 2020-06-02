package GestionePrenotazioni;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette la visualizzazione dello storico delle visite */
public class StoricoVisiteBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Oggetto che consente di visualizzare la lista delle visite effettuate*/
	@FXML
	private ListView<String> list=new ListView<>();

	/**Metodo per tornare alla schermata precedente
	 * @see StoricoVisiteControl#indietro() */
	@FXML
	void clickIndietro(ActionEvent event) {
		StoricoVisiteControl.indietro();
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'StoricoVisiteBoundary.fxml'.";
		list.setItems(FXCollections.observableArrayList(StoricoVisiteControl.getStoricoVisite()));
	}
}


