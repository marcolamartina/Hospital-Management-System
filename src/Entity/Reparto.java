package Entity;

import java.time.LocalTime;

/**Classe che contiene le informazioni di un determinato reparto: nome, identificativo, orario di apertura e di chiusura*/
public class Reparto {
    /**Identificativo del reparto*/
    private int idReparto;


    /**Nome del reparto*/
    private String nome;

    /**Orario di apertura del reparto*/
    private LocalTime apertura;

    /**Orario di chiusura del reparto*/
    private LocalTime chiusura;


    /**Costruttore che setta tutte le variabili*/
    private Reparto(int idReparto, String nome, LocalTime apertura, LocalTime chiusura) {
        this.idReparto = idReparto;
        this.nome = nome;
        this.apertura = apertura;
        this.chiusura = chiusura;
    }

    /**Metodo per la creazione di un nuovo Reparto con tutte le variabili settate*/
    public static Reparto create(int idReparto, String nome, LocalTime apertura, LocalTime chiusura){
        return new Reparto(idReparto, nome, apertura, chiusura);
    }


    /**Metodo getter della variabile idReparto*/
    public int getIdReparto() {
        return idReparto;
    }


    /**Metodo getter della variabile nome*/
    public String getNome() {
        return nome;
    }


    /**Metodo getter della variabile apertura*/
    public LocalTime getApertura() {
        return apertura;
    }


    /**Metodo getter della variabile chiusura*/
    public LocalTime getChiusura() {
        return chiusura;
    }


    /**Override del metodo toString della classe Object per ottenere una stringa con il nome del reparto
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return nome;
    }
}
