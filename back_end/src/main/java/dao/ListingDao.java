package dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dto.ListingDto;

import java.util.ArrayList;
import java.util.List;

//import jdk.internal.loader.BuiltinClassLoader;
import mongo.MongoConnection;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;

public class ListingDao extends MongoDao<ListingDto>
implements DataAccessObject<ListingDto> {

  private static ListingDao instance;


  public void bindCollection(){
    // get collection instance here
    instance.collection = mongoConnection.getCollection("collection", MongoCollection.class);

  }

  public static ListingDao getInstance(){
    if(instance == null){
      instance = new ListingDao(new MongoConnection());
      instance.bindCollection();
    }
    return instance;
  }

  // don't touch this
  public static ListingDao setTestConnection(MongoConnection connection){
    instance = new ListingDao(connection);
    instance.bindCollection();
    return instance;
  }

  ListingDao(MongoConnection connection){
    super(connection);
  }

  @Override
  public ListingDto put(ListingDto item) {
    collection.insertOne(item);
    return item;
  }

  @Override
  public List<ListingDto> getItems() {
    try {
      List<ListingDto> items = collection.find().into(new ArrayList<>());
      System.out.println(items);
      return items;
    }
    catch(Exception e){
      System.out.println("XD " + e);
      List<ListingDto> items = new ArrayList<>();
      items.add(new ListingDto("","",1,""));
      return items;
    }

  }

  @Override
  public void delete(String id) {
    // use Document to delete single item
    try {
      collection.deleteOne(eq("id", id));
    }
    catch(Exception e){

    }

  }

}
