import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import org.bson.types.ObjectId;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import static com.mongodb.client.model.Filters.eq;

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

    @OnWebSocketError
    public void error(Session session, Throwable error){
        // handles errors
        System.out.println(error.toString());
    }

    //helper methods
    private static void handleListing(String res) {
        //establish mongoConnection
        MongoClient mongoClient = new MongoClient("localhost",27017);

        MongoDatabase db = mongoClient.getDatabase("csc413finaldb");

        MongoCollection<Document> usersCollection  = db.getCollection  ("listings");

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

            //copies splits to a single size 2 array and then copies it to dataArray2
            for (int i = 0, x = 0; i<6; i++){
                pairHolder = dataArray1[i].split(":");
                System.arraycopy(pairHolder, 0, dataArray2,x,2);
                //ending
                x=x+2;
            }
            //dataArray2 odd index holds values (even holds keys)
            System.out.println("dataArray2" + Arrays.toString(dataArray2));
        }

        //default assignments
        desc = "";
        type = "";
        price = "";
        title = "";
        postId = "";
        postOption = "";

        //checks if json sent, then removes extra quotes
        if (res.contains("{")) {
            desc = dataArray2[1];
            desc = desc.replace("\"", "");
            type = dataArray2[3];
            type = type.replace("\"", "");
            price = dataArray2[5];
            price = price.replace("\"", "");
            title = dataArray2[7];
            title = title.replace("\"", "");
            postId = dataArray2[9];
            postId = postId.replace("\"", "");
            postOption = dataArray2[11];
            postOption = postOption.replace("\"", "");
            System.out.println("RES = " + res + "| postOption = " + postOption);
        }

        //checking postOption to see what the program needs to do
        if (res.contains("{")){
            switch (postOption) {
                case "create":
                    //send data and create json obj in MongoDB
                    Document doc = new Document("postId", postIdGenerator())
                            .append("desc", desc)
                            .append("type", type)
                            .append("price", price)
                            .append("title", title);

                    usersCollection.insertOne(doc);

                    break;
                case "edit":
                    //retrieve data and edit current listing with postId
                    //check if postId is not empty
                    if (!(postId.equals(""))) {

                    } else {
                        System.out.println("EDIT OPTION PROCCED: NO postId FOUND");
                    }

                    break;
                case "delete":
                    //delete listing with postId
                    //check if postId is not empty
                    if (!(postId.equals(""))) {
                        Bson filter = eq("postId", postId);
                        DeleteResult result = usersCollection.deleteOne(filter);
                        System.out.println(result);
                    } else {
                        System.out.println("DELETE OPTION PROCCED: NO postId FOUND");
                    }

                    break;
                default:
                    System.out.println("postOption ran through all options, end result is ELSE STATE");
                    break;
            }
        }

        //mongoTests
        //iterate over all documents in collection
        MongoCursor<Document> cursor = usersCollection.find().iterator();
        try {
            while (cursor.hasNext()) {
                //System.out.println("DOCUMENT PRINT: " + cursor.next().toJson());
                //returns add objectIds
                org.bson.Document nextDocument = cursor.next();
                try {
                    String d = nextDocument.get("_id").toString();
                    System.out.println("DOCUMENT ID: " + d);
                }
                catch(Exception e){
                    System.out.println("Iterator error: " + e);
                }
            }
        } finally {
            cursor.close();
        }
    }

    private static String postIdGenerator(){
        Random random = new Random();
        String postId = "";

        //
        for(int i = 0; i<10; i++){
            int randomInt = random.nextInt(10);
            //System.out.println("Random Int(should be single digit):"+ randomInt);
            postId = postId.concat(Integer.toString(randomInt));
        }

        return postId;
    }

}