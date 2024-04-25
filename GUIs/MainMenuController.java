package GUIs;

import NetworkClient.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class MainMenuController {


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
        //need a seperate thread that populates and updates the catalog?
        //updates how many of the books are left
        //reds or greys when there are 0 books
        //decrements on the display but does it need to decrement in the database??

    }

    public void signoutButton(){
        client.recievemessage("bye");
    }
}
