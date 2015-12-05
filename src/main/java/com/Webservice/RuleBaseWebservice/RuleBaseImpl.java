package com.Webservice.RuleBaseWebservice;

import javax.jws.WebService;


@WebService(endpointInterface = "com.Webservice.RuleBaseWebservice.RuleBase")
public class RuleBaseImpl implements RuleBase {

	@Override
	public void getRuleBase(int ssn) {
        System.out.println(ssn);
/*        LoanRequestRabbitMQ loanRequestRabbitMQ = new LoanRequestRabbitMQ();
        try {
            loanRequestRabbitMQ.startSendToMQ();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
