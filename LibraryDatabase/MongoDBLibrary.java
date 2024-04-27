package LibraryDatabase;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.client.*;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

import javax.print.Doc;
import java.util.ArrayList;

public class MongoDBLibrary {
    //set up mongoDB
    private static MongoClient mongo;
    private static MongoDatabase database;
    private static MongoCollection<LibraryItem> collection;
    private static MongoCollection<LibraryUsers> collection1;
    private static MongoCollection<LibraryItem> collection2;


    private static final String URI = "mongodb+srv://abbieywang:JavaI'mTrying888@cluster0.uc8cc1u.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DB = "library";
    private static final String COLLECTION = "libraryitems";
    private static final String COLLECTION1 = "libraryusers";
    private static final String COLLECTION2 = "pastlog";

//pass Instruction with library user and the library Item and add it to the DB, I think that works???
    public static void main(String[] args) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB).withCodecRegistry(pojoCodecRegistry);
        collection = database.getCollection(COLLECTION, LibraryItem.class);
        collection1 = database.getCollection(COLLECTION1, LibraryUsers.class);
        collection2 = database.getCollection(COLLECTION2, LibraryItem.class);

        //collection1.insertOne(new LibraryUsers("matthew", "xu"));

//        LibraryItem TSOA = new LibraryItem("Book", "The Song of Achillies", "Madeline Miller", "n/a", 1);
//        LibraryItem HeartStopper = new LibraryItem("Graphic Novel", "Heart Stopper 1", "Alice Oseman", "Alice Oseman", 1);
//        LibraryItem Circe = new LibraryItem("Book", "Circe", "Madeline Miller", "n/a", 1);
//
//        LibraryItem SA = new LibraryItem("Movie", "Spirited Away", "Hayao Miyazaki", "n/a", 1);
//        LibraryItem HP = new LibraryItem("Audio Book", "Pachinko", "Min Jin Lee", "n/a", 1);
//        LibraryItem PJ = new LibraryItem("Book", "Percy Jackson and the Olympians", "Rick Riordan", "John Rocco", 1);
//
//        addItem(TSOA);
//        addItem(HeartStopper);
//        addItem(Circe);
//        addItem(SA);
//        addItem(HP);
//        addItem(PJ);
//
//        LibraryUsers Matthew = new LibraryUsers("anniewanger", "meow");
////
//        LibraryUsers Chris = new LibraryUsers("mamayang", "mother");
//        collection1.insertOne(Chris);
//        collection1.insertOne(Matthew);
//
//        LibraryItem TSOA = new LibraryItem("Book", "The Song of Achillies", "Madeline Miller", "n/a", 5);
//        LibraryItem HeartStopper = new LibraryItem("Graphic Novel", "Heart Stopper 1", "Alice Oseman", "Alice Oseman", 2);
//        LibraryItem Circe = new LibraryItem("Book", "Circe", "Madeline Miller", "n/a", 1);
//
//        LibraryItem ITS = new LibraryItem("Movie", "Into the Spiderverse 1", "Peter Ramsey", "n/a", 2);
//        LibraryItem HP = new LibraryItem("Audio Book", "Pachinko", "Min Jin Lee", "n/a", 1);
//        LibraryItem PJ = new LibraryItem("Book", "Percy Jackson and the Olympians", "Rick Riordan", "John Rocco", 3);
////
//        addItem(ITS);
//        addItem(HeartStopper);
//        addItem(Circe);
//        addItem(SA);
//        addItem(HP);
//        addItem(PJ);


    }
    //change to documents

     public static void addItem(LibraryItem items) {
         Bson filter = Filters.and(
                 Filters.eq("title", items.getTitle()),
                 Filters.eq("type", items.getType())
         );

         LibraryItem check = collection.find(filter).first();
         if (check == null) {
             collection.insertOne(items);
             return;
         }
         else{
             items.setAmount(check.getAmount()+items.getAmount());
             collection.findOneAndReplace(filter,items);
         }

     }


     public static boolean addUser(LibraryUsers libuser){
         LibraryUsers checkUser = collection1.find(Filters.eq("username", libuser.getUsername())).first();

         System.out.println(checkUser);
         if(checkUser== null){
             collection1.insertOne(libuser);
             return true;
         }
         return false; //returns false if the user is already in the database
         //make a popup for that maybe

     }

     public static boolean isUser(LibraryUsers libussy){
         LibraryUsers checkUser = collection1.find(Filters.and(
                 Filters.eq("username", libussy.getUsername()),
                 Filters.eq("password", libussy.getPassword())
         )).first();

         System.out.println(checkUser);
         if(checkUser == null){
             return false;
         }
         return true;
     }

     public static ArrayList<LibraryItem> getEntireCatalog(){
        ArrayList<LibraryItem> returnCatalog = new ArrayList<>();

        FindIterable<LibraryItem> libItems = collection.find();
        for(LibraryItem lib :libItems){
            returnCatalog.add(lib);
            System.out.println(lib);
        }
        return returnCatalog;

     }

    public static void BorrowItem(LibraryItem items, LibraryUsers user) {
        //items.setAmount(1);
        //collection2.insertOne(new Instruction("Currently Borrowing", items, user));

        Bson filter = Filters.and(
                Filters.eq("title", items.getTitle()),
                Filters.eq("type", items.getType())
        );

        LibraryItem checkItem = collection.find(Filters.and(
                Filters.eq("title", items.getTitle()),
                Filters.eq("type", items.getType())
        )).first();

        int newAmount = checkItem.getAmount()-1;
        items.setAmount(newAmount);

        collection.findOneAndReplace(filter,items);

     //public static void addImage()
    }

    public static void ReturnItem(LibraryItem items, LibraryUsers user){
        items.setAmount(1);
        //collection3.insertOne(new Instruction("History/Returned", items, user));

        Bson filter1 = Filters.and(
                Filters.eq("libraryItem", items),
                Filters.eq("thisUser", user)
        );

        collection2.deleteOne(filter1);

        Bson filter = Filters.and(
                Filters.eq("title", items.getTitle()),
                Filters.eq("type", items.getType())
        );

        LibraryItem checkItem = collection.find(filter).first();

        int newAmount = checkItem.getAmount()+1;
        items.setAmount(newAmount);

        collection.findOneAndReplace(filter,items);

    }
    public static void resetUser(LibraryUsers libaryU){
        collection1.findOneAndReplace(Filters.eq("username", libaryU.getUsername()), libaryU);
        System.out.println(libaryU);

    }

    public static ArrayList<Instruction> getMyLog(LibraryUsers user){
//        ArrayList<Instruction> returnMyCatalog = new ArrayList<>();
//
//        //FindIterable<Instruction> myItems = collection2.find();
//        //for(Instruction mine : myItems) {
//            if (mine.getLibraryUser() == user) {
//                returnMyCatalog.add(mine);
//                System.out.println(mine);
//            }
//        }
//        return returnMyCatalog;
        return null;

    }
}