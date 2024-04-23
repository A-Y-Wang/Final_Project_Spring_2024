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

public class MongoDBLibrary {
    //set up mongoDB
    private static MongoClient mongo;
    private static MongoDatabase database;
    private static MongoCollection<LibraryItem> collection;

    private static final String URI = "mongodb+srv://abbieywang:JavaI'mTrying888@cluster0.uc8cc1u.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DB = "library";
    private static final String COLLECTION = "libraryitems";

    public static void main(String[] args) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB).withCodecRegistry(pojoCodecRegistry);
        collection = database.getCollection(COLLECTION, LibraryItem.class);
        //addItem
        //updateItem
        LibraryItem HeartStopper = new LibraryItem("Graphic Novel", "HeartStopper", "Alice Oseman", "Alice Oseman");
        addItem(HeartStopper);
        LibraryItem TSOA = new LibraryItem("Book", "The Song of Achilles", "Madeline Miller", "n/a");
        addItem(TSOA);
    }

     public static void addItem(LibraryItem items) {
        LibraryItem checkItem = collection.find(Filters.eq("author", items.getAuthor())).first();
        if(checkItem == null){
            collection.insertOne(items);
            return;
        }
        items.setAmount(checkItem.getAmount()+1);
        collection.findOneAndReplace(Filters.eq("author", items.getAuthor()), items);

     }

}
