package Model;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Cliente implements Runnable{
    private ImageView clientev;
    private AnchorPane anchor;
    private Monitor monitor;
    private static String[] positions;
    public Cliente(AnchorPane anchor, Monitor monitor){
        this.anchor = anchor;
        this.monitor = monitor;
        positions = new String[20];
        positions[0] = "300 189";
        positions[1] = "150 281";
        positions[2] = "220 332";
        positions[3] = "185 456";
        positions[4] = "361 505";
        positions[5] = "180 580";
        positions[6] = "275 591";

        positions[7] = "180 652";
        positions[8] = "275 648";
        positions[9] = "401 677";
        positions[10] = "508 667";

        positions[11] = "600 688";
        positions[12] = "707 673";
        positions[13] = "708 591";
        positions[14] = "625 592";
        positions[15] = "672 445";
        positions[16] = "580 367";
        positions[17] = "672 339";

        positions[18] = "611 243";
        positions[19] = "694 176";
    }
    @Override
    public void run() {
        Circle cliente = new Circle(15, Color.BURLYWOOD);
        //clientev =  new ImageView(new Image(getClass().getResourceAsStream("/resources/car1.jpg")));
        Platform.runLater(() -> {
            cliente.setLayoutX(156);
            cliente.setLayoutY(58);
            anchor.getChildren().add(cliente);
           // anchor.getChildren().add(clientev);
        });

        //Movimiento de los cleintes
        for(int i=0;i<10;i++){
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()-> cliente.setLayoutX(cliente.getLayoutX()+50));
        }
        boolean reservation = monitor.reservar(Thread.currentThread().getName());
        if(reservation) {
            Platform.runLater(()-> cliente.setFill(Color.TRANSPARENT));
        }
        else {
            Platform.runLater(()-> cliente.setFill(Color.TRANSPARENT));
            System.out.println("estoy saliendo");
        }

        //Posicionar a los clientes en lase mesas con forme el id
        int numMesa = monitor.entrar(Thread.currentThread().getName());
        String[] layout = positions[numMesa].split(" ");
        Platform.runLater(()-> {
            cliente.setLayoutX(Integer.parseInt(layout[0]));
            cliente.setLayoutY(Integer.parseInt(layout[1])+50);
        });
        System.out.println("N" + numMesa);

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Ordenar
        Platform.runLater(()-> cliente.setFill(Color.MAGENTA));
        monitor.ordenar();

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Comer
        Platform.runLater(()-> cliente.setFill(Color.MAROON));
        monitor.comer();
        try {
            Thread.sleep(19000);
        } catch (InterruptedException e) {
            e.printStackTrace();}

        //Salir
        Platform.runLater(() -> cliente.setFill(Color.TRANSPARENT));

        monitor.salir(numMesa);
    }
}
