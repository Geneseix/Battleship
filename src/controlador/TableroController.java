package controlador;

import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

public class TableroController {

    @FXML
    private VBox vbox;

    private Tablero tableroJg1, tableroJg2;

    private boolean corriendo = false;
    private int barcosAColocar = 5;

    @FXML
    private void initialize() {

        tableroJg1 = new Tablero(false, event -> {
            if (corriendo)
                return;

            Tablero.Celda cell = (Tablero.Celda) event.getSource();
            if (tableroJg1.colocarBarco(new Barco(barcosAColocar, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
                if (--barcosAColocar == 0) {
                    iniciarJuego();
                }
            }
        });

        vbox.getChildren().addAll(tableroJg1);
        
    }

    private void iniciarJuego() {
        // Deshabilitar la interacción con el tablero del jugador
        //tableroJg1.setDisable(true);

        // Crear y colocar los barcos del enemigo en su tablero
        tableroJg2 = new Tablero(true, event -> {});
        placeEnemyShips();

        // Iniciar el juego
        corriendo = true;
    }

    private void placeEnemyShips() {
        int type = 5;

        while (type > 0) {
            int x = (int) (Math.random() * 10);
            int y = (int) (Math.random() * 10);

            if (tableroJg2.colocarBarco(new Barco(type, Math.random() < 0.5), x, y)) {
                type--;
            }
        }
    }
}
