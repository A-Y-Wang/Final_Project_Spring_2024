package Network;

import LibraryDatabase.LibraryItem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static LibraryDatabase.MongoDBLibrary.addItem;

public class Server {

    public static void main(String[] args){
        new Server().setupNetworking();

    }

    List<Socket> sockets = new ArrayList<Socket>();
    //each user has a socket for which they communicate to the database


    private void setupNetworking(){
        try{
            ServerSocket server = new ServerSocket(1024);
            while(true){
                Socket clientSocket = server.accept();
                System.out.println("incoming transmission");

                sockets.add(clientSocket);

                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
            }
        }
        catch(IOException ioe) {
            System.out.println("grrr");
        }
    }

    //marks the book as borrowed
    class ClientHandler implements Runnable{

        private Socket clientSocket;

        ClientHandler(Socket clientSocket){
            this.clientSocket = clientSocket;
        }
        public void run(){
            try{
                LibraryItem item = (LibraryItem) (new ObjectInputStream(clientSocket.getInputStream()).readObject());
                System.out.println("Got the Book: " + item);
                //find this item
            }
            catch(IOException ioe) {}

            catch(ClassNotFoundException cnfe){}
        }
    }
}
