package Entity;

import java.time.LocalTime;


/**Classe che contiene le informazioni di una determinata prestazione: nome, durata, documenti necessari per effettuare la prestazione in questione e identificativo del reparto in cui può essere effettuata*/
public class Prestazione {
    /**Nome della prestazione*/
    private String nome;

    /**Durata della prestazione*/
    private LocalTime durata;

    /**Documenti necessari per la prestazione*/
    private String documenti;

    /**Identificativo del reparto in cui si effettua la prestazione*/
    private int Reparto_idReparto;



    /**Costruttore che setta tutte le variabili*/
    private Prestazione(String nome, LocalTime durata, String documenti, int reparto_idReparto) {
        this.nome = nome;
        this.durata = durata;
        this.documenti = documenti;
        Reparto_idReparto = reparto_idReparto;
    }


    /**Metodo per la creazione di un nuovo Prestazione con tutte le variabili settate*/
    public static Prestazione create(String nome, LocalTime durata, String documenti, int reparto_idReparto){
        return new Prestazione(nome, durata, documenti, reparto_idReparto);
    }

    /**Metodo getter della variabile Reparto_idReparto*/
    public int getReparto_idReparto() {
        return Reparto_idReparto;
    }


    /**Metodo getter della variabile nome*/
    public String getNome() {
        return nome;
    }


    /**Metodo getter della variabile documenti*/
    public String getDocumenti() {
        return documenti;
    }


    /**Metodo getter della variabile durata*/
    public LocalTime getDurata() {
        return durata;
    }


    /**Override del metodo toString della classe Object per ottenere una stringa con il nome della prestazione
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return nome;
    }
}
