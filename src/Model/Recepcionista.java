package Model;

public class Recepcionista implements Runnable{
    private Monitor monitor;
    public Recepcionista(Monitor monitor){
        this.monitor = monitor;
    }
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            monitor.recepcion();

        }
    }
}
