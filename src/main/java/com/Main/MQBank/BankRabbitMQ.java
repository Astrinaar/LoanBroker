package com.Main.MQBank;


import com.Util.RabbitMQUtil;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * Created by Sean Emerson on 17-12-2015.
 */
public class BankRabbitMQ {

    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    private final static String EXCHANGE_NAME_RECEIVE = "bankRabbitMQ";

    public static void main(String[] argv) throws IOException {
        Channel channel = rabbitMQUtil.createExchange(EXCHANGE_NAME_RECEIVE);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME_RECEIVE, "");

               Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String EXCHANGE_NAME_SEND;
                System.out.println("Received");
                if(properties.getReplyTo() != null) {
                  EXCHANGE_NAME_SEND = properties.getReplyTo().intern();
                }
                else {EXCHANGE_NAME_SEND = EXCHANGE_NAME_RECEIVE; }



                String reply = new String(body, StandardCharsets.UTF_8);
                System.out.println(reply);

                String[] values = reply.split(",");

                String ssn = values[0];
                int creditScore = Integer.parseInt(values[1]);
                int loanAmount = Integer.parseInt(values[2]);
                int loanDuration = Integer.parseInt(values[3]);


                int interestRate = calcInterestRate(loanDuration,creditScore, loanAmount);

                String replyMessage = ssn + "," + "" + interestRate;

                startSendToMQ(EXCHANGE_NAME_SEND,replyMessage);
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

    public static void startSendToMQ(String channelName, String replyMessage) throws IOException {
        RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
        Channel channel = rabbitMQUtil.createQueue(channelName);


        System.out.println(channelName);
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
