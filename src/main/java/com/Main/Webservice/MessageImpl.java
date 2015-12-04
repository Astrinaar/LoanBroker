package com.Main.Webservice;

import javax.jws.WebService;
import java.io.IOException;


@WebService(endpointInterface = "com.Main.Webservice.Message")
public class MessageImpl implements Message {

	@Override
	public void loanRequest(int ssn, int loanAmount, int loanDurationInMonths) {
        LoanRequestRabbitMQ loanRequestRabbitMQ = new LoanRequestRabbitMQ();
        try {
            loanRequestRabbitMQ.startSendToMQ();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
