package com.Main;

import com.Banks.WebserviceBank.BankWebserviceImpl;

import javax.xml.ws.Endpoint;

public class BankWebservice {
    public static void main(String[] args) {
   	    Endpoint.publish("http://localhost:9997/ws/webservicebank", new BankWebserviceImpl());
       }
}
