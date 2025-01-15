/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;


import modelo.Tablero;
import modelo.Barco;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modelo.Celda;
import modelo.ManejadorSesion;
import utilidades.bbdd.Bd;
import utilidades.bbdd.Gestor_conexion_POSTGRE;


/**
 *
 * @author Usuario
 */
public class TableroController {

    @FXML
    private VBox vbox;
    
    @FXML
    private Label txtTurno;
    
    @FXML
    private Label txtJg1;
    
    @FXML
    private Label txtJg2;
    
    @FXML
    private Button btnConfirmar;

    private Tablero tableroJugador1, tableroJugador2;
    private final Gestor_conexion_POSTGRE gestor = new Gestor_conexion_POSTGRE("battleship", false);
    private boolean hayGanador = false;
    private boolean juegoIniciado = false;
    private int barcosPorColocarJg1 = 5;
    private int barcosPorColocarJg2 = 5;
    private boolean turnoJugador1 = true;

    @FXML
    private void initialize() {
        btnConfirmar.setDisable(true); 
        
        Bd.consultaModificacion(gestor, "INSERT INTO partidas(id_jugador1, id_jugador2) VALUES ("+ManejadorSesion.getIdJugador1()+", "+ManejadorSesion.getIdJugador2()+")");
        txtTurno.setText("Turno de "+ManejadorSesion.getNombreJugador1());
        tableroJugador1 = new Tablero(false, event -> {
            if(!hayGanador){
                if(turnoJugador1){
                    if(juegoIniciado){                                               
                        Celda celda = (Celda) event.getSource();
                        if(!celda.disparar()){   
                            actualizarTurno(ManejadorSesion.getNombreJugador1());
                            turnoJugador1 = false;
                        }    
                        else{
                            actualizarTurno(ManejadorSesion.getNombreJugador2());
                        }
                    }
                    else{
                        Celda cell = (Celda) event.getSource();
                        if(tableroJugador1.colocarBarco(new Barco(barcosPorColocarJg1, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
                            if(--barcosPorColocarJg1 == 0){
                                btnConfirmar.setDisable(false);
                            }
                        }
                    }
                    if(tableroJugador1.barcos == 0){
                        mostrarAlertaGanador("¡"+ManejadorSesion.getNombreJugador2()+" ha ganado!");
                        String[][] idDeLaPartida;
                        idDeLaPartida = Bd.consultaSelect(gestor, "SELECT MAX(id_juego) FROM partidas");
                        Bd.consultaModificacion(gestor, "UPDATE partidas SET ganador = '"+ManejadorSesion.getNombreJugador2()+"', estado = 'finalizado' WHERE id_juego = "+idDeLaPartida[0][0]);
                        Bd.consultaModificacion(gestor, "UPDATE usuarios SET puntaje = puntaje + 30 where id_jugador = "+ManejadorSesion.getIdJugador2());
                        juegoIniciado=false;
                        hayGanador=true;
                    }
                }
            }
        });

        btnConfirmar.setDisable(true);

        tableroJugador2 = new Tablero(false, event ->{
            if(!hayGanador){
                if(!turnoJugador1){
                    if (juegoIniciado){            
                        Celda celda = (Celda) event.getSource();
                        if(!celda.disparar()){
                            actualizarTurno(ManejadorSesion.getNombreJugador2());
                            turnoJugador1 = true;
                        }    
                        else{
                            actualizarTurno(ManejadorSesion.getNombreJugador1());
                        }
                    }
                    else{
                        Celda cell = (Celda) event.getSource();
                        if(tableroJugador2.colocarBarco(new Barco(barcosPorColocarJg2, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)){
                            if(--barcosPorColocarJg2 == 0) {
                                btnConfirmar.setDisable(false);
                            }
                        }
                    }
                    if(tableroJugador2.barcos==0){
                        mostrarAlertaGanador("¡"+ManejadorSesion.getNombreJugador1()+" ha ganado!");
                        String[][] idDeLaPartida;
                        idDeLaPartida = Bd.consultaSelect(gestor, "SELECT MAX(id_juego) FROM partidas");
                        Bd.consultaModificacion(gestor, "update partidas set ganador = '"+ManejadorSesion.getNombreJugador1()+"', estado = 'finalizado' where id_juego = "+idDeLaPartida[0][0]);
                        Bd.consultaModificacion(gestor, "UPDATE usuarios SET puntaje = puntaje + 30 where id_jugador = "+ManejadorSesion.getIdJugador1());
                        juegoIniciado=false;
                        hayGanador=true;
                    }
                }
            }
        });

        VBox vboxTableroJg1 = new VBox();
        vboxTableroJg1.getChildren().add(tableroJugador1);
        VBox vboxTableroJg2 = new VBox();
        vboxTableroJg2.getChildren().add(tableroJugador2);

        VBox.setMargin(tableroJugador1, new Insets(75, 0, 0, 350)); 
        VBox.setMargin(tableroJugador2, new Insets(75, 0, 0, 350));

        vbox.getChildren().addAll(vboxTableroJg1);

    }
    
    private void mostrarAlertaGanador(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(mensaje);
        alert.setHeaderText(mensaje);
        alert.setContentText("¿Que quieres hacer ahora?");

        ButtonType buttonTypeRematch = new ButtonType("Revancha");
        ButtonType buttonTypeExit = new ButtonType("Salir");

        alert.getButtonTypes().setAll(buttonTypeRematch, buttonTypeExit);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeRematch) {
                reiniciarJuego();
            } else if (buttonType == buttonTypeExit) {
                Platform.exit();
            }
        });
    }

