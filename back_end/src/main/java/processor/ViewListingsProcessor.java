package processor;

import com.google.gson.Gson;
//import com.sun.tools.javac.util.List;
import dao.ListingDao;
import dto.ListingDto;
import dto.ResponseDto;
import java.util.Date;
import parser.ParsedUrl;

public class ViewListingsProcessor implements Processor{


  @Override
  public ResponseDto process(ParsedUrl parsedUrl, String body) {
    return new ResponseDto(new Date(), listingDao.getItems(), true);
  }
}
