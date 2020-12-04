package processor;

import dto.ResponseDto;
import java.util.Date;
import parser.ParsedUrl;

public class ErrorProcessor implements Processor{
  @Override
  public ResponseDto process(ParsedUrl parsedUrl, String body) {
    return new ResponseDto(new Date(), null, false);
  }
}
