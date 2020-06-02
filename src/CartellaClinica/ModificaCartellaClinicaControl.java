package CartellaClinica;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.InterfacciaGrafica;
import Entity.PrenotazioneEntity;

import java.sql.SQLException;


/**Classe che gestite tutte le operazioni necessarie ad un medico per poter modificare una cartella clinica di un paziente*/
class ModificaCartellaClinicaControl {


	/**Costruttore che mostra la finestra ModificaCartellaClinicaBoundary
	 * @see ModificaCartellaClinicaBoundary*/
	public ModificaCartellaClinicaControl() {
		InterfacciaGrafica.mostra("/CartellaClinica/ModificaCartellaClinicaBoundary.fxml");
	}


	/**Metodo che comunica al DBMS di aggiornare i dati della prenotazione
	 * @see DBMSospedale#aggiornaDatiPrenotazione(String, String, String, PrenotazioneEntity)
	 */
	static void confermaModifiche(String anamnesi, String diagnosi, String cure) {
		try {
			if(anamnesi!=null && diagnosi!=null && cure!=null){
				DBMSospedale.get().aggiornaDatiPrenotazione(anamnesi,diagnosi,cure,PrenotazioneEntity.getSelected());
				mostraMenuMedico();
				new NotificaBoundary("Modifica effettuata");
			}else{
				new NotificaBoundary("Compilare tutti e tre i campi");
			}
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}


	/**Metodo che mostra il menu del medico*/
	private static void mostraMenuMedico(){
		InterfacciaGrafica.mostra("/Autenticazione/MenuMedicoBoundary.fxml");
	}

}
