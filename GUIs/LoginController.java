package GUIs;

import LibraryDatabase.Instruction;
import LibraryDatabase.LibraryUsers;
import NetworkClient.Client;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController{

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

    @FXML
    TextField username;

    @FXML
    PasswordField password;



    public void newuserButton(){
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);  // Block events to other windows
        popupStage.setTitle("New User");

        // Create a button to close the popup
        Label label = new Label("Not a current user? Set up an account!");
        Button closeButton = new Button("Enter");
        TextField userNAME = new TextField();
        TextField passWORD = new TextField();
        VBox vbox = new VBox();
        userNAME.setPromptText("Username");
        passWORD.setPromptText("Password");

        // Layout for the popup
        StackPane popupContent = new StackPane();
        popupContent.getChildren().add(vbox);
        vbox.getChildren().add(label);
        vbox.getChildren().add(userNAME);
        vbox.getChildren().add(passWORD);
        vbox.getChildren().add(closeButton);
        closeButton.setOnAction(e -> {
            try {
                LibraryUsers NewUser = new LibraryUsers(userNAME.getText(), passWORD.getText());
                client.processRequest(new Instruction("New User", null, NewUser));
                ArrayList<Instruction> test = client.recievemessage();
                if (test.isEmpty()){
                    System.out.println("empty");
                }
                for (Instruction testInstruction: test) {
                    switch (testInstruction.getInstruction()) {
                        case "New User Confirmed":
                            System.out.println(testInstruction.getLibraryUser());;
                            System.out.println("New User Yes");
                            break;
                        case "New User Denied":
                            System.out.println(testInstruction.getLibraryUser());;
                            System.out.println("New User No");
                            break;
                    }
                }
                client.clearmessage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            popupStage.close();
        });
        Scene popupScene = new Scene(popupContent, 300, 200);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();

    }

    public void logonButton() throws IOException, InterruptedException {

        LibraryUsers login = new LibraryUsers(username.getText(), password.getText());
        client.processRequest(new Instruction("Login", null, login ));
        ArrayList<Instruction> test = client.recievemessage();
        if (test.isEmpty()){
            System.out.println("empty");
        }
        for (Instruction testInstruction: test) {
            switch (testInstruction.getInstruction()) {
                case "User Exists":
                    System.out.println(testInstruction.getLibraryUser());;
                    System.out.println("Valid User");
                    Stage stagey = (Stage)logon.getScene().getWindow();
                    maingui.loadMainScreen(stagey);
                    break;
                case "User Doesn't Exist":
                    System.out.println(testInstruction.getLibraryUser());;
                    System.out.println("Not Valid User");
                    break;
            }
        }
        client.clearmessage();
    }
}
