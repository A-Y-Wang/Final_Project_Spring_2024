package GUIs;

import NetworkClient.Client;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class MainGUI extends Application {

        @Override
        public void start(Stage applicationStage) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signin.fxml"));
            Scene scene = new Scene(loader.load());
            applicationStage.setTitle("LoginFX");
            applicationStage.setScene(scene);
            applicationStage.setResizable(false);
            applicationStage.show();
        }

        public static void loadMainScreen(Stage stage) throws IOException {

            Parent root = FXMLLoader.load(MainGUI.class.getResource("clientGUI.fxml"));
            //Scene scene = new Scene(root);
            root.setScaleY(1.5);
            root.setScaleX(1.5);
            Scene scene = new Scene(root, 1350, 900);
            stage.setTitle("MainFX");
            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }

