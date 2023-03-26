package Model;

import javafx.scene.layout.AnchorPane;
import principal.Controller;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HilosCreados extends Random implements Runnable{
    private AnchorPane anchor;
    private Restaurant restaurant;
    private Controller controller;
    public HilosCreados(AnchorPane anchor, Restaurant restaurant, Controller controller){
        this.anchor = anchor;
        this.restaurant=restaurant;
        this.controller = controller;
    }
    private Cliente cliente;
    @Override
    public void run() {
        for(int i=0;i<100;i++){
            cliente=new Cliente(anchor,restaurant);
            Thread Hcliente = new Thread(cliente);
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(4000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Hcliente.start();
        }
    }
}
