package parser;

import java.util.HashMap;
import java.util.Map;

public class ParsedUrl {

  final String path;
  private Map<String,String> queryArgs = new HashMap<>();

  public ParsedUrl(String url){
    String s = url.substring(url.indexOf("/")+1, url.length());
    s = s.substring(s.indexOf("/")+1, s.length());
    s = s.substring(s.indexOf("/"), s.length());
    if(s.contains("?")){
      queryArgs.put(s.substring(s.indexOf("?")+1, s.indexOf("?")+2), s.substring(s.length()-1, s.length()));
      s = s.substring(0, s.indexOf("?"));
    }

    path = s;


  }

  public String getValue(String key){
    return queryArgs.get(key);
  }

  public boolean hasQueryArgs(){
    return !(queryArgs.isEmpty());
  }

  public String getPath() {
    return path;
  }
}
