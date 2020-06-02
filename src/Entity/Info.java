package Entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**Classe che contiene le informazioni utili alla creazione del calendario necessario per prenotare una prestazione*/
public class Info {
    /**Data e ora della prenotazione*/
    private LocalDateTime dataOra;

    /**Codice fiscale di chi ha prenotato la prestazione*/
    private String Utente_codice_fiscale;

    /**Nome della prestazione prenotata*/
    private String Prestazione_nome;

    /**Ambulatorio in cui è prenotata la prestazione*/
    private int Ambulatorio_idAmbulatorio;

    /**Reparto in cui è prenotata la prestazione*/
    private int Ambulatorio_Reparto_idReparto;

    /**Durata della prestazione*/
    private LocalTime durata;

    /**Urgenza della prestazione, rappresentata dalle lettere U,B,D,P, che identificano i giorni entro i quali devono essere effettuate le prestazioni
     * U: Prestazione da eseguire entro 24 ore.
     * B: Prestazione da eseguire entro 10 gg.
     * D: Prestazione da eseguire entro 60 gg.
     * P: Prestazione da eseguire entro 180 gg.
     */
    private String urgenza;


    /**Costruttore che setta tutte le variabili*/
    private Info(LocalDateTime dataOra, String utente_codice_fiscale, String prestazione_nome, int ambulatorio_idAmbulatorio, int ambulatorio_Reparto_idReparto, LocalTime durata, String urgenza) {
        this.dataOra = dataOra;
        Utente_codice_fiscale = utente_codice_fiscale;
        Prestazione_nome = prestazione_nome;
        Ambulatorio_idAmbulatorio = ambulatorio_idAmbulatorio;
        Ambulatorio_Reparto_idReparto = ambulatorio_Reparto_idReparto;
        this.durata=durata;
        this.urgenza=urgenza;
    }


    /**Metodo per la creazione di un nuovo oggetto Info con tutte le variabili settate*/
    public static Info create(LocalDateTime dataOra, String utente_codice_fiscale, String prestazione_nome, int ambulatorio_idAmbulatorio, int ambulatorio_Reparto_idReparto, LocalTime durata, String urgenza){
        return new Info(dataOra, utente_codice_fiscale, prestazione_nome, ambulatorio_idAmbulatorio, ambulatorio_Reparto_idReparto,durata, urgenza);
    }


    /**Metodo getter della variabile dataOra*/
    public LocalDateTime getDataOra() {
        return dataOra;
    }


    /**Metodo getter della variabile Utente_codice_fiscale*/
    public String getUtente_codice_fiscale() {
        return Utente_codice_fiscale;
    }


    /**Metodo getter della variabile Prestazione_nome*/
    public String getPrestazione_nome() {
        return Prestazione_nome;
    }


    /**Metodo getter della variabile Ambulatorio_idAmbulatorio*/
    public int getAmbulatorio_idAmbulatorio() {
        return Ambulatorio_idAmbulatorio;
    }


    /**Metodo getter della variabile Ambulatorio_Reparto_idReparto*/
    public int getAmbulatorio_Reparto_idReparto() {
        return Ambulatorio_Reparto_idReparto;
    }


    /**Metodo getter della variabile durata*/
    public LocalTime getDurata() {
        return durata;
    }


    /**Metodo getter della variabile urgenza*/
    public String getUrgenza() {
        return urgenza;
    }


    /**Override del metodo toString della classe Object per ottenere solamente la data e l'orario della prenotazione
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return dataOra.toString();
    }
}
