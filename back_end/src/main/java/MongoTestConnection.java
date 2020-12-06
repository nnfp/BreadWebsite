import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoTestConnection {

    public static void main (String[]args){
        MongoClient mongoClient = new MongoClient("localhost",27017);

        MongoDatabase db = mongoClient.getDatabase("csc413finaldb");

        MongoCollection<Document> usersCollection  = db.getCollection  ("listings");

        Document doc = new Document("description :","test")
                .append("type :","test")
                .append("price :","test")
                .append("title :","test");

        usersCollection.insertOne(doc);
    }
}