package NetworkClient;

import GUIs.MainMenuController;
import GUIs.MainGUI;
import LibraryDatabase.Instruction;
import LibraryDatabase.LibraryItem;
import LibraryDatabase.LibraryUsers;
import NetworkServer.Server;
import javafx.application.Application;
import javafx.beans.binding.ObjectExpression;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Client{
    //are these supposed to all be in 1 package??

    //manages user log in retrieving books from the server??
    //connect to gui so clients can see what they retrieved from the Library
    boolean wait;
    Object syncobject = new Object();
    private Socket socket;
    ObjectOutputStream outee;
    ObjectInputStream innee;
    Object getin;
    ArrayList<LibraryItem> initialCatalog;
    MainMenuController mainController;
    private Thread listenerThread;
    Object message;
    private ArrayBlockingQueue<Object> messageQueue = new ArrayBlockingQueue<>(20);

//    public static void main (String[] args) {
//        new Client().setupNetworking();
//    }
    //make a new socket that is wrapped in a thread where it's constantly recieveing messages from the server
    //pass in a reference to the text area/catalog area of your gui to your client so that your observer thread can directly change the gui element
    //my browse function updates my catalog based off the mongoDB, this new thread should not affect it because each time I browse I still pull from mongo
    public Client(){
        try{
            socket = new Socket("localhost",1024);
            System.out.println("network established");

            outee = new ObjectOutputStream(socket.getOutputStream());
            innee = new ObjectInputStream(socket.getInputStream());
            listenerThread = new Thread(this::listenForUpdates);
            listenerThread.start();

        }catch(IOException ioe){
            System.out.println("fork");
        }
    }


    private void listenForUpdates() {
        try {
            while (true) {
                    if ((message = innee.readObject()) != null) {
                        //Object message = innee.readObject()
                        System.out.println(message);
                        if (message instanceof ArrayList) {
                            ArrayList<Object> mycatalog = (ArrayList<Object>) message;
                            if (mycatalog.get(0) instanceof LibraryItem) {
                                mainController.update((ArrayList<LibraryItem>) message);
                            }
//                            if (mycatalog.get(0) instanceof Instruction) {
//                                mainController.claimMyItems((ArrayList<Instruction>) message);
//                            }
                        } else {
                            messageQueue.put(message);
                        }
                    }
                }
            }
         catch (Exception e) {
            System.out.println("Error while listening for catalog updates from the server");
        }
    }


    //set some bools so then it determines what the gui outputs
    public Instruction processRequest(Instruction tellMe) throws IOException, ClassNotFoundException, InterruptedException {
        outee.reset();
        outee.writeObject(tellMe);
        outee.flush();
        //getin = (Instruction) innee.readObject();
        //System.out.println(getin);
        //or just return the array list???
        getin = messageQueue.take();
        return (Instruction) getin;
    }

    public void getCatalog(Instruction getlog) throws IOException, ClassNotFoundException {
        outee.reset();
        outee.writeObject(getlog);
        outee.flush();
    }

    public void getMyCatalog(Instruction getlog) throws IOException, ClassNotFoundException {
        outee.reset();
        outee.writeObject(getlog);
        outee.flush();
    }


    public void giveController(MainMenuController mainMenuController){
        this.mainController = mainMenuController;
    }




//    public void clearmessage(){
//        //returnToGUI.clear();
//    }


}

