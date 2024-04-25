package controlador;

import controlador.Tablero.Celda;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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

    private boolean juegoIniciado = false;
    private int barcosPorColocarJg1 = 5;
    private int barcosPorColocarJg2 = 5;
    private boolean turnoJugador1 = true;
    private boolean turnoEnemigo = false;

    @FXML
    private void initialize() {
        tableroJugador1 = new Tablero(false, event -> {
            if(turnoJugador1){
                if (juegoIniciado == true){
                    Celda celda = (Celda) event.getSource();
                    if(!celda.disparar()){
                        turnoJugador1=false;
                    }
                }
                else{
                    Celda cell = (Celda) event.getSource();
                    if (tableroJugador1.colocarBarco(new Barco(barcosPorColocarJg1, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
                        if (--barcosPorColocarJg1 == 0) {
                            btnConfirmar.setDisable(false);
                        }
                    }
                }
            }
        });
        
        btnConfirmar.setDisable(true);
        
        tableroJugador2 = new Tablero(false, event -> {
            if(!turnoJugador1){
                if (juegoIniciado == true){
                    Celda celda = (Celda) event.getSource();
                    if(!celda.disparar()){
                        turnoJugador1=true;
                    }
                    
                }
                else{
                    Celda cell = (Celda) event.getSource();
                    if (tableroJugador2.colocarBarco(new Barco(barcosPorColocarJg2, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
                        if (--barcosPorColocarJg2 == 0) {
                            btnConfirmar.setDisable(false);
                        }
                    }
                }
            }
        });
        // Creamos un VBox para contener el tablero del jugador 1
        VBox vboxTableroJg1 = new VBox();
        vboxTableroJg1.getChildren().add(tableroJugador1);
        VBox vboxTableroJg2 = new VBox();
        vboxTableroJg2.getChildren().add(tableroJugador2);
        // Establecemos márgenes para mover el tablero hacia la derecha
        VBox.setMargin(tableroJugador1, new Insets(0, 0, 0, 350)); // Mover el tablero hacia la derecha
        VBox.setMargin(tableroJugador2, new Insets(0, 0, 0, 350));
        vbox.getChildren().addAll(tableroJugador1);
        btnConfirmar.setDisable(true);
    }

    @FXML
    private void confirmarColocacion() {
        if (!turnoJugador1) {
            btnConfirmar.setVisible(false);
            txtTurno.setVisible(false);
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
            hbox.setMargin(vboxJugador2, new Insets(0, 0, 0, -200));
            hbox.setMargin(vboxJugador1, new Insets(0, 0, 0, -350));
            hbox.setSpacing(25);
            hbox.getChildren().addAll(vboxJugador2, vboxJugador1);
            vbox.getChildren().add(hbox);

            iniciarJuego(); 
            juegoIniciado = true;
        }

        else {
            txtTurno.setText("Turno del Jugador 2");           
            turnoJugador1 = false;
            vbox.getChildren().clear();
            vbox.getChildren().addAll(tableroJugador2);
            btnConfirmar.setDisable(true);
        }
    }
    
    private void iniciarJuego(){
        
    }


}
