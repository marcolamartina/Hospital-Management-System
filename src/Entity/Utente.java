package Entity;

import java.time.LocalDate;


/**Classe che contiene le informazioni personali di un utente del sistema: nome, cognome, codice fiscale, data e luogo di nascita, indirizzo email, numero di telefono e password*/
public class Utente {
    /**Codice fiscale dell'utente*/
    private String codiceFiscale;

    /**Nome dell'utente*/
    private String nome;

    /**Cognome dell'utente*/
    private String cognome;

    /**Data di nascita dell'utente*/
    private LocalDate dataDiNascita;

    /**Luogo di nascita dell'utente*/
    private String luogoDiNascita;

    /**Indirizzo email dell'utente*/
    private String email;

    /**Numero di telefono dell'utente*/
    private String telefono;

    /**Password dell'account*/
    private String password;


    /**Costruttore con tutte le variabili settate*/
    Utente(String codiceFiscale, String nome, String cognome, LocalDate dataDiNascita, String luogoDiNascita, String email, String telefono, String password) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.luogoDiNascita = luogoDiNascita;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
    }

    /**Metodo getter della variabile codiceFiscale*/
    public String getCodiceFiscale() {
        return codiceFiscale;
    }


    /**Metodo setter della variabile codiceFiscale*/
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }


    /**Metodo getter della variabile nome*/
    public String getNome() {
        return nome;
    }


    /**Metodo setter della variabile nome*/
    public void setNome(String nome) {
        this.nome = nome;
    }


    /**Metodo getter della variabile cognome*/
    public String getCognome() {
        return cognome;
    }


    /**Metodo setter della variabile cognome*/
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }


    /**Metodo getter della variabile dataDiNascita*/
    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }


    /**Metodo setter della variabile dataDiNascita*/
    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }


    /**Metodo getter della variabile luogoDiNascita*/
    public String getLuogoDiNascita() {
        return luogoDiNascita;
    }


    /**Metodo setter della variabile luogoDiNascita*/
    public void setLuogoDiNascita(String luogoDiNascita) {
        this.luogoDiNascita = luogoDiNascita;
    }


    /**Metodo getter della variabile email*/
    public String getEmail() {
        return email;
    }


    /**Metodo setter della variabile email*/
    public void setEmail(String email) {
        this.email = email;
    }


    /**Metodo getter della variabile telefono*/
    public String getTelefono() {
        return telefono;
    }


    /**Metodo setter della variabile telefono*/
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    /**Metodo getter della variabile password*/
    public String getPassword() {
        return password;
    }


    /**Metodo setter della variabile password*/
    public void setPassword(String password) {
        this.password = password;
    }
}
