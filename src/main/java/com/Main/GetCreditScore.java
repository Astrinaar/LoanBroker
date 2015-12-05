package com.Main;

import com.Client.CreditScoreService.*;
import com.Util.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class GetCreditScore {
    private final static String Routing_Key = "SSN";
    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();

    public static void main(String[] argv) throws IOException {
        getSSNValue();
    }

    public static String getCreditScore(String ssn) {
        CreditScoreService_Service sis = new CreditScoreService_Service();
        		CreditScoreService si = sis.getCreditScoreServicePort();
        return String.valueOf(si.creditScore(ssn));
    }

    public static void getSSNValue() throws IOException {
        Connection connection = rabbitMQUtil.connectToRabbitMQ();
        Channel channel = connection.createChannel();
        channel.queueDeclare(Routing_Key, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                if(!message.equals("-1")) {
                    System.out.println(" [x] Received '" + message + "'");
                    sendToGetBanks(message);
                }
            }
        };
        channel.basicConsume(Routing_Key, true, consumer);
    }

    public static void sendToGetBanks(String ssn) throws IOException {
        Connection connection = rabbitMQUtil.connectToRabbitMQ();
        if (connection != null) {
            String returnedCreditScore = getCreditScore(String.valueOf(ssn));
            System.out.println(" [x] Send Credit Score " +returnedCreditScore+ " from SSN " + "'" + ssn + "'" +" to Group4.GetBanks");
            Channel channel = connection.createChannel();
            channel.queueDeclare("CreditScore", false, false, false,null);

            channel.exchangeDeclare("Group4.GetBanks","fanout");
            channel.basicPublish("", "CreditScore", null, returnedCreditScore.getBytes());
        }
    }


}
