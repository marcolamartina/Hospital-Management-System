package Entity;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;

import java.sql.SQLException;
import java.time.LocalDate;

/**Classe che contiene le informazioni di un determinato ambulatorio: identificativo, data di inizio e fine dell’ultima emergenza verificatasi, identificativo del raparto in cui si trova*/
public class Ambulatorio {

    /**Identificativo dell'ambulatorio*/
    private int idAmbulatorio;

    /**Identificativo del reparto di appartenenza dell'ambulatorio*/
    private int Reparto_idReparto;


    /**Data di inizio dell'ultima emergenza impostata*/
    private LocalDate dataInizio;


    /**Data di fine dell'ultima emergenza impostata*/
    private LocalDate dataFine;


    /**Costruttore che setta tutte le variabili*/
    private Ambulatorio(int idAmbulatorio, int reparto_idReparto, LocalDate dataInizio, LocalDate dataFine) {
        this.idAmbulatorio = idAmbulatorio;
        Reparto_idReparto = reparto_idReparto;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }


    /**Costruttore di default*/
    public Ambulatorio() {
    }


    /**Metodo per la creazione di un nuovo ambulatorio con tutte le variabili settate*/
    public static Ambulatorio create(int idAmbulatorio, int reparto_idReparto, LocalDate dataInizio, LocalDate dataFine){
        return new Ambulatorio(idAmbulatorio, reparto_idReparto, dataInizio, dataFine);
    }

    /**Metodo getter della variabile idAmbulatorio*/
    public int getIdAmbulatorio() {
        return idAmbulatorio;
    }


    /**Metodo getter della variabile Reparto_idReparto*/
    public int getReparto_idReparto() {
        return Reparto_idReparto;
    }


    /**Metodo getter della variabile dataInizio*/
    public LocalDate getDataInizio() {
        return dataInizio;
    }



    /**Metodo getter della variabile dataFine*/
    public LocalDate getDataFine() {
        return dataFine;
    }


    /**Override del metodo equals della classe Object che confronta due ambulatori in base al loro identificativo idAmbulatorio
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if ((obj instanceof Ambulatorio)) {
            Ambulatorio amb = (Ambulatorio) obj;
            return amb.getIdAmbulatorio()==idAmbulatorio;
        } else {
            return false;
        }
    }

    /**Override del metodo toString della classe Object per ottenere una stringa con tutte le informazioni dell'ambulatorio
     * @see Object#toString()
     */
    @Override
    public String toString() {
        try {
            String stringa= "n°"+idAmbulatorio;
            while (stringa.length()<6){
                stringa+=" ";
            }
            stringa+=DBMSospedale.get().getReparto(Reparto_idReparto).getNome();
            if(!dataInizio.isEqual(LocalDate.of(1990,1,1))){
                stringa+="  Emergenza: da "+dataInizio+" a "+dataFine;
            }
            return stringa;
        } catch (SQLException e) {
            new NotificaBoundary("Connessione fallita");
            DBMSospedale.setConnessioneCaduta(true);
        }catch (Exception e) {
            new NotificaBoundary("Errore");
        }
        return null;
    }

}
