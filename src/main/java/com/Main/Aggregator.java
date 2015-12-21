/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Main;

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
import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.*;

/**
 * @author Sotof
 */
public class Aggregator {
    private final static String QUEUE_NAME_RECEIVE = "aggregator";
    private final static String QUEUE_NAME_SEND = "finalReply";
    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    static List<ReplyObject> waitingList = new ArrayList<ReplyObject>();
    static Calendar time = new GregorianCalendar(); // List of pending banks
    static Long initialise;

    public static void main(String[] argv) throws IOException {

        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_RECEIVE);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    ReplyObject replyObject = (ReplyObject) StringByteHelper.fromByteArrayToObject(body);
                    if (waitingList.isEmpty()) {
                        waitingList.add(replyObject);
                        System.out.println("Object added to Array");
                        initialise = time.getTimeInMillis();
                    } else {
                        if (replyObject.getSsn().equals(waitingList.get(0).getSsn())) {
                            waitingList.add(replyObject);
                        }
                    }
                    if (!waitingList.isEmpty()) {
                        ReplyObject finalReply = waitingList.get(0);

                        if (initialise + 3000 <= time.getTimeInMillis()) {
                            System.out.println(""+time.getTime());


                        for (ReplyObject reply : waitingList) {

                            int result = reply.getInterestRate().compareTo(finalReply.getInterestRate());
                            if (result > 1) {
                                finalReply = reply;
                            }
                        }

                        try {
                            sendFinalReply(finalReply);
                            waitingList.clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }} catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
            };

        channel.basicConsume(QUEUE_NAME_RECEIVE, true, consumer);


    }

    public static void sendFinalReply(ReplyObject replyObject) throws IOException {
        RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_SEND);

        channel.basicPublish("", QUEUE_NAME_SEND, null, StringByteHelper.fromObjectToByteArray(replyObject));
        System.out.println("Object sent");

    }


}
