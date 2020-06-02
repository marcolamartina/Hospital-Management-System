package GestioneEmergenze;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import Entity.Ambulatorio;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette di visualizzare e selezionare un ambulatorio in cui è in corso un’emergenza*/
public class ListaEmergenzeBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Oggetto che consente di visualizzare la lista di ambulatori in cui si è verificata un'emergenza*/
	@FXML
	private ListView<Ambulatorio> list=new ListView<>();

	/**Metodo che manda alla control i dati inseriti dall'utente
	 * @see GestioneEmergenzeControl#eliminaEmergenza(Ambulatorio)
	 * @see GestioneEmergenzeControl#selezionaDate(Ambulatorio) */
	@FXML
	void clickConferma(ActionEvent event) {
		if (GestioneEmergenzeControl.getObiettivo().compareTo("Elimina")==0) {
			if (list.getSelectionModel().getSelectedItem()!=null) {
				GestioneEmergenzeControl.eliminaEmergenza(list.getSelectionModel().getSelectedItem());
			} else {
				new NotificaBoundary("Nessuna emergenza selezionata");
			}
		}
		if (GestioneEmergenzeControl.getObiettivo().compareTo("Modifica")==0) {
			if (list.getSelectionModel().getSelectedItem()!=null) {
				GestioneEmergenzeControl.selezionaDate(list.getSelectionModel().getSelectedItem());
			} else {
				new NotificaBoundary("Nessuna emergenza selezionata");
			}
		}
	}

	/**Metodo per tornare alla schermata precedente
	 * @see GestioneEmergenzeControl#mostraMenu() */
	@FXML
	void clickIndietro(ActionEvent event) {
		GestioneEmergenzeControl.mostraMenu();
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'ListaEmergenzeBoundary.fxml'.";
		try{
			list.setItems(FXCollections.observableArrayList(DBMSospedale.get().getEmergenze()));
		}catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}
}
