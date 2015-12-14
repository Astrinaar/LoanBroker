package com.Main;

import com.Client.CreditScoreService.*;
import com.Model.LoanObject;
import com.Util.RabbitMQUtil;
import com.Util.StringByteHelper;
import com.rabbitmq.client.*;

import java.io.IOException;

public class GetCreditScore {
    private final static String QUEUE_NAME_RECEIVE = "loanRequest";
    private final static String QUEUE_NAME_SEND = "creditScore";
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
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_RECEIVE);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    sendToGetBanks(StringByteHelper.fromByteArrayToObject(body));
                    System.out.println(" [x] Received body and converted array to loan object");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        channel.basicConsume(QUEUE_NAME_RECEIVE, true, consumer);
    }

    public static void sendToGetBanks(LoanObject loanObject) throws IOException {
        Connection connection = rabbitMQUtil.connectToRabbitMQ();
        if (connection != null) {
            String returnedCreditScore = getCreditScore(loanObject.getSsn());
            System.out.println(" [x] Send Credit Score " +returnedCreditScore+ " from SSN " + "'" + loanObject.getSsn() + "'" +" to Group4.GetBanks");
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME_SEND, false, false, false,null);

            loanObject.setCreditScore(returnedCreditScore);

            //channel.exchangeDeclare("Group4.GetBanks","fanout");
            channel.basicPublish("", QUEUE_NAME_SEND, null, StringByteHelper.fromObjectToByteArray(loanObject));
        }
    }


}
