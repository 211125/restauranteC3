package Model;

import javafx.scene.layout.AnchorPane;

public class Mesero implements Runnable{
    private Monitor monitor;
    private AnchorPane padre;
    public Mesero(AnchorPane padre, Monitor monitor){
        this.monitor = monitor;
        this.padre=padre;
    }
    @Override
    public void run() {

        while (true){
            monitor.servirOrden();
        }
    }
}
