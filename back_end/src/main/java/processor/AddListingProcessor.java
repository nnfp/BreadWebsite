package processor;

import com.google.gson.Gson;
import dto.ListingDto;
import dto.ResponseDto;
import parser.ParsedUrl;

import java.util.Date;

public class AddListingProcessor implements Processor{

  @Override
  public ResponseDto process(ParsedUrl parsedUrl, String body) {
    Gson gson = new Gson();
    listingDao.put(gson.fromJson(body, ListingDto.class));
    return new ResponseDto(new Date(), listingDao.getItems(), true);
  }
}
