package Client;
import java.awt.event.*;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import Common.Casilla;
import Common.Constantes;
import Common.Dot;
import Common.Mapa;
import Common.Target;

import java.net.*;
import java.io.*;

public class GUI implements ActionListener, Constantes{

    JFrame ventana;
    JButton next;
    Mapa mapa;
    Target target;

    Dot dot;

    Socket client;
    ObjectOutputStream output;

    
    public GUI(){

        ventana = new JFrame("Client");
        mapa = new Mapa(this);


        ventana.add(mapa.panelTablero);

        next = new JButton("continuar");
        next.setActionCommand("next");

        ventana.add(next, BorderLayout.SOUTH);

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);
        this.dot = new Dot();
        this.target = new Target();
        Server server = new Server(dot, mapa);
        Thread hilo = new Thread(server);
        hilo.start();
        run();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mapa.tablero[target.coords[X]][target.coords[Y]].clearTarget();
        ((Casilla)e.getSource()).setAsTarget();
        this.target.coords = ((Casilla)e.getSource()).getCoords();

        try {
            this.client = new Socket("127.0.0.1", 9753);
            this.output = new ObjectOutputStream(client.getOutputStream());
            this.output.writeObject(target);
            this.output.flush();
            this.output.close();
            this.client.close();
        } catch (Exception ex) {
            //TODO: handle exception
        }
    }

    public void moveDot(){
        mapa.tablero[this.dot.lastPosition[X]][this.dot.lastPosition[Y]].clearDot();
        mapa.tablero[this.dot.currentPosition[X]][this.dot.currentPosition[Y]].setAsDot();
    }

    public void run(){
        while (true){
            this.dot.move();
            mapa.cleanMap(dot);
            moveDot();
            try {
                Thread.sleep(1000);
                }
            catch (Exception e) {
                //TODO: handle exception
            }
        }
    }

}