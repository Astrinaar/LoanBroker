package com.Webservice.LoanRequestWebservice;

import com.Model.LoanObject;
import com.Util.RabbitMQUtil;
import com.Util.StringByteHelper;
import com.rabbitmq.client.Channel;

import javax.jws.WebService;
import java.io.IOException;
import java.util.concurrent.TimeoutException;


@WebService(endpointInterface = "com.Webservice.LoanRequestWebservice.Message")
public class MessageImpl implements Message {

    private final static String QUEUE_NAME_SEND = "loanRequest";

	@Override
	public void loanRequest(String ssn, String loanAmount, String loanDurationInMonths) {
        try {

          startSendToMQ(ssn, loanAmount, loanDurationInMonths);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startSendToMQ(String ssn, String loanAmount, String loanDurationInMonths) throws IOException {
        LoanObject loanObject = new LoanObject(ssn,null, loanAmount, loanDurationInMonths, null);
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
}
