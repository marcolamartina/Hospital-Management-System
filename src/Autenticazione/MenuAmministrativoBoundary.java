package Autenticazione;

import Entity.AmministrativoEntity;
import GestioneEmergenze.GestioneEmergenzeControl;
import GestionePrenotazioni.PagamentoPrestazioneControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe controller del menu principale dell’utente amministrativo composto da: un tasto Gestione Paziente, un tasto Registrazione paziente, un tasto Pagamento prestazione, un tasto Gestione Emergenze, un tasto Registrazione impiegati e un tasto Disconnetti*/
public class MenuAmministrativoBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;



	/**URL della finestra*/
	@FXML
	private URL location;



	/**Nome e cognome dell'utente amministrativo che ha effettuato l'accesso*/
	@FXML
	private Label nomeCognome;



	/**Metodo che invoca il costruttore di DisconnettiControl
	 * @see DisconnettiControl*/
	@FXML
	void clickDisconnetti(ActionEvent event) {
		new DisconnettiControl(this);
	}



	/**Metodo che invoca il costruttore di GestioneEmergenzeControl
	 * @see GestioneEmergenzeControl*/
	@FXML
	void clickGestioneEmergenze(ActionEvent event) {
		new GestioneEmergenzeControl();
	}



	/**Metodo che invoca il costruttore di GestionePazienteControl
	 * @see GestionePazienteControl*/
	@FXML
	void clickGestionePaziente(ActionEvent event) {
		new GestionePazienteControl();
	}



	/**Metodo che invoca il costruttore di PagamentoPrestazioneControl
	 * @see PagamentoPrestazioneControl*/
	@FXML
	void clickPagamentoPrestazione(ActionEvent event) {
		new PagamentoPrestazioneControl();
	}



	/**Metodo che invoca il costruttore di RegistrazioneImpiegatiControl
	 * @see RegistrazioneImpiegatiControl*/
	@FXML
	void clickRegistrazioneImpiegati(ActionEvent event) {
		new RegistrazioneImpiegatiControl();
	}



	/**Metodo che invoca il costruttore di RegistrazioneControl
	 * @see RegistrazioneControl*/
	@FXML
	void clickRegistrazionePaziente(ActionEvent event) {
		new RegistrazioneControl();
	}



	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert nomeCognome != null : "fx:id=\"nomeCognome\" was not injected: check your FXML file 'MenuAmministrativoBoundary.fxml'.";
		nomeCognome.setText(AmministrativoEntity.getSelected().getCognome()+" "+AmministrativoEntity.getSelected().getNome());
	}
}
