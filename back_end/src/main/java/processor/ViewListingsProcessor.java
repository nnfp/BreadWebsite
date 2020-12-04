package processor;

import com.google.gson.Gson;
//import com.sun.tools.javac.util.List;
import dao.ListingDao;
import dto.ListingDto;
import dto.ResponseDto;
import java.util.Date;
import parser.ParsedUrl;

public class ViewListingsProcessor implements Processor{

  private static Gson gson = new Gson();

  @Override
  public ResponseDto process(ParsedUrl parsedUrl, String body) { return new ResponseDto(new Date(), listDaoInst.getItems(), true);
  }
}