    @FXML
    private void volverAlInicio(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PaginaInicio.fxml"));
        Parent root = loader.load();
        
        InicioController controladorPaginaInicio = loader.getController();
        
        Scene scene = new Scene(root);
        scene.setRoot(root); 
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Battleship - Inicio");
        stage.setScene(scene);
    }
    
   @FXML
    private void reiniciarJuego() {
        hayGanador = false;
        juegoIniciado = false;
        barcosPorColocarJg1 = 5;
        barcosPorColocarJg2 = 5;
        turnoJugador1 = true;
        btnConfirmar.setVisible(true);
         
        txtJg1.setVisible(false);
    
        txtJg2.setVisible(false);
        vbox.getChildren().clear();
        initialize();
    }
    
    @FXML
    private void confirmarColocacion(){
        if(!turnoJugador1){
            btnConfirmar.setVisible(false);
            txtTurno.setVisible(false);
            txtJg1.setText(ManejadorSesion.getNombreJugador1());
            txtJg2.setText(ManejadorSesion.getNombreJugador2());
            txtJg1.setVisible(true);
            txtJg2.setVisible(true);

            vbox.getChildren().clear();

            VBox vboxJugador1 = new VBox();
            VBox vboxJugador2 = new VBox();

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    tableroJugador1.getCelda(i, j).setFill(Color.TRANSPARENT);
                    tableroJugador1.getCelda(i, j).setStroke(Color.WHITE);
                }
            }

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    tableroJugador2.getCelda(i, j).setFill(Color.TRANSPARENT);
                    tableroJugador2.getCelda(i, j).setStroke(Color.WHITE);
                }
            }

            vboxJugador1.getChildren().add(tableroJugador1);
            vboxJugador2.getChildren().add(tableroJugador2);

            HBox hbox = new HBox();
            hbox.setSpacing(50);

            hbox.getChildren().addAll(vboxJugador1, vboxJugador2);

            VBox.setMargin(hbox, new Insets(0, 0, 0, -250)); 
            VBox.setMargin(tableroJugador2,new Insets(75,0,0,0));
            vbox.getChildren().addAll(hbox); 
            actualizarTurno(ManejadorSesion.getNombreJugador1());
            juegoIniciado = true;
        }
        else {
            txtTurno.setText("Turno de "+ManejadorSesion.getNombreJugador2());           
            turnoJugador1 = false;
            vbox.getChildren().clear(); 
            vbox.getChildren().addAll(tableroJugador2);
            btnConfirmar.setDisable(true);
        }
    }

    private void actualizarTurno(String nombreJugador) {
        txtTurno.setText("Turno de " + nombreJugador);
        txtTurno.setStyle("-fx-text-fill: #ffb200;"); 
        txtTurno.setVisible(true);  
    }
    
    public void mostrarAlertaInstrucciones() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instrucciones de Colocación");
        alert.setHeaderText(null); 
        alert.setContentText("Haz click derecho para posicionar los barcos en posicion vertical.");

        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }
}
