package processor;

import dao.ListingDao;
import dto.ResponseDto;
import java.util.ArrayList;
import java.util.Date;
import parser.ParsedUrl;

public class DeleteListingProcessor implements Processor{

  @Override
  public ResponseDto process(ParsedUrl parsedUrl, String body) {
    String id = parsedUrl.getValue(body);
    Boolean success = true;
    try {
      listingDao.delete(id);
    }
    catch(Exception e){
      success=false;
    }
    return new ResponseDto(new Date(), listingDao.getItems(), success);
  }
}