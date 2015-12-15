package com.Banks.WebserviceBank;

import javax.jws.WebService;


@WebService(endpointInterface = "com.Banks.WebserviceBank.BankWebservice")
public class BankWebserviceImpl implements BankWebservice {

	@Override
	public void getLoanRequest(String ssn, String creditScore, String loanAmount, String loanDuration) {
        System.out.println(ssn);

    }
}
