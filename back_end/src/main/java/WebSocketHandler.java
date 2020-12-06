import com.google.gson.Gson;
import org.bson.Document;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@WebSocket
public class WebSocketHandler {

    private static List<String> messages = new Vector<>();
    private static Map<Session, Session> sessionMap = new ConcurrentHashMap<>();


    public static void broadcast(String message){
        // loop through each active session
        sessionMap.keySet().forEach(session -> {
            try {
                session.getRemote().sendString(message);
            } catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    // Trigger when connection opens (AND STAYS OPEN)
    @OnWebSocketConnect
    public void connected(Session session) throws IOException {
        sessionMap.put(session, session); // remember all sessions
        System.out.println("A Client has connected.");
        Gson gson = new Gson();
        // test that we can see messages
        session.getRemote().sendString(gson.toJson(messages));
    }

    @OnWebSocketClose
    public void closed(Session session, int status, String reason){
        sessionMap.remove(session); // clear session
        System.out.println("A Client has disconnected.");

    }

    @OnWebSocketMessage
    public void message(Session session, String message){
        System.out.println("Client has sent: " + message);
        messages.add(message); //store message

        handleListing(message);
        // trigger a broadcast
        Gson gson = new Gson();
        broadcast(gson.toJson(messages));
    }

    //helper methods
    private static void handleListing(String res) {
        //listing variables
        String [] dataArray1;
        String [] dataArray2 = new String[12];
        String [] pairHolder;
        String resCopy;
        String desc, type, price, title, postId, postOption;


        //parsing res
        //checks if res contains '{' so as not to disturb other messages that aren't JSON
        if(res.contains("{")){
            resCopy = res.replace("{", "");
            resCopy = resCopy.replace("}", "");
            System.out.println("RES COPY:"+resCopy);
            dataArray1 = resCopy.split(",");
            System.out.println("dataArray1" + Arrays.toString(dataArray1));
            for (int i = 0, x = 0; i<6; i++){
                pairHolder = dataArray1[i].split(":");
                System.arraycopy(pairHolder, 0, dataArray2,x,2);
                //ending
                x=x+2;
            }
            System.out.println("dataArray2" + Arrays.toString(dataArray2));
        }




//        Document doc = new Document("description :","test")
//                .append("type :","test")
//                .append("price :","test")
//                .append("title :","test");
    }
}