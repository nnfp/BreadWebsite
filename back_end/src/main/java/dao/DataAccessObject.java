package dao;

import dto.BaseDto;
import java.util.List;

public interface DataAccessObject<T extends BaseDto> {
  String id = "";
  static DataAccessObject getInstance(){
    throw new RuntimeException("not implemented");
  }

  T put(T item);

  List<T> getItems();

  void delete(String id);

}
