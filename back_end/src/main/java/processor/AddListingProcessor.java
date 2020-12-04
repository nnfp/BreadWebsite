package processor;

import com.google.gson.Gson;
//import com.sun.tools.javac.util.List;
import dao.ListingDao;
import dto.ListingDto;
import dto.ResponseDto;
import java.util.Date;
import parser.ParsedUrl;

public class AddListingProcessor implements Processor{

  //private static Gson gson = new Gson();

  @Override
  public ResponseDto process(ParsedUrl parsedUrl, String body) {
    Gson gson = new Gson();
    listingDao.put(gson.fromJson(body, ListingDto.class));
    System.out.println(body);
    return new ResponseDto(new Date(), listingDao.getItems(), true);
  }
}
