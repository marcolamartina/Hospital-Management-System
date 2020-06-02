package Autenticazione;


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

/**Classe Controller di una finestra pop-up che notifica un processo avvenuto o meno con successo*/
public class NotificaBoundary {

	/**Messaggio che viene passato come parametro al costruttore*/
	private static String messaggio;


	/**Costruttore che crea un nuova finestra con il messaggio passato come parametro*/
	public NotificaBoundary(String messaggio) {
		NotificaBoundary.messaggio=messaggio;
		try {
			Stage stage=new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/Autenticazione/NotificaBoundary.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public NotificaBoundary() {
	}

	/**Bundle che contiene le informazioni della finestra*/
	@FXML
	private ResourceBundle resources;



	/**URL della finestra*/
	@FXML
	private URL location;



	/**Messaggio che viene visualizzato nella finestra*/
	@FXML
	private Label label;


	/**Metodo che chiude la finestra*/
	@FXML
	void clickOk(ActionEvent event) {
		Node source = (Node)  event.getSource();
		Stage stage  = (Stage) source.getScene().getWindow();
		stage.close();
	}

	/**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
		assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'NotificaBoundary.fxml'.";
		label.setText(messaggio);

	}



}
