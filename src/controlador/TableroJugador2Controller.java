package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Random;
import javafx.scene.layout.GridPane;

public class TableroJugador2Controller {
    
    private int[][] tableroJugador1 = new int[10][10];
    private int[][] tableroJugador2 = new int[10][10];
    private final int BARCO = 1;
    private final int AGUA = 0;

    @FXML
    private GridPane tableroJugador1GridPane;

    @FXML
    private GridPane tableroJugador2GridPane;

    @FXML
    public void initialize() {
        colocarBarcos(tableroJugador1, tableroJugador1GridPane);
        colocarBarcos(tableroJugador2, tableroJugador2GridPane);
    }

    private void colocarBarcos(int[][] tablero, GridPane gridPane) {
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int fila = rand.nextInt(10);
            int columna = rand.nextInt(10);
            if (tablero[fila][columna] != BARCO) {
                tablero[fila][columna] = BARCO;
                Button boton = (Button) getNodeFromGridPane(gridPane, columna, fila);
                boton.setStyle("-fx-background-color: #888888;");
            } else {
                i--;
            }
        }
    }

    @FXML
    public void clicEnTableroJugador2(MouseEvent event) {
        Button botonClicado = (Button) event.getTarget();
        int columna = GridPane.getColumnIndex(botonClicado);
        int fila = GridPane.getRowIndex(botonClicado);

        if (tableroJugador2[fila][columna] == BARCO) {
            botonClicado.setStyle("-fx-background-color: #FF0000;");
            tableroJugador2[fila][columna] = AGUA;
            if (juegoGanado(tableroJugador2)) {
                System.out.println("¡Has hundido todos los barcos!");
            }
        } else {
            botonClicado.setStyle("-fx-background-color: #0000FF;");
        }
    }

    private boolean juegoGanado(int[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == BARCO) {
                    return false; 
                }
            }
        }
        return true; 
    }

    private javafx.scene.Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}
