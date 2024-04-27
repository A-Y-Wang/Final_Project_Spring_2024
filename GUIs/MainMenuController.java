package GUIs;

import LibraryDatabase.Instruction;
import LibraryDatabase.LibraryItem;
import LibraryDatabase.LibraryUsers;
import NetworkClient.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    VBox myItems; //HERERER

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


    @FXML
    Tab Bookstab;

    @FXML
    Tab AudioBookstab;

    @FXML
    Tab GNTab;

    @FXML
    Tab MovieTab;




    public void setLibraryUserandClient(LibraryUsers libby, Client client) throws IOException, ClassNotFoundException, InterruptedException {

        //new button set on actions that pings
        this.client = client;
        this.libaryUser = libby;


        client.giveController(this);
        userLabel.setText("User: "+ libaryUser.getUsername());
        client.getCatalog(new Instruction("Catalog", null, null));
//        Thread.sleep(200);
//        client.getMyCatalog(new Instruction("My Catalog", null, libaryUser));

        //only at initialization do i need to display my current books
        //ArrayList<LibraryItem> medians

        //update(medians);

    }
    //keep a catalog of people who currently have this book and people who had this book
    public void update(ArrayList<LibraryItem> updateCatalog){
        all.addAll(updateCatalog);

        Platform.runLater(()->{

        VBox v2 = new VBox();
        VBox v3 = new VBox();
        VBox v4 = new VBox();
        VBox v5 = new VBox();

        Bookstab.setContent(v2);
        AudioBookstab.setContent(v3);
        GNTab.setContent(v4);
        MovieTab.setContent(v5);

        for(LibraryItem mediaGot:updateCatalog){

            HBox holder = new HBox();
            VBox set = new VBox();
            set.setPrefSize(300,100);
            Label nums = new Label(""+mediaGot.getAmount()); //if we feel like it we can add an amount label latter
            nums.setFont(new Font("Cambria", 13));

            Label contentlabel = new Label(mediaGot.getTitle()+"\nBy: "+mediaGot.getAuthor()
                    +"\nIllustrator: "+mediaGot.getIllustrator()+"\n"+mediaGot.getType()+"\n");//parse every \n

            contentlabel.setFont(new Font("Cambria", 13));
            set.getChildren().add(contentlabel);
            set.getChildren().add(nums);

            Button borrow = new Button("Borrow");
            borrow.setFont(new Font("Cambria", 11));
            borrow.setOnAction(e -> {
                     try {
                        client.getCatalog(new Instruction("Borrow", mediaGot, libaryUser));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                     }

                    Button returns = new Button("Return");
                    returns.setFont(new Font("Cambria", 11));

                    HBox myhold = new HBox();
                    VBox myset = new VBox();
                    myset.setPrefSize(175,100);
                    Label myLabel = new Label(mediaGot.getTitle()+"\nBy: "+mediaGot.getAuthor()
                            +"\nIllustrator: "+mediaGot.getIllustrator()+"\n"+mediaGot.getType()+"\nAmount: "+1);
                    myLabel.setFont(new Font("Cambria", 13));

                    myset.getChildren().add(myLabel);
                    myhold.getChildren().add(myset);
                    myhold.getChildren().add(returns);
                    myItems.getChildren().add(myhold);

//                    int newNum = Integer.parseInt(nums.getText()) - 1;
//                    nums.setText(newNum+"");
//                    if (newNum == 0){
//                        contentlabel.setDisable(true);
//                        nums.setDisable(true);
//                        borrow.setDisable(true);
//                    }
//                try {
//                    client.getCatalog(new Instruction("Borrow", mediaGot, libaryUser));
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                } catch (ClassNotFoundException ex) {
//                    throw new RuntimeException(ex);
//                }

                returns.setOnAction(ee->{
                    try {
                        client.getCatalog(new Instruction("Return", mediaGot, libaryUser));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                            myItems.getChildren().remove(myhold);

                });

            });
            if(mediaGot.getAmount() == 0){
                borrow.setDisable(true);
                contentlabel.setDisable(true);
                nums.setDisable(true);
            }
            holder.getChildren().add(set);
            holder.getChildren().add(borrow);
            switch(mediaGot.getType()){
                case "Book":
                    v2.getChildren().add(holder);
                    break;
                case "Audio Book":
                    v3.getChildren().add(holder);
                    break;
                case "Graphic Novel":
                    v4.getChildren().add(holder);
                    break;
                case "Movie":
                    v5.getChildren().add(holder);
                    break;

            }

        }});

    }

public void claimMyItems(ArrayList<Instruction> myOwnBooks){
    Platform.runLater(()-> {

        for (Instruction mymedia : myOwnBooks) {
            Button returns = new Button("Return");
            returns.setFont(new Font("Cambria", 11));

            HBox myhold = new HBox();
            VBox myset = new VBox();
            myset.setPrefSize(175, 100);
            Label myLabel = new Label(mymedia.getLibraryItem().getTitle() + "\nBy: " + mymedia.getLibraryItem().getAuthor()
                    + "\nIllustrator: " + mymedia.getLibraryItem().getIllustrator() + "\n" + mymedia.getLibraryItem().getType() + "\nAmount: " + 1);
            myLabel.setFont(new Font("Cambria", 13));

            returns.setOnAction(e->{
                try {
                    client.getCatalog(new Instruction("Return", mymedia.getLibraryItem(), libaryUser));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                myItems.getChildren().remove(myhold);

            });

            myset.getChildren().add(myLabel);
            myhold.getChildren().add(myset);
            myhold.getChildren().add(returns);
            myItems.getChildren().add(myhold);
        }
    });

}


    public void checkCheckBox(){
        if(AllCheck.isSelected()){

        }
        if(BookCheck.isSelected()){

        }
        if(AudioBookCheck.isSelected()){

        }
        if(GraphicCheck.isSelected()){

        }
        if(MovieCheck.isSelected()){

        }
    }
    public void showBooks(){}

    public void signoutButton(){
    }
}
