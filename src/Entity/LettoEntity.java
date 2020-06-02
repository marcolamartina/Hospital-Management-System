package Entity;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;

import java.sql.SQLException;


/**Classe che contiene le informazioni di un determinato letto, quindi la sua posizione e l’eventuale paziente che lo occupa
 */
public class LettoEntity {
    /**Numero del letto*/
    private int numeroLetto;

    /**Numero della stanza*/
    private int numeroStanza;

    /**Identificativo del reparto in cui si trova il letto*/
    private int idReparto;

    /**Codice fiscale del paziente che occupa attualmente il letto*/
    private String codiceFiscale;


    /**Costruttore che setta tutte le variabili*/
    private LettoEntity(int numeroLetto, int numeroStanza, int idReparto, String codiceFiscale) {
        this.numeroLetto = numeroLetto;
        this.numeroStanza = numeroStanza;
        this.idReparto = idReparto;
        this.codiceFiscale = codiceFiscale;
    }


    /**Metodo per la creazione di un nuovo LettoEntity con tutte le variabili settate*/
    public static LettoEntity create(int idLetto, int numeroStanza, int idReparto, String codiceFiscale){
        return new LettoEntity(idLetto, numeroStanza, idReparto, codiceFiscale);
    }


    /**Metodo getter della variabile numeroLetto*/
    public int getNumeroLetto() {
        return numeroLetto;
    }


    /**Metodo getter della variabile numeroStanza*/
    public int getNumeroStanza() {
        return numeroStanza;
    }


    /**Metodo getter della variabile idReparto*/
    public int getIdReparto() {
        return idReparto;
    }


    /**Metodo getter della variabile codiceFiscale*/
    public String getCodiceFiscale() {
        return codiceFiscale;
    }


    /**Override del metodo toString della classe Object per ottenere una stringa con tutte le informazioni del letto e del paziente che lo occupa
     * @see Object#toString()
     */
    @Override
    public String toString() {
        try {
            String stringa="";
            int offset=0;
            if(codiceFiscale!=null){
                PazienteEntity paziente=DBMSospedale.get().getPaziente(codiceFiscale);
                stringa+=paziente.getNome()+" "+paziente.getCognome();
                while(stringa.length()<35){
                    stringa+=" ";
                }
                stringa+=paziente.getCodiceFiscale();
                while(stringa.length()<60){
                    stringa+=" ";
                }
                offset=stringa.length();
            }
            stringa+=DBMSospedale.get().getReparto(idReparto).getNome();
            while(stringa.length()<30+offset){
                stringa+=" ";
            }
            stringa+="Stanza n°"+numeroStanza;
            while(stringa.length()<50+offset){
                stringa+=" ";
            }
            stringa +="Letto n°"+ numeroLetto;
            return stringa;
        } catch (SQLException e) {
            new NotificaBoundary("Connessione fallita");
            DBMSospedale.setConnessioneCaduta(true);
        }catch (Exception e) {
            new NotificaBoundary("Errore");
        }
        return "Stanza n°"+numeroStanza+"      Letto n°"+ numeroLetto;
    }
}
