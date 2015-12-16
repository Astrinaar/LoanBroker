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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Sotof
 */
public class Aggregator {
    private final static String QUEUE_NAME_RECEIVE = "aggregator";
    private final static String QUEUE_NAME_SEND = "bestQuote";
    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    static List<ReplyObject> waitingListPrime = new ArrayList<ReplyObject>(); // List of pending banks

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
                    List<ReplyObject> waitingList = getWaitingList();
                    
                    BigDecimal bd = new BigDecimal(0); // blaaaaaaaargh :(
                    ReplyObject iterator = new ReplyObject("",0,bd); // Duh
                    Date currentDatetime = new Date(); // If no SSN is in already we gotta put on a timestamp
                    boolean existsFlag = false; // Used to determine if SSN is already in
                    long currentDatetimeMilliseconds = currentDatetime.getTime(); // Used to check how time has passed
                    long pastDatetimeMilliseconds = 0; // Ditto
                    
                    
                    // First check if we got a matching SSN, AKA we already had
                    // one response for a user and thus need to compare which
                    // interest rate is better.
                    // Two ifs for clarity and because we need to make sure we
                    // don't make duplicate SSNs
                    for (int i = 0; i < waitingList.size(); i++) {
                        iterator = waitingList.get(i);
                        System.out.println("In 1");
                        System.out.println(iterator + "  " + replyObject);
                        if (iterator.getSsn() == replyObject.getSsn()){
                            System.out.println("In 2");
                            if (iterator.getIntrestRate().compareTo(replyObject.getIntrestRate()) < 0) {
                                System.out.println("In 3");
                                replyObject.setTimestamp(iterator.getTimestamp());
                                System.out.println(iterator + "  " + replyObject);
                                waitingList.remove(i);
                                waitingList.add(replyObject);
                            }
                            existsFlag = true;
                        }
                    }
                    
                    // Add SSN if it did not already exist
                    if (!existsFlag){
                       System.out.println("In X");
                       replyObject.setTimestamp(currentDatetimeMilliseconds);
                       waitingList.add(replyObject);
                    }
                    
                    // Run through the SSNs we've got and send out all those who
                    // exceed max wait time
                    for (int i = 0; i < waitingList.size(); i++) {
                        System.out.println("In Y");
                        iterator = waitingList.get(i);
                        System.out.println(iterator);
                        pastDatetimeMilliseconds = iterator.getTimestamp();
                        if(pastDatetimeMilliseconds + 60000 > currentDatetimeMilliseconds
                        && pastDatetimeMilliseconds != currentDatetimeMilliseconds) {
                            sendBestQuote(replyObject);
                            waitingList.remove(i);
                        }
                    }
                    
                    System.out.println(" [x] Received body and added to list" + currentDatetimeMilliseconds + "  " + pastDatetimeMilliseconds);
                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        channel.basicConsume(QUEUE_NAME_RECEIVE, true, consumer);
    }

    
    public static List<ReplyObject> getWaitingList () {
        return waitingListPrime;
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
