package controlador;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;


public class Tablero extends Parent {
    private VBox filas = new VBox();
    private boolean enemigo = false;
    public int barcos = 5;

    public Tablero(boolean enemigo, EventHandler<? super MouseEvent> manejador) {

        this.enemigo = enemigo;
        for (int y = 0; y < 10; y++) {
            HBox fila = new HBox();
            for (int x = 0; x < 10; x++) {
                Celda c = new Celda(x, y, this);
                c.setOnMouseClicked(manejador);
                fila.getChildren().add(c);
            }

            filas.getChildren().add(fila);
        }

        getChildren().add(filas);
    }

    public boolean colocarBarco(Barco barco, int x, int y) {
        if (puedeColocarBarco(barco, x, y)) {
            int longitud = barco.tipo;

            if (barco.vertical) {
                for (int i = y; i < y + longitud; i++) {
                    Celda celda = getCelda(x, i);
                    celda.barco = barco;
                    if (!enemigo) {
                        celda.setFill(Color.BLUE);
                        celda.setStroke(Color.WHITE);
                    }
                }
            } else {
                for (int i = x; i < x + longitud; i++) {
                    Celda celda = getCelda(i, y);
                    celda.barco = barco;
                    if (!enemigo) {
                        celda.setFill(Color.BLUE);
                        celda.setStroke(Color.WHITE);
                    }
                }
            }

            return true;
        }

        return false;
    }

    public Celda getCelda(int x, int y) {
        return (Celda) ((HBox) filas.getChildren().get(y)).getChildren().get(x);
    }

    private Celda[] getVecinos(int x, int y) {
        Point2D[] puntos = new Point2D[]{
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1)
        };

        List<Celda> vecinos = new ArrayList<Celda>();

        for (Point2D p : puntos) {
            if (esPuntoValido(p)) {
                vecinos.add(getCelda((int) p.getX(), (int) p.getY()));
            }
        }

        return vecinos.toArray(new Celda[0]);
    }

    private boolean puedeColocarBarco(Barco barco, int x, int y) {
        int longitud = barco.tipo;

        if (barco.vertical) {
            for (int i = y; i < y + longitud; i++) {
                if (!esPuntoValido(x, i))
                    return false;

                Celda celda = getCelda(x, i);
                if (celda.barco != null)
                    return false;

                for (Celda vecino : getVecinos(x, i)) {
                    if (!esPuntoValido(x, i))
                        return false;

                    if (vecino.barco != null)
                        return false;
                }
            }
        } else {
            for (int i = x; i < x + longitud; i++) {
                if (!esPuntoValido(i, y))
                    return false;

                Celda celda = getCelda(i, y);
                if (celda.barco != null)
                    return false;

                for (Celda vecino : getVecinos(i, y)) {
                    if (!esPuntoValido(i, y))
                        return false;

                    if (vecino.barco != null)
                        return false;
                }
            }
        }

        return true;
    }

    private boolean esPuntoValido(Point2D punto) {
        return esPuntoValido(punto.getX(), punto.getY());
    }

    private boolean esPuntoValido(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    public class Celda extends Rectangle {
        public int x, y;
        public Barco barco = null;
        public boolean fueDisparada = false;

        private Tablero tablero;

        public Celda(int x, int y, Tablero tablero) {
            super(50, 50);
            this.x = x;
            this.y = y;
            this.tablero = tablero;
            setFill(Color.TRANSPARENT);
            setStroke(Color.WHITE);
        }

        public boolean disparar() {
            fueDisparada = true;
            setFill(Color.BLACK);

            if (barco != null) {
                barco.impacto();
                setFill(Color.RED);
                if (!barco.estaVivo()) {
                    tablero.barcos--;
                }
                return true;
            }

            return false;
        }

       
        public void rotarBarco() {
            if (barco != null) {
                barco.vertical = !barco.vertical;
                actualizarVisual();
            }
        }

      
        private void actualizarVisual() {
            if (barco.vertical) {
                setWidth(50);
                setHeight(50 * barco.tipo);
            } else {
                setWidth(50 * barco.tipo);
                setHeight(50);
            }
        }
    }
}
