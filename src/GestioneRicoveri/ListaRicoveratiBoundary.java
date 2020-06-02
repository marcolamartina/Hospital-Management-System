package GestioneRicoveri;

import Entity.LettoEntity;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**Classe Controller di una finestra che mostra la lista dei pazienti ricoverati, con nome, cognome, codice fiscale, reparto, numero di stanza, numero di letto e permette di selezionare un paziente per effettuare un’operazione tramite i tasti “Elimina ricovero”, “Modifica cartella clinica”, “Aggiungi ricovero”*/
public class ListaRicoveratiBoundary {

    /**Lista di letti occupati*/
    private static List<LettoEntity> ricoverati;

    /**Metodo setter della variabile ricoverati*/
    public static void setRicoverati(List<LettoEntity> ricoverati) {
        ListaRicoveratiBoundary.ricoverati = ricoverati;
    }

    /**Bundle che contiene le informazioni della finestra*/
    @FXML
    private ResourceBundle resources;

    /**URL della finestra*/
    @FXML
    private URL location;

    /**Oggetto che consente di visualizzare la lista dei ricoverati*/
    @FXML
    private ListView<LettoEntity> list=new ListView<>();

    /**Metodo che invoca il metodo aggiungiRicovero della control GestioneRicoveriControl
     * @see GestioneRicoveriControl#aggiungiRicovero() */
    @FXML
    void clickAggiungiRicovero(ActionEvent event) {
        GestioneRicoveriControl.aggiungiRicovero();
    }

    /**Metodo che invoca il metodo eliminaRicovero della control GestioneRicoveriControl
     * @see GestioneRicoveriControl#eliminaRicovero(LettoEntity)*/
    @FXML
    void clickEliminaRicovero(ActionEvent event) {
        GestioneRicoveriControl.eliminaRicovero(list.getSelectionModel().getSelectedItem());
    }

    /**Metodo per tornare alla schermata precedente
     * @see GestioneRicoveriControl#mostraMenuMedico() */
    @FXML
    void clickIndietro(ActionEvent event) {
        GestioneRicoveriControl.mostraMenuMedico();
    }

    /**Metodo che invoca il metodo modificaCartellaClinica della control GestioneRicoveriControl
     * @see GestioneRicoveriControl#modificaCartellaClinica(LettoEntity) */
    @FXML
    void clickModificaCartellaClinica(ActionEvent event) {
        GestioneRicoveriControl.modificaCartellaClinica(list.getSelectionModel().getSelectedItem());
    }

    /**Metodo che inizializza la finestra*/
    @FXML
    void initialize() {
        assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'ListaRicoveratiBoundary.fxml'.";
        list.setItems(FXCollections.observableArrayList(ricoverati));
    }
}

