package processor;

import com.google.gson.Gson;
//import com.sun.tools.javac.util.List;
import dao.ListingDao;
import dto.ListingDto;
import dto.ResponseDto;
import java.util.Date;
import parser.ParsedUrl;

public class AddListingProcessor implements Processor{

  private static Gson gson = new Gson();

  @Override
  public ResponseDto process(ParsedUrl parsedUrl, String body) {
    Gson gson = new Gson();
    ListingDto list = gson.fromJson(body, ListingDto.class);
    listDaoInst.put(list);
    return new ResponseDto(new Date(), listDaoInst.getItems(), listDaoInst.getItems().size() > 0);
  }
}
