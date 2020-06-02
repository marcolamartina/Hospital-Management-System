package GestionePrenotazioni;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.InterfacciaGrafica;
import Entity.PrenotazioneEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra composta da un messaggio “Selezionare la data” e un tasto “Ok”*/
public class SelezionaDataBoundary {

	/**Riferimento alla control*/
	private static String control;

	/**Metodo getter della control*/
	public static String getControl() {
		return control;
	}

	/**Costruttore che salva il riferimento alla control che l'ha creata
	 * @see SelezionaDataBoundary*/
	public SelezionaDataBoundary(String control) {
		try {
			SelezionaDataBoundary.control=control;
			Stage stage=new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/GestionePrenotazioni/SelezioneDataBoundary.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**Costruttore di default*/
	public SelezionaDataBoundary() {
	}

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;

	/**URL della finestra*/
	@FXML
	private URL location;

	/**Metodo che chiude la finestra e mostra la finestra CalendarioBoundary
	 * @see CalendarioBoundary*/
	@FXML
	void clickOk(ActionEvent event) {
		try {
			CalendarioBoundary.setControl(new SelezioneDataControl(PrenotazioneEntity.getSelected(),control));
			InterfacciaGrafica.mostra("/GestionePrenotazioni/CalendarioBoundary.fxml");
			Node source = (Node)  event.getSource();
			Stage stage  = (Stage) source.getScene().getWindow();
			stage.close();
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {

	}
}
