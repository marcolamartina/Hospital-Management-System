package ComponentiEsterne;

import Entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**Classe che gestisce l'interfacciamento al DBMS dell'ospedale*/
public class DBMSospedale {
    /**Valore booleano che indica se la connessione è caduta (true) oppure no (false)*/
    private static boolean connessioneCaduta=false;

    /**Indirizzo IP del database*/
    private static String address;

    /**Username dell'utente che richiede l'accesso al database*/
    private static String username;

    /**Password dell'utente che richiede l'accesso al database*/
    private static String password;

    /**Nome del database*/
    private static String dataBaseName;

    /**Istanza di un oggetto di tipo Connection che serve per la connessione al database*/
    private static Connection conn;

    /**Variabile utilizzata per effettuare le query*/
    private PreparedStatement st;

    /**Variabile contenente i risultati delle query*/
    private ResultSet rs;

    /**Istanza di un oggetto della classe DBMSospedale*/
    private static DBMSospedale instance = new DBMSospedale("localhost", "root","password","ospedale");


    /**Metodo setter della variabile connessioneCaduta*/
    public static void setConnessioneCaduta(boolean connessioneCaduta) {
        DBMSospedale.connessioneCaduta = connessioneCaduta;
    }

    /**Metodo getter della variabile instance*/
    public static DBMSospedale get()throws Exception{
        if(connessioneCaduta){
            connect();
            connessioneCaduta=false;
        }
        return new DBMSospedale("localhost", "root","password","ospedale", conn);
    }


    /**Costruttore che setta le variabili address, username, password e dataBaseName*/
    private DBMSospedale(String address, String username, String password, String dataBaseName) {
        try {
            DBMSospedale.address = address;
            DBMSospedale.username = username;
            DBMSospedale.password = password;
            DBMSospedale.dataBaseName = dataBaseName;
            DBMSospedale.connect();
        } catch (Exception e) {
            setConnessioneCaduta(true);
        }

    }

    private DBMSospedale(String address, String username, String password, String dataBaseName, Connection conn) {
        try {
            DBMSospedale.address = address;
            DBMSospedale.username = username;
            DBMSospedale.password = password;
            DBMSospedale.dataBaseName = dataBaseName;
            DBMSospedale.conn=conn;
        } catch (Exception e) {
            setConnessioneCaduta(true);
        }

    }

    /**Metodo per effettuare la connessione al database*/
    private static void connect()throws Exception{
        String uri ;
        uri = "jdbc:mysql://" + address + "/" + dataBaseName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Rome";
        conn = DriverManager.getConnection(uri, username, password);
    }


    /**Metodo per ottenere la lista di tutti i reparti*/
    public List<Reparto> getReparti()throws Exception {
        List<Reparto> reparti= new ArrayList<>();
        st = conn.prepareStatement("SELECT * FROM Reparto ORDER BY Reparto.idReparto ASC ");
        rs = st.executeQuery();
        while(rs.next()) {
            reparti.add(parserReparto(rs));
        }
        return reparti;
    }

    /**Metodo che permette di ottenere un reparto a partire dal suo identificativo*/
    public Reparto getReparto(int idReparto) throws Exception{
        st = conn.prepareStatement("SELECT * FROM Reparto WHERE Reparto.idReparto=?");
        st.setInt(1, idReparto);
        rs=st.executeQuery();
        if(rs.next()) {
            return (parserReparto(rs));
        }
        return null;
    }

    /**Metodo per ottenere la lista di tutte le prestazioni che vengono effettuate in un reparto*/
    public List<Prestazione> getPrestazioni(Reparto reparto) throws Exception{
        List<Prestazione> prestazioni= new ArrayList<>();
        st = conn.prepareStatement("SELECT * FROM Prestazione WHERE Prestazione.Reparto_idReparto=?");
        st.setInt(1, reparto.getIdReparto());
        rs=st.executeQuery();
        while(rs.next()) {
            prestazioni.add(parserPrestazione(rs));
        }
        return prestazioni;
    }

    /**Metodo per ottenere tutte le prenotazioni di un paziente e che non sono ancora state effettuate*/
    public List<PrenotazioneEntity> getPrenotazioni(PazienteEntity paziente) throws Exception{
        List<PrenotazioneEntity> prenotazioni= new ArrayList<>();

        LocalDate oggi= LocalDate.now();
        LocalDateTime oggiInizio= oggi.atTime(00,00,01);
        Timestamp oggiParsed= Timestamp.valueOf(oggiInizio);

        st = conn.prepareStatement("SELECT * FROM Prenotazione " +
                "WHERE Prenotazione.Utente_codice_fiscale=? " +
                "AND Prenotazione.dataOra>=? " +
                "AND Prenotazione.diagnosi IS NULL");
        st.setString(1, paziente.getCodiceFiscale());
        st.setTimestamp(2, oggiParsed);
        rs=st.executeQuery();
        while(rs.next()) {
            prenotazioni.add(parserPrenotazione(rs));
        }
        return prenotazioni;
    }

    /**Metodo che permette di eliminare una prenotazione*/
    public void cancellaPrenotazione(PrenotazioneEntity prenotazione)throws Exception {
        st = conn.prepareStatement("DELETE FROM Prenotazione WHERE dataOra=? AND Ambulatorio_idAmbulatorio=? AND Utente_codice_fiscale=?");
        st.setTimestamp(1, Timestamp.valueOf(prenotazione.getDataOra()));
        st.setInt(2, prenotazione.getAmbulatorio_idAmbulatorio());
        st.setString(3, prenotazione.getUtente_codice_fiscale());
        st.execute();
    }

