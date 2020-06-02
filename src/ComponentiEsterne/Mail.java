package ComponentiEsterne;

import Autenticazione.NotificaBoundary;
import Entity.PrenotazioneEntity;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**Classe che gestisce l'invio delle email*/
public class Mail{
    /**Istanza della classe Mail*/
    private static Mail instance = new Mail();

    /**Istanza della classe Session*/
    private Session session;

    /**Indirizzo email mittente*/
    private final String sourceMail;

    /**Password dell'account email mittente*/
    private final String sourcePassword;

    /**Username dell'account email mittente*/
    private final String username;

    /**Metodo getter della variabile instance*/
    public static Mail get() {
        return instance;
    }

    /**Costruttore che instaura una connessione al server Mail*/
    private Mail() {
        username="ospedalePalermo@gmail.com";
        sourceMail="ospedalePalermo@gmail.com";
        sourcePassword= "ingegneriadelsoftware1!";
        // Assuming you are sending email through localhost
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,sourcePassword);
                    }
                });
    }

    /**Metodo per settare l'indirizzo email di destinazione*/
    private Message setMessage(String destinationMail){
        // Create a default MimeMessage object.
        Message message;
        message = new MimeMessage(session);

        // Set From: header field of the header.
        try {
            message.setFrom(new InternetAddress(sourceMail));
        } catch (MessagingException e) {
            new NotificaBoundary(e.getMessage());
            return null;
        }

        // Set To: header field of the header.
        try {
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinationMail));
        } catch (MessagingException e) {
            new NotificaBoundary(e.getMessage());
            return null;
        }

        // Set Subject: header field
        try {
            message.setSubject("Notifica prenotazione");
        } catch (MessagingException e) {
            new NotificaBoundary(e.getMessage());
            return null;
        }

        return message;
    }

    /**Metodo per inviare un email ad un paziente, per avvisarlo che è stata spostata una sua prenotazione per esigenze amministrative*/
    public void avvisaPazienteSpostamento(PrenotazioneEntity prenotazione,PrenotazioneEntity prenotazioneVecchia){
        try{
            Message message = this.setMessage(DBMSospedale.get().getEmail(prenotazione));
            String stringa="Gentile "+DBMSospedale.get().getNomeCognome(prenotazione)+
                    ",\nabbiamo avuto la necessità di spostare la sua prenotazione per " + prenotazione.getPrestazione_nome()+
                    " nel reparto di " + DBMSospedale.get().getReparto(prenotazione.getAmbulatorio_Reparto_idReparto()).getNome() + "."+
                    "\nda giorno " + prenotazioneVecchia.getDataOra().toLocalDate() +
                    " alle ore "+prenotazioneVecchia.getDataOra().toLocalTime().withMinute(0) +
                    " a giorno " + prenotazione.getDataOra().toLocalDate() +
                    " alle ore "+prenotazione.getDataOra().toLocalTime().withMinute(0);
            if(prenotazione.getPrezzo()!=0) {
                stringa+=".\nLe ricordiamo inoltre che il prezzo del ticket è di " + prenotazione.getPrezzo() +
                        "0€";
            }else{
                stringa+=".\nLe ricordiamo inoltre che essendo esente non dovrà pagare il ticket";
            }
            if(DBMSospedale.get().getDocumenti(prenotazione)!=null){
                stringa+= " e anche di portare con sè la seguente documentazione:\n" +DBMSospedale.get().getDocumenti(prenotazione);
            }
            stringa+="\nLa prestazione verrà svolta presso l'ambulatorio n°"+prenotazione.getAmbulatorio_idAmbulatorio()+
                    ".\nCi scusiamo per il disagio. Cordiali saluti";
            message.setText(stringa);
            //Send Message
            Transport.send(message);
        }catch (Exception e) {
            new NotificaBoundary("Errore invio mail");
        }
    }

    /**Metodo per inviare un email ad un paziente, per ricordargli della prenotazione*/
    public void inviaNotifiche(PrenotazioneEntity prenotazione){
        try{
            Message message = this.setMessage(DBMSospedale.get().getEmail(prenotazione));
            String stringa="Gentile "+DBMSospedale.get().getNomeCognome(prenotazione)+
                    ",\nle ricordiamo della sua prenotazione per " + prenotazione.getPrestazione_nome()+
                    " nel reparto di " + DBMSospedale.get().getReparto(prenotazione.getAmbulatorio_Reparto_idReparto()).getNome() +
                    "\nche si terrà giorno " + prenotazione.getDataOra().toLocalDate() +
                    " alle ore "+prenotazione.getDataOra().toLocalTime().withMinute(0);
            if(prenotazione.getPrezzo()!=0) {
                stringa+=".\nLe ricordiamo inoltre che il prezzo del ticket è di " + prenotazione.getPrezzo() +
                        "0€";
            }else{
                stringa+=".\nLe ricordiamo inoltre che essendo esente non dovrà pagare il ticket";
            }
            if(DBMSospedale.get().getDocumenti(prenotazione)!=null){
                stringa+= " e anche di portare con sè la seguente documentazione:\n" +DBMSospedale.get().getDocumenti(prenotazione);
            }
            stringa+=".\nLa prestazione verrà svolta presso l'ambulatorio n°"+prenotazione.getAmbulatorio_idAmbulatorio()+
                    ".\nIn caso di mancata disponibilità la preghiamo di eliminare la prenotazione.\nCordiali saluti";
            message.setText(stringa);
            //Send Message
            Transport.send(message);
        }catch (Exception e) {
            new NotificaBoundary("Errore invio mail");
        }
    }

    /**Metodo per inviare un email ad un paziente, per confermare la prenotazione da lui effettuata*/
    public void confermaPrenotazione(PrenotazioneEntity prenotazione){
        try{
            Message message = this.setMessage(DBMSospedale.get().getEmail(prenotazione));
            String stringa="Gentile "+DBMSospedale.get().getNomeCognome(prenotazione)+
                    ",\nle confermiamo la sua prenotazione per " + prenotazione.getPrestazione_nome()+
                    " nel reparto di " + DBMSospedale.get().getReparto(prenotazione.getAmbulatorio_Reparto_idReparto()).getNome() +
                    "\nche si terrà giorno " + prenotazione.getDataOra().toLocalDate() +
                    " alle ore "+prenotazione.getDataOra().toLocalTime().withMinute(0);
            if(prenotazione.getPrezzo()!=0) {
                stringa+=".\nLe ricordimo inoltre che il prezzo del ticket è di " + prenotazione.getPrezzo() +
                        "0€";
            }else{
                stringa+=".\nLe ricordimo inoltre che essendo esente non dovrà pagare il ticket";
            }
            if(DBMSospedale.get().getDocumenti(prenotazione)!=null){
                stringa+= " e anche di portare con sè la seguente documentazione:\n" +DBMSospedale.get().getDocumenti(prenotazione);
            }
            stringa+=".\nLa prestazione verrà svolta presso l'ambulatorio n°"+prenotazione.getAmbulatorio_idAmbulatorio()+
                    ".\nCordiali saluti";
            message.setText(stringa);
            //Send Message
            Transport.send(message);
        }catch (Exception e) {
            new NotificaBoundary("Errore invio mail");
        }
    }
    /**Metodo per inviare un email ad un paziente, per confermare l'eliminazione, da lui effettuata, di una prenotazione*/
    public void eliminaPrenotazione(PrenotazioneEntity prenotazione){
        try{
            Message message = this.setMessage(DBMSospedale.get().getEmail(prenotazione));
            String stringa="Gentile "+DBMSospedale.get().getNomeCognome(prenotazione)+
                    ",\nle confermiamo l'eliminazione della sua prenotazione per " + prenotazione.getPrestazione_nome()+
                    " nel reparto di " + DBMSospedale.get().getReparto(prenotazione.getAmbulatorio_Reparto_idReparto()).getNome();
            stringa+=".\nCordiali saluti";
            message.setText(stringa);
            //Send Message
            Transport.send(message);
        }catch (Exception e) {
            new NotificaBoundary("Errore invio mail");
        }
    }

    /**Metodo per inviare un email ad un paziente, per confermare la modifica, da lui effettuata, della data e dell'orario di una prenotazione*/
    public void modificaPrenotazione(PrenotazioneEntity prenotazione,PrenotazioneEntity prenotazioneVecchia) {
        try {
            Message message = this.setMessage(DBMSospedale.get().getEmail(prenotazione));
            String stringa = "Gentile " + DBMSospedale.get().getNomeCognome(prenotazione) +
                    ",\nle confermiamo la modifica della prenotazione per " + prenotazione.getPrestazione_nome() +
                    " nel reparto di " + DBMSospedale.get().getReparto(prenotazione.getAmbulatorio_Reparto_idReparto()).getNome() +
                    ".\nLa visita è stata da lei spostata da giorno " + prenotazioneVecchia.getDataOra().toLocalDate() +
                    " alle ore " + prenotazioneVecchia.getDataOra().toLocalTime().withMinute(0) +
                    " a giorno "+ prenotazione.getDataOra().toLocalDate() +
                    " alle ore "+ prenotazione.getDataOra().toLocalTime().withMinute(0);
            if (prenotazione.getPrezzo() != 0) {
                stringa += ".\nLe ricordimo inoltre che il prezzo del ticket è di " + prenotazione.getPrezzo() +
                        "0€";
            } else {
                stringa += ".\nLe ricordimo inoltre che essendo esente non dovrà pagare il ticket";
            }
            if (DBMSospedale.get().getDocumenti(prenotazione) != null) {
                stringa += " e anche di portare con sè la seguente documentazione:\n" + DBMSospedale.get().getDocumenti(prenotazione);
            }
            stringa += ".\nLa prestazione verrà svolta presso l'ambulatorio n°" + prenotazione.getAmbulatorio_idAmbulatorio() +
                    ".\nCordiali saluti";
            message.setText(stringa);
            //Send Message
            Transport.send(message);
        } catch (Exception e) {
            new NotificaBoundary("Errore invio mail");
        }
    }
}

