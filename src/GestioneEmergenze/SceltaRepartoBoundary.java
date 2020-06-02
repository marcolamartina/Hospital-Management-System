package GestioneEmergenze;

import Autenticazione.NotificaBoundary;
import Autenticazione.RegistrazioneImpiegatiControl;
import CartellaClinica.SelezionaPazienteControl;
import ComponentiEsterne.DBMSospedale;
import Entity.PazienteEntity;
import Entity.Reparto;
import GestionePrenotazioni.SceltaPrestazioneControl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette la scelta di un reparto da una lista*/
public class SceltaRepartoBoundary {

	/**Riferimento alla control*/
	private static Object control;

	/**Metodo setter della variabile control*/
	public static void setControl(Object control) {
		SceltaRepartoBoundary.control = control;
	}

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Oggetto che consente di visualizzare la lista dei reparti*/
	@FXML
	private ListView<Reparto> list=new ListView<>();

	/**Metodo che manda alla control il reparto selezionato dall'utente amministrativo
	 * @see RegistrazioneImpiegatiControl#scegliAmbulatorio(Reparto)
	 * @see GestioneEmergenzeControl#selezionaAmbulatorio(Reparto)
	 * @see SceltaPrestazioneControl#setReparto(Reparto)
	 * @see SceltaPrestazioneControl#sceltaPrestazione() */
	@FXML
	void clickConferma(ActionEvent event) {
		if (control instanceof RegistrazioneImpiegatiControl) {
			if (list.getSelectionModel().getSelectedItem()!=null) {
				RegistrazioneImpiegatiControl.scegliAmbulatorio(list.getSelectionModel().getSelectedItem());
			} else {
				new NotificaBoundary("Nessun reparto selezionato");
			}
		}else if(control instanceof GestioneEmergenzeControl){
			if (list.getSelectionModel().getSelectedItem()!=null) {
				GestioneEmergenzeControl.selezionaAmbulatorio(list.getSelectionModel().getSelectedItem());
			} else {
				new NotificaBoundary("Nessun reparto selezionato");
			}
		}else if(control instanceof SceltaPrestazioneControl){
			if (list.getSelectionModel().getSelectedItem()!=null) {
				SceltaPrestazioneControl.setReparto(list.getSelectionModel().getSelectedItem());
				SceltaPrestazioneControl.sceltaPrestazione();
			} else {
				new NotificaBoundary("Nessun reparto selezionato");
			}
		}

	}

	/**Metodo per tornare alla schermata precedente
	 * @see RegistrazioneImpiegatiControl#mostraSceltaTipoImpiegato()
	 * @see GestioneEmergenzeControl#mostraMenu()
	 * @see SelezionaPazienteControl#selezionaPaziente(PazienteEntity, int) */
	@FXML
	void clickIndietro(ActionEvent event) {
		if (control instanceof RegistrazioneImpiegatiControl) {
			RegistrazioneImpiegatiControl.mostraSceltaTipoImpiegato();
		}else if(control instanceof GestioneEmergenzeControl){
			GestioneEmergenzeControl.mostraMenu();
		}else if(control instanceof SceltaPrestazioneControl){
			SelezionaPazienteControl.selezionaPaziente(PazienteEntity.getSelected(),SelezionaPazienteControl.getIndice());
		}
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		try {
			assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'SceltaRepartoBoundary.fxml'.";
			list.setItems(FXCollections.observableArrayList(DBMSospedale.get().getReparti()));
			list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}
}
