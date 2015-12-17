package com.Banks.WebserviceBank;

import com.Model.LoanObject;
import com.Util.RabbitMQUtil;
import com.Util.StringByteHelper;
import com.rabbitmq.client.*;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * Created by Sean Emerson on 17-12-2015.
 */
public class BankRabbitMQ {

    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    private final static String EXCHANGE_NAME_RECEIVE = "RabbitMQTranslator";

    public static void main(String[] argv) throws IOException {
        Channel channel = rabbitMQUtil.createQueue(EXCHANGE_NAME_RECEIVE);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String EXCHANGE_NAME_SEND;
                if(properties.getReplyTo() != null) {
                  EXCHANGE_NAME_SEND = properties.getReplyTo().intern();
                    System.out.println("Received");
                }
                else {EXCHANGE_NAME_SEND = EXCHANGE_NAME_RECEIVE; }



                String reply = new String(body, StandardCharsets.UTF_8);
                Scanner scanner = new Scanner(reply);
                int ssn1 = scanner.nextInt();
                int ssn2 = scanner.nextInt();
                int creditScore = scanner.nextInt();
                int loanAmount = scanner.nextInt();
                int loanDuration = scanner.nextInt();
                scanner.skip(",");
                scanner.skip("-");

                String ssn = "" + ssn1 + ssn2;
                int interestRate = calcInterestRate(loanDuration,creditScore, loanAmount);

                String replyMessage = ""+ ssn + "," + interestRate;

                startSendToMQ(EXCHANGE_NAME_SEND,replyMessage);
            }
        };
        channel.basicConsume(EXCHANGE_NAME_RECEIVE, true, consumer);
    }

    public static void startSendToMQ(String channelName, String replyMessage) throws IOException {
        RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
        Channel channel = rabbitMQUtil.createQueue(channelName);



        channel.basicPublish("", channelName, null, replyMessage.getBytes());
        try {
            channel.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static int calcInterestRate(int duration, int creditScore, int amount){
       int interestRate = creditScore * amount / duration / 1000;
        return interestRate;
    }

}
