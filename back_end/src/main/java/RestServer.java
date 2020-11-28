import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class RestServer {
    public static void main(String[] args){
        // react has a proxy from 3000 -> 1234

        port(1234);
        webSocket("/ws", WebSocketHandler.class);
        List<String> messages = new ArrayList<>();

        // handle /storeNote
        get("/storeNote", (req, res) -> {
            // http://localhost:3000/storeNote?note=weoijsdiojg
            String message = req.queryMap().get("note").value();
            messages.add(message);
            System.out.println("Storing "
                    + message + " Total " + messages.size());
            return "Stored " + message;
        });

        get("/getNotes", (req, res) -> {
            Gson gson = new Gson();
            return gson.toJson(messages);
        });

    }
}
