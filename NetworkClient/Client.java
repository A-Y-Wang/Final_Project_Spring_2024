package NetworkClient;

import GUIs.ClientController;
import GUIs.MainGUI;
import LibraryDatabase.LibraryItem;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client{
    //are these supposed to all be in 1 package??

    //manages user log in retrieving books from the server??
    //connect to gui so clients can see what they retrieved from the Library
    private Socket socket;


//    public static void main (String[] args) {
//        new Client().setupNetworking();
//    }
    public Client(){
        try{
            socket = new Socket("localhost",1024);
            System.out.println("network established");;
        }catch(IOException ioe){
            System.out.println("fork");
        }
    }

//    public void setupNetworking(){
//        try{
//                socket = new Socket("localhost", 1024);
//                System.out.println("network established");
////            newGUI.start(mainstage);
//                // MainGUI.main(new String[] {"poop"});
//
////            LibraryItem ent = new LibraryItem("Book", "The Song of Achilles", "Madeline Miller", "n/a");
////            System.out.println("Sending book: " + ent);
////            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
////            oos.writeObject(ent);
////            oos.flush();
//        }
//        catch (IOException ioe){
//            ioe.printStackTrace();
//            System.out.println("oh no");
//        }
//    }

    public void sendMessage(String message) throws IOException {
        System.out.println(message);
        LibraryItem ent = new LibraryItem("Book", "The Song of Achilles", "Madeline Miller", "n/a");
        System.out.println("Sending book: " + ent);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(ent);
        oos.flush();

    }
    public void recievemessage(String message){
        System.out.println(message);
    }


}
