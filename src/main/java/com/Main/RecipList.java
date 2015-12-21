package com.Main;

import com.Client.MessageClient.LoanRequest;
import com.Model.LoanObject;
import com.Util.RabbitMQUtil;
import com.Util.StringByteHelper;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class RecipList {

    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    private final static String QUEUE_NAME_RECEIVE = "recipListAndBanks";
    //private final static String QUEUE_NAME_SEND;

    public static void main(String[] argv) throws IOException {
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_RECEIVE);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    LoanObject loanObject = (LoanObject)StringByteHelper.fromByteArrayToObject(body);
                    System.out.println(" [x] Received '" + loanObject.toString() + "'");
                    List<String> banks = loanObject.getBanks();
                    for (String bank : banks) {
                        if (bank.equals("bankJson")) {
                            startSendToMQ("jsonTranslator", loanObject);
                        }

                        if (bank.equals("bankRabbitMQ")) {
                            startSendToMQ("bankRabbitMQTranslator", loanObject);
                        }

                        if (bank.equals("bankXml")) {
                            startSendToMQ("bankTranslator", loanObject);
                        }

                        if (bank.equals("bankWebservice")) {
                            startSendToMQ("webserviceBankTranslator", loanObject);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        channel.basicConsume(QUEUE_NAME_RECEIVE, true, consumer);
    }

    public static void startSendToMQ(String channelName, LoanObject loanObject) throws IOException {
        RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
        Channel channel = rabbitMQUtil.createQueue(channelName);

        channel.basicPublish("", channelName, null, StringByteHelper.fromObjectToByteArray(loanObject));
        try {
            channel.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
