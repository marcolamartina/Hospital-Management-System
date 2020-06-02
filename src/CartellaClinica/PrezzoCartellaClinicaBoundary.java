package CartellaClinica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che notifica il prezzo al paziente e permette il pagamento tramite un tasto Paga*/
public class PrezzoCartellaClinicaBoundary {

	/**Prezzo della visualizzazione e stampa della cartella clinica*/
	private static String prezzo;


	/**Costruttore che inizializza la variabile prezzo al valore passato come parametro*/
	public PrezzoCartellaClinicaBoundary(String prezzo) {
		try {
			PrezzoCartellaClinicaBoundary.prezzo=prezzo;
			Stage stage=new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/CartellaClinica/PrezzoCartellaClinicaBoundary.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**Costruttore di default*/
	public PrezzoCartellaClinicaBoundary() {
	}

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;


	/**Label per la visualizzazione del prezzo*/
	@FXML
	private Label label;


	/**Metodo che richiama il pagamento gestito da StampaCartellaClinicaControl
	 * @see StampaCartellaClinicaControl#pagamento() */
	@FXML
	void clickPaga(ActionEvent event) {
		StampaCartellaClinicaControl.pagamento();
		Node source = (Node)  event.getSource();
		Stage stage  = (Stage) source.getScene().getWindow();
		stage.close();
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'PrezzoCartellaClinicaBoundary.fxml'.";
		label.setText("Prezzo: "+prezzo);
	}
}
