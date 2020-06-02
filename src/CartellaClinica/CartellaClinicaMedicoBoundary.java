package CartellaClinica;

import Entity.PazienteEntity;
import Entity.PrenotazioneEntity;
import GestionePrenotazioni.SceltaPrestazioneControl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette al medico di visualizzare la cartella clinica di un paziente prenotato per il giorno corrente, di inizializzarne la modifica e la prenotazione di una nuova prestazione*/
public class CartellaClinicaMedicoBoundary {

	/**Cartella clinica del paziente selezionato*/
	private static List<PrenotazioneEntity> cartellaClinica;

	/**Metodo setter della variabile cartellaClinica*/
	public static void setCartellaClinica(List<PrenotazioneEntity> cartellaClinica) {
		CartellaClinicaMedicoBoundary.cartellaClinica = cartellaClinica;
	}

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;


	/**Oggetto che consente di visualizzare la cartella clinica*/
	@FXML
	private ListView<PrenotazioneEntity> list=new ListView<>();


	/**Nome, cognome e codice fiscale del paziente selezionato*/
	@FXML
	private Label paziente;



	/**Metodo per tornare alla schermata precedente
	 * @see SelezionaPazienteControl#mostraMenu() */
	@FXML
	void clickIndietro(ActionEvent event) {
		SelezionaPazienteControl.mostraMenu();
	}



	/**Metodo che invoca il costruttore di ModificaCartellaClinicaControl
	 * @see ModificaCartellaClinicaControl*/
	@FXML
	void clickModifica(ActionEvent event) {
		new ModificaCartellaClinicaControl();
	}


	/**Metodo che invoca il costruttore di SceltaPrestazioneControl
	 * @see SceltaPrestazioneControl*/
	@FXML
	void clickPrestazioneUlteriore(ActionEvent event) {
		new SceltaPrestazioneControl();
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'CartellaClinicaMedicoBoundary.fxml'.";
		assert paziente != null : "fx:id=\"paziente\" was not injected: check your FXML file 'CartellaClinicaMedicoBoundary.fxml'.";
		list.setItems(FXCollections.observableArrayList(cartellaClinica));
		paziente.setText(PazienteEntity.getSelected().toFormatted());

	}
}

