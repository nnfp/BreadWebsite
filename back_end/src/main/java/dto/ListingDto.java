package dto;

public class ListingDto extends BaseDto {

  public  String description;
  public  String type;
  public  Integer price;
  public  String title;

  public ListingDto(){
    super(null);
  }

  public ListingDto(String id, String description, String type, Integer price, String title) {
    super(id);
    this.description = description;
    this.type = type;
    this.price = price;
    this.title = title;
  }

  public ListingDto(String description, String type, Integer price, String title) {
    super(null);
    this.description = description;
    this.type = type;
    this.price = price;
    this.title = title;
  }
}
