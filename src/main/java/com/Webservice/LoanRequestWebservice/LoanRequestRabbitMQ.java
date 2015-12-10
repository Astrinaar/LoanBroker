package com.Webservice.LoanRequestWebservice;

import com.Util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class LoanRequestRabbitMQ {
    private final static String QUEUE_NAME_SEND = "loanRequest";
    public void startSendToMQ(String ssn, int loanAmount, int loanDurationInMonths) throws IOException {
        RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
                Connection connection = rabbitMQUtil.connectToRabbitMQ();
                Channel channel = null;
                if (connection != null) {
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME_SEND, false, false, false,null);
            //for (int i = 0; i < Integer.MAX_VALUE; i++) {
            System.out.println("Sending SSN "+ "'" + ssn + "'" +" To Group4.GetCreditScore");
            channel.exchangeDeclare("Group4.GetCreditScore","fanout");
            channel.basicPublish("", QUEUE_NAME_SEND, null, ssn.getBytes());
            try {
                channel.close();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
