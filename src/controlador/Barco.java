package controlador;

import javafx.scene.Parent;

public class Barco extends Parent {
  
    private int tipo;
    private boolean vertical = true;
    private int vida;

    public Barco(int tipo, boolean vertical) {   
        this.tipo = tipo;
        this.vertical = vertical;
        vida = tipo;
    }
    
    public int getTipo(){
        return tipo;
    }
    
    public boolean esVertical(){
        return vertical;
    }
    
    public void impacto() {
        vida--;
    }

    public boolean estaVivo() {
        return vida > 0;
    }
}