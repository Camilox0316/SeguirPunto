package Server;

import java.net.*;
import java.io.*;
import Common.*;

public class Server implements Runnable{
    ServerSocket server;
    Socket client;
    ObjectInputStream input;
    Dot dot;

    public Server(Dot d){
        this.dot = d;
        try {
            this.server = new ServerSocket(9753);
        } catch (Exception e) {
            //TODO: handle exception
        }
        
    }

    public void run(){
        try {
            while(true){
                this.client = server.accept();
                this.input = new ObjectInputStream(client.getInputStream());
                this.dot.target = (Target) input.readObject();
                this.input.close();
                this.client.close();
            }
        } catch (Exception e) {
            //TODO: handle exception
        }

    }
    
}
