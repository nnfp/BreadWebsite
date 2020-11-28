package dto;

public class ListingDto extends BaseDto {

  public final String description;
  public final String type;
  public final Integer price;
  public final String title;

  public ListingDto(String id, String description, String type, Integer price, String title) {
    super(id);
    this.description = description;
    this.type = type;
    this.price = price;
    this.title = title;
  }

  public ListingDto(String description, String type, Integer price, String title) {
    super("d");
    this.description = description;
    this.type = type;
    this.price = price;
    this.title = title;
  }
}
