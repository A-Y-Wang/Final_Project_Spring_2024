package GUIs;

import NetworkClient.Client;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainGUI extends Application {

    Client client = new Client();

        @Override
        public void start(Stage applicationStage) throws IOException {
            openLogin(applicationStage);

        }

        public void openLogin(Stage stage) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signin.fxml"));
            Scene scene = new Scene(loader.load());

            LoginController loginController = loader.getController();
            loginController.setMainApp(this);
            loginController.setClient(client);

            stage.setTitle("Log In");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        }
        public void loadMainScreen(Stage stage) throws IOException {

            FXMLLoader loader1 = new FXMLLoader(MainGUI.class.getResource("clientGUI.fxml"));
            Parent root = loader1.load();

            MainMenuController mainController = loader1.getController();
            mainController.setMainApp(this);
            mainController.setClient(client);
            //Scene scene = new Scene(root);
            root.setScaleY(1.5);
            root.setScaleX(1.5);
            Scene scene = new Scene(root, 1350, 900);
            stage.setTitle("Catalog");
            stage.setScene(scene);
            stage.show();
        }


        public static void main(String[] args) {
            launch(args);
        }
    }

