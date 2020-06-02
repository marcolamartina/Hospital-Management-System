package GestionePrenotazioni;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.DBMSricette;
import ComponentiEsterne.InterfacciaGrafica;
import ComponentiEsterne.Mail;
import Entity.*;
import javafx.concurrent.Task;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**Classe che si occupa di permettere una volta scelta la prestazione, una data e l’orario della prestazione*/
public class SelezioneDataControl {
	/**Riferimento ad una map, che contiene gli slot orari liberi per ciascun ambulatorio*/
	private Map<Ambulatorio,List<LocalDateTime>> calendario;

	/**Tipo di prestazione che si vuole prenotare*/
	private Prestazione prestazione;

	/**Urgenza della prestazione*/
	private String urgenza;

	/**Dimensione in minuti degli slot orari*/
	private final long dimSlot=10;

	/**Mappa contenente le associazioni tra le lettere rappresentanti le urgenze e i relativi giorni entro cui devono essere effettuate le prestazioni*/
	private Map<String, Integer> datiUrgenze;

	/**Riferimento ad una map, che contiene gli orari in cui può essere effettuata una prenotazione, ovvero gli orari consecutivi alla fine delle altre prenotazioni e, se disponibili, anche gli orari del tipo hh:00, per ciascun ambulatorio*/
	private Map<Ambulatorio,List<LocalDateTime>> orari;

	/**Riferimento alla prenotazione di cui si vuole scegliere data e orario*/
	private PrenotazioneEntity prenotazione;

	/**Lista degli ambulatori disponibili*/
	private List<Ambulatorio> ambulatorioList;

	/**Lista di orari da mostrare all'utente*/
	private List<LocalDateTime> orariDaVisualizzare;

	/**Lista di giorni disponibili*/
	private List<LocalDate> giorniDaVisualizzare;

	/**Rifermineto alla control*/
	private String control;


	/**Costruttore che si occupa di decidere se abilitare o no lo spostamento automatico delle prenotazioni meno urgenti di quella che si vuole prenootare.
	 * Questa scelta viene effettuata valutando se vi sono giorni disponibili alla prenotazione, entro il limite di tempo dettato dall'urgenza.
	 *
	 */
	public SelezioneDataControl(PrenotazioneEntity prenotazione, String control) {
		try {
			this.prenotazione=prenotazione;
			this.prestazione=DBMSospedale.get().getPrestazione(prenotazione.getPrestazione_nome());
			this.urgenza=prenotazione.getUrgenza();
			this.calendario = new HashMap<>();
			this.orari= new HashMap<>();
			this.datiUrgenze=new HashMap<>();
			this.giorniDaVisualizzare=new ArrayList<>();
			this.orariDaVisualizzare=new ArrayList<>();
			this.control=control;
			datiUrgenze.put("U",1);
			datiUrgenze.put("B",10);
			datiUrgenze.put("D",60);
			datiUrgenze.put("P",180);
			if (control.compareTo("GestisciSpostamenti")!=0) {
				creaCalendario(false,LocalDateTime.now().plusDays(datiUrgenze.get(urgenza)).withHour(23).withMinute(59).withSecond(59));
				boolean sposta=false;
				if(orariDaVisualizzare.isEmpty()){
					sposta=true;
				}
				creaCalendario(sposta,LocalDateTime.now().plusYears(1).withHour(23).withMinute(59).withSecond(59));
			}
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}

	}

