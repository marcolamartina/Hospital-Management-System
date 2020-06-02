package GestionePrenotazioni;

import Autenticazione.NotificaBoundary;
import Entity.Prestazione;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette di scegliere una prestazione una volta scelto il reparto di appartenenza composta dall’elenco delle prestazioni eseguibili nel reparto selezionato, un elenco che permette di scegliere il Grado di urgenza, infine un tasto Conferma*/
public class SceltaPrestazioneBoundary {

	/**Lista delle prestazioni che si possono prenotare*/
	private static List<Prestazione> prestazioni;

	/**Metodo setter della variabile prestazioni*/
	public static void setPrestazioni(List<Prestazione> prestazioni) {
		SceltaPrestazioneBoundary.prestazioni = prestazioni;
	}

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Oggetto che consente di visualizzare la lista delle prestazioni*/
	@FXML
	private ListView<Prestazione> list=new ListView<>();

	/**Menu a tendina da cui selezionare l'urgenza*/
	@FXML
	private ComboBox<String> urgenza=new ComboBox<>();

	/**Metodo che manda alla control la prestazione e l'urgenza inseriti dal medico
	 * @see SceltaPrestazioneControl#conferma(Prestazione, String) */
	@FXML
	void clickConferma(ActionEvent event) {
		if(list.getSelectionModel().getSelectedItem()!=null && urgenza.getSelectionModel().getSelectedItem()!=null){
			SceltaPrestazioneControl.conferma(list.getSelectionModel().getSelectedItem(),urgenza.getSelectionModel().getSelectedItem());
		}else{
			new NotificaBoundary("Selezionare la prestazione e l'urgenza");
		}
	}

	/**Metodo per tornare alla schermata precedente
	 * @see SceltaPrestazioneControl#aggiorna() */
	@FXML
	void clickIndietro(ActionEvent event) {
		SceltaPrestazioneControl.aggiorna();
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'SceltaPrestazioneBoundary.fxml'.";
		assert urgenza != null : "fx:id=\"urgenza\" was not injected: check your FXML file 'SceltaPrestazioneBoundary.fxml'.";
		List<String> urgenze=new ArrayList<>();
		urgenze.add("U");
		urgenze.add("B");
		urgenze.add("D");
		urgenze.add("P");
		urgenza.setItems(FXCollections.observableArrayList(urgenze));
		list.setItems(FXCollections.observableArrayList(prestazioni));
	}
}
