package GUIs;

import NetworkClient.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


public class ClientController {


    private Client client;
    private MainGUI maingui;

    public void setMainApp(MainGUI maingui){
        this.maingui = maingui;
    }
    public void setClient(Client clit){
        this.client = clit;
    }

    @FXML
    Button signout;


    public void initialize(){

    }

    public void signoutButton(){
        client.recievemessage("bye");
    }
}
