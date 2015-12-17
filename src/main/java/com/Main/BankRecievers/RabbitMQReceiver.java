package com.Main.BankRecievers;

import com.Model.LoanObject;
import com.Model.ReplyObject;
import com.Util.RabbitMQUtil;
import com.Util.StringByteHelper;
import com.rabbitmq.client.*;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * Created by Sean Emerson on 17-12-2015.
 */
public class RabbitMQReceiver {

    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    private final static String QUEUE_NAME_RECEIVE = "rabbitMQReply4";
    private final static String QUEUE_NAME_SEND = "aggregator";

    public static void main(String[] argv) throws IOException {
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_RECEIVE);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    LoanObject loanObject = (LoanObject) StringByteHelper.fromByteArrayToObject(body);
                    System.out.println(" [x] Received '" + loanObject.toString() + "'");
                    ReplyObject replyObject = translate(body);
                    startSendToMQ(replyObject);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

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

    public static ReplyObject translate(byte[] body){
        String reply = new String(body, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(reply);
        int ssn = scanner.nextInt();
        BigDecimal interestRate = scanner.nextBigDecimal();
        scanner.skip(",");

        ReplyObject replyObject = new ReplyObject("RabbitMQ", ssn, interestRate);
        return replyObject;
    }
}
