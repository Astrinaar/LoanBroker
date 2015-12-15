package com.Main.BankRecievers;

import com.Model.LoanObject;
import com.Model.ReplyObject;
import com.Util.RabbitMQUtil;
import com.Util.StringByteHelper;
import com.rabbitmq.client.*;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by Sean Emerson on 15-12-2015.
 */
public class JSONReceiver {


    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    private final static String QUEUE_NAME_RECEIVE = "jsonreply4";
    private final static String QUEUE_NAME_SEND = "aggregator";

    public static void main(String[] argv) throws IOException {
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_RECEIVE);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {

                    String reply = new String(body, StandardCharsets.UTF_8);
                    System.out.println(" [x] Received '" + reply);
                JSONObject jsonObj = new JSONObject(reply);
                ReplyObject replyObject = new ReplyObject("jsonBank",jsonObj.getInt("ssn"),jsonObj.getBigDecimal("interestRate"));
                startSendToMQ(replyObject);

            }
        };
        channel.basicConsume(QUEUE_NAME_RECEIVE, true, consumer);
    }
    public static void startSendToMQ(ReplyObject replyObject) throws IOException {
        RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_SEND);

        channel.basicPublish("", QUEUE_NAME_SEND, null, StringByteHelper.fromObjectToByteArray(replyObject));
        try {
            channel.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}

