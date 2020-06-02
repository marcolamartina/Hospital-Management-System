package ComponentiEsterne;

import Entity.RicettaEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**Classe che gestisce l'interfacciamento al DBMS delle ricette*/
public class DBMSricette {
    /**Indirizzo IP del database*/
    private String address;

    /**Username dell'utente che richiede l'accesso al database*/
    private String username;

    /**Password dell'utente che richiede l'accesso al database*/
    private String password;

    /**Nome del database*/
    private String dataBaseName;

    /**Istanza di un oggetto di tipo Connection che serve per la connessione al database*/
    private Connection conn;

    /**Variabile utilizzata per effettuare le query*/
    private PreparedStatement st;

    /**Variabile contenente i risultati delle query*/
    private ResultSet rs;

    /**Istanza di un oggetto della classe DBMSricette*/
    private static final DBMSricette instance = new DBMSricette("localhost", "root","password","ricette");

    /**Metodo getter della variabile instance*/
    public static DBMSricette get(){
        return instance;
    }

    /**Costruttore che setta le variabili address, username, password e dataBaseName*/
    private DBMSricette(String address, String username, String password, String dataBaseName) {
        this.address = address;
        this.username = username;
        this.password = password;
        this.dataBaseName = dataBaseName;
        try{
            this.connect();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    /**Metodo per effettuare la connessione al database*/
    private void connect() throws SQLException {
        String uri ;
        uri = "jdbc:mysql://" + address + "/" + dataBaseName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Rome";
        this.conn = DriverManager.getConnection(uri, username, password);
    }

    /**Metodo per ottenere la ricetta associata al codice passato come parametro*/
    public RicettaEntity getRicetta(String codiceRicetta) throws Exception{
        st = conn.prepareStatement("SELECT * FROM Ricetta" +
                " WHERE codiceRicetta=?");
        st.setString(1, codiceRicetta);
        rs=st.executeQuery();
        if(rs.next()) {
            return parserRicetta(rs);
        }
        return null;
    }

    /**Metodo per ottenere tutte le ricette presenti nel database*/
    public List<RicettaEntity> getRicette() throws Exception{
        List<RicettaEntity> ricette=new ArrayList<>();

        st = conn.prepareStatement("SELECT * FROM Ricetta");
        rs=st.executeQuery();
        while(rs.next()) {
            ricette.add(parserRicetta(rs));
        }
        return ricette;
    }



    /**Metodo per inserire nel database una nuova ricetta*/
    public void inserisciRicetta(RicettaEntity ricetta)throws Exception{
        st = conn.prepareStatement("INSERT INTO Ricetta (codiceRicetta, urgenza, diagnosi, prestazione, reparto, codiceFiscale, esenzione) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)");

        st.setString(1, ricetta.getCodiceRicetta());
        st.setString(2, ricetta.getUrgenza());
        st.setString(3, ricetta.getDiagnosi());
        st.setString(4, ricetta.getPrestazione());
        st.setString(5, ricetta.getReparto());
        st.setString(6, ricetta.getCodiceFiscale());
        st.setBoolean(7,ricetta.isEsenzione());
        st.execute();
    }

    /**Metodo per eliminare una ricetta dal database*/
    public void eliminaRicetta(RicettaEntity ricetta)throws Exception{
        st = conn.prepareStatement("DELETE FROM Ricetta WHERE codiceRicetta=?");
        st.setString(1, ricetta.getCodiceRicetta());
        st.execute();
    }


    /**Metodo per convertire un resultSet in un oggetto di tipo RicettaEntity*/
    private RicettaEntity parserRicetta(ResultSet queryResult)throws Exception{
        String codiceRicetta=queryResult.getString("codiceRicetta");
        String urgenza = queryResult.getString("urgenza");
        String diagnosi = queryResult.getString("diagnosi");
        String prestazione = queryResult.getString("prestazione");
        String reparto = queryResult.getString("reparto");
        String codiceFiscale = queryResult.getString("codiceFiscale");
        boolean esenzione = queryResult.getBoolean("esenzione");
        return RicettaEntity.create(codiceRicetta,urgenza,diagnosi,prestazione,reparto,codiceFiscale,esenzione);
    }

}
