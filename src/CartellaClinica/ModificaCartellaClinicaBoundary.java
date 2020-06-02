package CartellaClinica;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import Entity.PazienteEntity;
import Entity.PrenotazioneEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette al medico di aggiornare la cartella clinica di un paziente */
public class ModificaCartellaClinicaBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;


	/**Nome, cognome e codice fiscale del paziente selezionato*/
	@FXML
	private Label paziente;


	/**Informazioni sulla prenotazione selezionata*/
	@FXML
	private Label prenotazione;


	/**Casella di testo dove inserire l'anamnesi*/
	@FXML
	private TextArea anamnesi;


	/**Casella di testo dove inserire la diagnosi*/
	@FXML
	private TextArea diagnosi;


	/**Casella di testo dove inserire le cure proposte*/
	@FXML
	private TextArea cure;

	/**Metodo per tornare alla schermata precedente*/
	@FXML
	void clickIndietro(ActionEvent event) {
		SelezionaPazienteControl.selezionaPaziente(PazienteEntity.getSelected(),SelezionaPazienteControl.indice);
	}


	/**Metodo che manda alla control i dati inseriti dal medico
	 * @see ModificaCartellaClinicaControl#confermaModifiche(String, String, String) */
	@FXML
	void confermaModifiche(ActionEvent event) {
		ModificaCartellaClinicaControl.confermaModifiche(anamnesi.getText(),diagnosi.getText(),cure.getText());
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		try {
			assert paziente != null : "fx:id=\"paziente\" was not injected: check your FXML file 'ModificaCartellaClinicaBoundary.fxml'.";
			assert prenotazione != null : "fx:id=\"prenotazione\" was not injected: check your FXML file 'ModificaCartellaClinicaBoundary.fxml'.";
			assert anamnesi != null : "fx:id=\"anamnesi\" was not injected: check your FXML file 'ModificaCartellaClinicaBoundary.fxml'.";
			assert diagnosi != null : "fx:id=\"diagnosi\" was not injected: check your FXML file 'ModificaCartellaClinicaBoundary.fxml'.";
			assert cure != null : "fx:id=\"cure\" was not injected: check your FXML file 'ModificaCartellaClinicaBoundary.fxml'.";
			paziente.setText(PazienteEntity.getSelected().toFormatted());
			prenotazione.setText(PrenotazioneEntity.getSelected().toFormatted());
			anamnesi.setText(PrenotazioneEntity.getSelected().getAnamnesi());
			diagnosi.setText(PrenotazioneEntity.getSelected().getDiagnosi());
			cure.setText(PrenotazioneEntity.getSelected().getCure());
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}
}
