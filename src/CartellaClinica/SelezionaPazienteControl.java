package CartellaClinica;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.InterfacciaGrafica;
import Entity.MedicoEntity;
import Entity.PazienteEntity;
import Entity.PrenotazioneEntity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


/**Classe che si occupa di permettere ad un medico di selezionare un paziente tra i pazienti che hanno prenotato una prestazione per il giorno corrente*/
public class SelezionaPazienteControl {

	/**Indice dell'elenco dei pazienti di ListaPazientiBoundary
	 * @see ListaPazientiBoundary*/
	static int indice;


	/**Metodo getter della variabile indice*/
	public static int getIndice() {
		return indice;
	}

	/**Costruttore che richiama il metodo mostraMenu
	 * @see SelezionaPazienteControl#mostraMenu() */
	public SelezionaPazienteControl() {
		mostraMenu();
	}


	/**Metodo per salvare le informazioni del paziente selezionato tramite ListaPazientiBoundary e della prenotazione ad esso relativa. Dopo il salvataggio dei dati, viene visualizzata la finestra CartellaClinicaMedicoBoundary
	 * @see DBMSospedale#getPrenotazione(PazienteEntity, MedicoEntity, int)
	 * @see ListaPazientiBoundary
	 * @see CartellaClinicaMedicoBoundary*/
	public static void selezionaPaziente(PazienteEntity paziente,int indice) {
		try {
			SelezionaPazienteControl.indice=indice;
			if(paziente!=null){
				PazienteEntity.setSelected(paziente);
				int numeroPrenotazione=0;
				int counter=0;
				for(PazienteEntity pazienteOggi:ListaPazientiBoundary.listaPazienti){
					if(pazienteOggi.getCodiceFiscale().equals(paziente.getCodiceFiscale())){
						numeroPrenotazione++;
					}
					if(counter==indice)break;
					counter++;
				}
				PrenotazioneEntity.setSelected(DBMSospedale.get().getPrenotazione(PazienteEntity.getSelected(), MedicoEntity.getSelected(),numeroPrenotazione-1));
				List<PrenotazioneEntity> cartellaClinica=DBMSospedale.get().getCartellaClinica(paziente);
				CartellaClinicaMedicoBoundary.setCartellaClinica(cartellaClinica);
				InterfacciaGrafica.mostra("/CartellaClinica/CartellaClinicaMedicoBoundary.fxml");
			}else{
				new NotificaBoundary("Selezionare un paziente");
			}
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}


	/**Metodo che mostra il menu del medico
	 * @see Autenticazione.MenuMedicoBoundary*/
	static void mostraMenuMedico(){
		InterfacciaGrafica.mostra("/Autenticazione/MenuMedicoBoundary.fxml");
	}


	/**Metodo che chiede al DBMS l'elenco dei pazienti prenotati per il giorno corrente e li mostra tramite la finestra ListaPazientiBoundary
	 * @see DBMSospedale#getPazienti(LocalDate, MedicoEntity)
	 * @see ListaPazientiBoundary*/
	static void mostraMenu(){
		try {
			List<PazienteEntity> pazienti=DBMSospedale.get().getPazienti(LocalDate.now(), MedicoEntity.getSelected());
			if(!pazienti.isEmpty()) {
				ListaPazientiBoundary.setListaPazienti(pazienti);
				ListaPazientiBoundary.setControl("SelezionaPaziente");
				InterfacciaGrafica.mostra("/CartellaClinica/ListaPazientiBoundary.fxml");
			}else{
				new NotificaBoundary("Non c'è nessuna visita prenotata\nper oggi");
			}
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}
}
