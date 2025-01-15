/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Usuario
 */
public class Celda extends Rectangle{
        public int x, y;
        public Barco barco = null;
        public boolean fueDisparada = false;

        private Tablero tablero;

        public Celda(int x, int y, Tablero tablero){
            super(45, 45);
            this.x = x;
            this.y = y;
            this.tablero = tablero;
            setFill(Color.TRANSPARENT);
            setStroke(Color.WHITE);
        }

        public boolean disparar(){
            fueDisparada = true;
            setFill(Color.BLACK);

            if (barco != null) {
                barco.impacto();
                setFill(Color.RED);
                if (!barco.estaVivo()){
                    tablero.barcos--;
                }
                return true;
            }
            return false;
        }
    }