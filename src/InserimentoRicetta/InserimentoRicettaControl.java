package InserimentoRicetta;

import Autenticazione.NotificaBoundary;
import ComponentiEsterne.DBMSospedale;
import ComponentiEsterne.DBMSricette;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class InserimentoRicettaControl extends Application {
    private static Stage stage;


    @Override
    public void start(Stage stage) {

    }

    public InserimentoRicettaControl() {
        try {
            stage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/InserimentoRicetta/InserimentoRicettaBoundary.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String generaCodice(){
        try {
            final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            Random rnd = new Random(System.currentTimeMillis());
            final int LENGHT = 15;

            StringBuilder sb = new StringBuilder(LENGHT);
            for (int i = 0; i < LENGHT; i++) {
                sb.append(ALPHABET.charAt(rnd.nextInt(ALPHABET.length())));
            }
            if(DBMSricette.get().getRicetta(sb.toString())==null) {
                return sb.toString();
            }else{
                return generaCodice();
            }
        } catch (SQLException e) {
            new NotificaBoundary("Connessione fallita");
            DBMSospedale.setConnessioneCaduta(true);
        }catch (Exception e) {
            new NotificaBoundary("Errore");
        }
        return null;
    }

    static void aggiorna(){
        try {
            Parent root = FXMLLoader.load(Class.forName("ComponentiEsterne.InterfacciaGrafica").getResource("/InserimentoRicetta/InserimentoRicettaBoundary.fxml"));
            Scene scene = new Scene(root);
            InserimentoRicettaControl.stage.setScene(scene);
            InserimentoRicettaControl.stage.show();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
