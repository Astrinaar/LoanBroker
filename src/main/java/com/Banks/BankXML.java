package com.Banks;

import com.Util.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class BankXML {
    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    private final static String QUEUE_NAME_RECEIVE = "BankXML";
    public static void main(String[] argv) throws IOException {
        getLoanRequest("080889-8989" , "222", "1111", "10");

    }

    public static void getLoanRequest (String ssn, String creditScore, String loanAmount, String loanDuration)throws IOException {
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_RECEIVE);
        channel.exchangeDeclare("cphbusiness.bankXML", "fanout");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                if (!message.equals("-1")) {
                    System.out.println(" [x] Received '" + message + "'");
                }
            }
        };
        channel.basicConsume(QUEUE_NAME_RECEIVE, true, consumer);
    };
}
