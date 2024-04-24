package GUIs;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController{

    @FXML
    Button logon;


    public void logonButton() throws IOException {
        Stage stagey = (Stage)logon.getScene().getWindow();
        MainGUI.loadMainScreen(stagey);
    }



}
