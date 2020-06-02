package GestioneEmergenze;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette all’amministrativo di scegliere tra Aggiungi emergenza, Modifica emergenza ed  Elimina emergenza, la funzione da attuare riguardo le emergenze*/
public class GestioneEmergenzeBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Metodo che invoca i metodi setObiettivo("Aggiunta") e selezionaReparto della control GestioneEmergenzeControl
	 * @see GestioneEmergenzeControl#setObiettivo(String)
	 * @see GestioneEmergenzeControl#selezionaReparto()*/
	@FXML
	void clickAggiungiEmergenza(ActionEvent event) {
		GestioneEmergenzeControl.setObiettivo("Aggiunta");
		GestioneEmergenzeControl.selezionaReparto();
	}

	/**Metodo che invoca i metodi setObiettivo("Elimina") e selezionaEmergenza della control GestioneEmergenzeControl
	 * @see GestioneEmergenzeControl#setObiettivo(String)
	 * @see GestioneEmergenzeControl#selezionaEmergenza()*/
	@FXML
	void clickEliminaEmergenza(ActionEvent event) {
		GestioneEmergenzeControl.setObiettivo("Elimina");
		GestioneEmergenzeControl.selezionaEmergenza();
	}

	/**Metodo per tornare alla schermata precedente
	 * @see GestioneEmergenzeControl#mostraMenuAmministrativo() */
	@FXML
	void clickIndietro(ActionEvent event) {
		GestioneEmergenzeControl.mostraMenuAmministrativo();
	}

	/**Metodo che invoca i metodi setObiettivo("Modifica") e selezionaEmergenza della control GestioneEmergenzeControl
	 * @see GestioneEmergenzeControl#setObiettivo(String)
	 * @see GestioneEmergenzeControl#selezionaEmergenza() */
	@FXML
	void clickModificaEmergenza(ActionEvent event) {
		GestioneEmergenzeControl.setObiettivo("Modifica");
		GestioneEmergenzeControl.selezionaEmergenza();
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {

	}
}
