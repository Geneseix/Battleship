/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Usuario
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/TableroPrueba.fxml"));
        Image icono = new Image(getClass().getResourceAsStream("/Vista/icono.png"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/Vista/styles.css").toExternalForm());
        //stage.setMaximized(true);
        stage.setTitle("Battleship");
        stage.getIcons().add(icono);
        stage.setScene(scene);
        stage.show();
      
    }

    public static void main(String[] args) {
        launch(args);
    }
}
