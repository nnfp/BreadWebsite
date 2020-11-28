package processor;

import dto.ResponseDto;
import parser.ParsedUrl;

import java.util.Date;

public class ViewListingsProcessor implements Processor{

  @Override
  public ResponseDto process(ParsedUrl parsedUrl, String body) {

    return new ResponseDto(new Date(), listingDao.getItems(), true);
  }
}