	/**Metodo che effettua una serie di controlli per ottenere una lista di giorni disponibili e di relativi orari, riservando 60 minuti, posizionati subito prima dell'orario di chiusura del reparto, alle prenotazioni urgenti.
	 *Gli orari mostrati all'utente saranno solamente delle fasce orarie, così da poter gestire meglio eventuali ritardi e anticipi, anche se la prenotazione sarà effettuata ad un preciso orario, scelto da una lista di orari disponibili, creata in modo da minimizzare i tempi morti.
	 */
	private void creaCalendario(boolean spostamentiOn, LocalDateTime fine)throws Exception{
		//Inizializzazione
		this.calendario = new HashMap<>();
		this.orari= new HashMap<>();
		this.giorniDaVisualizzare=new ArrayList<>();
		this.orariDaVisualizzare=new ArrayList<>();

		LocalDateTime inizio = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

		List<Info> infos= DBMSospedale.get().getInfo(prestazione,inizio, fine);
		Reparto reparto=DBMSospedale.get().getReparto(prestazione.getReparto_idReparto());
		LocalTime apertura=reparto.getApertura();
		LocalTime chiusura= reparto.getChiusura();

		//Creazione della map calendario completa
		ambulatorioList=DBMSospedale.get().getAmbulatoriNonEmergenza(reparto.getIdReparto(), inizio.toLocalDate(), fine.toLocalDate());
		for (Ambulatorio amb:ambulatorioList){
			calendario.put(amb,new ArrayList<>());
			for (LocalDateTime i = inizio; i.isBefore(fine) ; i = i.plusMinutes(dimSlot)) {
				if (urgenza.compareTo("U")==0){
					if(i.toLocalTime().isAfter(apertura.minusMinutes(1)) && i.toLocalTime().isBefore(chiusura) && ((i.toLocalDate().isAfter(amb.getDataFine())) || i.toLocalDate().isBefore(amb.getDataInizio()))) {
						calendario.get(amb).add(i);
					}
				}else{
					long minutiRiservati = 60;
					if(i.toLocalTime().isAfter(apertura.minusMinutes(1)) && i.toLocalTime().isBefore(chiusura.minusMinutes(minutiRiservati)) && ((i.toLocalDate().isAfter(amb.getDataFine())) || i.toLocalDate().isBefore(amb.getDataInizio()))) {
						calendario.get(amb).add(i);
					}
				}
			}
		}

		//Eliminazione degli slot occupati
		for(Info i:infos){
			inizio=i.getDataOra();
			fine=i.getDataOra().plusHours(i.getDurata().getHour()).plusMinutes(i.getDurata().getMinute());
			Ambulatorio amb=null;
			for(LocalDateTime pointer=inizio; pointer.isBefore(fine); pointer=pointer.plusMinutes(dimSlot)){
				for(Ambulatorio a:ambulatorioList){
					if(a.getIdAmbulatorio()==i.getAmbulatorio_idAmbulatorio()){
						amb=a;
					}
				}
				if(!spostamentiOn){
					if(calendario.containsKey(amb) && calendario.get(amb).contains(pointer)) {
						calendario.get(amb).remove(pointer);

					}
				}else{
					if(calendario.containsKey(amb) && calendario.get(amb).contains(pointer) && datiUrgenze.get(i.getUrgenza())<=datiUrgenze.get(urgenza)) {
						calendario.get(amb).remove(pointer);
					}
				}
			}
		}

		//Creazione della map orari
		for (Map.Entry<Ambulatorio, List<LocalDateTime>> entry : calendario.entrySet()) {
			//Ordinamento
			List<LocalDateTime> lista=entry.getValue();
			orari.put(entry.getKey(),new ArrayList<>());
			lista.sort((one, other) -> one.compareTo(other));

				ListIterator<LocalDateTime> iterator = lista.listIterator();
				LocalDateTime prec=LocalDateTime.of(1990,01,01,00,00);
				while(iterator.hasNext()) {
					LocalDateTime current = iterator.next();
					if(!prec.plusMinutes(dimSlot).isEqual(current)){
						if(puoPrenotare(entry.getKey(),current)){
							if (!orari.get(entry.getKey()).contains(current)) {
								orari.get(entry.getKey()).add(current);
							}
						}
					}
					if(current.getMinute()==0 && puoPrenotare(entry.getKey(), current)){
						if (!orari.get(entry.getKey()).contains(current)) {
							orari.get(entry.getKey()).add(current);
						}
					}

					prec=current;
				}
				//Si fa visualizzare solo l'ora di orari ma si aggiunge la prenotazione a quell'ora

		}

		//Creazione slot da visualizzare
		for(Map.Entry<Ambulatorio, List<LocalDateTime>> entry : orari.entrySet()){
			List<LocalDateTime> list=entry.getValue();
			for(LocalDateTime orari:list){
				LocalDateTime pointer=LocalDateTime.of(orari.getYear(),orari.getMonthValue(),orari.getDayOfMonth(),orari.getHour(),0);
				if(!orariDaVisualizzare.contains(pointer)){
					orariDaVisualizzare.add(pointer);
				}
			}
		}
		orariDaVisualizzare.sort((one, other) -> one.compareTo(other));

		for(Map.Entry<Ambulatorio, List<LocalDateTime>> entry : orari.entrySet()){
			List<LocalDateTime> list=entry.getValue();
			for(LocalDateTime orari:list){
				LocalDate pointer=LocalDate.of(orari.getYear(),orari.getMonthValue(),orari.getDayOfMonth());
				if(!giorniDaVisualizzare.contains(pointer)){
					giorniDaVisualizzare.add(pointer);
				}
			}
		}
		giorniDaVisualizzare.sort((one, other) -> one.compareTo(other));

	}

