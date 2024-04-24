package GUIs;

import NetworkClient.Client;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController{

    String Username;
    String Password;

    private MainGUI maingui;
    private Client client;

    public void setMainApp(MainGUI maingui){
        this.maingui = maingui;
    }

    public void setClient(Client clit){
        this.client = clit;
    }

    @FXML
    Button logon;

    @FXML
    Button newuser;


    public void newuserButton(){
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);  // Block events to other windows
        popupStage.setTitle("New User");

        // Create a button to close the popup
        Button closeButton = new Button("Enter");
        TextField userNAME = new TextField();
        TextField passWORD = new TextField();
        VBox vbox = new VBox();
        userNAME.setPromptText("Username");
        passWORD.setPromptText("Password");

        // Layout for the popup
        StackPane popupContent = new StackPane();
        popupContent.getChildren().add(vbox);
        vbox.getChildren().add(userNAME);
        vbox.getChildren().add(passWORD);
        vbox.getChildren().add(closeButton);
        closeButton.setOnAction(e -> {
            Username = userNAME.getText();
            Password = passWORD.getText();
            popupStage.close();
        });
        Scene popupScene = new Scene(popupContent, 300, 200);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();

    }

    public void logonButton() throws IOException {
        client.recievemessage("hi");
        Stage stagey = (Stage)logon.getScene().getWindow();
        maingui.loadMainScreen(stagey);
    }



}
