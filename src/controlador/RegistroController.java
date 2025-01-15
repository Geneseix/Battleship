/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Base64;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilidades.bbdd.Bd;
import utilidades.bbdd.Gestor_conexion_POSTGRE;

/**
 *
 * @author Usuario
 */
public class RegistroController {

    @FXML
    private TextField nomUsuario;

    @FXML
    private PasswordField contrasenia;
    
    @FXML
    private Label txtError;
    
    @FXML
    private Label txtError2;
    
    @FXML
    private void registrarUsuario() {
        String usuario = nomUsuario.getText();
        String contra = contrasenia.getText();
        int id = 0;
        if(!usuario.isEmpty() && !contra.isEmpty()){
        	Gestor_conexion_POSTGRE gestor = new Gestor_conexion_POSTGRE("battleship", true);
            String[][] consultaUltimoId;
            consultaUltimoId = Bd.consultaSelect(gestor, "select id_jugador from usuarios order by id_jugador desc limit 1");
            
            if(consultaUltimoId!=null){
                id = Integer.valueOf(consultaUltimoId[0][0]);
            }         
            if(sePuedeRegistrar(usuario)){
                Bd.consultaModificacion(gestor, "insert into usuarios(id_jugador,nombre, contrasena,puntaje) values("+(id+1)+",\'"+usuario+"\',\'"+hashContrasenia(contra)+"\',0);");
            }
            else{
                txtError.setVisible(true);
            }
            gestor.cerrar_Conexion(true);
        }
        else{
            txtError2.setVisible(true);
        }
    }
    
    @FXML
    private boolean sePuedeRegistrar(String usuario){
        String[][] vec;
        Gestor_conexion_POSTGRE gestor = new Gestor_conexion_POSTGRE("battleship", true);
        vec = Bd.consultaSelect(gestor, "select nombre from usuarios");
        if(vec!=null){
            for(int i=0;i<vec.length;i++){
                for(int j=0;j<vec[0].length;j++){
                    if(vec[i][j].equals(usuario)){
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    public static String hashContrasenia(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PaginaPrincipal.fxml"));
        Parent root = loader.load();
        
        PrincipalController controladorPaginaPrincipal = loader.getController();
        
        Scene scene = new Scene(root);
        scene.setRoot(root); 
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Battleship");
        stage.setScene(scene);
    }
}
