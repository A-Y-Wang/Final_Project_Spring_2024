package GUIs;

import LibraryDatabase.Instruction;
import LibraryDatabase.LibraryUsers;
import NetworkClient.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {

    private Client client;
    private String user;
    private String passy;


//    public void setClient(Client clit){
//        this.client = clit;
//    }

    @FXML
    Button logon;

    @FXML
    Button newuser;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    VBox bookbox;


    public void initialize(){
        client = new Client();
    }


    public void newuserButton(){
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);  // Block events to other windows
        popupStage.setTitle("New User Account");

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
                Instruction test = client.processRequest(new Instruction("New User", null, NewUser));

                    switch (test.getInstruction()) {
                        case "New User Confirmed":
                            System.out.println(test.getLibraryUser());;
                            System.out.println("New User Yes");
                            break;
                        case "New User Denied":
                            System.out.println(test.getLibraryUser());;
                            System.out.println("New User No");
                            System.out.println("New User No");
                            break;
                    }

                //client.clearmessage();
            } catch (IOException ex) {}

             catch (ClassNotFoundException ex) { } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            popupStage.close();
        });
        Scene popupScene = new Scene(popupContent, 300, 200);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();

    }

    public void logonButton() throws IOException, InterruptedException, ClassNotFoundException {

        LibraryUsers login = new LibraryUsers(username.getText(), password.getText());
        Instruction test = client.processRequest(new Instruction("Login", null, login ));

            switch (test.getInstruction()) {
                case "User Exists":
                    System.out.println(test.getLibraryUser());;
                    System.out.println("Valid User");
                    user = test.getLibraryUser().getUsername();
                    passy = test.getLibraryUser().getPassword();

                    Stage stagey = (Stage)logon.getScene().getWindow();
                    MainGUI.loadMainScreen(stagey, new LibraryUsers(user,passy), client);

                    break;

                case "User Doesn't Exist":
                    System.out.println(test.getLibraryUser());;
                    System.out.println("Not Valid User");

                    username.clear();
                    password.clear();

                    Stage popupStage = new Stage();
                    popupStage.initModality(Modality.APPLICATION_MODAL);  // Block events to other windows
                    popupStage.setTitle("ERROR");
                    AnchorPane popupContent = new AnchorPane();

                    Label NOTUSER = new Label(" Invalid Login >:(\n\n **Incorrect username or password. Try again**\n\n New users click the sign up button");
                    popupContent.getChildren().add(NOTUSER);
                    Scene popupScene = new Scene(popupContent, 320, 120);
                    popupStage.setScene(popupScene);
                    popupStage.showAndWait();

                    break;
            }

        //client.clearmessage();
    }


}
