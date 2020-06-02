package GestionePrenotazioni;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;


/**Classe Controller di una finestra composta da un pannello che mostra un calendario e una lista di orari disponibili per il giorno selezionato sul calendario*/
public class CalendarioBoundary {

	/**Riferimento alla control*/
	private static SelezioneDataControl control;


	/**Metodo getter della variabile control*/
	public static SelezioneDataControl getControl() {
		return control;
	}


	/**Metodo setter della variabile control*/
	public static void setControl(SelezioneDataControl control) {
		CalendarioBoundary.control = control;
	}

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Oggetto che consente di visualizzare la lista di orari disponibili*/
	@FXML
	private ListView<LocalTime> list=new ListView<>();


	/**Inferfaccia grafica per selezionare la data*/
	@FXML
	private DatePicker calendar=new DatePicker();


	/**Metodo che manda alla control il giorno e l'orario inseriti dall'utente
	 * @see SelezioneDataControl#conferma(LocalDate, LocalTime) */
	@FXML
	void clickConferma(ActionEvent event) {
		control.conferma(calendar.getValue(),list.getSelectionModel().getSelectedItem());
	}

	/**Metodo per tornare alla schermata precedente*/
	@FXML
	void clickIndietro(ActionEvent event) {
		if(SelezionaDataBoundary.getControl().compareTo("SceltaPrestazione")==0){
			SceltaPrestazioneControl.sceltaPrestazione();
		}else if(SelezionaDataBoundary.getControl().compareTo("InserimentoDatiRicetta")==0){
			InserimentoDatiRicettaControl.mostraInserimento();
		}else if(SelezionaDataBoundary.getControl().compareTo("ModificaData")==0){
			GestionePrenotazioniControl.aggiorna();
		}
	}

	/**Metodo che mostra gli orari disponibili nel giorno selezionato, chiedendoli alla control
	 * @see SelezioneDataControl#mostraOrari(LocalDate) */
	@FXML
	void clickGiorno(ActionEvent event) {
		list.setItems(FXCollections.observableArrayList(control.mostraOrari(calendar.getValue())));
	}

	/**Metodo che inizializza la finestra, in cui vengono disabilitati i giorni non disponibili*/
	@FXML
	void initialize() {
		assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'CalendarioBoundary.fxml'.";
		assert calendar != null : "fx:id=\"calendar\" was not injected: check your FXML file 'CalendarioBoundary.fxml'.";
		calendar.setShowWeekNumbers(false);
		calendar.setValue(control.getGiorniDaVisualizzare().get(0));
		list.setItems(FXCollections.observableArrayList(control.mostraOrari(calendar.getValue())));
		calendar.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				setDisable(empty || date.compareTo(today) < 0 || date.compareTo(today.plusYears(1))>0 || !control.getGiorniDaVisualizzare().contains(date));
				setDisabled(false);
			}
		});
	}
}

