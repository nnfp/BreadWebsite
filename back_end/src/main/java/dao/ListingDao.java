package dao;

import dto.BaseDto;
import dto.ListingDto;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListingDao implements DataAccessObject<ListingDto> {
  public List<ListingDto> storage = new ArrayList<>();
  public static ListingDao getInstance(){
    return new ListingDao();
  }

  private ListingDao(){

  }

  @Override
  public ListingDto put(ListingDto item) {
    storage.add(item);
    System.out.println(storage.get(0).id);
    return item;
  }

  @Override
  public List<ListingDto> getItems() {
    return storage;
  }

  @Override
  public void delete(String id) {
    for(int i=0;i<storage.size();i++){
      if(storage.get(i).id.equals(id)){
        storage.remove(i);
      }
    }
  }

}
