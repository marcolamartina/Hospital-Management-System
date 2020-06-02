package GestionePrenotazioni;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.InterfacciaGrafica;
import Entity.PrenotazioneEntity;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**Classe che si occupa delle operazioni volte al pagamento di una prestazione*/
public class PagamentoPrestazioneControl {

	/**Riferimento alla lista delle prenotazioni*/
	private static List<PrenotazioneEntity> prenotazioni;

	/**Metodo getter della variabile prenotazioni*/
	public static List<PrenotazioneEntity> getPrenotazioni() {
		return prenotazioni;
	}

	/**Costruttore che mostra la finestra CercaPazientePagamentoBoundary
	 * @see CercaPazientePagamentoBoundary*/
	public PagamentoPrestazioneControl() {
		InterfacciaGrafica.mostra("/GestionePrenotazioni/CercaPazientePagamentoBoundary.fxml");
	}

	/**Metodo che chiede al DBMS le prenotazioni da pagare del paziente associato al codice fiscale inserito e le mostra nella finestra PagamentoPrestazioneBoundary
	 * @see PagamentoPrestazioneBoundary
	 * @see DBMSospedale#getPrenotazioniDaPagare(String) */
	static void pagamento(String codiceFiscale) {
		try {
			if(codiceFiscale.length()==16){
				prenotazioni=DBMSospedale.get().getPrenotazioniDaPagare(codiceFiscale);
				InterfacciaGrafica.mostra("/GestionePrenotazioni/PagamentoPrestazioneBoundary.fxml");
			}else{
				new NotificaBoundary("Inserire un codice fiscale corretto");
			}
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}

	/**Metodo che comunica al DBMS di pagare la prestazione
	 * @see DBMSospedale#paga(PrenotazioneEntity) */
	static void paga(PrenotazioneEntity prenotazione) {
		try {
			DBMSospedale.get().paga(prenotazione);
			pagamento(prenotazioni.get(0).getUtente_codice_fiscale());
			mostraMenuAmministrativo();
			new NotificaBoundary("Pagamento effettuato con successo");
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
	}

	/**Metodo che mostra la finestra /MenuAmministrativoBoundary
	 * @see Autenticazione.MenuAmministrativoBoundary*/
	static void mostraMenuAmministrativo(){
		InterfacciaGrafica.mostra("/Autenticazione/MenuAmministrativoBoundary.fxml");
	}

	/**Metodo che mostra la finestra CercaPazientePagamentoBoundary
	 * @see CercaPazientePagamentoBoundary*/
	static void indietro(){
		InterfacciaGrafica.mostra("/GestionePrenotazioni/CercaPazientePagamentoBoundary.fxml");
	}

	/**Metodo per formattare la visualizzazione della lista prenotazioni
	 * @see PagamentoPrestazioneControl#prenotazioni*/
	static List<String> parserList(){
		try {
			List<String> lista=new ArrayList<>();
			for(PrenotazioneEntity pren:PagamentoPrestazioneControl.getPrenotazioni()){
				String stringa="";
				stringa+=pren.getPrestazione_nome();
				while(stringa.length()<30){
					stringa+=" ";
				}
				stringa+=DBMSospedale.get().getReparto(pren.getAmbulatorio_Reparto_idReparto()).getNome();
				while(stringa.length()<55){
					stringa+=" ";
				}
				stringa+="Ambulatorio n°"+pren.getAmbulatorio_idAmbulatorio();
				while(stringa.length()<90){
					stringa+=" ";
				}
				stringa+=pren.getPrezzo()+"0€";
				while(stringa.length()<105){
					stringa+=" ";
				}
				stringa+=pren.getDataOra().withMinute(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
				lista.add(stringa);
			}
			return lista;
		} catch (SQLException e) {
			new NotificaBoundary("Connessione fallita");
			DBMSospedale.setConnessioneCaduta(true);
		}catch (Exception e) {
			new NotificaBoundary("Errore");
		}
		return null;
	}

}
