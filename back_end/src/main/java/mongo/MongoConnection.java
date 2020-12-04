package mongo;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Arrays;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.codecs.pojo.Conventions;

public class MongoConnection {
  private MongoClient mongoClient;
  private MongoDatabase database;

  public MongoConnection(){
    PojoCodecProvider provider = PojoCodecProvider.builder().
        conventions(Arrays.asList(Conventions.ANNOTATION_CONVENTION)).automatic(true).build();
    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
        fromProviders(provider));
    // open connection
    mongoClient = new MongoClient("localhost", 27017);
    // get ref to database
    database = mongoClient.getDatabase("MyDatabase");
    database = database.withCodecRegistry(pojoCodecRegistry);
  }

  public MongoCollection getCollection(String name, Class clazz) {
    return database.getCollection(name, clazz);
  }

}
