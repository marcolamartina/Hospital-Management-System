package Entity;

import java.time.LocalDate;


/**Classe che contiene le informazioni di un determinato medico all'interno del sistema: matricola e informazioni personali, ereditate rispettivamente da Impiegato e da Utente*/
public class MedicoEntity extends Impiegato{
    /**Identificativo dell'ambulatorio in cui lavora il medico*/
    private int Ambulatorio_idAmbulatorio;

    /**Identificativo del reparto in cui lavora il medico*/
    private int Ambulatorio_Reparto_idReparto;

    /**Istanza di un oggetto di tipo MedicoEntity che rappresenta il medico attualmente loggato nel sistema*/
    private static MedicoEntity selected = null;


    /**Costruttore che setta tutte le variabili*/
    private MedicoEntity(String codiceFiscale, String nome, String cognome, LocalDate dataDiNascita, String luogoDiNascita, String email, String telefono, String password, int matricola, int ambulatorio_idAmbulatorio, int ambulatorio_Reparto_idReparto) {
        super(codiceFiscale, nome, cognome, dataDiNascita, luogoDiNascita, email, telefono, password, matricola);
        Ambulatorio_idAmbulatorio = ambulatorio_idAmbulatorio;
        Ambulatorio_Reparto_idReparto = ambulatorio_Reparto_idReparto;
    }


    /**Metodo per la creazione di un nuovo MedicoEntity con tutte le variabili settate*/
    public static MedicoEntity create(String codiceFiscale, String nome, String cognome, LocalDate dataDiNascita, String luogoDiNascita, String email, String telefono, String password, int matricola, int ambulatorio_idAmbulatorio, int ambulatorio_Reparto_idReparto){
        return new MedicoEntity(codiceFiscale, nome, cognome, dataDiNascita, luogoDiNascita, email, telefono, password, matricola, ambulatorio_idAmbulatorio, ambulatorio_Reparto_idReparto);
    }


    /**Metodo getter della variabile Ambulatorio_idAmbulatorio*/
    public int getAmbulatorio_idAmbulatorio() {
        return Ambulatorio_idAmbulatorio;
    }


    /**Metodo getter della variabile Ambulatorio_Reparto_idReparto*/
    public int getAmbulatorio_Reparto_idReparto() {
        return Ambulatorio_Reparto_idReparto;
    }


    /**Metodo getter della variabile selected*/
    public static MedicoEntity getSelected() {
        return selected;
    }


    /**Metodo setter della variabile selected*/
    public static void setSelected(MedicoEntity selected) {
        MedicoEntity.selected = selected;
    }
}
