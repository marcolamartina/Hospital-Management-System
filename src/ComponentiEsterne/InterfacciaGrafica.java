package ComponentiEsterne;


import Autenticazione.NotificaBoundary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**Classe che gestisce l'interfaccia grafica*/
public class InterfacciaGrafica extends Application {
    /**Stage su cui visualizzare le finestre*/
    private static Stage stage;

    /**Metodo per creare la finestra di benvenuto*/
    @Override
    public void start(Stage stage) {
        try {
            InterfacciaGrafica.stage=stage;
            Parent root = FXMLLoader.load(getClass().getResource("/Autenticazione/WelcomeBoundary.fxml"));
            Scene scene = new Scene(root);
            InterfacciaGrafica.stage.setScene(scene);
            InterfacciaGrafica.stage.show();
        }catch (Exception e) {
            new NotificaBoundary("Errore");
            e.printStackTrace();
        }

    }


    /**Metodo per visualizzare le finestre*/
    public static void mostra(String boundary) {
        try {
            Parent root = FXMLLoader.load(Class.forName("ComponentiEsterne.InterfacciaGrafica").getResource(boundary));
            Scene scene = new Scene(root);
            InterfacciaGrafica.stage.setScene(scene);
            InterfacciaGrafica.stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}
