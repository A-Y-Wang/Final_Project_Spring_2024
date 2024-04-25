package NetworkClient;

import GUIs.ClientController;
import GUIs.MainGUI;
import LibraryDatabase.LibraryItem;
import LibraryDatabase.LibraryUsers;
import NetworkServer.Server;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import static LibraryDatabase.MongoDBLibrary.addItem;
import static LibraryDatabase.MongoDBLibrary.addUser;

public class Client{
    //are these supposed to all be in 1 package??

    //manages user log in retrieving books from the server??
    //connect to gui so clients can see what they retrieved from the Library
    private Socket socket;
    ObjectOutputStream outee;
    ObjectInputStream innee;


//    public static void main (String[] args) {
//        new Client().setupNetworking();
//    }
    public Client(){
        try{
            socket = new Socket("localhost",1024);
            System.out.println("network established");

            outee = new ObjectOutputStream(socket.getOutputStream());
            innee = new ObjectInputStream(socket.getInputStream());

            Thread service = new Thread(new ServerHandler(socket, outee, innee));
            service.start();


        }catch(IOException ioe){
            System.out.println("fork");
        }
    }

    class ServerHandler implements Runnable {
        private Socket clientSocket;
        private ObjectOutputStream outee;
        private ObjectInputStream innee;
        Object getin;

        public ServerHandler(Socket clientSocket, ObjectOutputStream out, ObjectInputStream in) {
            this.clientSocket = clientSocket;
            this.outee = out;
            this.innee = in;

        }

        public void run() {
            while(true) {
                try {
                    if ((getin = (String) innee.readObject()) != null) {
                        System.out.println(getin);

                        //find this item
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }



//    public void setupNetworking(){
//        try{
//                socket = new Socket("localhost", 1024);
//                System.out.println("network established");
////            newGU.start(mainstage);
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
        LibraryItem ent = new LibraryItem("Book", "The Song of Achilles", "Madeline Miller", "n/a", -1);
        System.out.println("Sending book: " + ent);
        outee.writeObject(ent);
        outee.flush();

    }
    //set some bools so then it determines what the gui outputs
    public void addNewUser(LibraryUsers usa) throws IOException {
        System.out.printf("New User!! "+ usa);
        outee.reset();
        outee.writeObject(usa);
        outee.flush();

    }

//    public boolean doesUserExist(LibraryUsers ussy){
//
//    }

    public void recievemessage(String message){
        System.out.println(message);
    }


}

