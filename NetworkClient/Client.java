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
    Lock lock = new ReentrantLock();
    private Socket socket;
    public final ArrayList<Instruction> returnToGUI = new ArrayList<>();
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

            Thread service = new Thread(new ServerHandler(socket, outee, innee, returnToGUI));
            service.start();


        }catch(IOException ioe){
            System.out.println("fork");
        }
    }

    class ServerHandler implements Runnable {
        private Socket clientSocket;
        private ObjectOutputStream outee;
        private ObjectInputStream innee;
        Instruction getin;

        public ServerHandler(Socket clientSocket, ObjectOutputStream out, ObjectInputStream in, ArrayList<Instruction> returnToGUI) {
            this.clientSocket = clientSocket;
            this.outee = out;
            this.innee = in;

        }

        public void run() {
            while(true) {
                try {
                    lock.lock();
                        if ((getin = (Instruction) innee.readObject()) != null) {
                            returnToGUI.add(getin);
                            System.out.println(getin);
                            //System.out.println(getin);
                            //find this item
                        }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                finally {lock.unlock();}
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


    //set some bools so then it determines what the gui outputs
    public void processRequest(Instruction tellMe) throws IOException {
        outee.reset();
        outee.writeObject(tellMe);
        outee.flush();

    }

    public ArrayList<Instruction> recievemessage() throws InterruptedException {
        Thread.sleep(200);
        synchronized (lock) {
            return new ArrayList<>(returnToGUI);

        }
    }

    public void clearmessage(){
        returnToGUI.clear();
    }


}

