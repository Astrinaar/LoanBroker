package com.Main;

import com.Webservice.LoanRequestWebservice.MessageImpl;

import javax.xml.ws.Endpoint;

public class MessagePublisherWebservice {
    public static void main(String[] args) {
   	    Endpoint.publish("http://localhost:9999/ws/message", new MessageImpl());
       }
}
