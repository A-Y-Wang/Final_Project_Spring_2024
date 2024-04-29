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
    private static MongoCollection<LibraryItem> collection3;


    private static final String URI = "mongodb+srv://abbieywang:JavaI'mTrying888@cluster0.uc8cc1u.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DB = "library";
    private static final String COLLECTION = "libraryitems";
    private static final String COLLECTION1 = "libraryusers";
    private static final String COLLECTION3 = "pastlog";
    private static final String COLLECTION2 = "currentlog";


//pass Instruction with library user and the library Item and add it to the DB, I think that works???
    public static void main(String[] args) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB).withCodecRegistry(pojoCodecRegistry);
        collection = database.getCollection(COLLECTION, LibraryItem.class);
        collection1 = database.getCollection(COLLECTION1, LibraryUsers.class);
        collection2 = database.getCollection(COLLECTION2, LibraryItem.class);
        collection3 = database.getCollection(COLLECTION3, LibraryItem.class);

        //collection1.insertOne(new LibraryUsers("matthew", "xu"));

//        LibraryItem TSOA = new LibraryItem("","https://m.media-amazon.com/images/I/81msb6gUBTL._AC_UF1000,1000_QL80_DpWeblab_.jpg","Book", "The Song of Achillies", "Madeline Miller", "n/a", 1);
//        LibraryItem HeartStopper = new LibraryItem("","https://m.media-amazon.com/images/I/518iADaz0bL.jpg","Graphic Novel", "Heart Stopper V1", "Alice Oseman", "Alice Oseman", 1);
//        LibraryItem HeartStopper2 = new LibraryItem("","https://m.media-amazon.com/images/I/81k3L0vhJdL._AC_UF1000,1000_QL80_DpWeblab_.jpg","Graphic Novel", "Heart Stopper V2", "Alice Oseman", "Alice Oseman", 1);
//        LibraryItem HeartStopper3 = new LibraryItem("","https://m.media-amazon.com/images/I/71TJgLmYmmL._AC_UF1000,1000_QL80_.jpg","Graphic Novel", "Heart Stopper V3", "Alice Oseman", "Alice Oseman", 1);
//        LibraryItem HeartStopper4 = new LibraryItem("","https://m.media-amazon.com/images/I/81zFbAg92IL._AC_UF1000,1000_QL80_.jpg","Graphic Novel", "Heart Stopper V4", "Alice Oseman", "Alice Oseman", 2);
//        LibraryItem HeartStopper5 = new LibraryItem("","https://m.media-amazon.com/images/I/81rugFovc1L._AC_UF1000,1000_QL80_.jpg","Graphic Novel", "Heart Stopper V5", "Alice Oseman", "Alice Oseman", 1);
//        LibraryItem Circe = new LibraryItem("","https://m.media-amazon.com/images/I/B1eAVSHxJ4L._AC_UF1000,1000_QL80_DpWeblab_.jpg" , "Book", "Circe", "Madeline Miller", "n/a", 5);
//        LibraryItem TBT = new LibraryItem("", "https://m.media-amazon.com/images/I/914cHl9v7oL._AC_UF1000,1000_QL80_.jpg", "Audio Book", "The Book Thief", "Markus Zusak", "n/a", 3);
//
//
//        LibraryItem SA = new LibraryItem("","https://m.media-amazon.com/images/M/MV5BMjlmZmI5MDctNDE2YS00YWE0LWE5ZWItZDBhYWQ0NTcxNWRhXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg","Movie", "Spirited Away", "Hayao Miyazaki", "n/a", 3);
//        LibraryItem KFP = new LibraryItem("", "https://m.media-amazon.com/images/M/MV5BODJkZTZhMWItMDI3Yy00ZWZlLTk4NjQtOTI1ZjU5NjBjZTVjXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_.jpg", "Movie", "Kung Fu Panda", "Mark Osborne", "n/a", 4);
//        LibraryItem HP = new LibraryItem("","https://m.media-amazon.com/images/I/71CN89tqB2L._AC_UF1000,1000_QL80_.jpg","Audio Book", "Pachinko", "Min Jin Lee", "n/a", 2);
//        LibraryItem PJ = new LibraryItem("","https://m.media-amazon.com/images/I/51i4PF1DMYL._AC_UF1000,1000_QL80_.jpg","Book", "Percy Jackson and the Olympians", "Rick Riordan", "John Rocco", 1);
//
//        addItem(TSOA);
//        addItem(HeartStopper);
//        addItem(HeartStopper2);
//        addItem(HeartStopper3);
//        addItem(HeartStopper4);
//        addItem(HeartStopper5);
//        addItem(Circe);
//        addItem(SA);
//        addItem(HP);
//        addItem(PJ);
//        addItem(TBT);
//        addItem(KFP);
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
        Bson filter = Filters.and(
                Filters.eq("title", items.getTitle()),
                Filters.eq("type", items.getType()),
                Filters.eq("user", "")
        );
        LibraryItem checkItem = collection.find(Filters.and(
                Filters.eq("title", items.getTitle()),
                Filters.eq("type", items.getType()),
                Filters.eq("user", "")

        )).first();
        checkItem.setAmount(checkItem.getAmount()-1);
        collection.findOneAndReplace(filter,checkItem);

        items.setAmount(1);
        items.setUser(user.getUsername());
        collection2.insertOne(items);
        collection.insertOne(items);


     //public static void addImage()
    }

    public static void ReturnItem(LibraryItem items, LibraryUsers user){
        Bson filter1 = Filters.and(
                Filters.eq("title", items.getTitle()),
                Filters.eq("type", items.getType()),
                Filters.eq("user", user.getUsername())
        );

        collection2.deleteOne(filter1);
        collection.deleteOne(filter1);

        Bson filter = Filters.and(
                Filters.eq("title", items.getTitle()),
                Filters.eq("type", items.getType()),
                Filters.eq("user", "")

        );

        LibraryItem checkItem = collection.find(filter).first();
        int newAmount = checkItem.getAmount()+1;
        checkItem.setAmount(newAmount);
        collection.findOneAndReplace(filter,checkItem);

        checkItem.setUser(user.getUsername());
        checkItem.setAmount(1);
        collection3.insertOne(checkItem);;

    }
    public static void resetUser(LibraryUsers libaryU){
        collection1.findOneAndReplace(Filters.eq("username", libaryU.getUsername()), libaryU);
        System.out.println(libaryU);

    }

    public static ArrayList<Instruction> getMyLog(LibraryUsers user){
        ArrayList<Instruction> returnMyCatalog = new ArrayList<>();

//        System.out.println("bitchface");
//
//        FindIterable<Instruction> myItems = collection2.find();
//        System.out.println("cry");
//        for(Instruction mine : myItems) {
//            System.out.println(mine.getLibraryUser());
//            System.out.println(user);
//            if (mine.getLibraryUser() == user) {
//                returnMyCatalog.add(mine);
//                System.out.println(mine.getLibraryItem());
//            }
//        }
        return returnMyCatalog;
    }
}