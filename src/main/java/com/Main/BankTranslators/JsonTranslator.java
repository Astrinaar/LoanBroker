package com.Main.BankTranslators;

import com.Model.LoanObject;
import com.Util.RabbitMQUtil;
import com.Util.StringByteHelper;
import com.rabbitmq.client.*;
import org.json.JSONObject;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Sean Emerson on 15-12-2015.
 */
public class JsonTranslator {
    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    private final static String QUEUE_NAME_RECEIVE = "jsonTranslator";
    private final static String EXCHANGE_NAME_SEND = "cphbusiness.bankJSON";

    public static void main(String[] argv) throws IOException {
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_RECEIVE);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    LoanObject loanObject = (LoanObject)StringByteHelper.fromByteArrayToObject(body);
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
        String stringSsn = loanObject.getSsn();
        stringSsn = stringSsn.replace("-","");
        System.out.println(stringSsn);
        int ssn = Integer.parseInt(stringSsn);
        JSONObject JSONBank = new JSONObject().put("ssn", ssn);
        JSONBank.put("creditScore", loanObject.getCreditScore());
        JSONBank.put("loanAmount", loanObject.getLoanAmount());
        JSONBank.put("loanDuration", loanObject.getLoanDuration());

        return JSONBank;

    }
    public static void startSendToMQ(JSONObject JSONBank) throws IOException {
        RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
        Channel channel = rabbitMQUtil.createExchange(EXCHANGE_NAME_SEND);

        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties().builder();
        properties.replyTo("jsonreply4");


        channel.basicPublish(EXCHANGE_NAME_SEND, "", properties.build(), JSONBank.toString().getBytes());
        try {
            channel.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
