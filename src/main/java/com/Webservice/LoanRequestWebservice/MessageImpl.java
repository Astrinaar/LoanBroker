package com.Webservice.LoanRequestWebservice;

import javax.jws.WebService;
import java.io.IOException;


@WebService(endpointInterface = "com.Webservice.LoanRequestWebservice.Message")
public class MessageImpl implements Message {

	@Override
	public void loanRequest(String ssn, int loanAmount, int loanDurationInMonths) {
        LoanRequestRabbitMQ loanRequestRabbitMQ = new LoanRequestRabbitMQ();
        try {
            loanRequestRabbitMQ.startSendToMQ(ssn, loanAmount, loanDurationInMonths);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
