package CartellaClinica;

import Entity.PazienteEntity;
import Entity.PrenotazioneEntity;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette al paziente di  visualizzare e stampare la sua cartella clinica o di tornare al menu precedente*/
public class CartellaClinicaPazienteBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;


	/**Oggetto che consente di visualizzare la cartella clinica*/
	@FXML
	private ListView<PrenotazioneEntity> list=new ListView<>();


	/**Nome, cognome e codice fiscale del paziente che ha effettuato l'accesso*/
	@FXML
	private Label paziente;


	/**Metodo per tornare alla schermata precedente
	 * @see StampaCartellaClinicaControl#indietro() */
	@FXML
	void clickIndietro(ActionEvent event) {
		StampaCartellaClinicaControl.indietro();
	}


	/**Metodo invoca il metodo stampa di StampaCartellaClinicaControl
	 *
	 * @see StampaCartellaClinicaControl#stampa()
	 */
	@FXML
	void clickStampa(ActionEvent event) {
		StampaCartellaClinicaControl.stampa();
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'CartellaClinicaPazienteBoundary.fxml'.";
		assert paziente != null : "fx:id=\"paziente\" was not injected: check your FXML file 'CartellaClinicaPazienteBoundary.fxml'.";
		list.setItems(FXCollections.observableArrayList(StampaCartellaClinicaControl.getCartellaClinica()));
		paziente.setText(PazienteEntity.getSelected().toFormatted());
	}
}

