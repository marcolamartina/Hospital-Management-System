package CartellaClinica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permetta la ricerca di un paziente a partire dal suo nome e cognome*/
public class RicercaPazienteBoundary {


	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Casella di testo dove inserire il nome*/
	@FXML
	private TextField nome;

	/**Casella di testo dove inserire il cognome*/
	@FXML
	private TextField cognome;


	/**Metodo che manda alla control i dati inseriti dall'utente
	 * @see VisualizzaCartellaClinicaControl#cercaPazienti(String, String) */
	@FXML
	void clickConferma(ActionEvent event) {
		VisualizzaCartellaClinicaControl.cercaPazienti(nome.getText(),cognome.getText());
	}

	/**Metodo per tornare alla schermata precedente
	 * @see VisualizzaCartellaClinicaControl#mostraMenuMedico() */
	@FXML
	void clickIndietro(ActionEvent event) {
		VisualizzaCartellaClinicaControl.mostraMenuMedico();
	}



	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert nome != null : "fx:id=\"nome\" was not injected: check your FXML file 'RicercaPazienteBoundary.fxml'.";
		assert cognome != null : "fx:id=\"cognome\" was not injected: check your FXML file 'RicercaPazienteBoundary.fxml'.";

	}
}
