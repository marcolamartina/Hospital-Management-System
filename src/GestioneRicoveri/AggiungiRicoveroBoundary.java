package GestioneRicoveri;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette al medico di inserire il codice fiscale del paziente che si vuole ricoverare*/
public class AggiungiRicoveroBoundary {

    /**Bundle che contiene le informazioni della finestra*/
    @FXML
    private ResourceBundle resources;

    /**URL della finestra*/
    @FXML
    private URL location;

    /**Casella di testo dove inserire il codice fiscale dell'utente che si vuole gestire*/
    @FXML
    private TextField codiceFiscale;

    /**Metodo che manda alla control i dati inseriti dall'utente amministrativo
     * @see GestioneRicoveriControl#ricovera(String) */
    @FXML
    void clickConferma(ActionEvent event) {
        GestioneRicoveriControl.ricovera(codiceFiscale.getText());
    }

    /**Metodo per tornare alla schermata precedente
     * @see GestioneRicoveriControl#aggiorna() */
    @FXML
    void clickIndietro(ActionEvent event) {
        GestioneRicoveriControl.aggiorna();
    }

    /**Metodo che inizializza la finestra*/
    @FXML
    void initialize() {
        assert codiceFiscale != null : "fx:id=\"codiceFiscale\" was not injected: check your FXML file 'AggiungiRicoveroBoundary.fxml'.";

    }
}
