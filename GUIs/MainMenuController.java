package GUIs;

import LibraryDatabase.Instruction;
import LibraryDatabase.LibraryItem;
import LibraryDatabase.LibraryUsers;
import NetworkClient.Client;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class MainMenuController {

    private LibraryUsers libaryUser;
    private Client client;

//    public MainMenuController(LibraryUsers libraryUsers){
//        this.libaryUser = libraryUsers;
//    }

    ArrayList<LibraryItem> all = new ArrayList<>();
    ArrayList<LibraryItem> Books = new ArrayList<>();
    ArrayList<LibraryItem> AudioBooks = new ArrayList<>();
    ArrayList<LibraryItem> GraphicNovels = new ArrayList<>();
    ArrayList<LibraryItem> Movies = new ArrayList<>();


    @FXML
    Button signout;

    @FXML
    VBox bookbox;

    @FXML
    Label userLabel;

    @FXML
    ScrollPane catalogscroll;

    @FXML
    CheckBox AllCheck;

    @FXML
    CheckBox BookCheck;

    @FXML
    CheckBox AudioBookCheck;

    @FXML
    CheckBox GraphicCheck;

    @FXML
    CheckBox MovieCheck;

    public void setLibraryUser(LibraryUsers libby) throws IOException, ClassNotFoundException {
        this.libaryUser = libby;
        userLabel.setText("User: "+ libaryUser.getUsername());
    }

    public void initialize() throws IOException, ClassNotFoundException {
        client = new Client();
        ArrayList<LibraryItem> medians = client.getCatalog(new Instruction("Catalog", null, null));
        if (medians == null){
            System.out.println("empty");
        }
        //add to bookbox
        for(LibraryItem mediaGot:medians){
            all.add(mediaGot);
            switch(mediaGot.getType()){
                case "Book":
                    Books.add(mediaGot);
                    break;
                case "Audio Book":
                    AudioBooks.add(mediaGot);
                    break;
                case "Graphic Novel":
                    GraphicNovels.add(mediaGot);
                    break;
                case "Movie":
                    Movies.add(mediaGot);
                    break;
            }

            HBox holder = new HBox();
            VBox set = new VBox();
            set.setPrefSize(300,100);
            Label nums = new Label(mediaGot.getAmount());

            Label contentlabel = new Label(mediaGot.getTitle()+"\nBy: "+mediaGot.getAuthor()
                    +"\nIllustrator: "+mediaGot.getIllustrator()+"\n"+mediaGot.getType()+"\n");//parse every \n
            contentlabel.setFont(new Font("Cambria", 13));
            set.getChildren().add(contentlabel);

            Button borrow = new Button("Borrow");
            borrow.setFont(new Font("Cambria", 11));
            borrow.setOnAction(e -> {
                try {
                    HBox myhold = new HBox();
                    VBox myset = new VBox();
                    Label myLabel = new Label(mediaGot.getTitle()+"\nBy: "+mediaGot.getAuthor()
                            +"\nIllustrator: "+mediaGot.getIllustrator()+"\n"+mediaGot.getType()+"\nAmount: "+1);

                    myset.getChildren()
                    client.processRequest(new Instruction("Borrow", mediaGot, null));//just send to update
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            });

            holder.getChildren().add(set);
            holder.getChildren().add(borrow);
            bookbox.getChildren().add(holder);
        }



        //need a seperate thread that populates and updates the catalog?
        //updates how many of the books are left
        //reds or greys when there are 0 books
        //decrements on the display but does it need to decrement in the database??

    }

    public void checkCheckBox(){
        if(AllCheck.isSelected()){}
        if(BookCheck.isSelected()){}
        if(AudioBookCheck.isSelected()){}
        if(GraphicCheck.isSelected()){}
        if(MovieCheck.isSelected()){}
    }
    public void showBooks(){}

    public void signoutButton(){
    }
}
