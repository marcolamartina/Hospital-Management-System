package CartellaClinica;

import Entity.PazienteEntity;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette di selezionare tra una serie di pazienti*/
public class ListaPazientiBoundary {

	/**Lista dei pazienti che hanno una visita prenotata per il giorno corrente*/
	static List<PazienteEntity> listaPazienti;


	/**Identificatore della control*/
	private static String control;


	/**Metodo setter della variabile control*/
	public static void setControl(String control) {
		ListaPazientiBoundary.control = control;
	}


	/**Metodo setter della variabile listaPazienti*/
	static void setListaPazienti(List<PazienteEntity> listaPazienti) {
		ListaPazientiBoundary.listaPazienti = listaPazienti;
	}

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;



	/**Oggetto che consente di visualizzare lista dei pazienti*/
	@FXML
	private ListView<PazienteEntity> list=new ListView<>();

	/**Metodo che manda alla control i dati inseriti dall'utente
	 * @see VisualizzaCartellaClinicaControl#visualizzaCartellaClinica(PazienteEntity)
	 * @see SelezionaPazienteControl#selezionaPaziente(PazienteEntity, int) */
	@FXML
	void clickConferma(ActionEvent event) {
		if (control.compareTo("VisualizzaCartellaClinica")==0) {
			VisualizzaCartellaClinicaControl.visualizzaCartellaClinica(list.getSelectionModel().getSelectedItem());
		}else if (control.compareTo("SelezionaPaziente")==0) {
			SelezionaPazienteControl.selezionaPaziente(list.getSelectionModel().getSelectedItem(),list.getSelectionModel().getSelectedIndices().get(0));
		}
	}

	/**Metodo per tornare alla schermata precedente
	 * @see VisualizzaCartellaClinicaControl#mostraRicerca()
	 * @see SelezionaPazienteControl#mostraMenuMedico() */
	@FXML
	void clickIndietro(ActionEvent event) {
		if (control.compareTo("VisualizzaCartellaClinica")==0) {
			VisualizzaCartellaClinicaControl.mostraRicerca();
		}else if (control.compareTo("SelezionaPaziente")==0) {
			SelezionaPazienteControl.mostraMenuMedico();
		}
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'ListaPazientiBoundary.fxml'.";
		list.setItems(FXCollections.observableArrayList(listaPazienti));
	}
}
