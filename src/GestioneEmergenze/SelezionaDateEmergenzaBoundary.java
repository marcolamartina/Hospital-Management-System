package GestioneEmergenze;

import Autenticazione.NotificaBoundary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


/**Classe Controller di una finestra composta da un menu a tendina da cui scegliere la data di inizio e la data di fine dell'emergenza*/
public class SelezionaDateEmergenzaBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Inferfaccia grafica per selezionare la data di inizio dell'emergenza*/
	@FXML
	private DatePicker dataInizio;

	/**Inferfaccia grafica per selezionare la data di fine dell'emergenza*/
	@FXML
	private DatePicker dataFine;

	/**Metodo che manda alla control le date inserite dall'utente amministrativo
	 * @see GestioneEmergenzeControl#aggiungiEmergenza(LocalDate, LocalDate) */
	@FXML
	void clickConferma(ActionEvent event) {
		if(dataInizio.getValue()==null || dataFine.getValue()==null){
			new NotificaBoundary("Selezionare entrambe le date");
		}else if(dataInizio.getValue().isAfter(dataFine.getValue())){
			new NotificaBoundary("Le date inserite non sono corrette");
		}else{
			new GestioneEmergenzeControl().aggiungiEmergenza(dataInizio.getValue(),dataFine.getValue());
		}
	}

	/**Metodo per tornare alla schermata precedente
	 * @see GestioneEmergenzeControl#selezionaEmergenza()
	 * @see GestioneEmergenzeControl#selezionaAmbulatorio() */
	@FXML
	void clickIndietro(ActionEvent event) {
		if (GestioneEmergenzeControl.getObiettivo().compareTo("Aggiunta")==0) {
			GestioneEmergenzeControl.selezionaAmbulatorio();
		} else if(GestioneEmergenzeControl.getObiettivo().compareTo("Modifica")==0){
			GestioneEmergenzeControl.selezionaEmergenza();
		}
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert dataInizio != null : "fx:id=\"dataInizio\" was not injected: check your FXML file 'SelezionaDateEmergenzaBoundary.fxml'.";
		assert dataFine != null : "fx:id=\"dataFine\" was not injected: check your FXML file 'SelezionaDateEmergenzaBoundary.fxml'.";

		dataFine.setShowWeekNumbers(false);
		dataFine.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();

				setDisable(empty || date.compareTo(today) < 0 );
				setDisabled(false);
			}
		});

		dataInizio.setShowWeekNumbers(false);
		dataInizio.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();

				setDisable(empty || date.compareTo(today) < 0 );
				setDisabled(false);
			}
		});
	}
}

