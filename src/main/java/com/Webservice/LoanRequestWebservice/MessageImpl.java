package com.Webservice.LoanRequestWebservice;

import com.Model.LoanObject;
import com.Model.ReplyObject;
import com.Util.RabbitMQUtil;
import com.Util.StringByteHelper;
import com.rabbitmq.client.*;
import org.json.JSONObject;

import javax.jws.WebService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;


@WebService(endpointInterface = "com.Webservice.LoanRequestWebservice.Message")
public class MessageImpl implements Message {

    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    private final static String QUEUE_NAME_RECEIVE = "jsonTranslator";
    private final static String QUEUE_NAME_SEND = "loanRequest";
    ReplyObject replyObject = null;


	@Override
	public ReplyObject loanRequest(String ssn, String loanAmount, String loanDurationInMonths) {
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_RECEIVE);
        try {

          startSendToMQ(ssn, loanAmount, loanDurationInMonths);

        } catch (IOException e) {
            e.printStackTrace();
        }
        while (replyObject == null){
            lookForReply();
        }

        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return replyObject;
    }

    public void startSendToMQ(String ssn, String loanAmount, String loanDurationInMonths) throws IOException {
        LoanObject loanObject = new LoanObject(ssn, null, loanAmount, loanDurationInMonths, null);
        RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();

        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_SEND);

        System.out.println(" [x] Sending SSN " + "'" + ssn + "'" + " To Group4.GetCreditScore");
        System.out.println(loanObject.toString());

        channel.basicPublish("", QUEUE_NAME_SEND, null, StringByteHelper.fromObjectToByteArray(loanObject));
        try {
            channel.close();
        } catch (TimeoutException e) {
            e.printStackTrace();

        }
    }

    public void lookForReply(){

        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_RECEIVE);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
try {
                replyObject = (ReplyObject) StringByteHelper.fromByteArrayToObject(body);


            } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };

        try {
            channel.basicConsume(QUEUE_NAME_RECEIVE, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

