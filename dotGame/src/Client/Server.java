package Client;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import Common.*;
public class Server implements Runnable{
    ServerSocket server;
    Socket cliente;
    ObjectInputStream input;
    Dot dot, lastDot;

    public Server(Dot d){
        this.dot = d;
        try {
            this.server = new ServerSocket(9731);
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    @Override
    public void run() {
        try {
            while(true){
                this.cliente = server.accept();
                this.input = new ObjectInputStream(cliente.getInputStream());
                this.lastDot = (Dot)input.readObject();
                dot.currentPosition=lastDot.currentPosition;
                dot.lastPosition = lastDot.lastPosition;
                dot.target = lastDot.target;
                //dot = lastDot;
                System.out.println("dot: "+dot.currentPosition[0]+", "+dot.currentPosition[1]);
                System.out.println("last: "+dot.currentPosition[0]+", "+dot.currentPosition[1]);
                
                this.input.close();
                this.cliente.close();

            }
        } catch (Exception e) {
            //TODO: handle exception
        }
    }


}
