package LibraryDatabase;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;

public class MongoDBLibrary {
    //set up mongoDB
    private static MongoClient mongo;
    private static MongoDatabase database;
    private static MongoCollection<LibraryItem> collection;
    private static MongoCollection<LibraryUsers> collection1;

    private static final String URI = "mongodb+srv://abbieywang:JavaI'mTrying888@cluster0.uc8cc1u.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DB = "library";
    private static final String COLLECTION = "libraryitems";
    private static final String COLLECTION1 = "libraryusers";
    private static final String COLLECTION2 = "coverphotos";

    //ArrayList<LibraryItem> PotenialBooks


    public static void main(String[] args) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB).withCodecRegistry(pojoCodecRegistry);
        collection = database.getCollection(COLLECTION, LibraryItem.class);
        collection1 = database.getCollection(COLLECTION1, LibraryUsers.class);


        LibraryUsers Chris = new LibraryUsers("chrispoopoo", "12345");
        collection1.insertOne(Chris);
        //addItem
        //updateItem
//      for (LibraryItem potbooks: PotenialBooks){
//           addItem(potbooks);
//        }
    }

     public static void addItem(LibraryItem items) {
        LibraryItem checkItem = collection.find(Filters.eq("title", items.getTitle())).first();
        if(checkItem == null){
            collection.insertOne(items);
            return;
        }
        items.setAmount(checkItem.getAmount()+1);
        collection.findOneAndReplace(Filters.eq("title", items.getTitle()), items);

     }

     //public static void addImage()
}
