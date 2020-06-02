package Entity;

import java.time.LocalDate;

/**Classe che contiene la matricola dell'impiegato ed eredita dalla classe Utente le informazioni personali*/
class Impiegato extends Utente {
    /**Matricola dell'impiegato, assegnatagli al momento della registrazione*/
    private int matricola;


    /**Costruttore che setta tutte le variabili*/
    Impiegato(String codiceFiscale, String nome, String cognome, LocalDate dataDiNascita, String luogoDiNascita, String email, String telefono, String password, int matricola) {
        super(codiceFiscale, nome,cognome,dataDiNascita,luogoDiNascita,email,telefono,password);
        this.matricola = matricola;

    }

}
