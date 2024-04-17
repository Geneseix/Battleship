package controlador;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utilidades.bbdd.Bd;
import utilidades.bbdd.Gestor_conexion_POSTGRE;

public class PrincipalController {
    @FXML
    private TextField nomUsuario;

    @FXML
    private PasswordField contrasenia;
    
    @FXML
    private Label errorAlgunCampoVacio;
    
    @FXML
    private Label errorContraIncorrecta;
    
    @FXML
    private Label errorUsuarioNoEncontrado;
    
    @FXML
    private BorderPane root;

    @FXML
    private void iniciarSesion(ActionEvent event) throws IOException {
        String usuario = nomUsuario.getText().toLowerCase(); 
        String contra = contrasenia.getText();
        if (!usuario.isEmpty() && !contra.isEmpty()) {
            Gestor_conexion_POSTGRE gestor = new Gestor_conexion_POSTGRE("battleship", false);
            String consulta = "SELECT nombre, contraseña FROM usuarios WHERE LOWER(nombre) = '"+usuario+"'";
            String[][] resultadoConsulta = Bd.consultaSelect(gestor, consulta);

            if (resultadoConsulta != null && resultadoConsulta.length > 0) {
                String nombreUsuario = resultadoConsulta[0][0];
                String contraseñaAlmacenada = resultadoConsulta[0][1];
                if (RegistroController.hashContrasenia(contra).equals(contraseñaAlmacenada)) {
                    
                    irAPaginaInicio(event);
                } else {
                    // Contraseña incorrecta
                    ocultarErrores();
                    errorContraIncorrecta.setVisible(true);
                }
            } else {
                // Usuario no encontrado
                ocultarErrores();
                errorUsuarioNoEncontrado.setVisible(true);
            }
            gestor.cerrar_Conexion(false);
        } else {
            // Usuario o contraseña vacíos
            ocultarErrores();
            errorAlgunCampoVacio.setVisible(true);
        }
    }

    
    public String getNombreUsuario() {
    	return nomUsuario.getText();
    }
    
    public String getPuntaje() {
    	Gestor_conexion_POSTGRE gestor = new Gestor_conexion_POSTGRE("battleship",false);
    	String[][] resultadoConsulta = Bd.consultaSelect(gestor, "select puntaje from usuarios where nombre='"+nomUsuario.getText()+"'");
    	return resultadoConsulta[0][0];
    }
    
    private void ocultarErrores(){
        errorContraIncorrecta.setVisible(false);
        errorUsuarioNoEncontrado.setVisible(false);
        errorAlgunCampoVacio.setVisible(false);
    }

    @FXML
    private void salir(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar salida");
        alert.setHeaderText("¿Estás seguro?");

        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Cancelar");
        alert.getButtonTypes().setAll(okButton, cancelButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                Platform.exit();
            }
        });
    }

    
    @FXML
    private void cambiarARegistro(ActionEvent event) throws IOException {
    
        Image icono = new Image(getClass().getResourceAsStream("/vista/icono.png"));
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Registro.fxml"));
        Parent root = loader.load();
    
        RegistroController controladorEscenaRegistro = loader.getController();
    
        Scene scene = new Scene(root);
    
        Stage stage = new Stage();
        stage.setTitle("Registro"); 
        stage.getIcons().add(icono);
        stage.setScene(scene);
        stage.show();
    
        Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ventanaActual.close();
    }
    
    @FXML
    private void irAPaginaInicio(ActionEvent event) throws IOException {

        Image icono = new Image(getClass().getResourceAsStream("/vista/icono.png"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PaginaInicio.fxml"));
        Parent root = loader.load();

        InicioController controladorPaginaInicio = loader.getController();

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Battleship");
        stage.getIcons().add(icono);
        stage.setScene(scene);
        stage.show();

        Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ventanaActual.close();
    }
}
