package GestionePrenotazioni;

import Autenticazione.NotificaBoundary;
import Entity.PrenotazioneEntity;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette il pagamento di tutte le prestazioni appartenenti ad un determinato paziente*/
public class PagamentoPrestazioneBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Oggetto che consente di visualizzare la lista delle prenotazioni da pagare*/
	@FXML
	private ListView<String> list=new ListView<>();

	/**Metodo per tornare alla schermata precedente
	 * @see PagamentoPrestazioneControl#indietro() */
	@FXML
	void clickIndietro(ActionEvent event) {
		PagamentoPrestazioneControl.indietro();
	}

	/**Metodo che comunica alla control di pagare la prestazione selezionata
	 * @see PagamentoPrestazioneControl#paga(PrenotazioneEntity) */
	@FXML
	void clickPaga(ActionEvent event) {
		if (!list.getSelectionModel().getSelectedIndices().isEmpty()) {
			PagamentoPrestazioneControl.paga(PagamentoPrestazioneControl.getPrenotazioni().get(list.getSelectionModel().getSelectedIndices().get(0)));
		}else{
			new NotificaBoundary("Selezionare una prenotazione");
		}
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'PagamentoPrestazioneBoundary.fxml'.";
		list.setItems(FXCollections.observableArrayList(PagamentoPrestazioneControl.parserList()));
	}
}
