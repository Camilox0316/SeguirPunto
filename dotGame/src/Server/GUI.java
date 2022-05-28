package Server;
import java.awt.event.*;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import Common.Constantes;
import Common.Dot;
import Common.Mapa;

public class GUI implements ActionListener, Constantes{

    JFrame ventana;
    JButton next;
    Mapa mapa;

    Dot dot;
    
    Socket client;
    ObjectOutputStream outPut;

    public GUI(){

        ventana = new JFrame("Server");
        mapa = new Mapa(this);


        ventana.add(mapa.panelTablero);

        next = new JButton("continuar");
        next.addActionListener(this);
        next.setActionCommand("next");

        ventana.add(next, BorderLayout.SOUTH);

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);

        this.dot = new Dot();
        Server server = new Server(this.dot);
        Thread hilo = new Thread(server);
        hilo.start();

        moveDot();
        run();

    }

    @Override
    public void actionPerformed(ActionEvent e) {  
    }

    public void moveDot(){
        mapa.tablero[dot.lastPosition[X]][dot.lastPosition[Y]].clearDot();
        mapa.tablero[dot.currentPosition[X]][dot.currentPosition[Y]].setAsDot();
    }

    public void bringDot(){
        try {
            this.client = new Socket("127.0.0.1", 9731);
            this.outPut = new ObjectOutputStream(client.getOutputStream());
            this.outPut.writeObject(dot);
            this.outPut.flush();
            this.outPut.close();
            this.client.close();
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public void run(){
        while (true){
            this.dot.move();
            moveDot();
            bringDot();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
    }


}