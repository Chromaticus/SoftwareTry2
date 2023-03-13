package com.example.softwaretry2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Main class is the starting point of the application. It extends the JavaFX Application class and overrides its
 * start method to load the main FXML file and create the application window.
 * FUTURE ENHANCEMENT: A barcode or RFID scanning integration would improve accuracy and speed up inventory tracking management.
 * */
public class Main extends Application {

    /**
     * This method is called by the JavaFX platform to start the application.
     * @param stage The primary stage for the application, onto which the application scene can be set.
     * @throws IOException If the FXML file cannot be loaded.
     * */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-screen-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 830, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *The main() method is the entry point of the application. It launches the JavaFX runtime by calling the launch()
     * method inherited from the Application class.
     * @param args The command line arguments.
     * */
    public static void main(String[] args) {
        launch();
    }
}