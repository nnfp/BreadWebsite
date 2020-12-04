package processor;

import dao.ListingDao;
import dto.ListingDto;
import dto.ResponseDto;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import parser.ParsedUrl;

public class FilterProcessor implements Processor{

  @Override
  public ResponseDto process(ParsedUrl parsedUrl, String body) {
    return new ResponseDto(null, null, true);
  }
}
