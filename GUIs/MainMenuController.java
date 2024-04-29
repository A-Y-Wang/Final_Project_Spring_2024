package GUIs;

import LibraryDatabase.Instruction;
import LibraryDatabase.LibraryItem;
import LibraryDatabase.LibraryUsers;
import NetworkClient.Client;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainMenuController {

    private LibraryUsers libaryUser;
    private Client client;


//    public MainMenuController(LibraryUsers libraryUsers){
//        this.libaryUser = libraryUsers;
//    }

    ArrayList<LibraryItem> all = new ArrayList<>();
    ArrayList<LibraryItem> BorrowedBooks = new ArrayList<>();
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

    @FXML
    CheckBox title;
    @FXML
    CheckBox author;

    @FXML
    ImageView mediaCover;

    @FXML
    Button loaditems;

    @FXML
    CheckBox defaultcheck;

    @FXML
    TitledPane filter;

    @FXML
    TextField searchbar;

    @FXML
    Button clickHereHelp;


        URL resource = getClass().getResource("/GUIs/90s-game-ui-6-185099.mp3");
        AudioClip borrowBook = new AudioClip(resource.toString());

        URL resource1 = getClass().getResource("/GUIs/error-warning-login-denied-132113.mp3");
        AudioClip signoutsound = new AudioClip(resource1.toString());

        URL resource2 = getClass().getResource("/GUIs/bloop-1-184019.mp3");
        AudioClip returnB = new AudioClip(resource2.toString());



    public void setLibraryUserandClient(LibraryUsers libby, Client client) throws IOException, ClassNotFoundException, InterruptedException {

        //new button set on actions that pings
        this.client = client;
        this.libaryUser = libby;



        client.giveController(this);
        userLabel.setText("User: "+ libaryUser.getUsername());
        client.getCatalog(new Instruction("Catalog", null, null));
        //Thread.sleep(200);

    }

    //keep a catalog of people who currently have this book and people who had this book
    public void update(ArrayList<LibraryItem> updateCatalog){
        Platform.runLater(()->{
        all.clear();
        all.addAll(updateCatalog);
        for(LibraryItem mytem: all){
            if(!mytem.getUser().equals(""))
            BorrowedBooks.add(mytem);
        }
        VBox v2 = new VBox();
        VBox v3 = new VBox();
        VBox v4 = new VBox();
        VBox v5 = new VBox();

        Bookstab.setContent(v2);
        AudioBookstab.setContent(v3);
        GNTab.setContent(v4);
        MovieTab.setContent(v5);

        for(LibraryItem mediaGot : all){
            if(mediaGot.getUser().equals("")) {
                System.out.println(mediaGot.getTitle()+" "+mediaGot.getAmount());

                HBox holder = new HBox();
                VBox set = new VBox();
                set.setPrefSize(200, 100);
                //Label nums = new Label("" + mediaGot.getAmount()); //if we feel like it we can add an amount label latter
                //nums.setFont(new Font("Cambria", 13));

                Label contentlabel = new Label(mediaGot.getTitle() + "\nBy: " + mediaGot.getAuthor()
                        + "\nIllustrator: " + mediaGot.getIllustrator() + "\n" + mediaGot.getType() + "\n" + mediaGot.getAmount());//parse every \n

                contentlabel.setFont(new Font("Cambria", 13));
                set.getChildren().add(contentlabel);
                //set.getChildren().add(nums);

                Button borrow = new Button("Borrow");
                Button view = new Button("View");
                borrow.setFont(new Font("Cambria", 11));
                view.setFont(new Font("Cambria", 11));
                view.setOnAction(e->{
                    String imageUrl = mediaGot.getimageURL();  // Replace with an actual URL
                    // Load the image from the URL
                    Image image = new Image(imageUrl);
                    mediaCover.setImage(image);
                });
                borrow.setOnAction(e -> {
                    borrowBook.play();
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
                    myset.setPrefSize(175, 100);
                    Label myLabel = new Label(mediaGot.getTitle() + "\nBy: " + mediaGot.getAuthor()
                            + "\nIllustrator: " + mediaGot.getIllustrator() + "\n" + mediaGot.getType() + "\nAmount: " + 1);
                    myLabel.setFont(new Font("Cambria", 13));

                    myset.getChildren().add(myLabel);
                    myhold.getChildren().add(myset);
                    myhold.getChildren().add(returns);
                    myItems.getChildren().add(myhold);


                    returns.setOnAction(ee -> {
                        returnB.play();
                        mediaGot.setUser(libaryUser.getUsername());
                        try {
                            client.getCatalog(new Instruction("Return", mediaGot, libaryUser)); //medianoname
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        myItems.getChildren().remove(myhold);
                    });

                });
                if (mediaGot.getAmount() == 0) {
                    borrow.setDisable(true);
                    contentlabel.setDisable(true);
                    //nums.setDisable(true);
                }
                holder.getChildren().add(set);
                holder.getChildren().add(view);
                holder.getChildren().add(borrow);
                switch (mediaGot.getType()) {
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
            }
        }
        });

    }

public void claimMyItems() throws IOException, ClassNotFoundException, InterruptedException {


        for (LibraryItem mymedia : BorrowedBooks) {
            if (mymedia.getUser().equals(libaryUser.username)) {
                LibraryItem mine = mymedia;
                Button returns = new Button("Return");
                returns.setFont(new Font("Cambria", 11));


                HBox myhold = new HBox();
                VBox myset = new VBox();
                myset.setPrefSize(175, 100);
                Label myLabel = new Label(mine.getTitle() + "\nBy: " + mine.getAuthor()
                        + "\nIllustrator: " + mine.getIllustrator() + "\n" + mine.getType() + "\nAmount: " + mine.getAmount());
                myLabel.setFont(new Font("Cambria", 13));

                returns.setOnAction(e -> {
                    try {
                        returnB.play();
                        client.getCatalog(new Instruction("Return", mine, libaryUser)); //mine has the name
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
        }
        loaditems.setDisable(true);
//    });

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
    public void showBooks(){
        String search = searchbar.getText();
        if(defaultcheck.isSelected()){
            System.out.println("yes");
            VBox V1 = (VBox)Bookstab.getContent();
            VBox V2 = (VBox)AudioBookstab.getContent();
            VBox V3 = (VBox)GNTab.getContent();
            VBox V4 = (VBox)MovieTab.getContent();
            V1.getChildren().stream()
                    .filter(child -> child instanceof HBox)
                    .map(child -> (HBox) child)
                    .forEach(hbox -> hbox.getChildren().stream()
                            .filter(hboxChild -> hboxChild instanceof VBox)
                            .map(hboxChild -> (VBox) hboxChild)
                            .forEach(innerVBox -> innerVBox.getChildren().stream()
                                    .filter(innerVBoxChild -> innerVBoxChild instanceof Label)
                                    .map(innerVBoxChild -> (Label) innerVBoxChild)
                                    .forEach(label -> {
                                        System.out.println(label.getText());
                                        if(!label.getText().contains(search)){
                                            hbox.setVisible(false);
                                                }
                                        else if(label.getText().contains(search)){
                                            hbox.setVisible(true);
                                        }
                                            }
                                        )
                            )
                    );
            V2.getChildren().stream()
                    .filter(child -> child instanceof HBox)
                    .map(child -> (HBox) child)
                    .forEach(hbox -> hbox.getChildren().stream()
                            .filter(hboxChild -> hboxChild instanceof VBox)
                            .map(hboxChild -> (VBox) hboxChild)
                            .forEach(innerVBox -> innerVBox.getChildren().stream()
                                    .filter(innerVBoxChild -> innerVBoxChild instanceof Label)
                                    .map(innerVBoxChild -> (Label) innerVBoxChild)
                                    .forEach(label -> {
                                                System.out.println(label.getText());
                                                if(!label.getText().contains(search)){
                                                    hbox.setVisible(false);
                                                }
                                                else if(label.getText().contains(search)){
                                                    hbox.setVisible(true);
                                                }
                                            }
                                    )
                            )
                    );
            V3.getChildren().stream()
                    .filter(child -> child instanceof HBox)
                    .map(child -> (HBox) child)
                    .forEach(hbox -> hbox.getChildren().stream()
                            .filter(hboxChild -> hboxChild instanceof VBox)
                            .map(hboxChild -> (VBox) hboxChild)
                            .forEach(innerVBox -> innerVBox.getChildren().stream()
                                    .filter(innerVBoxChild -> innerVBoxChild instanceof Label)
                                    .map(innerVBoxChild -> (Label) innerVBoxChild)
                                    .forEach(label -> {
                                                System.out.println(label.getText());
                                                if(!label.getText().contains(search)){
                                                    hbox.setVisible(false);
                                                }
                                                else if(label.getText().contains(search)){
                                                    hbox.setVisible(true);
                                                }
                                            }
                                    )
                            )
                    );
            V4.getChildren().stream()
                    .filter(child -> child instanceof HBox)
                    .map(child -> (HBox) child)
                    .forEach(hbox -> hbox.getChildren().stream()
                            .filter(hboxChild -> hboxChild instanceof VBox)
                            .map(hboxChild -> (VBox) hboxChild)
                            .forEach(innerVBox -> innerVBox.getChildren().stream()
                                    .filter(innerVBoxChild -> innerVBoxChild instanceof Label)
                                    .map(innerVBoxChild -> (Label) innerVBoxChild)
                                    .forEach(label -> {
                                                System.out.println(label.getText());
                                                if(!label.getText().contains(search)){
                                                    hbox.setVisible(false);
                                                }
                                                else if(label.getText().contains(search)){
                                                    hbox.setVisible(true);
                                                }
                                            }
                                    )
                            )
                    );
            }

        }

    public void signoutButton() {
        signoutsound.play();

        Button yes = new Button("Yes");
        Button no = new Button("No");

        HBox help = new HBox();
        help.setSpacing(10);
        help.getChildren().add(yes);
        help.getChildren().add(no);

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);  // Block events to other windows
        popupStage.setTitle("Sign Out Window");
        AnchorPane popupContent = new AnchorPane();
        VBox leave = new VBox();
        popupContent.getChildren().add(leave);

        Label getout = new Label("Are you sure you want to sign out?");
        leave.getChildren().add((getout));
        leave.getChildren().add(help);

        no.setOnAction(e ->
                popupStage.close());

        yes.setOnAction(ee -> {
            popupStage.close();
            Platform.exit();
            System.exit(0);
        });

        Scene popupScene = new Scene(popupContent, 320, 120);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    public void titleButton(){
        if(title.isSelected()){
            filter.setText("Title");
        }
        if(!title.isSelected()){
            filter.setText("Default");
        }
    }
    public void authorbutton(){
        if(title.isSelected()){
            filter.setText("Author");
        }
        if(!title.isSelected()){
            filter.setText("Default");
        }
    }

    public void defaultbutton(){

    }

    public void animation() throws IOException {

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);  // Block events to other windows
        popupStage.setTitle("Help Desk");
        AnchorPane popupContent = new AnchorPane();
        VBox v = new VBox();
        Label sorry = new Label("Sorry the software developer was lazy :(");
        v.getChildren().add(sorry);
        FileInputStream inputstream = new FileInputStream("C:\\422C\\Final Project\\GUIs\\genshin-impact.gif");
        Image image = new Image(inputstream);
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);

        v.getChildren().add(imageView);
        popupContent.getChildren().add(v);
        Scene popupScene = new Scene(popupContent, 400, 420);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();

    }


}
