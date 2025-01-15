/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


/**
 *
 * @author Usuario
 */
public class Barco{
  
    private int tipo;
    private boolean vertical = true;
    private int vida;

    public Barco(int tipo, boolean vertical){   
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
    
    public void impacto(){
        vida--;
    }

    public boolean estaVivo(){
        return vida > 0;
    }
}