package principal;

import Model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Observable;
import java.util.Observer;


public class Controller implements Observer {

    @FXML
    private AnchorPane anchor;

    @FXML
    private Button btnIniciar;

    @FXML
    private Label lblMesero;

    @FXML
    private Circle chef;

    @FXML
    private Circle mesero;

    private String[] positions;

    @FXML
    void Finalizar(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void IniciarAnimacion(ActionEvent event) {

        mesero.setFill(Color.GREEN);
        btnIniciar.setDisable(true);
        Restaurant restaurant = new Restaurant();
        restaurant.addObserver(this::update);
        Mesero mesero=new Mesero(anchor, restaurant);
        Recepcionista recepcionista=new Recepcionista(restaurant);
        Cocinero cocinero = new Cocinero(restaurant);
        HilosCreados hilosCreados = new HilosCreados(anchor, restaurant, this);
        Thread hiloMesero = new Thread(mesero);
        Thread hiloRecepcionista = new Thread(recepcionista);
        Thread hCocinero = new Thread(cocinero);
        Thread hCreadorClientes = new Thread(hilosCreados);
        hiloMesero.setDaemon(true);
        hiloMesero.start();
        hiloRecepcionista.setDaemon(true);
        hiloRecepcionista.start();
        hCocinero.setDaemon(true);
        hCocinero.start();
        hCreadorClientes.setDaemon(true);
        hCreadorClientes.start();
        positions = new String[23];
        positions[0] = "235 60";
        positions[1] = "336 60";
        positions[2] = "438 60";
        positions[3] = "546 60";
        positions[4] = "644 60";
        positions[5] = "750 60";
        positions[6] = "833 60";

        positions[7] = "929 114";
        positions[8] = "929 224";
        positions[9] = "929 307";
        positions[10] = "836 398";

        positions[11] = "740 398";
        positions[12] = "644 389";
        positions[13] = "550 389";
        positions[14] = "445 389";
        positions[15] = "336 389";
        positions[16] = "235 389";
        positions[17] = "166 315";

        positions[18] = "166 224";
        positions[19] = "166 127";
    }

    @Override
    public void update(Observable o, Object arg) {
        synchronized (this) {
            if (((String)arg).contains("ocupadoMesero")){
                String[] cadena = ((String) arg).split(" ");
                int numMesa = Integer.parseInt(cadena[1]);
                Platform.runLater(()-> lblMesero.setText("Atendiendo: "+(numMesa+1)));
                mesero.setFill(Color.RED);
            }else
            if (((String)arg).contains("libreMesero")){
                Platform.runLater(()-> lblMesero.setText(""));
                mesero.setFill(Color.GREEN);
            }
        }
    }
}
