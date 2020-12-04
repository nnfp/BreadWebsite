package processor;

import dao.ListingDao;
import dto.ResponseDto;
import parser.ParsedUrl;

public interface Processor {
  ListingDao listingDao = ListingDao.getInstance();
  public ResponseDto process(ParsedUrl parsedUrl, String body);
}