	/**Metodo che sceglie le eventuali prenotazioni da spostare*/
	private void scegliPrenotazioneDaSpostare()throws Exception{

		List<PrenotazioneEntity> prenotazioni=DBMSospedale.get().getPrenotazioniDaSpostare(prenotazione.getDataOra(), prenotazione.getAmbulatorio_idAmbulatorio(),prestazione.getDurata());
		GestisciSpostamentiControl.gestisciSpostamenti(prenotazioni);
	}


	/**Metodo getter della variabile giorniDaVisualizzare*/
	public List<LocalDate> getGiorniDaVisualizzare() {
		return giorniDaVisualizzare;
	}

	/**Metodo che conferma la prenotazione nel giorno e orario passati come parametro, chiedendo al DBMS di aggiungere la prenotazione, dopo averne calcolato il prezzo con il metodo calcolaPrezzo.
	 * Inoltre si occupa di mandare una mail di conferma dell'avvenuta prenotazione e di eliminare dal DBMS delle ricette la ricetta utilizzata.
	 * @see DBMSospedale#aggiungiPrenotazione(PrenotazioneEntity)
	 * @see Mail#confermaPrenotazione(PrenotazioneEntity)
	 * @see DBMSricette#eliminaRicetta(RicettaEntity)
	 */
	public void conferma(LocalDate giorno,LocalTime ora) {
		try {
			if(giorno!=null && ora!=null){
				PrenotazioneEntity prenotazioneVecchia=prenotazione.copia();
				prenotazione.setDataOra(scegliOrario(LocalDateTime.of(giorno.getYear(),giorno.getMonthValue(),giorno.getDayOfMonth(),ora.getHour(),ora.getMinute())));
				scegliAmbulatorio();
				scegliPrenotazioneDaSpostare();
				calcolaPrezzo(DBMSospedale.get().getEsenzione(PazienteEntity.getSelected()));
				DBMSospedale.get().aggiungiPrenotazione(prenotazione);
				if(control.compareTo("SceltaPrestazione")==0){
					Task task=new Task(){
						@Override
						protected Object call() throws Exception {
							Mail.get().confermaPrenotazione(prenotazione);
							return null;
						}
					};
					Thread thread=new Thread(task);
					thread.setDaemon(true);
					thread.start();

					InterfacciaGrafica.mostra("/Autenticazione/MenuMedicoBoundary.fxml");
				}else if(control.compareTo("InserimentoDatiRicetta")==0){
					Task task=new Task(){
						@Override
						protected Object call() throws Exception {
							Mail.get().confermaPrenotazione(prenotazione);
							return null;
						}
					};
					Thread thread=new Thread(task);
					thread.setDaemon(true);
					thread.start();
					DBMSricette.get().eliminaRicetta(RicettaEntity.getSelected());
					RicettaEntity.setSelected(null);
					InterfacciaGrafica.mostra("/Autenticazione/MenuPazienteBoundary.fxml");
				}else if(control.compareTo("ModificaData")==0){
					Task task=new Task(){
						@Override
						protected Object call() throws Exception {
							Mail.get().modificaPrenotazione(prenotazione, prenotazioneVecchia);
							return null;
						}
					};
					Thread thread=new Thread(task);
					thread.setDaemon(true);
					thread.start();

					DBMSospedale.get().cancellaPrenotazione(prenotazioneVecchia);
					GestionePrenotazioniControl.aggiorna();
				}
				String stringa="Prenotazione effettuata";
				if(prestazione.getDocumenti()!=null) {
					new NotificaBoundary(stringa+".\nPortare i seguenti documenti\nal momento della visita:\n"+prestazione.getDocumenti());
				}else{
					new NotificaBoundary(stringa);
				}
			}else{
				new NotificaBoundary("Selezionare data e ora");
			}
		} catch (SQLException e) {
			DBMSospedale.setConnessioneCaduta(true);
			CalendarioBoundary.setControl(new SelezioneDataControl(PrenotazioneEntity.getSelected(),control));
			InterfacciaGrafica.mostra("/GestionePrenotazioni/CalendarioBoundary.fxml");
			new NotificaBoundary("Connessione fallita");
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}

	/**Metodo che calcola il prezzo della prestazione, ovvero 0,50€ al minuto oppure 0€ nel caso in cui il paziente sia esente*/
	private void calcolaPrezzo(boolean esenzione) {
		if(esenzione){
			prenotazione.setPrezzo(0);
			prenotazione.setPagata(true);
		}else{
			double prezzo=(Integer.valueOf(prestazione.getDurata().getMinute()).doubleValue()/2+prestazione.getDurata().getHour()*30);
			prenotazione.setPrezzo(prezzo);
			prenotazione.setPagata(false);
		}
	}


	/**Metodo getter della variabile orari*/
	public Map<Ambulatorio, List<LocalDateTime>> getOrari() {
		return orari;
	}


	/**Metodo che ritorna gli orari disponibili per il giorno passato come parametro*/
	public List<LocalTime> mostraOrari(LocalDate giorno) {
		List<LocalTime> listaOrari=new ArrayList<>();
		for(LocalDateTime giornoOra:orariDaVisualizzare){
			if (giornoOra.toLocalDate().isEqual(giorno)) {
				listaOrari.add(giornoOra.toLocalTime());
			}
		}
		return listaOrari;
	}


	/**Metodo che sceglie l'ambulatorio in cui andrà effettuata la prenotazione*/
	private void scegliAmbulatorio() {
		for (Ambulatorio ambulatorio : ambulatorioList) {
			if (orari.get(ambulatorio).contains(prenotazione.getDataOra())) {
				prenotazione.setAmbulatorio_idAmbulatorio(ambulatorio.getIdAmbulatorio());
				break;
			}
		}
	}


	/**Metodo che restituisce un orario in cui andrà effettuata la prenotazione a partire da una fascia oraria(del tipo hh:00) passata come parametro*/
	private LocalDateTime scegliOrario(LocalDateTime orario){
		for(Ambulatorio a:ambulatorioList){
			for(LocalDateTime slot:orari.get(a)){
				if(slot.getYear()==orario.getYear() && slot.getMonth()==orario.getMonth() && slot.getDayOfMonth()==orario.getDayOfMonth() && slot.getHour()==orario.getHour()){
					return slot;
				}
			}
		}
		return null;
	}


	/**Metodo che verifica se è possibile prenotare in un determinato ambulatorio e in un determinato orario, passati come parametri*/
	private boolean puoPrenotare(Ambulatorio ambulatorio, LocalDateTime dataOra){
		List<LocalDateTime> lista=calendario.get(ambulatorio);
		List<LocalDateTime> slotNecessari=new ArrayList<>();
		for(LocalDateTime i=dataOra; i.isBefore(dataOra.plusHours(prestazione.getDurata().getHour()).plusMinutes(prestazione.getDurata().getMinute())); i=i.plusMinutes(dimSlot)){
			slotNecessari.add(i);
		}
		for(LocalDateTime slot:slotNecessari){
			if(!lista.contains(slot)){
				return false;
			}
		}
		return true;
	}


	/**Metodo che crea un calendario, così da ottenere il primo giorno disponibile alla prenotazione*/
	public void creaCalendarioPerSpostamenti()throws Exception{

		LocalDateTime i=LocalDateTime.now().plusDays(datiUrgenze.get(urgenza)+1).withHour(23).withMinute(59).withSecond(0).withNano(0);
		do {
			creaCalendario(false,i);
			i=i.plusDays(2);
		} while (orariDaVisualizzare.isEmpty());
	}



}
