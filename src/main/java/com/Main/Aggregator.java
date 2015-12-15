/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Main;

import com.Client.CreditScoreService.CreditScoreService;
import com.Client.CreditScoreService.CreditScoreService_Service;
import com.Model.LoanObject;
import com.Model.ReplyObject;
import com.Util.RabbitMQUtil;
import com.Util.StringByteHelper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

/**
 *
 * @author Sotof
 */
public class Aggregator {
    private final static String QUEUE_NAME_RECEIVE = "aggregator";
    private final static String QUEUE_NAME_SEND = "bestQuote";
    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();

    public static void main(String[] argv) throws IOException {
        getBestQuote();
    }

    public static void getBestQuote() throws IOException {
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_RECEIVE);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    ReplyObject replyObject = (ReplyObject)StringByteHelper.fromByteArrayToObject(body);
                    sendBestQuote(replyObject);
                    System.out.println(" [x] Received body and converted array to loan object");
                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        channel.basicConsume(QUEUE_NAME_RECEIVE, true, consumer);
    }

    public static void sendBestQuote(ReplyObject replyObject) throws IOException {
        Connection connection = rabbitMQUtil.connectToRabbitMQ();
        if (connection != null) {
            //String returnedCreditScore = getCreditScore(replyObject.getSsn());
            System.out.println(" [x] Received '" + replyObject);
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME_SEND, false, false, false,null);

            
            
            //channel.exchangeDeclare("Group4.GetBanks","fanout");
            channel.basicPublish("", QUEUE_NAME_SEND, null, StringByteHelper.fromObjectToByteArray(replyObject));
        }
    }
}
