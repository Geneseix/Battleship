/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Usuario
 */
public class InicioController {
    
    @FXML
    private Label usuario;
        
    @FXML
    private Label puntaje;
        
    public void setNombreUsuario(String nombreUsuario) {
        usuario.setText(nombreUsuario);
    }
	
    public void setPuntaje(String puntaje) {
	this.puntaje.setText(puntaje);
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
	
    @FXML
    private void inicioSesionJg2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PaginaPrincipal.fxml"));
        Parent root = loader.load(); 
        PrincipalController controladorPaginaPrincipal = loader.getController();

        if (controladorPaginaPrincipal != null && controladorPaginaPrincipal.getLabelInicioSesionJg2() != null) {
            controladorPaginaPrincipal.getLabelInicioSesionJg2().setVisible(true);
        }  

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Battleship");
        stage.setScene(scene);
    }

    @FXML
    private void irAlPerfil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PaginaPerfil.fxml"));
        Parent root = loader.load();
        
        PerfilController controladorPaginaInicio = loader.getController();
        controladorPaginaInicio.setNombreUsuario();
        controladorPaginaInicio.setPuntaje();
        Scene scene = new Scene(root);
        scene.setRoot(root); 
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Battleship - Perfil");
        stage.setScene(scene);
    }
    
    
    
}
