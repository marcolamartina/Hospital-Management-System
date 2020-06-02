package Autenticazione;

import CartellaClinica.SelezionaPazienteControl;
import CartellaClinica.VisualizzaCartellaClinicaControl;
import Entity.MedicoEntity;
import GestioneRicoveri.GestioneRicoveriControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe controller del menu principale del medico composto da: un tasto Seleziona paziente, un tasto Visualizza cartella clinica, un tasto Gestione ricoveri e un tasto Disconnetti*/
public class MenuMedicoBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;


	/**Nome e cognome del medico che ha effettuato l'accesso*/
	@FXML
	private Label nomeCognome;



	/**Metodo che invoca il costruttore di DisconnettiControl
	 * @see DisconnettiControl*/
	@FXML
	void clickDisconnetti(ActionEvent event) {
		new DisconnettiControl(this);
	}




	/**Metodo che invoca il costruttore di SelezionaPazienteControl
	 * @see SelezionaPazienteControl*/
	@FXML
	void clickSelezionaPaziente(ActionEvent event) {
		new SelezionaPazienteControl();
	}



	/**Metodo che invoca il costruttore di VisualizzaCartellaClinicaControl
	 * @see VisualizzaCartellaClinicaControl*/
	@FXML
	void clickVisualizzaCartellaClinica(ActionEvent event) {
		new VisualizzaCartellaClinicaControl();
	}




	/**Metodo che invoca il costruttore di GestioneRicoveriControl
	 * @see GestioneRicoveriControl*/
	@FXML
	void clickGestioneRicoveri(ActionEvent event) {
		new GestioneRicoveriControl();
	}




	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert nomeCognome != null : "fx:id=\"nomeCognome\" was not injected: check your FXML file 'MenuMedicoBoundary.fxml'.";
		nomeCognome.setText(MedicoEntity.getSelected().getCognome()+" "+MedicoEntity.getSelected().getNome());

	}
}
