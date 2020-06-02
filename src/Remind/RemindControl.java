package Remind;


import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.Mail;
import Entity.PrenotazioneEntity;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;


/**Classe che si occupa di mandare una mail a tutti gli utenti che hanno prenotato le prestazioni per ricordare loro della visita e allega la lista dei documenti da portare e quale sarà l'ambulatorio dove verrà effettuata la visita*/
public class RemindControl {

	/**Viene lanciato un timer che ogni giorno a mezzanotte manda le mail di remind a coloro che hanno prenotazioni l'indomani. Se il sistema, a causa di un malfunzionamento o manutenzione, viene spento e riacceso,
	 *controlla che le mail di remind per il giorno successivo siano già state inviate, avendo salvato la data dell'ultimo remind effettuato in un file.
	 *Se il controllo ha esito negativo, allora saranno inviate le mail*/
	public static void inviaNotifiche() {
		//LocalDateTime ora=LocalDateTime.now();

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					LocalDateTime lastNotified=null;
					try {
						DBMSospedale.get().cancellaPrenotazioniNonEffettuate();
						FileInputStream fis = new FileInputStream("ultimoRemind.ser");
						ObjectInputStream ois = new ObjectInputStream (fis);
						lastNotified = (LocalDateTime) ois.readObject();
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}

					for(PrenotazioneEntity prenotazione: DBMSospedale.get().getPrenotazioni(LocalDateTime.now().plusDays(1).toLocalDate())) {

						if (LocalDateTime.now().getDayOfYear()>lastNotified.minusMinutes(1).getDayOfYear()) {
							Mail.get().inviaNotifiche(prenotazione);
						}
					}
					lastNotified = LocalDateTime.now();
					salvaOrario(lastNotified.withNano(0).withSecond(0));
				} catch (SQLException e) {
					new NotificaBoundary(e.getMessage());
					DBMSospedale.setConnessioneCaduta(true);
				}catch (Exception e) {
					new NotificaBoundary(e.getMessage());
				}
			}
		}, new GregorianCalendar(LocalDateTime.now().getYear(),LocalDateTime.now().getMonthValue()-1,LocalDateTime.now().getDayOfMonth(),0,0).getTime(),86400000);
	}


	/**Metodo che salva la data dell'ultimo remind effettuato*/
	private static void salvaOrario(LocalDateTime lastNotified) {
		try {
			//Saving of object in a file
			FileOutputStream file = new FileOutputStream("ultimoRemind.ser");
			ObjectOutputStream out = new ObjectOutputStream(file);

			// Method for serialization of object
			out.writeObject(lastNotified);

			out.close();
			file.close();
		}

		catch(IOException ex) {
			new NotificaBoundary("Impossibile aggiornare i dati sulle notifiche inviate");
		}
	}
}
