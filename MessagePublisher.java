package Main;

import javax.xml.ws.Endpoint;

public class MessagePublisher {
    public static void main(String[] args) {
   	    Endpoint.publish("http://localhost:9999/ws/message", new MessageImpl());
       }
}
