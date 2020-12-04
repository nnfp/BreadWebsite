package pointofsale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.ResponseDto;
import javax.inject.Inject;
import parser.ParsedUrl;
import processor.Processor;
import processor.ProcessorFactory;

public class ListingService {

  private ProcessorFactory processorFactory;
  private static ListingService instance;
  private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

  private ListingService(ProcessorFactory processorFactory) {
    this.processorFactory = processorFactory;
  }

  public static ListingService getInstance(){
    return getInstance(new ProcessorFactory());
  }

  public static ListingService getInstance(ProcessorFactory factory){
    if(instance == null){
      instance = new ListingService(factory);
    }
    return instance;
  }

  public String restApi(String url, String body){
    try {
      ParsedUrl parse = new ParsedUrl(url);
      System.out.println(parse.getPath());
      Gson gson = new Gson();
      System.out.println(body);
      ResponseDto res = ProcessorFactory.getProcessor(parse).process(parse, body);
      System.out.println("XD" + res);
      return gson.toJson(res);
    }
    catch(Exception e){
      System.out.println(e);
      return "ERROR";
    }

  }

}
