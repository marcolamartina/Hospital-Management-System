package Autenticazione;

import ComponentiEsterne.InterfacciaGrafica;
import Entity.AmministrativoEntity;
import Entity.MedicoEntity;
import Entity.PazienteEntity;

/**Classe che gestisce tutte le operazioni per quanto riguarda il logout di qualsiasi tipo di utente*/
class DisconnettiControl {

	/**Costruttore che effettua il logout di un paziente*/
	DisconnettiControl(MenuPazienteBoundary menuPazienteBoundary) {
		PazienteEntity.setSelected(null);
		if(AmministrativoEntity.getSelected()==null) {
			InterfacciaGrafica.mostra("/Autenticazione/WelcomeBoundary.fxml");
		}else{
			InterfacciaGrafica.mostra("/Autenticazione/MenuAmministrativoBoundary.fxml");
		}
	}

	/**Costruttore che effettua il logout di un medico*/
	DisconnettiControl(MenuMedicoBoundary menuMedicoBoundary) {
		MedicoEntity.setSelected(null);
		PazienteEntity.setSelected(null);
		InterfacciaGrafica.mostra("/Autenticazione/WelcomeBoundary.fxml");
	}

	/**Costruttore che effettua il logout di un utente amministrativo*/
	DisconnettiControl(MenuAmministrativoBoundary menuAmministrativoBoundary) {
		AmministrativoEntity.setSelected(null);
		PazienteEntity.setSelected(null);
		InterfacciaGrafica.mostra("/Autenticazione/WelcomeBoundary.fxml");
	}

}
