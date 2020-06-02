package GestioneEmergenze;

import Autenticazione.NotificaBoundary;
import Autenticazione.RegistrazioneImpiegatiControl;
import ComponentiEsterne.DBMSospedale;
import Entity.Ambulatorio;
import Entity.Reparto;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette la scelta di un ambulatorio da una lista*/
public class SceltaAmbulatorioBoundary {

	/**Riferimento al reparto*/
	private static Reparto reparto;

	/**Riferimento alla control*/
	private static Object control;


	/**Metodo setter della variabile reparto*/
	public static void setReparto(Reparto reparto) {
		SceltaAmbulatorioBoundary.reparto = reparto;
	}


	/**Metodo setter della variabile control*/
	public static void setControl(Object control) {
		SceltaAmbulatorioBoundary.control = control;
	}

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Oggetto che consente di visualizzare la lista degli ambulatori*/
	@FXML
	private ListView<Ambulatorio> list=new ListView<>();

	/**Metodo che manda alla control l'ambulatorio selezionato
	 * @see GestioneEmergenzeControl#selezionaDate(Ambulatorio)
	 * @see RegistrazioneImpiegatiControl#registraMedico(Ambulatorio)  */
	@FXML
	void clickConferma(ActionEvent event) {
		if (control instanceof RegistrazioneImpiegatiControl) {
			if (list.getSelectionModel().getSelectedItem()!=null) {
				RegistrazioneImpiegatiControl.registraMedico(list.getSelectionModel().getSelectedItem());
			} else {
				new NotificaBoundary("Nessun ambulatorio selezionato");
			}
		}else if(control instanceof GestioneEmergenzeControl){
			if (list.getSelectionModel().getSelectedItem()!=null) {
				GestioneEmergenzeControl.selezionaDate(list.getSelectionModel().getSelectedItem());
			} else {
				new NotificaBoundary("Nessun ambulatorio selezionato");
			}
		}
	}

	/**Metodo per tornare alla schermata precedente
	 * @see RegistrazioneImpiegatiControl#scegliReparto()
	 * @see GestioneEmergenzeControl#selezionaReparto() */
	@FXML
	void clickIndietro(ActionEvent event) {
		if (control instanceof RegistrazioneImpiegatiControl) {
			RegistrazioneImpiegatiControl.scegliReparto();
		}else if(control instanceof GestioneEmergenzeControl){
			GestioneEmergenzeControl.selezionaReparto();
		}
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		try {
			assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'SceltaAmbulatorioBoundary.fxml'.";
			if (control instanceof GestioneEmergenzeControl) {
				list.setItems(FXCollections.observableArrayList(DBMSospedale.get().getAmbulatoriSenzaEmergenza(reparto)));
			} else {
				list.setItems(FXCollections.observableArrayList(DBMSospedale.get().getAmbulatori(reparto)));
			}
			list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}

}
