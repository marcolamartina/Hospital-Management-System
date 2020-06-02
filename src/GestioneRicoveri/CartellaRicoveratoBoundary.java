package GestioneRicoveri;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import Entity.MedicoEntity;
import Entity.PazienteEntity;
import Entity.Prestazione;
import Entity.Reparto;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che permette al medico di aggiornare la cartella clinica di un ricoverato*/
public class CartellaRicoveratoBoundary {

    /**Bundle che contiene le informazioni della finestra*/
    @FXML
    private ResourceBundle resources;

    /**URL della finestra*/
    @FXML
    private URL location;

    /**Nome e cognome del paziente ricoverato*/
    @FXML
    private Label paziente;

    /**Casella di testo dove inserire l'anamnesi*/
    @FXML
    private TextArea anamnesi;

    /**Casella di testo dove inserire la diagnosi*/
    @FXML
    private TextArea diagnosi;

    /**Casella di testo dove inserire le cure proposte al paziente ricoverato*/
    @FXML
    private TextArea cure;

    /**Menu a tendina da cui selezionare la prestazione, tra quelle che si possono effettuare nel reparto di appartenenza del medico*/
    @FXML
    private ComboBox<Prestazione> prestazione=new ComboBox<>();

    /**Metodo che manda alla control le modifiche alla cartella clinica del ricoverato
     * @see GestioneRicoveriControl#aggiornaCartella(Prestazione, String, String, String) */
    @FXML
    void clickConferma(ActionEvent event) {
        GestioneRicoveriControl.aggiornaCartella(prestazione.getSelectionModel().getSelectedItem(),anamnesi.getText(),diagnosi.getText(),cure.getText());
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
        assert paziente != null : "fx:id=\"paziente\" was not injected: check your FXML file 'CartellaRicoveratoBoundary.fxml'.";
        assert anamnesi != null : "fx:id=\"anamnesi\" was not injected: check your FXML file 'CartellaRicoveratoBoundary.fxml'.";
        assert diagnosi != null : "fx:id=\"diagnosi\" was not injected: check your FXML file 'CartellaRicoveratoBoundary.fxml'.";
        assert cure != null : "fx:id=\"cure\" was not injected: check your FXML file 'CartellaRicoveratoBoundary.fxml'.";
        assert prestazione != null : "fx:id=\"prestazione\" was not injected: check your FXML file 'CartellaRicoveratoBoundary.fxml'.";
        try {
            Reparto reparto=DBMSospedale.get().getReparto(MedicoEntity.getSelected().getAmbulatorio_Reparto_idReparto());
            paziente.setText(PazienteEntity.getSelected().toFormatted());
            prestazione.setItems(FXCollections.observableArrayList(DBMSospedale.get().getPrestazioni(reparto)));
            anamnesi.setText(null);
            diagnosi.setText(null);
            cure.setText(null);

        } catch (SQLException e) {
            new NotificaBoundary("Connessione fallita");
            DBMSospedale.setConnessioneCaduta(true);
        }catch (Exception e) {
            new NotificaBoundary("Errore");
        }
    }
}


