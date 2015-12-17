package com.Main.BankTranslators;

import com.Model.LoanObject;
import com.Util.RabbitMQUtil;
import com.Util.StringByteHelper;
import com.rabbitmq.client.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by Sean Emerson on 17-12-2015.
 */
public class RabbitMQTranslator {

    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    private final static String QUEUE_NAME_RECEIVE = "bankRabbitMQTranslator";
    private final static String EXCHANGE_NAME_SEND = "bankRabbitMQ";

    public static void main(String[] argv) throws IOException {
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_RECEIVE);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    LoanObject loanObject = (LoanObject) StringByteHelper.fromByteArrayToObject(body);
                    System.out.println(" [x] Received '" + loanObject.toString() + "'");
                    startSendToMQ(loanObject);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        channel.basicConsume(QUEUE_NAME_RECEIVE, true, consumer);
    }

    public static void startSendToMQ(LoanObject loanObject) throws IOException {
        RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
        Channel channel = rabbitMQUtil.createExchange(EXCHANGE_NAME_SEND);

        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties().builder();
        properties.replyTo("rabbitMQreply4");

        String message = "" + loanObject.getSsn()
                + "," + loanObject.getCreditScore()
                + "," + loanObject.getLoanAmount()
                + "," + loanObject.getLoanDuration();

        channel.basicPublish(EXCHANGE_NAME_SEND, "", properties.build(), message.getBytes());
        try {
            channel.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
