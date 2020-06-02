package Entity;

import java.time.LocalDate;

/**Classe che contiene le informazioni di un determinato utente amministrativo all'interno del sistema: matricola e informazioni personali, ereditate rispettivamente da Impiegato e da Utente*/
public class AmministrativoEntity extends Impiegato {

    /**Istanza di un oggetto di tipo AmministrativoEntity che rappresenta l'utente amministrativo attualmente loggato nel sistema*/
    private static AmministrativoEntity selected = null;


    /**Costruttore che setta tutte le variabili*/
    private AmministrativoEntity(String codiceFiscale, String nome, String cognome, LocalDate dataDiNascita, String luogoDiNascita, String email, String telefono, String password, int matricola) {
        super(codiceFiscale, nome, cognome, dataDiNascita, luogoDiNascita, email, telefono, password, matricola);
    }


    /**Metodo per la creazione di un nuovo AmministrativoEntity con tutte le variabili settate*/
    public static AmministrativoEntity create(String codiceFiscale, String nome, String cognome, LocalDate dataDiNascita, String luogoDiNascita, String email, String telefono, String password, int matricola){
        return new AmministrativoEntity(codiceFiscale, nome, cognome, dataDiNascita, luogoDiNascita, email, telefono, password, matricola);
    }


    /**Metodo getter della variabile selected*/
    public static AmministrativoEntity getSelected() {
        return selected;
    }


    /**Metodo setter della variabile selected*/
    public static void setSelected(AmministrativoEntity selected) {
        AmministrativoEntity.selected = selected;
    }
}
