package GestioneRicoveri;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.InterfacciaGrafica;
import Entity.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**Classe che permette tutte le operazioni volta all’aggiunta di un ricovero, all’eliminazione di un ricovero o alla modifica della cartella clinica di un ricoverato*/
public class GestioneRicoveriControl {


    /**Lista dei letti occupati dai ricoverati*/
    private static List<LettoEntity> ricoverati=new ArrayList<>();

    /**Costruttore che richiama il metodo aggiorna
     * @see GestioneRicoveriControl#aggiorna() */
    public GestioneRicoveriControl() {
        aggiorna();
    }

    /**Metodo che mostra la finestra AggiungiRicoveroBoundary*/
    static void aggiungiRicovero(){
        InterfacciaGrafica.mostra("/GestioneRicoveri/AggiungiRicoveroBoundary.fxml");
    }

    /**Metodo che consente l'eliminazione del ricovero del paziente che occupa il letto selezionato
     * @see DBMSospedale#cancellaRicovero(LettoEntity)
     * @see GestioneRicoveriControl#mostraMenuMedico() */
    static void eliminaRicovero(LettoEntity letto){
        try {
            if(letto!=null){
                DBMSospedale.get().cancellaRicovero(letto);
                mostraMenuMedico();
                new NotificaBoundary("Ricovero eliminato");
            }else{
                new NotificaBoundary("Selezionare un paziente ricoverato");
            }
        } catch (SQLException e) {
            new NotificaBoundary("Connessione fallita");
            DBMSospedale.setConnessioneCaduta(true);
        }catch (Exception e) {
            new NotificaBoundary("Errore");
        }
    }

    /**Metodo che mostra la finestra CartellaRicoveratoBoundary
     * @see CartellaRicoveratoBoundary*/
    static void modificaCartellaClinica(LettoEntity letto){
        try {
            if(letto!=null){
                PazienteEntity.setSelected(DBMSospedale.get().getPaziente(letto.getCodiceFiscale()));
                InterfacciaGrafica.mostra("/GestioneRicoveri/CartellaRicoveratoBoundary.fxml");
            }else{
                new NotificaBoundary("Selezionare un paziente ricoverato");
            }
        } catch (SQLException e) {
            new NotificaBoundary("Connessione fallita");
            DBMSospedale.setConnessioneCaduta(true);
        }catch (Exception e) {
            new NotificaBoundary("Errore");
        }
    }

    /**Metodo che comunica al DBMS di aggiornare la cartella del ricoverato
     * @see DBMSospedale#aggiornaCartellaRicoverato(PrenotazioneEntity) */
    static void aggiornaCartella(Prestazione prestazione, String anamnesi, String diagnosi, String cure){
        try {
            if(prestazione!=null && anamnesi!=null && diagnosi!=null && cure!=null){
                double prezzo;
                if(PazienteEntity.getSelected().getEsenzione()){
                    prezzo=0;
                }else{
                    prezzo=(Integer.valueOf(prestazione.getDurata().getMinute()).doubleValue()/2+prestazione.getDurata().getHour()*30);
                }
                DBMSospedale.get().aggiornaCartellaRicoverato(PrenotazioneEntity.create(LocalDateTime.now().withNano(0),PazienteEntity.getSelected().getCodiceFiscale(),prestazione.getNome(),"U",diagnosi,anamnesi,cure,null,MedicoEntity.getSelected().getAmbulatorio_idAmbulatorio(),MedicoEntity.getSelected().getAmbulatorio_Reparto_idReparto(),prezzo,PazienteEntity.getSelected().getEsenzione()));
                mostraMenuMedico();
                new NotificaBoundary("Modifica effettuata");
            }else{
                new NotificaBoundary("Riempire tutti i campi");
            }
        } catch (SQLException e) {
            new NotificaBoundary("Connessione fallita");
            DBMSospedale.setConnessioneCaduta(true);
        }catch (Exception e) {
            new NotificaBoundary("Errore");
        }
    }

    /**Metodo che interroga il DBMS sulla presenza di letti disponibili e comunica di aggiungere un ricovero nel caso in cui i letti non sono tutti occupati
     * @see DBMSospedale#getLettoDisponibile(int)
     * @see DBMSospedale#aggiuntaRicovero(LettoEntity, String)
     * @see GestioneRicoveriControl#mostraMenuMedico()*/
    static void ricovera(String codiceFiscale){
        try {
            if(codiceFiscale!=null){
                PazienteEntity paziente=DBMSospedale.get().getPaziente(codiceFiscale);
                if(paziente!=null){
                    if (!DBMSospedale.get().isRicoverato(codiceFiscale)) {
                        LettoEntity letto=DBMSospedale.get().getLettoDisponibile(MedicoEntity.getSelected().getAmbulatorio_Reparto_idReparto());
                        if(letto!=null){
                            DBMSospedale.get().aggiuntaRicovero(letto,codiceFiscale);
                            mostraMenuMedico();
                            new NotificaBoundary("Ricovero aggiunto.\nIl letto assegnato è il n°"+letto.getNumeroLetto()+"\npresso la stanza n°"+letto.getNumeroStanza()+"\nnel reparto di "+DBMSospedale.get().getReparto(letto.getIdReparto()).getNome());
                        }else{
                            new NotificaBoundary("Non ci sono letti disponibili");
                        }
                    }else{
                        new NotificaBoundary("Paziente già ricoverato");
                    }
                }else{
                    new NotificaBoundary("Codice fiscale non corretto");
                }
            }else{
                new NotificaBoundary("Inserire il codice fiscale");
            }
        } catch (SQLException e) {
            new NotificaBoundary("Connessione fallita");
            DBMSospedale.setConnessioneCaduta(true);
        }catch (Exception e) {
            new NotificaBoundary("Errore");
        }
    }

    /**Metodo che interroga il DBMS per ottenere la lista dei pazienti ricoverati nel reparto del medico che ha effettuato l'accessoe li mostra nella finestra ListaRicoveratiBoundary
     * @see DBMSospedale#getListaRicoverati(int)
     * @see ListaRicoveratiBoundary*/
    static void aggiorna(){
        try {
            ricoverati=DBMSospedale.get().getListaRicoverati(MedicoEntity.getSelected().getAmbulatorio_Reparto_idReparto());
            ListaRicoveratiBoundary.setRicoverati(ricoverati);
            InterfacciaGrafica.mostra("/GestioneRicoveri/ListaRicoveratiBoundary.fxml");
        } catch (SQLException e) {
            new NotificaBoundary("Connessione fallita");
            DBMSospedale.setConnessioneCaduta(true);
        }catch (Exception e) {
            new NotificaBoundary("Errore");
        }
    }

    /**Metodo che mostra il menu del medico*/
    static void mostraMenuMedico(){
        InterfacciaGrafica.mostra("/Autenticazione/MenuMedicoBoundary.fxml");
    }
}
