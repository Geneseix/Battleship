/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import modelo.ManejadorSesion;
import utilidades.bbdd.Bd;
import utilidades.bbdd.Gestor_conexion_POSTGRE;

/**
 *
 * @author Usuario
 */
public class PerfilController {
    @FXML
    private Label usuario;
    
    @FXML
    private Label puntaje;
    
    public void setNombreUsuario(){
        usuario.setText(ManejadorSesion.getNombreJugador1());
    }
    
    public void setPuntaje(){
        Gestor_conexion_POSTGRE gestor = new Gestor_conexion_POSTGRE("battleship", false);
        String[][] puntajeUsuario;
        puntajeUsuario = Bd.consultaSelect(gestor, "select puntaje from usuarios where nombre ='"+ManejadorSesion.getNombreJugador1()+"'");
        puntaje.setText("Puntuacion: "+puntajeUsuario[0][0]);
        gestor.cerrar_Conexion(false);
    }
}
