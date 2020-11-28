package processor;

import parser.ParsedUrl;

public class ProcessorFactory {

  public static Processor getProcessor(ParsedUrl parsedUrl){
    if(parsedUrl.getPath().contains("viewListings")){
      return new ViewListingsProcessor();
    }
    if(parsedUrl.getPath().contains("createListing")){
      return new AddListingProcessor();
    }
    if(parsedUrl.getPath().contains("deleteListing")){
      return new DeleteListingProcessor();
    }

    return null;
  }
}
