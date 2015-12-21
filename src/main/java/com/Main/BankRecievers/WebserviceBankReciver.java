package com.Main.BankRecievers;

import com.Client.WebserviceBankClient.BankWebservice;
import com.Client.WebserviceBankClient.BankWebserviceImplService;
import com.Client.WebserviceBankClient.IOException_Exception;
import com.Model.LoanObject;
import com.Model.ReplyObject;
import com.Util.RabbitMQUtil;
import com.Util.StringByteHelper;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class WebserviceBankReciver {

    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    private final static String QUEUE_NAME_RECEIVE = "webserviceBank4";
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
                    ReplyObject replyObject = getCreditScoreFromWebserviceBank(loanObject);
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

    public static ReplyObject getCreditScoreFromWebserviceBank(LoanObject loanObject) {
        BankWebserviceImplService sis = new BankWebserviceImplService();
        BankWebservice si = sis.getBankWebserviceImplPort();
        try {
            BigDecimal interestRate = si.getInterestRate(loanObject.getSsn(),loanObject.getCreditScore(),loanObject.getLoanAmount(),loanObject.getLoanDuration());
            return new ReplyObject("bankWebservice",Integer.getInteger(loanObject.getSsn()),interestRate);
        } catch (IOException_Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
