package com.Main;

import com.Client.RuleBaseClient.IOException_Exception;
import com.Client.RuleBaseClient.RuleBase;
import com.Client.RuleBaseClient.RuleBaseImplService;
import com.Model.LoanObject;
import com.Util.RabbitMQUtil;
import com.Util.StringByteHelper;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class GetBanks {
    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    private final static String QUEUE_NAME_RECEIVE = "creditScore";
    private final static String QUEUE_NAME_SEND = "recipListAndBanks";

    public static void main(String[] argv) throws IOException {
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_RECEIVE);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    LoanObject loanObject = (LoanObject)StringByteHelper.fromByteArrayToObject(body);
                    System.out.println(" [x] Received '" + loanObject.toString() + "'");
                    sendBankToRuleBase(loanObject);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        };
        channel.basicConsume(QUEUE_NAME_RECEIVE, true, consumer);
    }

    public static void sendBankToRuleBase(LoanObject loanObject) {
        RuleBaseImplService sis = new RuleBaseImplService();
        RuleBase si = sis.getRuleBaseImplPort();
        try {
            startSendToMQ(loanObject, si.getRuleBase(Integer.parseInt(loanObject.getCreditScore())));

        } catch (IOException_Exception e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startSendToMQ(LoanObject loanObject, List<String> banks) throws IOException {
        RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_SEND);
        loanObject.setBanks(banks);
        System.out.println(loanObject.toString() + "Sending to RecipList");
        channel.basicPublish("", QUEUE_NAME_SEND, null, StringByteHelper.fromObjectToByteArray(loanObject));
        try {
            channel.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
