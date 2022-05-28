package Common;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.*;

public class Mapa implements Constantes{
    public JPanel panelTablero;
    public Casilla[][] tablero;


    public Mapa(ActionListener listener){
        tablero = new Casilla[TABLERO_SIZE][TABLERO_SIZE];
        panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(TABLERO_SIZE, TABLERO_SIZE));
        for (int i = 0; i < TABLERO_SIZE; i++){
            for (int j = 0; j < TABLERO_SIZE; j++){
                tablero[i][j] = new Casilla(i, j);
                tablero[i][j].addActionListener(listener);
                panelTablero.add(tablero[i][j]);
            }
        }
    }
    public void cleanMap(Dot d){
        for (int row = 0; row < TABLERO_SIZE; row++){
            for (int column = 0; column < TABLERO_SIZE; column++){
                tablero[row][column].clearDot();
                paintTarget(d);
            }
        }
        paintDot(d);
    }

    public void paintTarget(Dot d){
        tablero[d.target.coords[X]][d.target.coords[Y]].setAsTarget();
    }

    public void paintDot(Dot d){
        tablero[d.currentPosition[X]][d.currentPosition[Y]].setAsDot();
    }
}
