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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sotof
 */
public class Aggregator extends Thread {
    private final static String QUEUE_NAME_RECEIVE = "aggregator";
    private final static String QUEUE_NAME_SEND = "finalReply";
    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    static List<ReplyObject> waitingList = new ArrayList<ReplyObject>(); // List of pending banks


    public static void main(String[] argv) throws IOException{

        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_RECEIVE);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    ReplyObject replyObject = (ReplyObject)StringByteHelper.fromByteArrayToObject(body);
                    if (waitingList.isEmpty()){waitingList.add(replyObject);
                               System.out.println("Object added to Array");}

                    else {
                        if(replyObject.getSsn().equals(waitingList.get(0).getSsn())){waitingList.add(replyObject);}
                    }

                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }

            
        };
        channel.basicConsume(QUEUE_NAME_RECEIVE, true, consumer);
        Thread t = new Thread(){
            public void run(){

                if (!waitingList.isEmpty()){
                ReplyObject finalReply = waitingList.get(0);

                for (ReplyObject reply: waitingList) {

                   int result = reply.getInterestRate().compareTo(finalReply.getInterestRate());
                   if (result>1) {finalReply = reply;}
                }

                try {
                    sendFinalReply(finalReply);
                    waitingList.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }}
        };

        while (true){
            try {
                t.start();
                t.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }




    }

    public static void sendFinalReply(ReplyObject replyObject) throws  IOException{
        RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_SEND);

        channel.basicPublish("",QUEUE_NAME_SEND, null, StringByteHelper.fromObjectToByteArray(replyObject));

        }


}
