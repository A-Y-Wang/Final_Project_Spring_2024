package NetworkClient;

import GUIs.MainMenuController;
import GUIs.MainGUI;
import LibraryDatabase.Instruction;
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
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static LibraryDatabase.MongoDBLibrary.addItem;
public class Client{
    //are these supposed to all be in 1 package??

    //manages user log in retrieving books from the server??
    //connect to gui so clients can see what they retrieved from the Library
    private Socket socket;
    ObjectOutputStream outee;
    ObjectInputStream innee;
    Instruction getin;
    ArrayList<LibraryItem> initialCatalog;

//    public static void main (String[] args) {
//        new Client().setupNetworking();
//    }
    public Client(){
        try{
            socket = new Socket("localhost",1024);
            System.out.println("network established");

            outee = new ObjectOutputStream(socket.getOutputStream());
            innee = new ObjectInputStream(socket.getInputStream());

        }catch(IOException ioe){
            System.out.println("fork");
        }
    }
    //set some bools so then it determines what the gui outputs
    public Instruction processRequest(Instruction tellMe) throws IOException, ClassNotFoundException {
        outee.reset();
        outee.writeObject(tellMe);
        outee.flush();
        getin = (Instruction) innee.readObject();
        //or just return the array list???
        return getin;
    }

    public ArrayList<LibraryItem> getCatalog(Instruction getlog) throws IOException, ClassNotFoundException {
        outee.reset();
        outee.writeObject(getlog);
        outee.flush();
        initialCatalog = (ArrayList<LibraryItem>) innee.readObject(); //holy shit plz work
        return initialCatalog;
    }

    public void clearmessage(){
        //returnToGUI.clear();
    }


}

