package GestionePrenotazioni;

import Autenticazione.NotificaBoundary;
import Entity.PrenotazioneEntity;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra utile nella gestione delle prenotazioni composta dall’elenco delle prenotazioni e la possibilità di inizializzare, per ogni prenotazione, la modifica, l'eliminazione e la visualizzazione della lista dei documenti necessari*/
public class GestionePrenotazioniBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;


	/**Oggetto che consente di visualizzare la lista di prenotazioni*/
	@FXML
	private ListView<String> list=new ListView<>();


	/**Metodo che comunica alla control la prenotazione di cui si vuole conoscere la lista dei documenti necessari
	 * @see GestionePrenotazioniControl#documenti(PrenotazioneEntity)
	 */
	@FXML
	void clickDocumenti(ActionEvent event) {
		if (!list.getSelectionModel().getSelectedIndices().isEmpty()) {
			GestionePrenotazioniControl.documenti(GestionePrenotazioniControl.getPrenotazioni().get(list.getSelectionModel().getSelectedIndices().get(0)));
		}else{
			new NotificaBoundary("Selezionare una prenotazione");
		}
	}


	/**Metodo che comunica alla control la prenotazione che si vuole eliminare
	 * @see GestionePrenotazioniControl#elimina(PrenotazioneEntity)
	 */
	@FXML
	void clickElimina(ActionEvent event) {
		if (!list.getSelectionModel().getSelectedIndices().isEmpty()) {
			GestionePrenotazioniControl.elimina(GestionePrenotazioniControl.getPrenotazioni().get(list.getSelectionModel().getSelectedIndices().get(0)));
		}else{
			new NotificaBoundary("Selezionare una prenotazione");
		}
	}

	/**Metodo per tornare alla schermata precedente
	 * @see GestionePrenotazioniControl#mostraMenuPaziente() */
	@FXML
	void clickIndietro(ActionEvent event) {
		GestionePrenotazioniControl.mostraMenuPaziente();
	}


	/**Metodo che comunica alla control la prenotazione di cui si vuole modificare la data e l'orario
	 * @see GestionePrenotazioniControl#modifica(PrenotazioneEntity)
	 */
	@FXML
	void clickModifica(ActionEvent event) {
		if (!list.getSelectionModel().getSelectedIndices().isEmpty()) {
			GestionePrenotazioniControl.modifica(GestionePrenotazioniControl.getPrenotazioni().get(list.getSelectionModel().getSelectedIndices().get(0)));
		}else{
			new NotificaBoundary("Selezionare una prenotazione");
		}
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'GestionePrenotazioniBoundary.fxml'.";
		list.setItems(FXCollections.observableArrayList(GestionePrenotazioniControl.parserList()));
	}
}
