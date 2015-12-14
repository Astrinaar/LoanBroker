package com.Webservice.LoanRequestWebservice;

import com.Model.LoanObject;
import com.Util.RabbitMQUtil;
import com.Util.StringByteHelper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class LoanRequestRabbitMQ {
    private final static String QUEUE_NAME_SEND = "loanRequest";

    public void startSendToMQ(String ssn, String loanAmount, String loanDurationInMonths) throws IOException {
        LoanObject loanObject = new LoanObject(ssn, loanAmount, loanDurationInMonths);
        RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_SEND);
        //for (int i = 0; i < Integer.MAX_VALUE; i++) {
        System.out.println(" [x] Sending SSN " + "'" + ssn + "'" + " To Group4.GetCreditScore");
        //channel.exchangeDeclare("Group4.GetCreditScore","fanout");

        channel.basicPublish("", QUEUE_NAME_SEND, null, StringByteHelper.fromObjectToByteArray(loanObject));
        try {
            channel.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
