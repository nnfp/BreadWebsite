package dao;

import dto.ListingDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import mongo.MongoConnection;
import org.bson.Document;
import org.bson.conversions.Bson;

public class ListingDao extends MongoDao<ListingDto>
implements DataAccessObject<ListingDto> {
  private static ListingDao instance;
  private List<ListingDto> arrList = new ArrayList<>();
  public void bindCollection(){
    // get collection instance here
    // set the mongoCollection to take in ListingDto objects
    collection = mongoConnection.getCollection("collection", ListingDto.class); //
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
    arrList.add(item);
    collection.insertOne(item);
    return item;
  }

  @Override
  public List<ListingDto> getItems() {

    try {
      return collection.find()
              .into(new ArrayList<>());
    } catch (Exception e) {
      return arrList;
    }
  }

  @Override
  public void delete(String id) {
    // use Document to delete single item
    Bson deleteId = new Document("entryId",id);
    collection.deleteOne(deleteId);

    arrList = arrList.stream()
            .filter(entryId -> entryId.equals(id))
            .collect(Collectors.toList());
  }

}
