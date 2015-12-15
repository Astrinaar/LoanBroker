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
 * Created by Sean Emerson on 15-12-2015.
 */
public class JsonTranslator {
    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    private final static String QUEUE_NAME_RECEIVE = "jsonTranslator";
    private final static String QUEUE_NAME_SEND = "bank";

    public static void main(String[] argv) throws IOException {
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_RECEIVE);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    LoanObject loanObject = StringByteHelper.fromByteArrayToObject(body);
                    System.out.println(" [x] Received '" + loanObject.toString() + "'");
                    JSONObject loanRequestJSON = JSONTranslator(loanObject);
                    startSendToMQ(loanRequestJSON);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


            }
        };
        channel.basicConsume(QUEUE_NAME_RECEIVE, true, consumer);
    }

    public static JSONObject JSONTranslator (LoanObject loanObject) {

        JSONObject JSONBank = new JSONObject().put("ssn", loanObject.getSsn());
        JSONBank.put("creditScore", loanObject.getCreditScore());
        JSONBank.put("loanAmount", loanObject.getLoanAmount());
        JSONBank.put("loanDuration", loanObject.getLoanDuration());

        return JSONBank;

    }
    public static void startSendToMQ(JSONObject JSONBank) throws IOException {
        RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_SEND);


        channel.basicPublish("", QUEUE_NAME_SEND, null, JSONBank.toString().getBytes());
        try {
            channel.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
