package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TableroJugador1Controller {

    @FXML
    private GridPane tableroJugador1;

    private Button[][] celdas; // Matriz de botones que representan las celdas del tablero
    private boolean[][] barcos; // Matriz que indica la presencia de barcos en las celdas del tablero
    private Barco[] barcosColocados;
    private Button barcoSeleccionado; // Barco que se está moviendo

    @FXML
    public void initialize() {
        inicializarTablero();
        Random rnd = new Random();
        String[] orientaciones = {"Horizontal", "Vertical"};
        barcosColocados = new Barco[5];
        barcosColocados[0] = new Barco("Portaaviones", 5, orientaciones[rnd.nextInt(2)], 0);
        barcosColocados[1] = new Barco("Buque", 4, orientaciones[rnd.nextInt(2)], 0);
        barcosColocados[2] = new Barco("Submarino", 3, orientaciones[rnd.nextInt(2)], 0);
        barcosColocados[3] = new Barco("Crucero", 2, orientaciones[rnd.nextInt(2)], 0);
        barcosColocados[4] = new Barco("Lancha", 1, orientaciones[rnd.nextInt(2)], 0);
        colocarBarcosAleatoriamente();
    }

    private void inicializarTablero() {
        celdas = new Button[10][10];
        barcos = new boolean[10][10];

        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                Button celda = new Button();
                celda.setMinSize(67, 54); // Tamaño de la celda
                celda.setMaxSize(67, 54);
                tableroJugador1.add(celda, columna, fila);
                celdas[fila][columna] = celda;
            }
        }
    }

    private void colocarBarcosAleatoriamente() {
        Random rnd = new Random();

        for (int i = 0; i < barcosColocados.length; i++) {
            Barco barco = barcosColocados[i];
            int fila, columna;
            String orientacion;

            // Genera aleatoriamente fila, columna y orientación
            fila = rnd.nextInt(10);
            columna = rnd.nextInt(10);
            orientacion = barco.getOrientacion();

            // Verifica si la posición generada es válida para colocar el barco
            while (!validarPosicionBarco(barco, fila, columna, orientacion)) {
                fila = rnd.nextInt(10);
                columna = rnd.nextInt(10);
                orientacion = barco.getOrientacion();
            }

            // Coloca el barco en el tablero
            colocarBarcoEnTablero(barco, fila, columna, orientacion);
        }
    }

    private boolean validarPosicionBarco(Barco barco, int fila, int columna, String orientacion) {
        int tamaño = barco.getLongitud();
        int filaFinal = fila;
        int columnaFinal = columna;

        if (orientacion.equals("Vertical")) {
            filaFinal += tamaño - 1;
        } else {
            columnaFinal += tamaño - 1;
        }

        // Verifica si el barco se sale del tablero
        if (filaFinal >= 10 || columnaFinal >= 10) {
            return false;
        }

        // Verifica si el barco se superpone con otro barco o está demasiado cerca
        for (int i = fila - 1; i <= filaFinal + 1; i++) {
            for (int j = columna - 1; j <= columnaFinal + 1; j++) {
                if (i >= 0 && i < 10 && j >= 0 && j < 10) {
                    if (barcos[i][j]) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

private void colocarBarcoEnTablero(Barco barco, int fila, int columna, String orientacion) {
    int tamaño = barco.getLongitud();
    Rectangle barcoRect = new Rectangle(); // Creamos un nuevo rectángulo para representar el barco
    barcoRect.setWidth(67 * tamaño); // Establecemos el ancho del rectángulo para que coincida con la longitud del barco
    barcoRect.setHeight(54);
    barcoRect.setFill(Color.BLUE); // Color del barco
       
    if (orientacion.equals("Vertical")) {
        for (int i = 0; i < tamaño; i++) {
            int filaActual = fila + i;
            int columnaActual = columna;

            if (tableroJugador1.getChildren().contains(barcoRect)) {
                tableroJugador1.getChildren().remove(barcoRect); // Eliminamos el rectángulo del barco si ya está en el GridPane
            }

            barcos[filaActual][columnaActual] = true;
            tableroJugador1.add(barcoRect, columna, filaActual); // Agregamos el rectángulo del barco en su lugar
        }
    } else {
        for (int i = 0; i < tamaño; i++) {
            int filaActual = fila;
            int columnaActual = columna + i;

            if (tableroJugador1.getChildren().contains(barcoRect)) {
                tableroJugador1.getChildren().remove(barcoRect); // Eliminamos el rectángulo del barco si ya está en el GridPane
            }

            barcos[filaActual][columnaActual] = true;
            tableroJugador1.add(barcoRect, columnaActual, fila); // Agregamos el rectángulo del barco en su lugar
        }
    }
}
    private Button barcoEnCelda(Button celda) {
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                if (celdas[fila][columna] == celda && barcos[fila][columna]) {
                    // Encuentra el botón de la celda que contiene un barco
                    return celda; // Retorna el botón encontrado
                }
            }
        }
        return null; // Si no hay barco en la celda, devuelve null
    }
}
