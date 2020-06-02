package GestionePrenotazioni;

import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.Mail;
import Entity.Ambulatorio;
import Entity.PrenotazioneEntity;
import javafx.concurrent.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**Classe che gestisce tutte le operazioni per rendere automatizzato il sistema a fronte di uno spostamento di una prestazione, causato dalla necessità di aggiungere una prenotazione con livello di urgenza più alto.*/
public class GestisciSpostamentiControl {

	/**Metodo che modifica la data e l'orario delle prenotazioni passate come parametro, impostandoli al primo orario e giorno disponibile.
	 * Inoltre si occupa di mandare una mail ai pazienti a cui è stata spostata la prenotazione
	 * @see Mail#avvisaPazienteSpostamento(PrenotazioneEntity, PrenotazioneEntity)
	 * @see SelezioneDataControl#creaCalendarioPerSpostamenti()
	 */
	public static void gestisciSpostamenti(List<PrenotazioneEntity> prenotazioni)throws Exception{
				List<PrenotazioneEntity> prenotazioniVecchie=new ArrayList<>();
				Map<String, Integer> datiUrgenze = new HashMap<>();
				datiUrgenze.put("U", 1);
				datiUrgenze.put("B", 10);
				datiUrgenze.put("D", 60);
				datiUrgenze.put("P", 180);

				prenotazioni.sort((one, other) -> datiUrgenze.get(one.getUrgenza()).compareTo(datiUrgenze.get(other.getUrgenza())));

				for (PrenotazioneEntity pren : prenotazioni) {
					PrenotazioneEntity prenotazioneVecchia = new PrenotazioneEntity(pren.getDataOra(), pren.getUtente_codice_fiscale(), pren.getPrestazione_nome(), pren.getUrgenza(), pren.getDiagnosi(), pren.getAnamnesi(), pren.getCure(), pren.getDiagnosi_medico_curante(), pren.getAmbulatorio_idAmbulatorio(), pren.getAmbulatorio_Reparto_idReparto(), pren.getPrezzo(), pren.isPagata());
					SelezioneDataControl slc = new SelezioneDataControl(pren, "GestisciSpostamenti");
					slc.creaCalendarioPerSpostamenti();
					Map<Ambulatorio, List<LocalDateTime>> map = slc.getOrari();
					LocalDateTime dataOra = LocalDateTime.of(9999, 12, 30, 23, 59);
					Ambulatorio ambulatorio = new Ambulatorio();
					for (Map.Entry<Ambulatorio, List<LocalDateTime>> entry : map.entrySet()) {
						List<LocalDateTime> list = entry.getValue();
						for (LocalDateTime orari : list) {
							if (orari.isBefore(dataOra)) {
								dataOra = orari;
								ambulatorio = entry.getKey();
							}
						}
					}
					pren.setAmbulatorio_idAmbulatorio(ambulatorio.getIdAmbulatorio());
					pren.setDataOra(dataOra);
					DBMSospedale.get().aggiungiPrenotazione(pren);
					DBMSospedale.get().cancellaPrenotazione(prenotazioneVecchia);
					prenotazioniVecchie.add(prenotazioneVecchia);

				}

				Task task=new Task() {
					@Override
					protected Object call() throws Exception {
						Task task=new Task() {
							@Override
							protected Object call() throws Exception {
								for(int i=0; i<prenotazioni.size();i++) {
									Mail.get().avvisaPazienteSpostamento(prenotazioni.get(i), prenotazioniVecchie.get(i));
								}
								return null;
							}

						};

						Thread thread=new Thread(task);
						thread.setDaemon(true);
						thread.start();
						thread.join();
						return null;
					}

				};

				Thread thread=new Thread(task);
				thread.setDaemon(true);
				thread.start();

	}

}
