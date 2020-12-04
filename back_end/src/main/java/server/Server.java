package server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import pointofsale.ListingService;

public class Server {

  public static void main(String[] args) {
    Server instance = new Server();
    instance.runServer();
  }

  public String processRequest(String request){
    int index, index2;
    index = request.indexOf("\n");
    index2 = request.indexOf("/");
    String url = "http://localhost:1299" + request.substring(index2, index-9);

    System.out.println(url);
    try {
      index = request.lastIndexOf("{");
      index2 = request.lastIndexOf("}");
      String body = request.substring(index, index2 + 1);
      System.out.println("X");
      return ListingService.getInstance().restApi(url, body);
    }
    catch(Exception e) {
      String body = "";
      return ListingService.getInstance().restApi(url, body);
    }

  }

  public void runServer() {
    ServerSocket ding;
    Socket socket = null;
    try {
      ding = new ServerSocket(1299);
      System.out.println("Opened socket " + 1299);
      while (true) {
        // keeps listening for new clients, one at a time
        try {
          socket = ding.accept(); // waits for client here
        } catch (IOException e) {
          System.out.println("Error opening socket");
          System.exit(1);
        }

        InputStream stream = socket.getInputStream();

        int c;
        String raw = "";
        do {
          c = stream.read();
          raw+=(char)c;
        } while(stream.available()>0);
        BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
        PrintWriter writer = new PrintWriter(out, true);  // char output to the client
        // every response will always have the status-line, date, and server name
        writer.println("HTTP/1.1 200 OK");
        writer.println("Server: TEST");
        writer.println("Connection: close");
        writer.println("Content-type: application/json");
        writer.println("");
        // response body
        writer.println(processRequest(raw));
        socket.close();
      }
    } catch (IOException e) {
      System.out.println("Error opening socket");
      System.exit(1);
    }
  }
}
