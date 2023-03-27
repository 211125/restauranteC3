package Model;

import java.util.Observable;

public class Monitor extends Observable {

    public boolean reservacionLibre;
    public boolean client;
    public boolean accEntrar;
    public int numClientes;
    public String reservado;
    public int orden;
    public int comida;
    public int peticiones;
    public int contador=0;
    public boolean confirmacion;
    public int maxNumClientes;
    public boolean[] mesas;
    public int auxMesa;

    public Monitor(){
        reservacionLibre = true;
        client=false;
        accEntrar=false;
        numClientes=0;
        reservado ="";
        orden=0;
        comida=0;
        peticiones=0;
        auxMesa = -1;
        confirmacion=false;
        maxNumClientes = 0;
        mesas = new boolean[20];

        for (int i=0; i<19; i++) {
            mesas[i] = false;
        }
    }

    public synchronized boolean reservar(String nombre){
        //Para el hilo cliente
        //Solo un hilo a la vez puede puede reservar una mesa
        synchronized (this) {
            if(reservacionLibre){
                reservacionLibre =false;
                reservado = nombre;
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
            else {
                return false;
            }
        }
    }

    public int entrar(String nombre){
        // comprueba y verifica la entrada de los clientes
        int numMesa = -1;
        try {
            if(reservado.equals(nombre)){
                confirmacion = false;
                numMesa = 19;
                auxMesa = 19;
            }else{
                synchronized (this) {
                    numClientes++;
                    maxNumClientes++;
                    while (maxNumClientes==19) {
                        wait();
                    }
                    accEntrar=true;
                    client=true;
                    for (int i=0; i<19; i++) {
                        if(!mesas[i]) {
                            numMesa = i;
                            auxMesa = i;
                            mesas[i] = true;
                            i = 100;
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers("seat " + numMesa);
        return numMesa;
    }
    public void ordenar(){
        //Solo un hilo a la vez puede entrar a ordenar
        synchronized (this) {
            orden++;
            notifyAll();
        }
    }

    public void servirOrden(){
        //El mesero atiende una mesa
        String txt = "libreMesero";
        boolean aux = false;
        synchronized (this) {
            if (orden<=0){
                txt = "libreMesero";
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                aux = true;
                txt = "ocupadoMesero";
                peticiones++;
                orden--;
            }
            notifyAll();
            setChanged();
            notifyObservers(txt +" "+ auxMesa);
        }
        if (aux){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void cocinar(){
        //Se cocina solo una vez, y esto va conforme a al pedido
        String txt = "libre";
        synchronized (this) {
            if (peticiones<=0){
                txt = "libre";
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                txt = "ocupado";
                comida++;
                peticiones--;
            }
            notifyAll();
            setChanged();
            notifyObservers(txt);
        }
    }
    public void comer(){
        synchronized (this) {
            while (comida<=0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            comida--;
        }
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void salir(int numMesaLibre){
        //Solo puede salir un cliente a la vez
        synchronized (this) {
            if(!confirmacion){
                confirmacion=true;
                reservacionLibre =true;
            }else{
                numClientes--;
                maxNumClientes--;
                client=false;
                System.out.println(numClientes+" Clientes en fila");
            }
            mesas[numMesaLibre] = false;
            notifyAll();
            contador++;
            setChanged();
            notifyObservers("" + contador);
        }
    }
    public void recepcion(){
        //Solo deja entrar un cliente a la vez
        synchronized (this) {
            while(numClientes < 1 || client){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            accEntrar=false;
            notifyAll();
        }
    }

}
