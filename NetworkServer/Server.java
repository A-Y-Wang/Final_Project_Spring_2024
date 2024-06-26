package NetworkServer;

import LibraryDatabase.Instruction;
import LibraryDatabase.LibraryItem;
import LibraryDatabase.LibraryUsers;
import LibraryDatabase.MongoDBLibrary;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static LibraryDatabase.MongoDBLibrary.*;
//database should store who currently has the book and then move them to who
// had that book in a different database

//Observer has a list of the clients which typically calls each client method to update the catalog with the new catalog information

public class Server {

    ServerSocket serverSocket;
    ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args){
        //make new items and add it to te database

//        ArrayList<LibraryItem> LibIt = new ArrayList<>();
//        LibraryItem HeartStopper = new LibraryItem("Graphic Novel", "HeartStopper", "Alice Oseman", "Alice Oseman");
//        LibraryItem TSOA = new LibraryItem("Book", "The Song of Achilles", "Madeline Miller", "n/a");
//        LibIt.add(HeartStopper);
//        LibIt.add(TSOA);
        //MongoDBLibrary.main(LibIt);
        MongoDBLibrary.main(new String[]{});
        new Server().setupNetworking();

    }
    List<Socket> sockets = new ArrayList<Socket>();
    List<ObjectOutputStream> outsocket = new ArrayList<>();
    //each user has a socket for which they communicate to the database
    //iterate through all the sockets and send the book -1 message

    private void setupNetworking(){
        try{
            serverSocket = new ServerSocket(1024);
            ObjectOutputStream outin;
            ObjectInputStream inout;

            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("incoming transmission");

                sockets.add(clientSocket);

                outin = new ObjectOutputStream(clientSocket.getOutputStream());
                inout = new ObjectInputStream(clientSocket.getInputStream());

                outsocket.add(outin);

                Thread clients = new Thread(new ClientHandler(clientSocket, outin, inout));
                clients.start();
            }
        }
        catch(IOException | ClassNotFoundException ioe) {
            System.out.println("grrr");
        }
    }


    //marks the book as borrowed
    class ClientHandler implements Runnable{

        private Socket clientSocket;
        private ObjectOutputStream outin;
        private ObjectInputStream inout;
        ClientHandler(Socket clientSocket, ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException {
            this.clientSocket = clientSocket;
            this.outin = out;
            this.inout = in;
        }

        public void run() {
            while(true) {
                //lock.lock();
                try {
                    Instruction recieve = (Instruction)inout.readObject();
                    switch(recieve.getInstruction()){
                        case "New User":
                            System.out.println("got a library user");

                            ArrayList<Instruction> send = new ArrayList<>();

                            System.out.println(recieve.getLibraryUser());

                            boolean confirmedNewUser = addUser(recieve.getLibraryUser());
                            if (confirmedNewUser){
                                Instruction confirm = new Instruction("New User Confirmed", null, recieve.getLibraryUser());
                                send.add(confirm);
                                outin.writeObject(send);
                                outin.flush();
                            }
                            else {
                                Instruction confirm = new Instruction("New User Denied", null, recieve.getLibraryUser());
                                send.add(confirm);
                                outin.writeObject(send);
                                outin.flush();
                            }
                            break;

                        case "Login":
                            System.out.println("attempt to login");

                            System.out.println(recieve.getLibraryUser()); //look here!!!
                            ArrayList<Instruction> go = new ArrayList<>();

                            boolean isLibraryUser = isUser(recieve.getLibraryUser());

                            if (isLibraryUser){
                                Instruction exist = new Instruction("User Exists", null, recieve.getLibraryUser());
                                go.add(exist);
                                outin.writeObject(go);
                                outin.flush();
                            }
                            else {
                                Instruction exist = new Instruction("User Doesn't Exists", null, recieve.getLibraryUser());
                                go.add(exist);
                                outin.writeObject(go);
                                outin.flush();
                            }
                            System.out.println("info sent to client");
                            break;

                        case "Catalog": //return arraylist
                            ArrayList<LibraryItem> catalog = getEntireCatalog();
                                outin.writeObject(catalog);
                                outin.flush();//}
                            break;

                        case "My Catalog":
                            ArrayList<Instruction> MYcatalog = getMyLog(recieve.getLibraryUser());
                            outin.writeObject(MYcatalog);
                            outin.flush();
                            break;

                        case "Borrow": //return arraylist
                            BorrowItem(recieve.getLibraryItem(), recieve.getLibraryUser());
                            ArrayList<LibraryItem> catalogminus = getEntireCatalog();
                            for (ObjectOutputStream oos: outsocket){
                                oos.writeObject(catalogminus);
                                oos.flush();}
                            break;

                        case "Return": //return arraylist
                            ReturnItem(recieve.getLibraryItem(), recieve.getLibraryUser());
                            ArrayList<LibraryItem> catalogplus = getEntireCatalog();
                            for (ObjectOutputStream oos: outsocket){
                                oos.writeObject(catalogplus);
                                oos.flush();};
                            break;

                        case "Reset Password":
                            resetUser(recieve.getLibraryUser());
                            Instruction place = new Instruction("reeee", null, null);
                            outin.writeObject(place);
                            outin.flush();
                            break;

                    }

                }
                catch (IOException | ClassNotFoundException ioe) {
                }
//                finally {
//                    lock.unlock();
//                }
            } //only one thread can get certain things at a time
        }
    }
}
