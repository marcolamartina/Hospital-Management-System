package Autenticazione;

import Entity.AmministrativoEntity;
import Entity.MedicoEntity;
import Entity.PazienteEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra di default che il sistema crea all’apertura del software composta da un tasto Paziente, un tasto Medico, un tasto Amministrativo, per scegliere con che credenziali ci si  vuole autenticare*/
public class WelcomeBoundary {

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;


	/**Metodo che invoca il costruttore di AccessControl
	 * @see AccessControl#AccessControl(AmministrativoEntity) */
	@FXML
	void clickAmministrativo(ActionEvent event){
		new AccessControl(AmministrativoEntity.getSelected());
	}


	/**Metodo che invoca il costruttore di AccessControl
	 * @see AccessControl#AccessControl(MedicoEntity) */
	@FXML
	void clickMedico(ActionEvent event){
		new AccessControl(MedicoEntity.getSelected());
	}



	/**Metodo che invoca il costruttore di AccessControl
	 * @see AccessControl#AccessControl(PazienteEntity) */
	@FXML
	void clickPaziente(ActionEvent event){
		new AccessControl(PazienteEntity.getSelected());
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
	}
}