    /**Metodo che elimina le prenotazioni che non sono state effettuate, 3 giorni dopo la data in cui erano state prenotate */
    public void cancellaPrenotazioniNonEffettuate()throws Exception {
        st = conn.prepareStatement("DELETE FROM Prenotazione WHERE dataOra<? AND diagnosi IS NULL");
        st.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now().minusDays(3)));
        st.execute();
    }

    /**Metodo che permette di ottenere i dati di un medico a partire da codice fiscale e password*/
    public MedicoEntity getMedico(String codiceFiscale, String password)throws Exception {

        st = conn.prepareStatement("SELECT * FROM Medico,Utente " +
                "WHERE Medico.Utente_codiceFiscale=? " +
                "AND Utente.password=? " +
                "AND Utente.codiceFiscale=Medico.Utente_codiceFiscale");
        st.setString(1, codiceFiscale);
        st.setString(2, password);
        rs=st.executeQuery();

        if(rs.next()){
            return parserMedico(rs);
        }
        return null;
    }

    /**Metodo che permette di ottenere la lista di tutti i ricoverati all'interno di un reparto*/
    public List<LettoEntity> getListaRicoverati(int idReparto) throws Exception{
        List<LettoEntity> lista=new ArrayList<>();

        st = conn.prepareStatement("SELECT * FROM Letto " +
                "WHERE Letto.Utente_codiceFiscale is not null " +
                "AND Letto.Stanza_Reparto_idReparto=?");
        st.setInt(1,idReparto);
        rs=st.executeQuery();

        while(rs.next()){
            lista.add(parserLetto(rs));
        }
        return lista;
    }
    /**Metodo che controlla che un paziente non sia già ricoverato. Restituisce true se il paziente associato al codice fiscale passato come parametro è già ricoverato, false altrimenti*/
    public boolean isRicoverato(String codiceFiscale) throws Exception{
        st = conn.prepareStatement("SELECT * FROM Letto " +
                "WHERE Letto.Utente_codiceFiscale=? ");
        st.setString(1,codiceFiscale);
        rs=st.executeQuery();

        return rs.next();
    }

    /**Metodo che permette di trovare un letto non ancora occupato all'interno di un reparto*/
    public LettoEntity getLettoDisponibile(int idReparto) throws Exception{
        st = conn.prepareStatement("SELECT * FROM Letto " +
                "WHERE Letto.Utente_codiceFiscale is null " +
                "AND Letto.Stanza_Reparto_idReparto=?");
        st.setInt(1,idReparto);
        rs=st.executeQuery();

        if(rs.next()){
            return parserLetto(rs);
        }
        return null;
    }

    /**Metodo che associa un letto ad un determinato paziente*/
    public void aggiuntaRicovero(LettoEntity letto, String codiceFiscale) throws Exception{
        st = conn.prepareStatement("UPDATE Letto" +
                " SET Utente_codiceFiscale = ?" +
                " WHERE numeroLetto=? AND Stanza_numeroStanza=? AND Stanza_Reparto_idReparto=?");
        st.setString(1, codiceFiscale);
        st.setInt(2,letto.getNumeroLetto());
        st.setInt(3,letto.getNumeroStanza());
        st.setInt(4,letto.getIdReparto());
        st.execute();
    }

    /**Metodo che libera un letto precedentemente occupato*/
    public void cancellaRicovero(LettoEntity letto)throws Exception{
        st = conn.prepareStatement("UPDATE Letto" +
                " SET Utente_codiceFiscale = null" +
                " WHERE numeroLetto=? AND Stanza_numeroStanza=? AND Stanza_Reparto_idReparto=?");
        st.setInt(1,letto.getNumeroLetto());
        st.setInt(2,letto.getNumeroStanza());
        st.setInt(3,letto.getIdReparto());
        st.execute();
    }

    /**Metodo che permette di ottenere i dati di un medico tramite il suo codice fiscale*/
    public MedicoEntity getMedico(String codiceFiscale)throws Exception {

        st = conn.prepareStatement("SELECT * FROM Medico,Utente " +
                "WHERE Medico.Utente_codiceFiscale=? " +
                "AND Utente.codiceFiscale=Medico.Utente_codiceFiscale");
        st.setString(1, codiceFiscale);
        rs=st.executeQuery();

        if(rs.next()){
            return parserMedico(rs);
        }
        return null;
    }

    /**Metodo che permette di ottenere i dati di una prestazione tramite il suo nome*/
    public Prestazione getPrestazione(String nome)throws Exception {

        st = conn.prepareStatement("SELECT * FROM Prestazione " +
                "WHERE Prestazione.nome=? ");
        st.setString(1, nome);
        rs=st.executeQuery();

        if(rs.next()){
            return parserPrestazione(rs);
        }
        return null;
    }

    /**Metodo che permette di ottenere i dati di un utente amministrativo tramite codice fiscale e password*/
    public AmministrativoEntity getAmministrativo(String codiceFiscale, String password)throws Exception {
        st = conn.prepareStatement("SELECT * FROM Amministrativo,Utente " +
                "WHERE Amministrativo.Utente_codiceFiscale=? " +
                "AND Utente.password=? " +
                "AND Utente.codiceFiscale=Amministrativo.Utente_codiceFiscale");
        st.setString(1, codiceFiscale);
        st.setString(2, password);
        rs=st.executeQuery();

        if(rs.next()){
            return parserAmministrativo(rs);
        }
        return null;
    }

    /**Metodo che permette di ottenere i dati di un medico tramite il suo codice fiscale*/
    public AmministrativoEntity getAmministrativo(String codiceFiscale)throws Exception {
        st = conn.prepareStatement("SELECT * FROM Amministrativo,Utente " +
                "WHERE Amministrativo.Utente_codiceFiscale=? " +
                "AND Utente.codiceFiscale=Amministrativo.Utente_codiceFiscale");
        st.setString(1, codiceFiscale);
        rs=st.executeQuery();

        if(rs.next()){
            return parserAmministrativo(rs);
        }
        return null;
    }

    /**Metodo che permette di ottenere i dati di un paziente tramite codice fiscale e password*/
    public PazienteEntity getPaziente(String codiceFiscale, String password)throws Exception {
        st = conn.prepareStatement("SELECT * FROM Utente " +
                "WHERE codiceFiscale=? " +
                "AND password=? ");
        st.setString(1, codiceFiscale);
        st.setString(2, password);
        rs=st.executeQuery();

        if(rs.next()){
            return parserPaziente(rs);
        }
        return null;
    }

    /**Metodo che permette di ottenere i dati di un paziente tramite il suo codice fiscale*/
    public PazienteEntity getPaziente(String codiceFiscale)throws Exception {
        st = conn.prepareStatement("SELECT * FROM Utente " +
                "WHERE codiceFiscale=? ");
        st.setString(1, codiceFiscale);
        rs=st.executeQuery();
        if(rs.next()){
            return parserPaziente(rs);
        }
        return null;
    }

    /**Metodo che permette di inserire nel database i dati di un nuovo utente*/
    public void inserisciPaziente(String nome, String cognome, String codiceFiscale, LocalDate DataDiNascita, String luogoDiNascita, String telefono, String email, String password)throws Exception {
        st = conn.prepareStatement("INSERT INTO Utente (codiceFiscale,password,nome,cognome,dataDiNascita,luogoDiNascita,telefono,email,esenzione) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        st.setString(1, codiceFiscale);
        st.setString(2, password);
        st.setString(3, nome);
        st.setString(4, cognome);
        st.setDate(5, Date.valueOf(DataDiNascita));
        st.setString(6, luogoDiNascita);
        st.setString(7, telefono);
        st.setString(8, email);
        st.setBoolean(9,false);
        st.execute();
    }


    /**Metodo che permette di registrare un nuovo medico in un determinato ambulatorio*/
    public void registraMedico(String codiceFiscale, Ambulatorio ambulatorio)throws Exception {
        st = conn.prepareStatement("SELECT MAX(matricola) FROM Medico");
        rs = st.executeQuery();
        int newMatricola;
        if(rs.next()) {
            newMatricola = rs.getInt("MAX(matricola)")+1;
        } else {
            newMatricola = 1;
        }

        st = conn.prepareStatement("INSERT INTO Medico (matricola,Utente_codiceFiscale, Ambulatorio_idAmbulatorio, Ambulatorio_Reparto_idReparto) " +
                "VALUES (?, ?, ?, ?)");
        st.setInt(1, newMatricola);
        st.setString(2, codiceFiscale);
        st.setInt(3, ambulatorio.getIdAmbulatorio());
        st.setInt(4, ambulatorio.getReparto_idReparto());
        st.execute();
    }

    /**Metodo che permette di inserire nel database i dati di un nuovo utente amministrativo*/
    public void registraAmministrativo(String codiceFiscale) throws Exception{
        st = conn.prepareStatement("SELECT MAX(matricola) FROM Amministrativo");
        rs = st.executeQuery();
        int newMatricola;
        if(rs.next()) {
            newMatricola = rs.getInt("MAX(matricola)")+1;
        } else {
            newMatricola = 1;
        }

        st = conn.prepareStatement("INSERT INTO Amministrativo (matricola,Utente_codiceFiscale) " +
                "VALUES (?, ?)");
        st.setInt(1, newMatricola);
        st.setString(2, codiceFiscale);
        st.execute();
    }

    /**Metodo che permette di ottenere tutte le prenotazioni effettuate in una determinata data, che viene passata come parametro*/
    public List<PrenotazioneEntity> getPrenotazioni(LocalDate domani)throws Exception{
        LocalDateTime inizioGiornata= domani.atTime(00,00,01);
        LocalDateTime fineGiornata= domani.atTime(23,59,59);

        List<PrenotazioneEntity> prenotazioni= new ArrayList<>();
        st = conn.prepareStatement("SELECT * FROM Prenotazione " +
                "WHERE Prenotazione.dataOra>=? " +
                "AND Prenotazione.dataOra<=?");
        st.setTimestamp(1, Timestamp.valueOf(inizioGiornata));
        st.setTimestamp(2, Timestamp.valueOf(fineGiornata));
        rs=st.executeQuery();

        while(rs.next()) {
            prenotazioni.add(parserPrenotazione(rs));
        }
        return prenotazioni;
    }

    /**Metodo che permette di ottenere la mail di un paziente a partire da una sua prenotazione*/
    public String getEmail(PrenotazioneEntity prenotazione)throws Exception{
        st = conn.prepareStatement("SELECT email FROM Utente " +
                "WHERE Utente.codiceFiscale=?");
        st.setString(1, prenotazione.getUtente_codice_fiscale());
        rs=st.executeQuery();
        if(rs.next()){
            return rs.getString("email");
        }
        return null;
    }

    /**Metodo che permette di ottenere la lista di tutti gli ambulatori associati allo stesso reparto, che viene passato come parametro*/
    public List<Ambulatorio> getAmbulatori(Reparto reparto) throws Exception{
        List<Ambulatorio> ambulatori= new ArrayList<>();
        st = conn.prepareStatement("SELECT * FROM Ambulatorio " +
                "WHERE Ambulatorio.Reparto_idReparto=?");
        st.setInt(1, reparto.getIdReparto());
        rs=st.executeQuery();

        while(rs.next()) {
            ambulatori.add(parserAmbulatorio(rs));
        }
        return ambulatori;
    }

    /**Metodo che permette di ottenere i dati di un reparto tramite il suo nome*/
    public int getReparto(String nomeReparto)throws Exception{
        st = conn.prepareStatement("SELECT Reparto.idReparto FROM Reparto " +
                "WHERE Reparto.nome=? ");
        st.setString(1, nomeReparto);
        rs=st.executeQuery();
        if(rs.next()){
            return rs.getInt("idReparto");
        }
        return 0;
    }

    /**Metodo che permette di ottenere la lista di tutti gli ambulatori associati allo stesso reparto, a partire dal suo identificativo*/
    public List<Ambulatorio> getAmbulatori(int idReparto) throws Exception{
        List<Ambulatorio> ambulatori= new ArrayList<>();
        st = conn.prepareStatement("SELECT * FROM Ambulatorio " +
                "WHERE Ambulatorio.Reparto_idReparto=?");
        st.setInt(1, idReparto);
        rs=st.executeQuery();

        while(rs.next()) {
            ambulatori.add(parserAmbulatorio(rs));
        }
        return ambulatori;
    }

    /**Metodo che permette di ottenere la lista di tutti gli ambulatori di un reparto che, in un determinato periodo, non si trovano in uno stato di emergenza*/
    public List<Ambulatorio> getAmbulatoriNonEmergenza(int idReparto, LocalDate inizio, LocalDate fine)throws Exception {
        List<Ambulatorio> ambulatori= new ArrayList<>();

        Date inizioParsed= Date.valueOf(inizio);
        Date fineParsed= Date.valueOf(fine);

        st = conn.prepareStatement("SELECT * FROM Ambulatorio " +
                "WHERE Ambulatorio.Reparto_idReparto=? " +
                "AND NOT (Ambulatorio.dataFine>=? " +
                "AND Ambulatorio.dataInizio<=? )");
        st.setInt(1, idReparto);
        st.setDate(2, fineParsed);
        st.setDate(3, inizioParsed);
        rs=st.executeQuery();

        while(rs.next()) {
            ambulatori.add(parserAmbulatorio(rs));
        }
        return ambulatori;
    }

    /**Metodo che permette di ottenere la lista di tutti gli ambulatori di un determinato reparto per cui non si è ancora programmato un periodo di emergenza*/
    public List<Ambulatorio> getAmbulatoriSenzaEmergenza(Reparto reparto)throws Exception {
        List<Ambulatorio> ambulatori= new ArrayList<>();

        Date oggi= Date.valueOf(LocalDate.now());

        st = conn.prepareStatement("SELECT * FROM Ambulatorio " +
                "WHERE Ambulatorio.Reparto_idReparto=? " +
                "AND Ambulatorio.dataFine<=? ");
        st.setInt(1, reparto.getIdReparto());
        st.setDate(2, oggi);
        rs=st.executeQuery();

        while(rs.next()) {
            ambulatori.add(parserAmbulatorio(rs));
        }
        return ambulatori;
    }

    /**Metodo che permette di ottenere una lista di tutte le prenotazioni che sono state effettuate in un ambulatorio, in un determinato periodo*/
    public List<PrenotazioneEntity> getPrenotazioni(Ambulatorio ambulatorio, LocalDate dataInizio, LocalDate dataFine)throws Exception {
        LocalDateTime dataInizioParsed= dataInizio.atTime(00,00,01);
        LocalDateTime dataFineParsed= dataFine.atTime(23,59,59);

        List<PrenotazioneEntity> prenotazioni= new ArrayList<>();

        st = conn.prepareStatement("SELECT * FROM Prenotazione " +
                "WHERE Prenotazione.Ambulatorio_idAmbulatorio=? " +
                "AND Prenotazione.dataOra>=? " +
                "AND Prenotazione.dataOra<=? " +
                "AND Prenotazione.diagnosi is null");
        st.setInt(1, ambulatorio.getIdAmbulatorio());
        st.setTimestamp(2, Timestamp.valueOf(dataInizioParsed));
        st.setTimestamp(3, Timestamp.valueOf(dataFineParsed));
        rs=st.executeQuery();

        while(rs.next()) {
            prenotazioni.add(parserPrenotazione(rs));
        }
        return prenotazioni;
    }

    /**Metodo che permette di mettere un ambulatorio in stato di emergenza, in un determinato periodo*/
    public void aggiungiEmergenza(Ambulatorio Ambulatorio, LocalDate dataInizio, LocalDate dataFine) throws Exception{
        st = conn.prepareStatement("UPDATE Ambulatorio " +
                "SET dataInizio = ?, dataFine = ? " +
                "WHERE Ambulatorio.idAmbulatorio = ? " +
                "AND Ambulatorio.Reparto_idReparto= ?");
        st.setDate(1, Date.valueOf(dataInizio));
        st.setDate(2, Date.valueOf(dataFine));
        st.setInt(3, Ambulatorio.getIdAmbulatorio());
        st.setInt(4,Ambulatorio.getReparto_idReparto());
        st.execute();
    }

    /**Metodo che permette di ottenere una lista di tutti gli ambulatori in stato di emergenza*/
    public List<Ambulatorio> getEmergenze() throws Exception{
        List<Ambulatorio> ambulatori= new ArrayList<>();

        LocalDate oggi= LocalDate.now();
        Date oggiParsed= Date.valueOf(oggi);

        st = conn.prepareStatement("SELECT * FROM Ambulatorio " +
                "WHERE dataFine >= ? ");
        st.setDate(1, oggiParsed);
        rs=st.executeQuery();

        while(rs.next()) {
            ambulatori.add(parserAmbulatorio(rs));
        }
        return ambulatori;
    }

    /**Metodo che permette di eliminare lo stato di emergenza di un determinato ambulatorio, che viene passato come parametro*/
    public void eliminaEmergenza(Ambulatorio Ambulatorio) throws Exception{
        st = conn.prepareStatement("UPDATE Ambulatorio " +
                "SET dataInizio = ?, dataFine = ? " +
                " WHERE Ambulatorio.idAmbulatorio = ? AND Ambulatorio.Reparto_idReparto= ?");
        st.setDate(1,Date.valueOf(LocalDate.of(1990,1,1)));
        st.setDate(2,Date.valueOf(LocalDate.of(1990,1,1)));
        st.setInt(3, Ambulatorio.getIdAmbulatorio());
        st.setInt(4,Ambulatorio.getReparto_idReparto());
        st.execute();
    }

    /**Metodo che permette di modificare la data di inizio e di fine emergenza di un determinato ambulatorio*/
    public void modificaEmergenza(Ambulatorio Ambulatorio, LocalDate dataInizio, LocalDate dataFine) throws Exception{
        st = conn.prepareStatement("UPDATE Ambulatorio " +
                "SET dataInizio = ?, dataFine = ? " +
                "WHERE Ambulatorio.idAmbulatorio = ? " +
                "AND Ambulatorio.Reparto_idReparto= ?");
        st.setDate(1, Date.valueOf(dataInizio));
        st.setDate(2, Date.valueOf(dataFine));
        st.setInt(3, Ambulatorio.getIdAmbulatorio());
        st.setInt(4,Ambulatorio.getReparto_idReparto());
        st.execute();
    }

    /**Metodo che permette di ottenere la lista di tutti i pazienti che devono essere visitati nel giorno corrente da un determinato medico*/
    public List<PazienteEntity> getPazienti(LocalDate oggi, MedicoEntity medico) throws Exception{
        LocalDateTime dataOggiInizio= oggi.atTime(00,00,01);
        LocalDateTime dataoggiFine= oggi.atTime(23,59,59);

        List<PazienteEntity> pazienti= new ArrayList<>();

        st = conn.prepareStatement("SELECT Utente.* FROM Prenotazione,Utente " +
                "WHERE Prenotazione.dataOra<=? " +
                "AND Prenotazione.dataOra>=? " +
                "AND Prenotazione.Ambulatorio_idAmbulatorio=? " +
                "AND Prenotazione.Ambulatorio_Reparto_idReparto=? " +
                "AND Prenotazione.pagata=1 "+
                "AND Prenotazione.Utente_codice_fiscale=Utente.codiceFiscale " +
                "ORDER BY Prenotazione.dataOra ASC");
        st.setTimestamp(1, Timestamp.valueOf(dataoggiFine));
        st.setTimestamp(2, Timestamp.valueOf(dataOggiInizio));
        st.setInt(3,medico.getAmbulatorio_idAmbulatorio());
        st.setInt(4,medico.getAmbulatorio_Reparto_idReparto());
        rs=st.executeQuery();

        while(rs.next()) {
            pazienti.add(parserPaziente(rs));
        }
        return pazienti;
    }

    /**Metodo che permette di ottenere un lista di tutte i dati delle prestazioni sanitarie effettuate da un determinato paziente*/
    public List<PrenotazioneEntity> getCartellaClinica(PazienteEntity paziente) throws Exception{
        List<PrenotazioneEntity> prenotazioni= new ArrayList<>();

        LocalDate oggi= LocalDate.now();
        LocalDateTime oggiInizio= oggi.atTime(23,59,59);
        Timestamp oggiParsed = Timestamp.valueOf(oggiInizio);

        st = conn.prepareStatement("SELECT * FROM Prenotazione " +
                "WHERE Prenotazione.Utente_codice_fiscale=? " +
                "AND Prenotazione.dataOra<=? " +
                "AND Prenotazione.diagnosi IS NOT NULL " +
                "ORDER BY Prenotazione.dataOra DESC");
        st.setString(1, paziente.getCodiceFiscale());
        st.setTimestamp(2, oggiParsed);
        rs=st.executeQuery();

        while(rs.next()) {
            prenotazioni.add(parserPrenotazione(rs));
        }
        return prenotazioni;
    }

    /**Metodo che permette di ottenere i dati di una prenotazione effettuata da un paziente, che verrà visitato da un determinato medico nel giorno corrente, entrambi passati come parametri.
     * Inoltre se non è stato pagato il ticket, ove previsto, non verrà considerata*/
    public PrenotazioneEntity getPrenotazione(PazienteEntity paziente, MedicoEntity medico,int indice) throws Exception{

        LocalDate oggi=LocalDate.now();
        LocalDateTime dataOggiInizio= oggi.atTime(00,00,01);
        LocalDateTime dataoggiFine= oggi.atTime(23,59,59);

        st = conn.prepareStatement("SELECT Prenotazione.* FROM Prenotazione,Utente " +
                "WHERE Prenotazione.dataOra<=? " +
                "AND Prenotazione.dataOra>=? " +
                "AND Prenotazione.Ambulatorio_idAmbulatorio=? " +
                "AND Prenotazione.Ambulatorio_Reparto_idReparto=? " +
                "AND Prenotazione.pagata=1 " +
                "AND Prenotazione.Utente_codice_fiscale=Utente.codiceFiscale "+
                "AND Utente.codiceFiscale=?");
        st.setTimestamp(1, Timestamp.valueOf(dataoggiFine));
        st.setTimestamp(2, Timestamp.valueOf(dataOggiInizio));
        st.setInt(3,medico.getAmbulatorio_idAmbulatorio());
        st.setInt(4,medico.getAmbulatorio_Reparto_idReparto());
        st.setString(5, paziente.getCodiceFiscale());
        rs=st.executeQuery();

        int counter=0;
        while(rs.next()) {
            if(counter==indice){
                return parserPrenotazione(rs);
            }
            counter++;
        }
        return null;
    }

    /**Metodo che permette di modificare i dati di una determinata prenotazione*/
    public void aggiornaDatiPrenotazione(String anamnesi, String diagnosi, String cure, PrenotazioneEntity prenotazione) throws Exception{
        st = conn.prepareStatement("UPDATE Prenotazione " +
                "SET anamnesi = ?, diagnosi = ?, cure = ? " +
                " WHERE Prenotazione.dataOra = ? AND Ambulatorio_idAmbulatorio= ? AND Utente_codice_fiscale=?");
        st.setString(1, anamnesi);
        st.setString(2, diagnosi);
        st.setString(3, cure);
        st.setTimestamp(4,Timestamp.valueOf(prenotazione.getDataOra()));
        st.setInt(5, prenotazione.getAmbulatorio_idAmbulatorio());
        st.setString(6, prenotazione.getUtente_codice_fiscale());
        st.execute();
    }

    /**Metodo che permette di ottenere una lista di tutti i pazienti che hanno un determinato nome e cognome, entrambi passati come parametro*/
    public List<PazienteEntity> getPazienti(String nome, String cognome)throws Exception {
        List<PazienteEntity> pazienti= new ArrayList<>();
        st = conn.prepareStatement("SELECT * FROM Utente " +
                "WHERE Utente.nome=? " +
                "AND Utente.cognome=? ");
        st.setString(1, nome);
        st.setString(2, cognome);
        rs=st.executeQuery();


        while(rs.next()) {
            pazienti.add(parserPaziente(rs));
        }
        return pazienti;
    }

    /**Metodo che permette di ottenere una lista di tutte le visite effettuate da un determinato paziente*/
    public List<String> getStoricoVisite(PazienteEntity paziente) throws Exception{
        List<String> prenotazioni= new ArrayList<>();
        List<Reparto> reparti=getReparti();
        LocalDate oggi= LocalDate.now();
        LocalDateTime oggiInizio= oggi.atTime(23,59,59);
        Timestamp oggiParsed = Timestamp.valueOf(oggiInizio);

        st = conn.prepareStatement("SELECT * FROM Prenotazione " +
                "WHERE Prenotazione.Utente_codice_fiscale=? " +
                "AND Prenotazione.dataOra<=? " +
                "AND Prenotazione.diagnosi IS NOT NULL " +
                "ORDER BY Prenotazione.dataOra DESC");
        st.setString(1, paziente.getCodiceFiscale());
        st.setTimestamp(2, oggiParsed);
        rs=st.executeQuery();


        while(rs.next()) {
            PrenotazioneEntity pren=parserPrenotazione(rs);
            String stringa="";
            stringa+=pren.getPrestazione_nome();
            while(stringa.length()<40){
                stringa+=" ";
            }
            stringa+=reparti.get(pren.getAmbulatorio_Reparto_idReparto()-1).getNome();
            while(stringa.length()<80){
                stringa+=" ";
            }
            stringa+=pren.getDataOra().withMinute(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            prenotazioni.add(stringa);
        }
        return prenotazioni;
    }

    /**Metodo che permette di modificare lo stato di esenzione di un determinato paziente, e di modificare di conseguenza anche i prezzi delle prestazioni non ancora effettuate*/
    public void setEsenzione(PazienteEntity paziente, boolean esenzione)throws Exception {
        st = conn.prepareStatement("UPDATE Utente" +
                " SET esenzione = ?" +
                " WHERE Utente.codiceFiscale=?");
        st.setBoolean(1, esenzione);
        st.setString(2, paziente.getCodiceFiscale());
        st.execute();
        if(esenzione){
            st = conn.prepareStatement("UPDATE Prenotazione" +
                    " SET prezzo = 0, pagata=true" +
                    " WHERE Prenotazione.Utente_codice_fiscale=? AND Prenotazione.dataOra>=?");
            st.setString(1, paziente.getCodiceFiscale());
            st.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            st.execute();
        }else{
            List<PrenotazioneEntity> lista=getPrenotazioni(paziente);
            for(PrenotazioneEntity prenotazione:lista){
                LocalTime durata=getPrestazione(prenotazione.getPrestazione_nome()).getDurata();
                double prezzo=Integer.valueOf(durata.getMinute()).doubleValue()/2+durata.getHour()*30;
                st = conn.prepareStatement("UPDATE Prenotazione" +
                        " SET prezzo = ?, pagata=false" +
                        " WHERE Prenotazione.Utente_codice_fiscale=? AND Prenotazione.dataOra=? AND Prenotazione.Ambulatorio_idAmbulatorio=?");
                st.setDouble(1,prezzo);
                st.setString(2, paziente.getCodiceFiscale());
                st.setTimestamp(3, Timestamp.valueOf(prenotazione.getDataOra()));
                st.setInt(4, prenotazione.getAmbulatorio_idAmbulatorio());
                st.execute();
            }
        }
    }

    /**Metodo che permette di aggiungere una prestazione allo storico delle visite*/
    public void aggiornaCartellaRicoverato(PrenotazioneEntity prenotazione) throws Exception{
        st = conn.prepareStatement("INSERT INTO Prenotazione (dataOra, Prestazione_nome, Utente_codice_fiscale, diagnosi_medico_curante, urgenza, prezzo, pagata, Ambulatorio_idAmbulatorio, Ambulatorio_Reparto_idReparto, diagnosi, anamnesi, cure) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        st.setTimestamp(1, Timestamp.valueOf(prenotazione.getDataOra()));
        st.setString(2, prenotazione.getPrestazione_nome());
        st.setString(3, prenotazione.getUtente_codice_fiscale());
        st.setString(4, prenotazione.getDiagnosi_medico_curante());
        st.setString(5, prenotazione.getUrgenza());
        st.setDouble(6, prenotazione.getPrezzo());
        st.setBoolean(7, prenotazione.isPagata());
        st.setInt(8, prenotazione.getAmbulatorio_idAmbulatorio());
        st.setInt(9, prenotazione.getAmbulatorio_Reparto_idReparto());
        st.setString(10, prenotazione.getDiagnosi());
        st.setString(11, prenotazione.getAnamnesi());
        st.setString(12, prenotazione.getCure());
        st.execute();
    }

    /**Metodo che permette di ottenere una lista di documenti necessari per una data prestazione*/
    public String getDocumenti(PrenotazioneEntity prenotazione)throws Exception {
        st = conn.prepareStatement("SELECT documenti FROM Prestazione" +
                " WHERE Prestazione.nome=?");
        st.setString(1, prenotazione.getPrestazione_nome());
        rs=st.executeQuery();

        if(rs.next()) {
            return rs.getString("documenti");
        }
        return null;
    }
/*
    public List<PazienteEntity> getPazienti()throws Exception {
        List<PazienteEntity> lista=new ArrayList<>();
        st = conn.prepareStatement("SELECT * FROM Utente");
        rs=st.executeQuery();

        while(rs.next()) {
            lista.add(parserPaziente(rs));
        }
        return lista;
    }

    public void modificaCodiciFiscali(List<PazienteEntity> lista) throws Exception{
        for(PazienteEntity paziente:lista){
            if(paziente.getCodiceFiscale().length()==15){
                String newCod=paziente.getCodiceFiscale().substring(0,8)+"E"+paziente.getCodiceFiscale().substring(8);

                if (getMedico(paziente.getCodiceFiscale())!=null) {
                    inserisciPaziente(paziente.getNome(),paziente.getCognome(),newCod,paziente.getDataDiNascita(),paziente.getLuogoDiNascita(),paziente.getTelefono(),paziente.getEmail(),paziente.getPassword());
                    st = conn.prepareStatement("UPDATE Medico" +
                            " SET Medico.Utente_codiceFiscale=? " +
                            " WHERE Medico.Utente_codiceFiscale=?");
                    st.setString(1,newCod);
                    st.setString(2,paziente.getCodiceFiscale());
                    st.execute();
                    st=conn.prepareStatement("DELETE FROM Utente WHERE codiceFiscale=?");
                    st.setString(1,paziente.getCodiceFiscale());
                    st.execute();
                }else if (getAmministrativo(paziente.getCodiceFiscale())!=null) {
                    inserisciPaziente(paziente.getNome(),paziente.getCognome(),newCod,paziente.getDataDiNascita(),paziente.getLuogoDiNascita(),paziente.getTelefono(),paziente.getEmail(),paziente.getPassword());
                    st = conn.prepareStatement("UPDATE Amministrativo" +
                            " SET Amministrativo.Utente_codiceFiscale=? " +
                            " WHERE Amministrativo.Utente_codiceFiscale=?");
                    st.setString(1,newCod);
                    st.setString(2,paziente.getCodiceFiscale());
                    st.execute();
                    st=conn.prepareStatement("DELETE FROM Utente WHERE codiceFiscale=?");
                    st.setString(1,paziente.getCodiceFiscale());
                    st.execute();
                }else{
                    st = conn.prepareStatement("UPDATE Utente" +
                            " SET codiceFiscale=? " +
                            " WHERE codiceFiscale=?");
                    st.setString(1,newCod);
                    st.setString(2,paziente.getCodiceFiscale());
                    st.execute();
                }
            }
        }
    }


    public void aggiungiMedico()throws Exception{
        List<PazienteEntity> pazienti=getPazienti();
        for (Ambulatorio ambulatorio:getAmbulatori(11)) {
            for(PazienteEntity paziente:pazienti){
                if(getMedico(paziente.getCodiceFiscale())==null && getAmministrativo(paziente.getCodiceFiscale())==null){
                    registraMedico(paziente.getCodiceFiscale(),ambulatorio);
                    break;
                }
            }
        }
    }
*/

    /**Metodo che permette di ottenere il nome e il cognome di un paziente, la cui prenotazione viene passata come parametro*/
    public String getNomeCognome(PrenotazioneEntity prenotazione) throws Exception{
        st = conn.prepareStatement("SELECT nome,cognome FROM Utente" +
                " WHERE Utente.codiceFiscale=?");
        st.setString(1, prenotazione.getUtente_codice_fiscale());
        rs=st.executeQuery();

        if(rs.next()) {
            String stringa;
            stringa=rs.getString("cognome")+" "+rs.getString("nome");
            return stringa;
        }
        return null;
    }

    /**Metodo che permette di ottenere una lista delle prenotazioni non ancora pagate da un paziente tramite il suo codice fiscale, passato come parametro*/
    public List<PrenotazioneEntity> getPrenotazioniDaPagare(String codiceFiscale)throws Exception {
        List<PrenotazioneEntity> prenotazioni= new ArrayList<>();

        st = conn.prepareStatement("SELECT * FROM Prenotazione " +
                "WHERE Prenotazione.Utente_codice_fiscale=? " +
                "AND (Prenotazione.pagata=false OR Prenotazione.pagata IS NULL)");
        st.setString(1, codiceFiscale);
        rs=st.executeQuery();
        while(rs.next()) {
            prenotazioni.add(parserPrenotazione(rs));
        }
        return prenotazioni;
    }


    /**Metodo che permette di cambiare lo stato di una prenotazione da non pagata a pagata*/
    public void paga(PrenotazioneEntity prenotazione) throws Exception{
        st = conn.prepareStatement("UPDATE Prenotazione" +
                " SET pagata = 1" +
                " WHERE Prenotazione.dataOra = ? AND Ambulatorio_idAmbulatorio = ? AND Utente_codice_fiscale = ?");
        st.setTimestamp(1,Timestamp.valueOf(prenotazione.getDataOra()));
        st.setInt(2, prenotazione.getAmbulatorio_idAmbulatorio());
        st.setString(3, prenotazione.getUtente_codice_fiscale());
        st.execute();
    }

    /**Metodo che permette di sapere se un paziente, passato come parametro, è esente dal pagare(true) o no(false)*/
    public boolean getEsenzione(PazienteEntity paziente) throws Exception{
        st = conn.prepareStatement("SELECT esenzione FROM Utente" +
                " WHERE Utente.codiceFiscale=?");
        st.setString(1, paziente.getCodiceFiscale());
        rs=st.executeQuery();

        if(rs.next()) {
            return rs.getBoolean("esenzione");
        }
        return false;
    }

    /**Metodo che permette di ottenere una lista di tutte le prenotazioni effettuate in un determinato ambulatorio, in un determinato periodo, passando come parametri la data dell'inizio e la durata del periodo e l'identificativo dell'ambulatorio*/
    public List<PrenotazioneEntity> getPrenotazioniDaSpostare(LocalDateTime dataOra, int idAmbulatorio, LocalTime durata)throws Exception {
        List<PrenotazioneEntity> prenotazioni= new ArrayList<>();

        st = conn.prepareStatement("SELECT Prenotazione.*, Prestazione.durata FROM Prenotazione,Prestazione" +
                " WHERE Prenotazione.Ambulatorio_idAmbulatorio=?" +
                " AND (Prenotazione.dataOra<? AND Prenotazione.dataOra>?) " +
                " AND Prenotazione.Prestazione_nome=Prestazione.nome" +
                " ORDER BY Prenotazione.dataOra DESC");
        st.setInt(1,idAmbulatorio);
        st.setTimestamp(2,Timestamp.valueOf(dataOra.plusMinutes(durata.getMinute()).plusHours(durata.getHour())));
        int maxDurata = 3;
        st.setTimestamp(3,Timestamp.valueOf(dataOra.minusHours(maxDurata)));
        rs=st.executeQuery();

        while(rs.next()) {
            LocalDateTime inizio= rs.getTimestamp("Prenotazione.dataOra").toLocalDateTime();
            LocalTime offset= rs.getTime("Prestazione.durata").toLocalTime();
            LocalDateTime fine =inizio.plusHours(offset.getHour()).plusMinutes(offset.getMinute());
            if(fine.isAfter(dataOra)){
                PrenotazioneEntity pren =parserPrenotazione(rs);
                if(!prenotazioni.contains(pren)) {
                    prenotazioni.add(pren);
                }
            }
        }
        return prenotazioni;
    }

    public void riempiAmbulatori() throws Exception{
        int amb=1;
        for (int j=1;j<11;j++) {
            for(int i=1;i<5;i++){
                st = conn.prepareStatement("INSERT INTO Ambulatorio (idAmbulatorio,dataInizio,dataFine,Reparto_idReparto) " +
                        "VALUES (?, '1990-01-01 00:00','1990-01-01 00:00', ?)");
                st.setInt(1, amb);
                st.setInt(2, j);
                st.execute();
                amb++;
            }
        }
    }

    public void riempiPrenotazioni(LocalDate giorno)throws Exception{
        List<PazienteEntity> pazienti=generaPazientiRandom();
        int i=0;
        for (Ambulatorio ambulatorio:getAmbulatori(6)) {
            for (LocalTime ora=LocalTime.of(8,0);ora.isBefore(LocalTime.of(18,0));ora=ora.plusMinutes(30)) {
                PrenotazioneEntity prenotazione;
                PazienteEntity paziente=pazienti.get(i);
                if (ora.isBefore(LocalTime.of(16,59))) {
                    prenotazione = new PrenotazioneEntity(giorno.atTime(ora.getHour(),ora.getMinute()),paziente.getCodiceFiscale(),getPrestazioni(getReparto(ambulatorio.getReparto_idReparto())).get(1).getNome(),"P",null,null,null,"Sospetta aritmia",ambulatorio.getIdAmbulatorio(),ambulatorio.getReparto_idReparto(),15.0,false);
                } else {
                    prenotazione = new PrenotazioneEntity(giorno.atTime(ora.getHour(),ora.getMinute()),paziente.getCodiceFiscale(),getPrestazioni(getReparto(ambulatorio.getReparto_idReparto())).get(1).getNome(),"U",null,null,null,"Sospetta aritmia",ambulatorio.getIdAmbulatorio(),ambulatorio.getReparto_idReparto(),15.0,false);
                }
                aggiungiPrenotazione(prenotazione);
                i++;
            }
        }
    }
/*
    public void riempiStanze() throws Exception{
        for(Reparto reparto:getReparti()){
            for (int numeroStanza=1; numeroStanza<=10;numeroStanza++) {
                st = conn.prepareStatement("INSERT INTO Stanza (numeroStanza, Reparto_idReparto) " +
                        "VALUES (?, ?)");
                st.setInt(1, numeroStanza);
                st.setInt(2, reparto.getIdReparto());
                st.execute();

                for (int numeroLetto=1; numeroLetto<=2;numeroLetto++) {
                    st = conn.prepareStatement("INSERT INTO Letto (numeroLetto, Stanza_numeroStanza, Stanza_Reparto_idReparto, Utente_codiceFiscale) " +
                            "VALUES (?, ?, ?, ?)");
                    st.setInt(1, numeroLetto);
                    st.setInt(2, numeroStanza);
                    st.setInt(3, reparto.getIdReparto());
                    st.setString(4, null);
                    st.execute();

                }
            }
        }
    }

    public void generaUtentiRandom()throws Exception{
        List<String> nomi=new ArrayList<>();
        List<String> cognomi=new ArrayList<>();
        nomi.add("Marco");
        nomi.add("Francesco");
        nomi.add("Giovanni");
        nomi.add("Paolo");
        nomi.add("Mario");
        nomi.add("Sonia");
        nomi.add("Valeria");
        nomi.add("Serena");
        nomi.add("Luca");
        nomi.add("Gaetano");
        nomi.add("Salvatore");
        nomi.add("Giuseppe");
        nomi.add("Davide");
        nomi.add("Roberta");

        cognomi.add("Rossi");
        cognomi.add("Verdi");
        cognomi.add("Neri");
        cognomi.add("Bianchi");
        cognomi.add("Gialli");
        cognomi.add("Ferrari");
        cognomi.add("Esposito");
        cognomi.add("Russo");
        cognomi.add("Romano");
        cognomi.add("Colombo");
        cognomi.add("Ricci");
        cognomi.add("Marino");
        cognomi.add("Greco");
        cognomi.add("Bruno");
        cognomi.add("Gallo");
        cognomi.add("Ponti");

        Random random=new Random();
        for(int i=1;i<=80;i++){
            String nome=nomi.get(random.nextInt(nomi.size()));
            String cognome=cognomi.get(random.nextInt(cognomi.size()));
            LocalDate dataDiNascita=LocalDate.of(1990-random.nextInt(40),random.nextInt(12)+1,random.nextInt(28)+1);
            String giorno=dataDiNascita.getDayOfMonth().toString();
            if(giorno.length!=2){
                String giornata=dataDiNascita.getDayOfMonth().toString();
                giorno="0"+giornata
            }
            String anno=(dataDiNascita.getYear()-1900);
            if(anno.length!=2){
                String annata=(dataDiNascita.getYear()-1900).toString();
                anno="0"+annata
            }
            String codiceFiscale=nome.substring(0,3).toUpperCase()+cognome.substring(0,3).toUpperCase()+anno+cognomi.get(random.nextInt(cognomi.size())).substring(0,1)+giorno+"G237"+cognomi.get(random.nextInt(cognomi.size())).substring(0,1);
            String telefono="3"+(random.nextInt(899999999)+100000000);
            PazienteEntity paziente=PazienteEntity.create(codiceFiscale,nome,cognome,dataDiNascita,"Palermo","pazienteProva1@gmail.com",telefono,"password",false);
            inserisciPaziente(paziente);
        }

    }

    public void generaMedici()throws Exception{
        int j=0;
        List<PazienteEntity> pazienti=generaPazientiRandom();
        for(Reparto reparto:getReparti()){
            for(Ambulatorio ambulatorio:getAmbulatori(reparto)){
                for(int i=0;i<2;i++){
                    registraMedico(pazienti.get(j).getCodiceFiscale(),ambulatorio);
                    j++;
                }
            }
        }
    }
*/
    private List<PazienteEntity> generaPazientiRandom()throws Exception{
        List<PazienteEntity> pazienti=new ArrayList<>();
        st = conn.prepareStatement("SELECT * FROM Utente" +
                " WHERE NOT codiceFiscale=? " +
                " ORDER BY nome DESC ");
        st.setString(1,"LMRMRC97E28C421N");
        rs=st.executeQuery();
        int i=0;
        while(rs.next() && i<80){
            pazienti.add(parserPaziente(rs));
            i++;
        }
        return pazienti;
    }

    /**Metodo che aggiunge una prenotazione, passata come parametro, all'interno del database*/
    public void aggiungiPrenotazione(PrenotazioneEntity prenotazione) throws Exception{
        st = conn.prepareStatement("INSERT INTO Prenotazione (dataOra, Prestazione_nome, Utente_codice_fiscale, diagnosi_medico_curante, urgenza, prezzo, pagata, Ambulatorio_idAmbulatorio, Ambulatorio_Reparto_idReparto) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        st.setTimestamp(1, Timestamp.valueOf(prenotazione.getDataOra()));
        st.setString(2, prenotazione.getPrestazione_nome());
        st.setString(3, prenotazione.getUtente_codice_fiscale());
        st.setString(4, prenotazione.getDiagnosi_medico_curante());
        st.setString(5, prenotazione.getUrgenza());
        st.setDouble(6, prenotazione.getPrezzo());
        st.setBoolean(7, prenotazione.isPagata());
        st.setInt(8, prenotazione.getAmbulatorio_idAmbulatorio());
        st.setInt(9, prenotazione.getAmbulatorio_Reparto_idReparto());
        st.execute();
    }

    /**Metodo per ottenere tutte le prenotazioni con le prestazioni associate in un determinato periodo*/
    public List<Info> getInfo(Prestazione prestazione, LocalDateTime inizio, LocalDateTime fine) throws Exception{
        List<Info> info= new ArrayList<>();

        st = conn.prepareStatement("SELECT Prenotazione.dataOra, Prenotazione.Prestazione_nome, Prenotazione.Utente_codice_fiscale, Prenotazione.Ambulatorio_idAmbulatorio, Prenotazione.Ambulatorio_Reparto_idReparto, Prestazione.durata, Prenotazione.urgenza FROM Prenotazione,Prestazione" +
                " WHERE Prenotazione.Ambulatorio_Reparto_idReparto=? "+
                " AND Prenotazione.dataOra<=? " +
                " AND Prenotazione.dataOra>=?" +
                " AND Prenotazione.Prestazione_nome=Prestazione.nome ");
        st.setInt(1,prestazione.getReparto_idReparto());
        st.setTimestamp(2,Timestamp.valueOf(fine));
        st.setTimestamp(3,Timestamp.valueOf(inizio));
        rs=st.executeQuery();

        while(rs.next()){
            info.add(parserInfo(rs));
        }
        return info;
    }

    /**Metodo che converte un ResultSet in PazienteEntity*/
    private PazienteEntity parserPaziente(ResultSet queryResult) throws Exception{
        String codiceFiscale=queryResult.getString("codiceFiscale");
        String password = queryResult.getString("password");
        String nome = queryResult.getString("nome");
        String cognome = queryResult.getString("cognome");
        LocalDate dataDiNascita = queryResult.getDate("DataDiNascita").toLocalDate();
        String telefono = queryResult.getString("telefono");
        String email = queryResult.getString("email");
        boolean esenzione = queryResult.getBoolean("esenzione");
        String luogoDiNascita = queryResult.getString("luogoDiNascita");
        return PazienteEntity.create(codiceFiscale,nome,cognome,dataDiNascita,luogoDiNascita,email,telefono,password,esenzione);
    }

    /**Metodo che converte un ResultSet in Reparto*/
    private Reparto parserReparto(ResultSet queryResult) throws Exception{
        int idReparto=queryResult.getInt("idReparto");
        String nome = queryResult.getString("nome");
        LocalTime apertura= queryResult.getTime("apertura").toLocalTime();
        LocalTime chiusura= queryResult.getTime("chiusura").toLocalTime();
        return Reparto.create(idReparto, nome, apertura, chiusura);
    }

    /**Metodo che converte un ResultSet in Prestazione*/
    private Prestazione parserPrestazione(ResultSet queryResult) throws Exception{
        int Reparto_idReparto=queryResult.getInt("Reparto_idReparto");
        String nome = queryResult.getString("nome");
        LocalTime durata= queryResult.getTime("durata").toLocalTime();
        String documenti = queryResult.getString("documenti");
        return Prestazione.create(nome, durata, documenti, Reparto_idReparto);
    }

    /**Metodo che converte un ResultSet in PrenotazioneEntity*/
    private PrenotazioneEntity parserPrenotazione(ResultSet queryResult)throws Exception {
        LocalDateTime dataOra= queryResult.getTimestamp("dataOra").toLocalDateTime();
        String Utente_codice_fiscale=queryResult.getString("Utente_codice_fiscale");
        String prestazione_nome=queryResult.getString("Prestazione_nome");
        String urgenza=queryResult.getString("urgenza");
        String diagnosi=queryResult.getString("diagnosi");
        String anamnesi=queryResult.getString("anamnesi");
        String cure=queryResult.getString("cure");
        String diagnosi_medico_curante=queryResult.getString("diagnosi_medico_curante");
        int ambulatorio_idAmbulatorio=queryResult.getInt("Ambulatorio_idAmbulatorio");
        int ambulatorio_Reparto_idReparto=queryResult.getInt("Ambulatorio_Reparto_idReparto");
        boolean pagata=queryResult.getBoolean("pagata");
        double prezzo=queryResult.getDouble("prezzo");

        return PrenotazioneEntity.create(dataOra, Utente_codice_fiscale, prestazione_nome, urgenza, diagnosi, anamnesi, cure, diagnosi_medico_curante, ambulatorio_idAmbulatorio, ambulatorio_Reparto_idReparto, prezzo, pagata);
    }

    /**Metodo che converte un ResultSet in Info*/
    private Info parserInfo(ResultSet queryResult) throws Exception{
        LocalDateTime dataOra= queryResult.getTimestamp("dataOra").toLocalDateTime();
        String Utente_codice_fiscale=queryResult.getString("Utente_codice_fiscale");
        String prestazione_nome=queryResult.getString("Prestazione_nome");
        int ambulatorio_idAmbulatorio=queryResult.getInt("Ambulatorio_idAmbulatorio");
        int ambulatorio_Reparto_idReparto=queryResult.getInt("Ambulatorio_Reparto_idReparto");
        LocalTime durata= queryResult.getTime("durata").toLocalTime();
        String urgenza=queryResult.getString("urgenza");
        return Info.create(dataOra, Utente_codice_fiscale, prestazione_nome, ambulatorio_idAmbulatorio, ambulatorio_Reparto_idReparto,durata,urgenza);
    }

    /**Metodo che converte un ResultSet in MedicoEntity*/
    private MedicoEntity parserMedico(ResultSet queryResult)throws Exception {
        String codiceFiscale=queryResult.getString("codiceFiscale");
        String password = queryResult.getString("password");
        String nome = queryResult.getString("nome");
        String cognome = queryResult.getString("cognome");
        LocalDate dataDiNascita = queryResult.getDate("DataDiNascita").toLocalDate();
        String telefono = queryResult.getString("telefono");
        String email = queryResult.getString("email");
        String luogoDiNascita = queryResult.getString("luogoDiNascita");
        int matricola = queryResult.getInt("matricola");
        int Ambulatorio_idAmbulatorio = queryResult.getInt("Ambulatorio_idAmbulatorio");
        int Ambulatorio_Reparto_idReparto = queryResult.getInt("Ambulatorio_Reparto_idReparto");
        return MedicoEntity.create(codiceFiscale,nome,cognome,dataDiNascita,luogoDiNascita,email,telefono,password,matricola,Ambulatorio_idAmbulatorio,Ambulatorio_Reparto_idReparto);
    }

    /**Metodo che converte un ResultSet in AmministrativoEntity*/
    private AmministrativoEntity parserAmministrativo(ResultSet queryResult) throws Exception{
        String codiceFiscale=queryResult.getString("codiceFiscale");
        String password = queryResult.getString("password");
        String nome = queryResult.getString("nome");
        String cognome = queryResult.getString("cognome");
        LocalDate dataDiNascita = queryResult.getDate("DataDiNascita").toLocalDate();
        String telefono = queryResult.getString("telefono");
        String email = queryResult.getString("email");
        String luogoDiNascita = queryResult.getString("luogoDiNascita");
        int matricola = queryResult.getInt("matricola");
        return AmministrativoEntity.create(codiceFiscale,nome,cognome,dataDiNascita,luogoDiNascita,email,telefono,password,matricola);
    }

    /**Metodo che converte un ResultSet in Ambulatorio*/
    private Ambulatorio parserAmbulatorio(ResultSet queryResult) throws Exception{
        int idAmbulatorio=queryResult.getInt("idAmbulatorio");
        int Reparto_idReparto=queryResult.getInt("Reparto_idReparto");
        LocalDate dataInizio= queryResult.getDate("dataInizio").toLocalDate();
        LocalDate dataFine= queryResult.getDate("dataFine").toLocalDate();
        return Ambulatorio.create(idAmbulatorio,Reparto_idReparto,dataInizio,dataFine);
    }

    /**Metodo che converte un ResultSet in LettoEntity*/
    private LettoEntity parserLetto(ResultSet queryResult) throws Exception{
        int numeroLetto=queryResult.getInt("numeroLetto");
        int numeroStanza=queryResult.getInt("Stanza_numeroStanza");
        int idReparto=queryResult.getInt("Stanza_Reparto_idReparto");
        String codiceFiscale=queryResult.getString("Utente_codiceFiscale");
        return LettoEntity.create(numeroLetto,numeroStanza,idReparto,codiceFiscale);
    }
}

