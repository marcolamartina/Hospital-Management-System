package Autenticazione;

import CartellaClinica.StampaCartellaClinicaControl;
import Entity.PazienteEntity;
import GestionePrenotazioni.GestionePrenotazioniControl;
import GestionePrenotazioni.InserimentoDatiRicettaControl;
import GestionePrenotazioni.StoricoVisiteControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe controller del menu principale del paziente composto da: un tasto Gestione prenotazioni, un tasto Storico visite, un tasto Nuova prenotazione, un tasto Cartella clinica e un tasto Disconnetti*/
public class MenuPazienteBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;


	/**Nome e cognome del paziente che ha effettuato l'accesso*/
	@FXML
	private Label nomeCognome;


	/**Metodo che invoca il costruttore di StampaCartellaClinicaControl
	 * @see StampaCartellaClinicaControl
	 */
	@FXML
	void clickCartellaClinica(ActionEvent event) {
		new StampaCartellaClinicaControl();
	}



	/**Metodo che invoca il costruttore di DisconnettiControl
	 * @see DisconnettiControl*/
	@FXML
	void clickDisconnetti(ActionEvent event) {
		new DisconnettiControl(this);
	}


	/**Metodo che invoca il costruttore di DisconnettiControl
	 * @see DisconnettiControl*/
	@FXML
	void clickGestionePrenotazioni(ActionEvent event) {
		new GestionePrenotazioniControl();
	}




	/**Metodo che invoca il costruttore di InserimentoDatiRicettaControl
	 * @see InserimentoDatiRicettaControl*/
	@FXML
	void clickNuovaPrenotazione(ActionEvent event) {
		new InserimentoDatiRicettaControl();
	}





	/**Metodo che invoca il costruttore di StoricoVisiteControl
	 * @see StoricoVisiteControl*/
	@FXML
	void clickStoricoVisite(ActionEvent event) {
		new StoricoVisiteControl();
	}



	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert nomeCognome != null : "fx:id=\"nomeCognome\" was not injected: check your FXML file 'MenuPazienteBoundary.fxml'.";
		nomeCognome.setText(PazienteEntity.getSelected().getCognome()+" "+ PazienteEntity.getSelected().getNome());
	}
}
