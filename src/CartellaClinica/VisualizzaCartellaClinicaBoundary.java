package CartellaClinica;

import Entity.PrenotazioneEntity;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette la visualizzazione della cartella clinica di un paziente*/
public class VisualizzaCartellaClinicaBoundary {


	/**Cartella clinica del paziente selezionato*/
	private static List<PrenotazioneEntity> cartellaClinica;


	/**Metodo setter per la variabile cartellaClinica*/
	public static void setCartellaClinica(List<PrenotazioneEntity> cartellaClinica) {
		VisualizzaCartellaClinicaBoundary.cartellaClinica = cartellaClinica;
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


	/**Nome, cognome e codice fiscale del paziente di cui si sta visualizzando la cartella clinica*/
	@FXML
	private Label paziente;

	/**Metodo per tornare alla schermata precedente
	 * @see VisualizzaCartellaClinicaControl#mostraMenuMedico() */
	@FXML
	void clickIndietro(ActionEvent event) {
		VisualizzaCartellaClinicaControl.mostraMenuMedico();
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'VisualizzaCartellaClinicaBoundary.fxml'.";
		assert paziente != null : "fx:id=\"paziente\" was not injected: check your FXML file 'VisualizzaCartellaClinicaBoundary.fxml'.";
		list.setItems(FXCollections.observableArrayList(cartellaClinica));
		paziente.setText(VisualizzaCartellaClinicaControl.getPaziente().toFormatted());
	}
}

