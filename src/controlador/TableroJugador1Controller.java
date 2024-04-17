package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TableroJugador1Controller {

    @FXML
    private GridPane tableroJugador1;
    
    private Button[][] celdas;
    private boolean[][] barcos;
    private List<List<Button>> botonesBarcos;
    private List<Button> barcoSeleccionado;
    Barco[] barcosColocados = new Barco[5];
    
    @FXML
    public void initialize() {
        inicializarTablero();
        Random rnd = new Random();
        String[] orientaciones = {"Horizontal", "Vertical"};
        
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
        botonesBarcos = new ArrayList<>();

        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                Button celda = new Button();
                celda.setMinSize(67, 54);
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

            fila = rnd.nextInt(10);
            columna = rnd.nextInt(10);
            orientacion = barco.getOrientacion();

            while (!validarPosicionBarco(barco, fila, columna, orientacion)) {
                fila = rnd.nextInt(10);
                columna = rnd.nextInt(10);
                orientacion = barco.getOrientacion();
            }

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

        if (filaFinal >= 10 || columnaFinal >= 10) {
            return false;
        }

        for (int i = Math.max(0, fila - 1); i <= Math.min(9, filaFinal + 1); i++) {
            for (int j = Math.max(0, columna - 1); j <= Math.min(9, columnaFinal + 1); j++) {
                if (barcos[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    private void colocarBarcoEnTablero(Barco barco, int fila, int columna, String orientacion) {
        int tamaño = barco.getLongitud();
        List<Button> botonesDelBarco = new ArrayList<>();

        if (orientacion.equals("Vertical")) {
            for (int i = 0; i < tamaño; i++) {
                int filaActual = fila + i;
                Button barcoButton = new Button();
                barcoButton.setMinSize(67, 54);
                barcoButton.setMaxSize(67, 54);
                barcoButton.setStyle("-fx-background-color: blue; -fx-border-color: black");
                barcoButton.setId("barco_" + filaActual + "_" + columna);
                barcoButton.setOnDragDetected(event -> dragDetect(event));
                barcoButton.setOnMouseDragged(event -> arrastrar(event));
                barcoButton.setOnMouseReleased(event -> finalizarArrastre(event));
                tableroJugador1.add(barcoButton, columna, filaActual);
                barcos[filaActual][columna] = true;
                botonesDelBarco.add(barcoButton);
            }
        } else {
            for (int i = 0; i < tamaño; i++) {
                int columnaActual = columna + i;
                Button barcoButton = new Button();
                barcoButton.setMinSize(67, 54);
                barcoButton.setMaxSize(67, 54);
                barcoButton.setStyle("-fx-background-color: blue; -fx-border-color: black");
                barcoButton.setId("barco_" + fila + "_" + columnaActual);
                barcoButton.setOnDragDetected(event -> dragDetect(event));
                barcoButton.setOnMouseDragged(event -> arrastrar(event));
                barcoButton.setOnMouseReleased(event -> finalizarArrastre(event));
                tableroJugador1.add(barcoButton, columnaActual, fila);
                barcos[fila][columnaActual] = true;
                botonesDelBarco.add(barcoButton);
            }
        }

        botonesBarcos.add(botonesDelBarco);
    }

    private void dragDetect(MouseEvent event) {
        System.out.println("Drag detectado");
        Button sourceButton = (Button) event.getSource();
        barcoSeleccionado = buscarBotonesDelBarco(sourceButton);
        if (barcoSeleccionado != null) {
            for (Button boton : barcoSeleccionado) {
                boton.startFullDrag();
            }
        }
    }

private void arrastrar(MouseEvent event) {
    if (barcoSeleccionado != null) {
        double x = event.getSceneX() - tableroJugador1.getLayoutX();
        double y = event.getSceneY() - tableroJugador1.getLayoutY();

        int fila = (int) (y / 54);
        int columna = (int) (x / 67);

        int filaInicial = GridPane.getRowIndex(barcoSeleccionado.get(0));
        int columnaInicial = GridPane.getColumnIndex(barcoSeleccionado.get(0));

        int deltaFila = fila - filaInicial;
        int deltaColumna = columna - columnaInicial;

        for (Button boton : barcoSeleccionado) {
            int nuevaFila = Math.max(0, Math.min(9, GridPane.getRowIndex(boton) + deltaFila));
            int nuevaColumna = Math.max(0, Math.min(9, GridPane.getColumnIndex(boton) + deltaColumna));
            GridPane.setRowIndex(boton, nuevaFila);
            GridPane.setColumnIndex(boton, nuevaColumna);
        }
    }
}


    private void finalizarArrastre(MouseEvent event) {
        System.out.println("Finalizar arrastre");
        if (barcoSeleccionado != null) {
            double x = event.getSceneX() - tableroJugador1.getLayoutX();
            double y = event.getSceneY() - tableroJugador1.getLayoutY();
            int fila = (int) (y / 54);
            int columna = (int) (x / 67);

            if (validarPosicionBarco(barcoSeleccionado.get(0), fila, columna)) {
                for (Button boton : barcoSeleccionado) {
                    tableroJugador1.getChildren().remove(boton);
                    tableroJugador1.add(boton, columna, fila);
                }
                actualizarMatrizBarcos(barcoSeleccionado.get(0), fila, columna);
            }
            barcoSeleccionado = null;
        }
    }

    private List<Button> buscarBotonesDelBarco(Button celda) {
        for (List<Button> botonesDelBarco : botonesBarcos) {
            if (botonesDelBarco.contains(celda)) {
                return botonesDelBarco;
            }
        }
        return null;
    }
    
    private Button barcoEnCelda(Button celda) {
        for (List<Button> botonesDelBarco : botonesBarcos) {
            if (botonesDelBarco.contains(celda)) {
                return celda;
            }
        }
        return null;
    }

    private boolean validarPosicionBarco(Button barco, int fila, int columna) {
        if (fila < 0 || fila >= 10 || columna < 0 || columna >= 10) {
            return false;
        }

        for (Button boton : barcoSeleccionado) {
            int filaBoton = GridPane.getRowIndex(boton);
            int columnaBoton = GridPane.getColumnIndex(boton);
            if (fila + filaBoton < 0 || fila + filaBoton >= 10 || columna + columnaBoton < 0 || columna + columnaBoton >= 10) {
                return false;
            }
            if (barcoEnCelda(celdas[fila + filaBoton][columna + columnaBoton]) != null) {
                return false;
            }
        }
        return true;
    }

    private void actualizarMatrizBarcos(Button barco, int fila, int columna) {
        // Lógica para actualizar la matriz de barcos con la nueva posición del barco
    }
}
