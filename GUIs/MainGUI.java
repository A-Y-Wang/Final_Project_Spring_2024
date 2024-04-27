package GUIs;

import LibraryDatabase.LibraryUsers;
import NetworkClient.Client;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class MainGUI extends Application {

   // Client client = new Client();

        @Override
        public void start(Stage applicationStage) throws IOException, InterruptedException {
            openLogin(applicationStage);

        }

        public void openLogin(Stage stage) throws IOException, InterruptedException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            stage.setTitle("Log In");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        }
        public static void loadMainScreen(Stage stage, LibraryUsers libraryUsers) throws IOException, ClassNotFoundException {

            FXMLLoader loader1 = new FXMLLoader(MainGUI.class.getResource("clientGUI.fxml"));

            Parent root = loader1.load();
            MainMenuController mainController = loader1.getController();
            mainController.setLibraryUser(libraryUsers);

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

