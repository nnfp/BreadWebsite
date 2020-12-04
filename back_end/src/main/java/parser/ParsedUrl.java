package parser;

import java.util.HashMap;
import java.util.Map;

public class ParsedUrl {

  final String path;
  private Map<String,String> queryArgs = new HashMap<>();

  public ParsedUrl(String url){
    String[] parts = url.split("\\?");
    if(parts.length == 2){
      System.out.println(parts[1]);
      String[] queryParts = parts[1].split("&");
      for (int i = 0; i < queryParts.length; i++) {
        String[] pair = queryParts[i].split("=");
        queryArgs.put(pair[0], pair[1]);
      }
    }
    String[] hostParts = parts[0].split(":");
    path = hostParts[2].substring(hostParts[2].indexOf("/"));
  }

  public String getValue(String key){
    return queryArgs.get(key);
  }

  public boolean hasQueryArgs(){
    return !queryArgs.isEmpty();
  }

  public String getPath() {
    return path;
  }
}
