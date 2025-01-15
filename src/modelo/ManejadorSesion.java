/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import utilidades.bbdd.Bd;
import utilidades.bbdd.Gestor_conexion_POSTGRE;

/**
 *
 * @author Usuario
 */
public class ManejadorSesion {
    private static boolean jg1HaIniciadoSesion;
    private static boolean jg2HaIniciadoSesion;
    private static int idJugador1;
    private static int idJugador2;
    
    public static boolean getSesionJg1(){
        return jg1HaIniciadoSesion;
    }
    
    public static boolean getSesionJg2(){
        return jg2HaIniciadoSesion;
    }
    
    public static void setSesionJg1(boolean sesionJg1){
        jg1HaIniciadoSesion = sesionJg1;
    }
    
    public static void setSesionJg2(boolean sesionJg2){
        jg2HaIniciadoSesion = sesionJg2;
    }
    
    public static int getIdJugador1(){
        return idJugador1;
    }
    
    public static int getIdJugador2(){
        return idJugador2;
    }
    
    public static void setIdJugador1(int idJg1){
        idJugador1 = idJg1;
    }
    
    public static void setIdJugador2(int idJg2){
        idJugador2 = idJg2;
    }
    
    public static String getNombreJugador1(){
        Gestor_conexion_POSTGRE gestor = new Gestor_conexion_POSTGRE("battleship", false);
        String[][] consultaNombre;
        consultaNombre = Bd.consultaSelect(gestor, "select nombre from usuarios where id_jugador = "+getIdJugador1());
        gestor.cerrar_Conexion(true);
        return consultaNombre[0][0];
    }
    
    public static String getNombreJugador2(){
        Gestor_conexion_POSTGRE gestor = new Gestor_conexion_POSTGRE("battleship", false);
        String[][] consultaNombre;
        consultaNombre = Bd.consultaSelect(gestor, "select nombre from usuarios where id_jugador = "+getIdJugador2());
        gestor.cerrar_Conexion(true);
        return consultaNombre[0][0];
    }
}
