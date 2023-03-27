package Model;

public class Cocinero implements Runnable{
    private Monitor monitor;
    public Cocinero(Monitor monitor){
        this.monitor = monitor;
    }
    @Override
    public void run() {
        while(true){
            monitor.cocinar();
        }
    }
}
