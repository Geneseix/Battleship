
package controlador;

/**
 *
 * @author Usuario
 */
public class Barco {
    
    private String tipo;
    private int longitud;
    private String orientacion;
    private int golpes;
    
    public Barco(String tipo, int longitud, String orientacion,int golpes){
        this.tipo = tipo;
        this.longitud = longitud;
        this.orientacion = orientacion;
        this.golpes = golpes;
    }
    public String getTipo(){
        return tipo;
    }
    public int getLongitud(){
        return longitud;
    }
    public String getOrientacion(){
        return orientacion;
    }
    
    public int getGolpes(){
        return golpes;
    }
    
}
