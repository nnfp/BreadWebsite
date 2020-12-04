package processor;

import parser.ParsedUrl;

public class ProcessorFactory {

  public static Processor getProcessor(ParsedUrl parsedUrl){
    switch (parsedUrl.getPath()){
      case "/api/createListing":
        return new AddListingProcessor();
      case "/api/viewListings":
        if(parsedUrl.hasQueryArgs()){
          return new FilterProcessor();
        }else{
          return new ViewListingsProcessor();
        }
      case "/api/deleteListing":
        return new DeleteListingProcessor();
      default:
        return new ErrorProcessor();
    }
  }
}
