import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
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

import javax.print.Doc;
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
        sendJsonListings();
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
        if(message.startsWith("admin ")) {
            handleListing(message);

            //grabbing mongo documents
            sendJsonListings();
        }
        if(message.startsWith("filter ")){
            
        }
        // trigger a broadcast
        //Gson gson = new Gson();
        //broadcast(gson.toJson(messages));
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
            dataArray1 = resCopy.split(",");

            //copies splits to a single size 2 array and then copies it to dataArray2
            for (int i = 0, x = 0; i<6; i++){
                pairHolder = dataArray1[i].split(":");
                System.arraycopy(pairHolder, 0, dataArray2,x,2);
                //ending
                x=x+2;
            }
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
        }

        //print incoming data to review
        System.out.println ("RESPONSE PARSED:\n" +
                "   Title: " + title + "\n" +
                "   Description: " + desc + "\n" +
                "   Price: " + price + "\n" +
                "   Bread Type: " + type + "\n" +
                "   PostId: " + postId + "\n" +
                "   Post Option: " + postOption);

        //checking postOption to see what the program needs to do
        if (res.contains("{")){
            switch (postOption) {
                case "create":
                    //send data and create json obj in MongoDB
                    Document doc = new Document("postId", postIdGenerator())
                            .append("desc", desc)
                            .append("type", type)
                            .append("price", price)
                            .append("title", title)
                            .append("ts", new Date());

                    usersCollection.insertOne(doc);

                    break;
                case "edit":
                    //retrieve data and edit current listing with postId
                    //check if postId is not empty
                    if (!(postId.equals(""))) {
                        usersCollection.updateOne(Filters.eq("postId",postId), Updates.set("desc", desc));
                        usersCollection.updateOne(Filters.eq("postId",postId), Updates.set("type", type));
                        usersCollection.updateOne(Filters.eq("postId",postId), Updates.set("price", price));
                        usersCollection.updateOne(Filters.eq("postId",postId), Updates.set("title", title));
                        usersCollection.updateOne(Filters.eq("postId",postId), Updates.set("ts", new Date()));
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
                    System.out.println("postOption ran through all cases, end result is DEFAULT CASE");
                    break;
            }
        }
        mongoClient.close();
    }

    private static String postIdGenerator(){
        Random random = new Random();
        String postId = "";

        for(int i = 0; i<10; i++){
            int randomInt = random.nextInt(10);
            postId = postId.concat(Integer.toString(randomInt));
        }
        while(checkPostIdExists(postId)){
            random = new Random();
            postId = "";

            for(int i = 0; i<10; i++){
                int randomInt = random.nextInt(10);
                postId = postId.concat(Integer.toString(randomInt));
            }
        }
        return postId;
    }
    private static boolean checkPostIdExists(String postId){
        MongoClient mongoClient = new MongoClient("localhost",27017);

        MongoDatabase db = mongoClient.getDatabase("csc413finaldb");

        MongoCollection<Document> usersCollection  = db.getCollection  ("listings");
        MongoCursor<Document> cursor = usersCollection.find().iterator();
        try {
            while (cursor.hasNext()){
                String nextJson = cursor.next().toJson();
                if(nextJson.contains(postId)){
                    mongoClient.close();
                    return true;
                }
            }
        } finally {
            cursor.close();
        }
        mongoClient.close();
        return false;
    }

    private static void sendJsonListings () {
        MongoClient mongoClient = new MongoClient("localhost",27017);

        MongoDatabase db = mongoClient.getDatabase("csc413finaldb");

        MongoCollection<Document> usersCollection  = db.getCollection  ("listings");

        //grabbing mongo documents
        MongoCursor<Document> cursor = usersCollection.find().iterator();
        try {
            while (cursor.hasNext()){
                String nextJson = cursor.next().toJson();
                System.out.println("Document Data: " + nextJson);
                broadcast(nextJson);
            }
        } finally {
            cursor.close();
        }
        mongoClient.close();
    }

}