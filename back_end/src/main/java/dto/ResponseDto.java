package dto;

import java.util.Date;
import java.util.List;

public class ResponseDto<T extends BaseDto>{

  public final Date date;
  public final List<T> items;
  public final Boolean success;

  public ResponseDto(Date date, List<T> items, Boolean success) {
    this.date = date;
    this.items = items;
    this.success = success;
  }
}
