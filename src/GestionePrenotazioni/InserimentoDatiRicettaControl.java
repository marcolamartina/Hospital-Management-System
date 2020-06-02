package GestionePrenotazioni;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.DBMSricette;
import ComponentiEsterne.InterfacciaGrafica;
import Entity.PazienteEntity;
import Entity.PrenotazioneEntity;
import Entity.RicettaEntity;

import java.sql.SQLException;


/**Classe che gestisce tutte le operazioni per inserire i dati di una ricetta volte a creare una prenotazione nel sistema*/
public class InserimentoDatiRicettaControl {

	/**Costruttore che invoca il metodo mostraInserimento
	 * @see InserimentoDatiRicettaControl#mostraInserimento() */
	public InserimentoDatiRicettaControl() {
		mostraInserimento();
	}

	/**Metodo che verifica la validità del codice della ricetta inserito e se valido invoca il metodo setEsenzione del DBMS e mostra la finestra RicettaBoundary
	 * @see DBMSospedale#setEsenzione(PazienteEntity, boolean)
	 * @see RicettaBoundary*/
	static void verifica(String codiceRicetta) {
		try {
			RicettaEntity ricetta=DBMSricette.get().getRicetta(codiceRicetta);
			if(ricetta!=null){
				if(ricetta.getCodiceFiscale().compareToIgnoreCase(PazienteEntity.getSelected().getCodiceFiscale())==0){
					DBMSospedale.get().setEsenzione(PazienteEntity.getSelected(),ricetta.isEsenzione());
					RicettaEntity.setSelected(ricetta);
					InterfacciaGrafica.mostra("/GestionePrenotazioni/RicettaBoundary.fxml");
				}
			}
			if(ricetta==null || ricetta.getCodiceFiscale().compareToIgnoreCase(PazienteEntity.getSelected().getCodiceFiscale())!=0){
				new NotificaBoundary("Ricetta non valida");
			}
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}

	/**Metodo che mostra la finestra MenuPazienteBoundary
	 * @see Autenticazione.MenuPazienteBoundary*/
	static void mostraMenuPaziente(){
		InterfacciaGrafica.mostra("/Autenticazione/MenuPazienteBoundary.fxml");
	}

	/**Metodo che salva i dati della prestazione da prenotare e mostra la finestra SelezionaDataBoundary
	 * @see SelezionaDataBoundary
	 * @see PrenotazioneEntity#setSelected(PrenotazioneEntity) */
	static void creaPrenotazione(){
		try {
			RicettaEntity ricetta=RicettaEntity.getSelected();
			PrenotazioneEntity.setSelected(new PrenotazioneEntity(ricetta.getCodiceFiscale(),ricetta.getPrestazione(),ricetta.getUrgenza(),ricetta.getDiagnosi(),DBMSospedale.get().getReparto(ricetta.getReparto())));
			new SelezionaDataBoundary("InserimentoDatiRicetta");
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}

	/**Metodo che mostra la finestra InserimentoDatiRicettaBoundary
	 * @see InserimentoDatiRicettaBoundary*/
	static void mostraInserimento(){
		InterfacciaGrafica.mostra("/GestionePrenotazioni/InserimentoDatiRicettaBoundary.fxml");
	}



}
