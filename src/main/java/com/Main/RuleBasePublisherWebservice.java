package com.Main;

import com.Webservice.RuleBaseWebservice.RuleBaseImpl;

import javax.xml.ws.Endpoint;

public class RuleBasePublisherWebservice {
    public static void main(String[] args) {
   	    Endpoint.publish("http://localhost:9998/ws/rulebase", new RuleBaseImpl());
       }
}
