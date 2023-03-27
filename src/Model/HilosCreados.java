package Model;

import javafx.scene.layout.AnchorPane;
import Controllers.Controller;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HilosCreados extends Random implements Runnable{
    private AnchorPane anchor;
    private Monitor monitor;
    private Controller controller;
    public HilosCreados(AnchorPane anchor, Monitor monitor, Controller controller){
        this.anchor = anchor;
        this.monitor = monitor;
        this.controller = controller;
    }
    private Cliente cliente;
    @Override
    public void run() {
        for(int i=0;i<100;i++){
            cliente=new Cliente(anchor, monitor);
            Thread Hcliente = new Thread(cliente);
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Hcliente.start();
        }
    }
}
