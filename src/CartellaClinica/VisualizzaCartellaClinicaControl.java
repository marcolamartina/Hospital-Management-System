package CartellaClinica;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.InterfacciaGrafica;
import Entity.PazienteEntity;
import Entity.PrenotazioneEntity;

import java.sql.SQLException;
import java.util.List;


/**Classe che si occupa di gestire tutte le operazioni per permettere di visualizzare la cartella clinica di un qualsiasi paziente*/
public class VisualizzaCartellaClinicaControl {

	/**Paziente di cui si vuole visualizzare la cartella clinica*/
	private static PazienteEntity paziente;


	/**Metodo setter per la variabile paziente*/
	private static void setPaziente(PazienteEntity paziente) {
		VisualizzaCartellaClinicaControl.paziente = paziente;
	}


	/**Metodo getter per la variabile paziente*/
	public static PazienteEntity getPaziente() {
		return paziente;
	}


	/**Costruttore che mostra la finestra RicercaPazienteBoundary richiamando il metodo mostraRicerca
	 * @see RicercaPazienteBoundary
	 * @see VisualizzaCartellaClinicaControl#mostraRicerca()
	 */
	public VisualizzaCartellaClinicaControl() {
		mostraRicerca();
	}

	/**Metodo che interroga il DBMS per ottenere la lista di pazienti con nome e cognome corrispondenti a quelli passati come parametro e ottenuti tramite il form RicercaPazientiBoundary.
	 * Viene inoltre mostrata la finestra ListaPazientiBoundary da cui scegliere il paziente corretto
 	 * @see DBMSospedale#getPazienti(String, String)
	 * @see RicercaPazienteBoundary
	 * @see ListaPazientiBoundary
	 */
	static void cercaPazienti(String nome,String cognome) {
		try {
			if(nome.length()>1 && cognome.length()>1 ){
				List<PazienteEntity> list=DBMSospedale.get().getPazienti(nome, cognome);
				if (!list.isEmpty()) {
					ListaPazientiBoundary.setListaPazienti(list);
					ListaPazientiBoundary.setControl("VisualizzaCartellaClinica");
					InterfacciaGrafica.mostra("/CartellaClinica/ListaPazientiBoundary.fxml");
				} else {
					mostraMenuMedico();
					new NotificaBoundary("Nessun paziente trovato");
				}
			}else{
				new NotificaBoundary("Inserire nome e cognome");
			}
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}


	/**Metodo per visualizzare la cartella clinica del paziente selezionato, tramite la finestra ListaPazientiBoundary, e quindi mostrando la finestra VisualizzaCartellaClinicaBoundary
	 * @see ListaPazientiBoundary
	 * @see VisualizzaCartellaClinicaBoundary
	 */
	static void visualizzaCartellaClinica(PazienteEntity paziente) {
		try {
			if (paziente!=null) {
				setPaziente(paziente);
				List<PrenotazioneEntity> cartellaClinica=DBMSospedale.get().getCartellaClinica(paziente);
				VisualizzaCartellaClinicaBoundary.setCartellaClinica(cartellaClinica);
				InterfacciaGrafica.mostra("/CartellaClinica/VisualizzaCartellaClinicaBoundary.fxml");
			} else {
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


	/**Metodo che mostra la finestra RicercaPazienteBoundary
	 * @see RicercaPazienteBoundary
	 */
	static void mostraRicerca(){
		InterfacciaGrafica.mostra("/CartellaClinica/RicercaPazienteBoundary.fxml");
	}

}
