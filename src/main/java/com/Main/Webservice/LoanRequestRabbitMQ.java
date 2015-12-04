package com.Main.Webservice;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class LoanRequestRabbitMQ {
    public void startSendToMQ() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("datdb.cphbusiness.dk");
        factory.setPort(5672);
        factory.setUsername("student");
        factory.setPassword("cph");
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        Channel channel = null;
        if (connection != null) {
            channel = connection.createChannel();
            String message = "Sean er en bedstefar";
            channel.queueDeclare("hello", false, false, false,null);
            //for (int i = 0; i < Integer.MAX_VALUE; i++) {
                channel.basicPublish("", "hello", null, message.getBytes());
    	    //}
            channel.exchangeDeclare("Group4.GetCreditScore","fanout");
        }
    }
}
