package processor;

import dao.ListingDao;
import dto.ResponseDto;
import java.util.ArrayList;
import java.util.Date;
import mongo.MongoConnection;
import parser.ParsedUrl;

public class DeleteListingProcessor implements Processor{

  @Override
  public ResponseDto process(ParsedUrl parsedUrl, String body) {
    String id = parsedUrl.getValue("id");
    listDaoInst.delete(id);
    return new ResponseDto(new Date(), listDaoInst.getItems(), true);
  }
}