package com.Main;

import com.Webservice.WebserviceBank.BankWebserviceImpl;

import javax.xml.ws.Endpoint;

public class BankWebservice {
    public static void main(String[] args) {
   	    Endpoint.publish("http://localhost:9996/ws/webservicebank", new BankWebserviceImpl());
       }
}
