package com.Main.BankRecievers;

import com.Model.LoanObject;
import com.Model.ReplyObject;
import com.Util.RabbitMQUtil;
import com.Util.StringByteHelper;
import com.rabbitmq.client.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class XMLReceiver {

    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    private final static String QUEUE_NAME_RECEIVE = "xmlReply4";
    private final static String QUEUE_NAME_SEND = "aggregator";

    public static void main(String[] argv) throws IOException {
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_RECEIVE);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                //try {
                    //LoanObject loanObject = (LoanObject) StringByteHelper.fromByteArrayToObject(body);
                    System.out.println(" [x] Received '" + body.toString() + "'");
                    ReplyObject replyObject = translate(body);
                    startSendToMQ(replyObject);
               // } catch (ClassNotFoundException e) {
                   // e.printStackTrace();
                   // return;
                //}
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

    public void translateXmlToObject(byte[] bytes) {

    }


    public static ReplyObject translate(byte[] body){
        String reply = new String(body, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(reply);
        int ssn = scanner.nextInt();
        BigDecimal interestRate = scanner.nextBigDecimal();
        scanner.skip(",");

        return new ReplyObject("bankXml", ssn, interestRate);
    }
}
