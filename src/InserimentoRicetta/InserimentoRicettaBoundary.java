package InserimentoRicetta;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.DBMSricette;
import Entity.Prestazione;
import Entity.Reparto;
import Entity.RicettaEntity;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InserimentoRicettaBoundary {

    static RicettaEntity ricettaSelezionata;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<RicettaEntity> list=new ListView<>();

    @FXML
    private TextField codiceFiscale;

    @FXML
    private ComboBox<Prestazione> prestazione=new ComboBox<>();

    @FXML
    private ComboBox<Reparto> reparto=new ComboBox<>();

    @FXML
    private ComboBox<String> urgenza=new ComboBox<>();

    @FXML
    private TextArea diagnosi;

    @FXML
    private ComboBox<String> esenzione=new ComboBox<>();

    @FXML
    private TextField codice;

    @FXML
    void clickConferma(ActionEvent event) {
        try {
            boolean esenz;
            if(urgenza.getSelectionModel().getSelectedItem()!=null && diagnosi.getText()!=null && prestazione.getSelectionModel().getSelectedItem().toString()!=null && reparto.getSelectionModel().getSelectedItem().getNome()!=null && codiceFiscale.getText()!=null && esenzione.getSelectionModel().getSelectedItem()!=null) {
                esenz = esenzione.getSelectionModel().getSelectedItem().compareTo("Esente") == 0;
                String codiceRicetta=InserimentoRicettaControl.generaCodice();
                RicettaEntity ricetta=RicettaEntity.create(codiceRicetta, urgenza.getSelectionModel().getSelectedItem(), diagnosi.getText(), prestazione.getSelectionModel().getSelectedItem().toString(), reparto.getSelectionModel().getSelectedItem().getNome(), codiceFiscale.getText().toUpperCase(), esenz);
                DBMSricette.get().inserisciRicetta(ricetta);
                ricettaSelezionata=ricetta;
                InserimentoRicettaControl.aggiorna();

            }else{
                new NotificaBoundary("Riempire tutti i campi");
            }
        } catch (SQLException e) {
            new NotificaBoundary("Connessione fallita");
            e.printStackTrace();
            DBMSospedale.setConnessioneCaduta(true);
        }catch (Exception e) {
            new NotificaBoundary("Errore");
        }

    }


    @FXML
    void aggiornaPrestazioni(ActionEvent event) {
        try {
            prestazione.setItems(FXCollections.observableArrayList(DBMSospedale.get().getPrestazioni(reparto.getSelectionModel().getSelectedItem())));
        } catch (SQLException e) {
            new NotificaBoundary("Connessione fallita");
            DBMSospedale.setConnessioneCaduta(true);
        }catch (Exception e) {
            new NotificaBoundary("Errore");
        }
    }



    /**Metodo che inizializza la finestra*/
	@FXML
	void initialize() {
        assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'InserimentoRicettaBoundary.fxml'.";
        assert codiceFiscale != null : "fx:id=\"codiceFiscale\" was not injected: check your FXML file 'InserimentoRicettaBoundary.fxml'.";
        assert prestazione != null : "fx:id=\"prestazione\" was not injected: check your FXML file 'InserimentoRicettaBoundary.fxml'.";
        assert reparto != null : "fx:id=\"reparto\" was not injected: check your FXML file 'InserimentoRicettaBoundary.fxml'.";
        assert urgenza != null : "fx:id=\"urgenza\" was not injected: check your FXML file 'InserimentoRicettaBoundary.fxml'.";
        assert diagnosi != null : "fx:id=\"diagnosi\" was not injected: check your FXML file 'InserimentoRicettaBoundary.fxml'.";
        assert esenzione != null : "fx:id=\"esenzione\" was not injected: check your FXML file 'InserimentoRicettaBoundary.fxml'.";
        try {
            list.setItems(FXCollections.observableArrayList(DBMSricette.get().getRicette()));
            reparto.setItems(FXCollections.observableArrayList(DBMSospedale.get().getReparti()));
            List<String> urgenze=new ArrayList<>();
            urgenze.add("U");
            urgenze.add("B");
            urgenze.add("D");
            urgenze.add("P");
            urgenza.setItems(FXCollections.observableArrayList(urgenze));
            List<String> esenzioni=new ArrayList<>();
            esenzioni.add("Esente");
            esenzioni.add("Non esente");
            esenzione.setItems(FXCollections.observableArrayList(esenzioni));
            list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                // Your action here
                codice.setText(list.getSelectionModel().getSelectedItem().getCodiceRicetta());
            });
            if (ricettaSelezionata!=null) {
                list.getSelectionModel().select(ricettaSelezionata);
            }
        } catch (SQLException e) {
            new NotificaBoundary("Connessione fallita");
            DBMSospedale.setConnessioneCaduta(true);
        }catch (Exception e) {
            new NotificaBoundary("Errore");
        }
    }
}
